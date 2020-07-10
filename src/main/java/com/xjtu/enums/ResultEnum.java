package com.xjtu.enums;

import lombok.Getter;

/**
 * Created by 10270 on 2020/7/10.
 */
@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(3,"商品库存不正确")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
