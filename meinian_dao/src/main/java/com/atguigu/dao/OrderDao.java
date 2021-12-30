package com.atguigu.dao;

import com.atguigu.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    List<Order> findOrderByCondition(Order orderParam);

    void add(Order order);

    Map<String, Object> findById(Integer id);

    int getDateOrderNumber(String today);

    int getDateVisitsNumber(String today);

    int getThisWeekAndMonthOrderNumber(Map<String, Object> paramWeek);

    int getThisWeekAndMonthVisitsNumber(Map<String, Object> paramWeek);

    List<Map<String, Object>> getHotSetmeal();
}
