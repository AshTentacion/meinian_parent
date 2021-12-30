package com.atguigu.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @作者 靖雪阳
 * @时间 2021-8-28 下午 3:40
 * @类名 UserService
 * @描述
 */
@Component
public class UserService implements UserDetailsService {
    //模拟数据库中的数据
    static Map<String, com.atguigu.pojo.User> map = new HashMap<>();
    static {
        com.atguigu.pojo.User user1 =
                new com.atguigu.pojo.User("admin","admin","123");
        com.atguigu.pojo.User user2 =
                new com.atguigu.pojo.User("zhangsan","123","321");
        map.put(user1.getUsername(), user1);
        map.put(user2.getUsername(), user2);
    }



    //根据用户名加载用户信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username = " + username);
        //模拟根据用户名查询数据库
        com.atguigu.pojo.User userInDb = map.get(username);
        if (userInDb == null) {
            //根据用户名没有查询到用户，抛出异常，表示登录名有误
            return null;
        }
        //模拟数据库中的密码，后期需要查询数据库
        String passwordInDB = "{noop}" + userInDb.getPassword();
        //授权，后期需要改为查询数据库动态获取用户拥有的权限和角色
        List<GrantedAuthority> lists = new ArrayList<>();
        lists.add(new SimpleGrantedAuthority("add"));
        lists.add(new SimpleGrantedAuthority("delete"));
        lists.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        lists.add(new SimpleGrantedAuthority("ROLE_ABC"));


        //return new User(username,passwordInDB,lists);//这里是框架提供的User类
        //使用密文对输入的密码进行校验
        return new User(username,passwordInDB,lists);
    }
}
