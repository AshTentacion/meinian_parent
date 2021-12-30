package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Reference
    LoginService loginService;

    @PostMapping("/check")
    public Result check(HttpServletResponse response, @RequestBody Map map){
        //验证手机号 没api先不做了
        String phone = (String) map.get("telephone");
        //用手机号验证是否注册过, 如果没注册就快速注册
        Member member = loginService.findByTelephone(phone);
        if(member == null){
            member = new Member();
            member.setPhoneNumber(phone);
            member.setRegTime(new Date());
            loginService.add(member);
        }
        //添加cookie
        Cookie cookie = new Cookie("login_number_telephone",phone);
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24*30);
        response.addCookie(cookie);
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
