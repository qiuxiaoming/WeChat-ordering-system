package utils;

import com.xjtu.enums.CodeEnum;

/**
 * Created by 10270 on 2020/7/23.
 */
public class EnumUtil {

    //重要，多看
    public static <T extends CodeEnum> T getByCode(Integer code,Class<T> enumClass){
        for (T each: enumClass.getEnumConstants()) {
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
