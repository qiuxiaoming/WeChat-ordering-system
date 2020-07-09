package com.xjtu.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Created by 10270 on 2020/7/9.
 */
@Entity
@Data
public class ProductInfo {

    @Id
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

    //状态0正常1下架
    private Integer productStatus;

    //类目标号
    private Integer categoryType;


}
