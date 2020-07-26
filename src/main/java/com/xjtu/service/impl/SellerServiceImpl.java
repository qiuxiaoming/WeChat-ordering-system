package com.xjtu.service.impl;

import com.xjtu.dataobject.SellerInfo;
import com.xjtu.repository.SellerInfoRepository;
import com.xjtu.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 10270 on 2020/7/25.
 */

@Service
public class SellerServiceImpl implements SellerService{

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {

        SellerInfo sellerInfo = sellerInfoRepository.findByOpenid(openid);

        return sellerInfo;
    }
}
