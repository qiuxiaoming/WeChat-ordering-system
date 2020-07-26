package com.xjtu.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 卖家信息
 * Created by 10270 on 2020/7/25.
 */

@Entity
@Data
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    //微信openid
    private String openid;
}
