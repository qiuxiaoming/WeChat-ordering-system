package com.xjtu.exception;

import com.xjtu.enums.ResultEnum;
import lombok.Getter;

/**
 * Created by 10270 on 2020/7/10.
 */
@Getter
public class SellException extends RuntimeException {

     private Integer code;

     public SellException(ResultEnum resultEnum){
         super(resultEnum.getMessage());
         this.code = resultEnum.getCode();
     }

     public SellException(Integer code,String message){
         super(message);
         this.code = code;
     }
}
