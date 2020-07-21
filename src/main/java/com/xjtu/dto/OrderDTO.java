package com.xjtu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xjtu.dataobject.OrderDetail;
import com.xjtu.enums.OrderStatusEnum;
import com.xjtu.enums.PayStatusEnum;
import lombok.Data;
import utils.serializer.Date2LongSerialzer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 10270 on 2020/7/10.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private String orderId;

    //买家名字
    private String buyerName;

    //买家电话
    private String buyerPhone;

    //买家地址
    private String buyerAddress;

    //买家微信openid
    private String buyerOpenid;

    //订单总金额
    private BigDecimal orderAmount;

    //订单状态，默认0新下单
    private Integer orderStatus ;

    //支付状态，默认0未支付
    private Integer payStatus ;

    //创建时间
    @JsonSerialize(using = Date2LongSerialzer.class)
    private Date createTime;

    //修改时间
    @JsonSerialize(using = Date2LongSerialzer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
