package com.xjtu.service.impl;

import com.xjtu.dto.OrderDTO;
import com.xjtu.enums.ResultEnum;
import com.xjtu.exception.SellException;
import com.xjtu.service.BuyerService;
import com.xjtu.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.ResultVOUtils;

import javax.security.sasl.SaslException;

/**
 * Created by 10270 on 2020/7/21.
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;


    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid,orderId);
        return orderDTO;
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid,orderId);
        if (orderDTO == null){
            log.error("【取消订单】 查询不到订单 orderId = {}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId){
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null){
            return null;
        }

        //判断是否是自己的订单
        if(!openid.equals(orderDTO.getBuyerOpenid())){
            log.error("【查询订单】 订单的openId不一致，openId={},orderDto={}",openid,orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
