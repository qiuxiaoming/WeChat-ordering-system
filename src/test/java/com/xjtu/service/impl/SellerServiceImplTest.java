package com.xjtu.service.impl;

import com.xjtu.dataobject.SellerInfo;
import com.xjtu.service.SellerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by 10270 on 2020/7/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {

    @Autowired
    private SellerService sellerService;

    private static final String OPENID = "abc";

    @Test
    public void findSellerInfoByOpenid() throws Exception {
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(OPENID);
        assertEquals(OPENID,sellerInfo.getOpenid());

    }

}