package com.xjtu.service;

import com.xjtu.dataobject.ProductCategory;

import java.util.List;

/**
 * Created by 10270 on 2020/7/9.
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList);

    ProductCategory save(ProductCategory productCategory);
}
