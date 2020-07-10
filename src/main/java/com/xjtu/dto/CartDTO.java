package com.xjtu.dto;

import lombok.Data;

/**
 * Created by 10270 on 2020/7/10.
 */
@Data
public class CartDTO {

    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
