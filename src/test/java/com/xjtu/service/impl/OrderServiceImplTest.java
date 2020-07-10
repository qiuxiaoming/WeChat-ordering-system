package com.xjtu.service.impl;

import com.xjtu.dataobject.OrderDetail;
import com.xjtu.dataobject.OrderMaster;
import com.xjtu.dto.OrderDTO;
import com.xjtu.enums.OrderStatusEnum;
import com.xjtu.enums.PayStatusEnum;
import com.xjtu.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 10270 on 2020/7/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "110110";

    private final String ORDER_ID = "1594371009869309528";



    @Test
    public void createOrder() throws Exception {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("北京");
        orderDTO.setBuyerName("小马哥");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        orderDTO.setBuyerPhone("156325632323");

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123457");
        orderDetail.setProductQuantity(2);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);
        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderService.createOrder(orderDTO);
        assertNotNull(result);

    }

    @Test
    public void findOrder() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        log.info("查询订单，orderDTO ：{}",orderDTO);
        assertEquals(ORDER_ID,orderDTO.getOrderId());
    }

    @Test
    public void findList() throws Exception {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID,pageRequest);
        log.info("查询订单数目：{}",orderDTOPage.getTotalElements());
        assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        log.info("取消订单，orderDTO ：{}",result);
        assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());

    }

    @Test
    public void finish() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        log.info("完结订单，orderDTO ：{}",result);
        assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() throws Exception {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        log.info("支付订单，orderDTO ：{}",result);
        assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }

}