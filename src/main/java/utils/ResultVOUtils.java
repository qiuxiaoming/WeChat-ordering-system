package utils;

import com.xjtu.VO.ResultVO;

/**
 * Created by 10270 on 2020/7/9.
 */
//TODO 无参构造方法
public class ResultVOUtils {

    public static ResultVO success(Object object){

        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(){

        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO error(Integer code,String message){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(message);
        return resultVO;
    }
}
