package com.xjtu.controller;

import com.xjtu.config.ProjectUrlConfig;
import com.xjtu.constant.CookieConstant;
import com.xjtu.constant.RedisConstant;
import com.xjtu.dataobject.SellerInfo;
import com.xjtu.enums.ResultEnum;
import com.xjtu.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import utils.CookieUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by 10270 on 2020/7/25.
 */
@Controller
@Slf4j
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String,Object> map){
        //1.openid和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if(sellerInfo == null){
            map.put("msg", ResultEnum.LOGIN_FAIL);
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        //2.设置token到redis
        Integer expire = RedisConstant.EXPIRE;
        String token = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),
                openid,expire, TimeUnit.SECONDS);

        //3.设置token到cookie
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);


        //注意，跳转尽量用绝对地址
        return new ModelAndView("redirect:" + projectUrlConfig.getSell() +"/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest httpServletRequest,
                               HttpServletResponse httpServletResponse,
                               Map<String, Object> map){

        //1.从cookie里查询
            Cookie cookie = CookieUtil.get(httpServletRequest,CookieConstant.TOKEN);

            if (cookie != null){
                //2.清除redis
                redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));

                //3.清除cookie
                CookieUtil.set(httpServletResponse,cookie.getName(),null,0);
            }

            map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessage());
            map.put("url","/sell/seller/order/list");

            return new ModelAndView("/common/success",map);

    }
}
