package com.xjtu.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xjtu.service.CategoryService;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 10270 on 2020/7/9.
 */
@Data
public class ProductVO implements Serializable{


    private static final long serialVersionUID = -1716374281730630595L;
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
