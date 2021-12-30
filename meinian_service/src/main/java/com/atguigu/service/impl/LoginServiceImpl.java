package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.MemberDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = LoginService.class)
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    MemberDao memberDao;

    @Override
    public Member findByTelephone(String phone) {
        return memberDao.findByTelephone(phone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}
