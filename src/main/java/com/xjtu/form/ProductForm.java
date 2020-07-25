package com.xjtu.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 10270 on 2020/7/25.
 */
@Data
public class ProductForm {

    private String productId;

    //名字
    private String productName;

    //单价
    private BigDecimal productPrice;

    //库存
    private Integer productStock;

    //描述
    private String productDescription;

    //小图
    private String productIcon;

    //类目标号
    private Integer categoryType;
}
