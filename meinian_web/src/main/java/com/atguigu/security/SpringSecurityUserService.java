package com.atguigu.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.pojo.Permission;
import com.atguigu.pojo.Role;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);
        if(user == null){
            return null; //有security框架做异常处理，跳转到登录页面
        }

        //构建权限集合
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                grantedAuthorities.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }

        org.springframework.security.core.userdetails.User securityUser = new
                org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), grantedAuthorities);
        return securityUser;
    }
}
