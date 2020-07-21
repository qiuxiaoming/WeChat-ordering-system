package com.xjtu.controller;

import com.xjtu.VO.ProductInfoVO;
import com.xjtu.VO.ProductVO;
import com.xjtu.VO.ResultVO;
import com.xjtu.dataobject.ProductCategory;
import com.xjtu.dataobject.ProductInfo;
import com.xjtu.service.CategoryService;
import com.xjtu.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.ResultVOUtils;

import java.beans.Beans;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 10270 on 2020/7/9.
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProducterController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public ResultVO list(){
        //1.查询所有商家的商品
        List<ProductInfo> productInfoList = productService.findUPAll();

        //2.查询类目（一次性查询）
        List<Integer> categoryList = new ArrayList<>();
        for (ProductInfo productInfo : productInfoList) {
            categoryList.add(productInfo.getCategoryType());
        }
        List<ProductCategory> upProductCategoryList = categoryService.findByCategoryTypeIn(categoryList);



        //3.数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory: upProductCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productVO.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);

        }

        return ResultVOUtils.success(productVOList);
    }
}
