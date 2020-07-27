package com.xjtu.controller;

import com.xjtu.dataobject.ProductCategory;
import com.xjtu.dataobject.ProductInfo;
import com.xjtu.enums.ResultEnum;
import com.xjtu.exception.SellException;
import com.xjtu.form.ProductForm;
import com.xjtu.service.CategoryService;
import com.xjtu.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import utils.KeyUtil;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by 10270 on 2020/7/24.
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 商品列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "1") Integer size,
                             Map<String,Object> map){
        PageRequest pageRequest = new PageRequest(page - 1,size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("product/list",map);
    }

    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try {
            ProductInfo productInfo = productService.onSale(productId);
        }catch (SellException e){
            log.error("【卖家上架商品】 发生异常 {}", e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return  new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);

    }

    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try {
            ProductInfo productInfo = productService.offSale(productId);
        }catch (SellException e){
            log.error("【卖家下架商品】 发生异常 {}", e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return  new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);

    }

    /**
     * 商品展示
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String,Object> map){
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo",productInfo);
        }
        List<ProductCategory> categoryList =categoryService.findAll();
        map.put("categoryList",categoryList);

        return new ModelAndView("/product/index",map);
    }

    /**
     * 商品修改新增
     * @param productForm
     * @param bindingResult
     * @param map
     * @return
     */
    @CacheEvict(key = "123",cacheNames = "product")
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return  new ModelAndView("common/error",map);
        }
        ProductInfo productInfo = new ProductInfo();
        try{
            if (!StringUtils.isEmpty(productForm.getProductId())){
                productInfo = productService.findOne(productForm.getProductId());
            }else{
                productForm.setProductId(KeyUtil.getUniqueKey());
            }

            BeanUtils.copyProperties(productForm,productInfo);
            productService.save(productInfo);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return  new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/product/index");
        return new ModelAndView("common/success",map);

    }
}
