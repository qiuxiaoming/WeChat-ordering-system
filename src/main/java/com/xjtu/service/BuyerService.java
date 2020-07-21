package com.xjtu.service;

import com.xjtu.dto.OrderDTO;

/**
 * Created by 10270 on 2020/7/21.
 */
public interface BuyerService {

    public OrderDTO findOrderOne(String openid,String orderId);

    public OrderDTO cancelOrder(String openid,String orderId);
}
