package com.atguigu.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @作者 靖雪阳
 * @时间 2021-8-29 下午 8:26
 * @类名 HelloController
 * @描述
 */
@RequestMapping("/hello")
@RestController
public class HelloController {
    @RequestMapping("/add")
    //表示用户必须有add权限才能调用当前方法
    @PreAuthorize("hasAuthority('add')")
    public String add(){
        System.out.println("add......");
        return "success";
    }
    @RequestMapping("/update")
    //表示用户必须有ROLE_ADMIN角色才能调用当前方法
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(){
        System.out.println("update......");
        return "success";
    }

    @RequestMapping("/delete")
    //表示用户必须有ABC角色才能调用当前方法
    @PreAuthorize("hasRole('ABC')")
    public String delete(){
        System.out.println("delete......");
        return "success";
    }


}
