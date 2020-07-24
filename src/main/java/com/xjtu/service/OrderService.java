package com.xjtu.service;

import com.xjtu.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 10270 on 2020/7/10.
 */

public interface OrderService {

    //创建订单
    OrderDTO createOrder(OrderDTO orderDTO);

    //查询某个订单
    OrderDTO findOne(String orderId);

    //查询订单列表
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    //取消订单
    OrderDTO cancel(OrderDTO orderDTO);

    //完结订单
    OrderDTO finish(OrderDTO orderDTO);

    //支付订单
    OrderDTO paid(OrderDTO orderDTO);

    ///查询订单列表
    Page<OrderDTO> findList(Pageable pageable);

}
