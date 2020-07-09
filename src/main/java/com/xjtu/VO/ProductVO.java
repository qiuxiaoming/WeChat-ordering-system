package com.xjtu.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xjtu.service.CategoryService;
import lombok.Data;

import java.util.List;

/**
 * Created by 10270 on 2020/7/9.
 */
@Data
public class ProductVO {

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
