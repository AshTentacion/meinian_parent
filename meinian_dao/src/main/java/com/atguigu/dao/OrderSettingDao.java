package com.atguigu.dao;

import com.atguigu.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    int findOrderSettingByOrderDate(Date orderDate);

    void edit(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Map<String, String> param);

    OrderSetting getOrderSettingByOrderDate(Date date);

    void editReservationsByOrderDate(OrderSetting orderSetting);
}
