package com.xjtu.service;

import com.xjtu.dataobject.SellerInfo;

/**
 * Created by 10270 on 2020/7/25.
 */
public interface SellerService {

    public SellerInfo findSellerInfoByOpenid(String openid);
}
