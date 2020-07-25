package com.xjtu.controller;

import com.xjtu.dataobject.ProductCategory;
import com.xjtu.exception.SellException;
import com.xjtu.form.CategoryForm;
import com.xjtu.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by 10270 on 2020/7/25.
 */
@RequestMapping("/seller/category")
@Controller
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 类目列表
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> productCategories = categoryService.findAll();
        map.put("productCategories",productCategories);
        return new ModelAndView("/category/list",map);
    }

    /**
     *类目展示
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false) Integer  categoryId,
                              Map<String,Object> map){
        if(categoryId != null){
            ProductCategory productCategory = categoryService.findOne(categoryId);
            map.put("productCategory",productCategory);
        }

        return new ModelAndView("/category/index",map);
    }

    /**
     * 类目修改新增 
     * @param categoryForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String,Object> map){

        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }

        ProductCategory productCategory = new ProductCategory();
        try{
            if (categoryForm.getCategoryId()!= null){
                productCategory = categoryService.findOne(categoryForm.getCategoryId());
            }

            BeanUtils.copyProperties(categoryForm,productCategory);
            categoryService.save(productCategory);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }

        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);
    }

}
