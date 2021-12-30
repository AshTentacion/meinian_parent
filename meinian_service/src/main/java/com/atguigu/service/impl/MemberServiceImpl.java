package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.MemberDao;
import com.atguigu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;


    @Override
    public List<Integer> findMemberCountByMonth(List<String> months) {
        List<Integer> monthCounts = new ArrayList<>();
        if (months != null && months.size() > 0) {
            for (String month : months) {
                int count = memberDao.findMemberCountByMonth(month);
                monthCounts.add(count);
            }
        }

        return monthCounts;
    }
}
