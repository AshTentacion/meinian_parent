package com.atguigu.service;

import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;

public interface LoginService {

    Member findByTelephone(String phone);

    void add(Member member);
}
