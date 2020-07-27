package com.xjtu.repository;

import com.xjtu.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 10270 on 2020/7/9.
 * 测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void testfindOne(){
        ProductCategory  productCategory=repository.findById(1).orElse(null);
        System.out.println(productCategory.toString());
    }

    @Test
    public void testSave(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(2);
        productCategory.setCategoryName("女生最爱");
        productCategory.setCategoryType(3);
        ProductCategory productCategory1 = repository.save(productCategory);
        assertNotNull(null,productCategory1);

    }
    @Test
    public void testFindByCategoryTypeIn(){
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(3);
        List<ProductCategory> categories = repository.findByCategoryTypeIn(list);
        System.out.println(categories);
        assertNotEquals(0,categories.size());
    }
}