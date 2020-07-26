package com.xjtu.service.impl;

import com.xjtu.dto.OrderDTO;
import com.xjtu.service.OrderService;
import com.xjtu.service.PushMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by 10270 on 2020/7/26.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private OrderService orderService;

    @Test
    public void orderStatus() throws Exception {

        OrderDTO orderDTO = orderService.findOne("1594371009869309528");
        pushMessageService.OrderStatus(orderDTO);

    }

}