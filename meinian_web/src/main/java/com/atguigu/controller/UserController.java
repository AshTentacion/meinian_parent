package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getUsername")
    public Result getUsername(){
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_USERNAME_FAIL);
        }

    }
}
