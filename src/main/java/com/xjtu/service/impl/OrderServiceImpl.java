package com.xjtu.service.impl;

import com.xjtu.converter.OrderMaster2OrderDTOConverter;
import com.xjtu.dataobject.OrderDetail;
import com.xjtu.dataobject.OrderMaster;
import com.xjtu.dataobject.ProductInfo;
import com.xjtu.dto.CartDTO;
import com.xjtu.dto.OrderDTO;
import com.xjtu.enums.OrderStatusEnum;
import com.xjtu.enums.PayStatusEnum;
import com.xjtu.enums.ResultEnum;
import com.xjtu.exception.SellException;
import com.xjtu.repository.OrderDetailRepository;
import com.xjtu.repository.OrderMasterRepository;
import com.xjtu.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import utils.KeyUtil;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 10270 on 2020/7/10.
 */
@Service
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayService payService;

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {

        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        List<CartDTO> cartDTOList = new ArrayList<CartDTO>();

        String orderId = KeyUtil.getUniqueKey();
        //1.查询商品数量，价格
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2.计算订单总价
            orderAmount = productInfo.getProductPrice().
                    multiply(new BigDecimal(orderDetail.getProductQuantity())).
                    add(orderAmount);

            //3.写入订单数据库(orderMaster和OrderDetail)
            //3.1订单详情入库
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);

            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);
        }

        //3.2订单主表入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());


        orderMasterRepository.save(orderMaster);

        //4.扣库存
//        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().mapper(e->
//            new CartDTO(e.getProductId(),e.getProductQuantity())).
//                collect(Collectors.toList());
       productService.decreaseStock(cartDTOList);

       //发送websocket消息
        webSocket.sendMessage(orderDTO.getOrderId());

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList.isEmpty()){
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】：订单状态不正确，orderId:{}, orderStatus:{}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null){
            log.error("【取消订单】：更新失败,orderMaster:{}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】：订单无商品详情,orderDTO:{}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().
                map( e -> new CartDTO(e.getProductId(),e.getProductQuantity())).
                collect(Collectors.toList());

        productService.increaseStock(cartDTOList);

        //如果已支付，需要退款
        if (orderDTO.getOrderStatus().equals(OrderStatusEnum.FINISHED.getCode())){
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!OrderStatusEnum.NEW.getCode().equals(orderDTO.getOrderStatus())){
            log.error("【完结订单】，订单状态不正确：orderId:{}, orderStatus:{}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result ==null){
            log.error("【完结订单】：更新失败,orderMaster:{}",result);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //推送微信模板消息
        pushMessageService.OrderStatus(orderDTO);

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {

        //判断订单状态
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!OrderStatusEnum.NEW.getCode().equals(orderDTO.getOrderStatus())){
            log.error("【支付订单】，订单状态不正确：orderId:{}, orderStatus:{}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if(!PayStatusEnum.WAIT.getCode().equals(orderDTO.getPayStatus())){
            log.error("【支付订单】，订单支付状态不正确：orderDTO:{}, payStatus:{}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result ==null){
            log.error("【完结订单】：更新失败,orderMaster:{}",result);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }
}
