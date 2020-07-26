package com.xjtu.dataobject.mapper;

import com.xjtu.SellApplication;
import com.xjtu.config.WebSocketConfig;
import com.xjtu.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by 10270 on 2020/7/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SellApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;


    @Test
    public void insertByMap() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("categoryName","最近热门小吃1");
        map.put("categoryType",7);
        int result = productCategoryMapper.insertByMap(map);
        assertEquals(1,result);

    }

    @Test
    public void insertByObject() throws Exception{
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("最近热门小吃2");
        productCategory.setCategoryType(2);
        int result = productCategoryMapper.insertByObject(productCategory);
        assertEquals(1,result);
    }

    @Test
    public void findByCategoryType() throws Exception{
        ProductCategory productCategory = productCategoryMapper.findByCategoryType(2);
        assertEquals("最近热门小吃2",productCategory.getCategoryName());
    }

    @Test
    public void findByCatepgoryName() throws Exception{
        List<ProductCategory> categoryList = productCategoryMapper.findByCategoryName("最近热门小吃");
        assertEquals(2,categoryList.size());
    }

    @Test
    public void updateByProductCategory() throws Exception{
        int result = productCategoryMapper.updateByProductCategory("小吃",7);
        assertNotNull(result);
    }

    @Test
    public void updateByProductObject() throws Exception{
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("最近热门小吃1");
        productCategory.setCategoryType(7);
        int result = productCategoryMapper.updateByProductObject(productCategory);
        assertEquals(1,result);
    }

    @Test
    public void deleteByCategory() throws Exception{
        int result = productCategoryMapper.deleteByCategory(7);
        assertNotNull(result);
    }

    @Test
    public void selectByCategoryType() throws Exception{
        ProductCategory productCategory = productCategoryMapper.selectByCategoryType(6);
        assertNotNull(productCategory);
    }
}