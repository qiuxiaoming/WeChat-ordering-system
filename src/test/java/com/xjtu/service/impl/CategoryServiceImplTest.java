package com.xjtu.service.impl;

import com.xjtu.dataobject.ProductCategory;
import com.xjtu.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 10270 on 2020/7/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void findOne() throws Exception {
        ProductCategory productCategory = categoryService.findOne(1);
        assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> productCategories = categoryService.findAll();
        assertNotEquals(0,productCategories.size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<Integer> categoryTypeList = new ArrayList<Integer>();
        categoryTypeList.add(1);
        categoryTypeList.add(4);
        List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(categoryTypeList);
        assertNotEquals(0,productCategories.size());
    }

    @Test
    @Transactional
    public void save() throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("专享");
        productCategory.setCategoryType(12);
        ProductCategory result = categoryService.save(productCategory);
        assertNotNull(result);
    }

}