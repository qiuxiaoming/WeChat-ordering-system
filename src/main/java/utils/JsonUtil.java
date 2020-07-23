package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by 10270 on 2020/7/23.
 */
public class JsonUtil {
    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

}
