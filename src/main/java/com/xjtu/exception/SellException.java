package com.xjtu.exception;

import com.xjtu.enums.ResultEnum;

/**
 * Created by 10270 on 2020/7/10.
 */
public class SellException extends RuntimeException {

     private Integer code;

     public SellException(ResultEnum resultEnum){
         super(resultEnum.getMessage());
         this.code = resultEnum.getCode();
     }
}
