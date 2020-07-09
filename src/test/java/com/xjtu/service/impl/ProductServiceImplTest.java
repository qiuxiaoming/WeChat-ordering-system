package com.xjtu.service.impl;

import com.xjtu.dataobject.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 10270 on 2020/7/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl service;

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo = service.findOne("123456");
        assertNotNull(productInfo);
    }

    @Test
    public void findUPAll() throws Exception {
        List<ProductInfo> productInfoList = service.findUPAll();
        assertNotEquals(0,productInfoList);
    }

    @Test
    public void findAll() throws Exception {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<ProductInfo> productInfoPage = service.findAll(pageRequest);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(1562000);
        productInfo.setProductDescription("很好吃的皮皮虾");
        productInfo.setProductIcon("http://xxxxxx");
        productInfo.setProductStatus(1);
        productInfo.setCategoryType(1);
        ProductInfo result = service.save(productInfo);
        assertNotNull(result);
    }

}