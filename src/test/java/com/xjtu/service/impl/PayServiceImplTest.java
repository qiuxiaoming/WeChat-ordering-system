package com.xjtu.service.impl;

import com.lly835.bestpay.model.RefundResponse;
import com.xjtu.dto.OrderDTO;
import com.xjtu.service.OrderService;
import com.xjtu.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by 10270 on 2020/7/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    public void create() throws Exception {

        OrderDTO orderDTO = orderService.findOne("1595326863930466997");
        payService.create(orderDTO);

    }

    @Test
    public void refund(){

        OrderDTO orderDTO = orderService.findOne("1595427130656621022");
        payService.refund(orderDTO);
    }

}