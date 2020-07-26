package com.xjtu.dataobject.mapper;

import com.xjtu.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 该类用于测试mybatis使用
 * Created by 10270 on 2020/7/26.
 */
public interface ProductCategoryMapper {

    @Insert("insert into product_category(category_name,category_type) values(#{categoryName, jdbcType = VARCHAR} , #{categoryType, jdbcType = INTEGER})")
    int insertByMap(Map<String, Object> map);

    @Insert("insert into product_category(category_name,category_type) values(#{categoryName, jdbcType = VARCHAR} , #{categoryType, jdbcType = INTEGER})")
    int insertByObject(ProductCategory productCategory);

    @Select("select * from product_category where category_type = #{categoryType}")
    @Results({
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_type", property = "categoryType"),
            @Result(column = "category_name", property = "categoryName"),
    })
    ProductCategory findByCategoryType(Integer categoryType);

    @Select("select * from product_category where category_name = #{categoryName}")
    @Results({
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_type", property = "categoryType"),
            @Result(column = "category_name", property = "categoryName"),
    })
    List<ProductCategory> findByCategoryName(String categoryName);

    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    int updateByProductCategory(@Param("categoryName") String categoryName,@Param("categoryType") int categoryType);

    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    int updateByProductObject(ProductCategory productCategory);

    @Delete("delete from product_category where category_type = #{categoryType}")
    int deleteByCategory(Integer categoryType);

    //xml注解方式
    ProductCategory selectByCategoryType(Integer categoryType);
}
