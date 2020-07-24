package com.xjtu.enums;

import lombok.Getter;

/**
 * Created by 10270 on 2020/7/10.
 */
@Getter
public enum OrderStatusEnum implements CodeEnum{
    NEW(0,"新下单"),
    FINISHED(1,"完结"),
    CANCEL(2,"已取消"),
    ;

    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
