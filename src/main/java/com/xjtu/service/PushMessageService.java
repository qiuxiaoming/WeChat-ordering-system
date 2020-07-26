package com.xjtu.service;

import com.xjtu.dto.OrderDTO;

/**
 * 推送消息
 * Created by 10270 on 2020/7/26.
 */
public interface PushMessageService {

    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void OrderStatus(OrderDTO orderDTO);
}
