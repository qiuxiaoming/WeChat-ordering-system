package com.xjtu.VO;

import lombok.Data;

import java.util.List;

/**
 * Created by 10270 on 2020/7/9.
 */
@Data
public class ResultVO<T> {

    //错误码
    private Integer code;

    //提示信息
    private String msg;

    //具体内容
    private T data;
}
