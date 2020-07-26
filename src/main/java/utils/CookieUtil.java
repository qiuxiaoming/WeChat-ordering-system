package utils;

import org.apache.http.HttpResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * cookie工具类
 * Created by 10270 on 2020/7/25.
 */
public class CookieUtil {

    /**
     * 设置
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge){

        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 获取Cookie
     * @param httpServletRequest
     * @param name
     */
    public static Cookie get(HttpServletRequest httpServletRequest,
                           String name){

        Map<String, Cookie> cookieMap = readCookieMap(httpServletRequest);
        if (cookieMap.containsKey(name)){
            return cookieMap.get(name);
        }else{
            return null;
        }


    }

    /**
     * 将Cookie封装为map
     * @param httpServletRequest
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest httpServletRequest){
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(),cookie);
            }

        }
        return cookieMap;
    }
}
