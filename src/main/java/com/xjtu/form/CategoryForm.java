package com.xjtu.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by 10270 on 2020/7/25.
 */
@Data
public class CategoryForm {

    private Integer categoryId;


    private String categoryName;


    private Integer categoryType;
}
