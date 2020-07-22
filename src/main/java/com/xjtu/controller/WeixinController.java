package com.xjtu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by 10270 on 2020/7/21.
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {

    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
        log.info("进入auth方法");
        log.info("code={}", code);
        String url ="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx5772d32ecf748e4f&secret=9e645f9ce6ccf4a6969a0d2ffa603d32&code="
                +code+"&grant_type=authorization_code";
        RestTemplate template = new RestTemplate();
        String response = template.getForObject(url,String.class);
        log.info("response={}",response);

    }
}
