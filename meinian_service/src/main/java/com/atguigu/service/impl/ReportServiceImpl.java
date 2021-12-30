package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.service.ReportService;
import com.atguigu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderDao orderDao;

    @Override
    public Map getBusinessReportData() {
        //创建要返回的Map
        HashMap<String, Object> returnMap = new HashMap<>();
        try {
            //获取今天的日期
            String today = DateUtils.parseDate2String(new Date());
            //获取本周一的日期
            String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
            //获取本周日的日期
            String thisWeekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
            //获取本月的第一天(1号)
            String thisMonthFirstDay = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
            //获取本月的最后一天（31号）
            String thisMonthEndDay = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());

            //获取今日新增会员的数量
            int todayMembers = memberDao.getTodayMembers(today);
            //获取所有会员的数量
            int allMembers = memberDao.getAllMembers();
            //获取本周新增会员数
            int thisWeekNewMembers = memberDao.getThisWeekAndMonthNewMember(thisWeekMonday);
            //获取本月新增会员数
            int thisMonthNewMembers = memberDao.getThisWeekAndMonthNewMember(thisMonthFirstDay);

            //获取今日预约数
            int todayOrderNumber = orderDao.getDateOrderNumber(today);
            //获取今日出游数
            int todayVisitsNumber = orderDao.getDateVisitsNumber(today);
            //获取本周预约数
            Map<String,Object> paramWeek = new HashMap<>();
            paramWeek.put("begin",thisWeekMonday);
            paramWeek.put("end",thisWeekSunday);
            int thisWeekOrderNumber = orderDao.getThisWeekAndMonthOrderNumber(paramWeek);
            //获取本月预约数
            Map<String,Object> paramMonth = new HashMap<>();
            paramMonth.put("begin",thisMonthFirstDay);
            paramMonth.put("end",thisMonthEndDay);
            int thisMonthOrderNumber = orderDao.getThisWeekAndMonthOrderNumber(paramMonth);
            //获取本周出游数
            int thisWeekVisitsNumber = orderDao.getThisWeekAndMonthVisitsNumber(paramWeek);
            //获取本月出游数
            int thisMonthVisitsNumber = orderDao.getThisWeekAndMonthVisitsNumber(paramMonth);
            //获取当下热门套餐
            List<Map<String,Object>> hotSetmeal = orderDao.getHotSetmeal();

            //将查询到的数据封装到map集合中
            returnMap.put("reportDate",today);
            returnMap.put("todayNewMember",todayMembers);
            returnMap.put("totalMember",allMembers);
            returnMap.put("thisWeekNewMember",thisWeekNewMembers);
            returnMap.put("thisMonthNewMember",thisMonthNewMembers);

            returnMap.put("todayOrderNumber",todayOrderNumber);
            returnMap.put("todayVisitsNumber",todayVisitsNumber);
            returnMap.put("thisWeekOrderNumber",thisWeekOrderNumber);
            returnMap.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
            returnMap.put("thisMonthOrderNumber",thisMonthOrderNumber);
            returnMap.put("thisMonthVisitsNumber",thisMonthVisitsNumber);

            returnMap.put("hotSetmeal",hotSetmeal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }










}
