package com.xjtu.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 10270 on 2020/7/9.
 */
@Data
public class ResultVO<T> implements Serializable{


    private static final long serialVersionUID = -4837392777166418370L;

    //错误码
    private Integer code;

    //提示信息
    private String msg;

    //具体内容（这个泛型的用处）utils包下
    private T data;
}
