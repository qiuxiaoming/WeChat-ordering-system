package com.xjtu.aspect;

import com.xjtu.constant.CookieConstant;
import com.xjtu.constant.RedisConstant;
import com.xjtu.exception.SellException;
import com.xjtu.exception.SellerAuthorizeException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import utils.CookieUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 买家授权切面
 * Created by 10270 on 2020/7/26.
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.xjtu.controller.Seller*.*(..))" +
            "&& !execution(public * com.xjtu.controller.SellerUserController.*(..))")
    public void verify(){}

    //注意，由于没有商家号，故注释做调试
    /**@Before("verify()")
    public void doVerify(){
        ServletRequestAttributes requestAttributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null){
            log.warn("【登录校验】 Cookie中查不到token");
            throw new SellerAuthorizeException();
        }

        //去redis里查询
        String redisTokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if (StringUtils.isEmpty(redisTokenValue)){
            log.warn("【登录校验】 Redis中查不到token,{}",String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            throw new SellerAuthorizeException();
        }

    }*/
}
