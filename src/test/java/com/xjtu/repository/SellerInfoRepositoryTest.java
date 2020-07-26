package com.xjtu.repository;

import com.xjtu.dataobject.SellerInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import utils.KeyUtil;

import static org.junit.Assert.*;

/**
 * Created by 10270 on 2020/7/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {
    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Test
    public void save() throws Exception{
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.getUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");
        SellerInfo result = sellerInfoRepository.save(sellerInfo);
        assertNotNull(result);
    }




    @Test
    public void findByOpenId() throws Exception {
        SellerInfo sellerInfo = sellerInfoRepository.findByOpenid("abc");
        assertEquals("abc",sellerInfo.getOpenid());
    }

}