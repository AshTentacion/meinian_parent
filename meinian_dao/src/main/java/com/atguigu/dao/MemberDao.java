package com.atguigu.dao;

import com.atguigu.pojo.Member;

public interface MemberDao {
    Member getMemberByTelephone(String telephone);

    void add(Member member);

    Member findByTelephone(String phone);

    int findMemberCountByMonth(String month);

    int getTodayMembers(String today);

    int getAllMembers();

    int getThisWeekAndMonthNewMember(String thisWeekMonday);
}
