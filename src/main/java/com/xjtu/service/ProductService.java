package com.xjtu.service;

import com.xjtu.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by 10270 on 2020/7/9.
 */
public interface ProductService {

    ProductInfo findOne(String productId);

    //查询在架商品列表
    List<ProductInfo> findUPAll();

    //查询所有商品
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

}
