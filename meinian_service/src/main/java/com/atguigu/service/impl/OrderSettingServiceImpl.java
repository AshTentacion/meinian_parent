package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import com.atguigu.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    @Override
    public void editNumberByDate(Map map) throws ParseException { //2021-5-2
        String dateStr = (String) map.get("orderDate");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(dateStr);
        OrderSetting orderSetting = new OrderSetting();
        orderSetting.setOrderDate(date);
        orderSetting.setNumber(Integer.parseInt((String) map.get("number")));
        int count = orderSettingDao.findOrderSettingByOrderDate(date);
        if(count > 0){
            orderSettingDao.edit(orderSetting);
        }else{
            orderSettingDao.add(orderSetting);
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) { //2021-1-1 
        String beginDate = date + "-1";
        String endDate = date + "-31";
        Map<String, String> param = new HashMap<>();
        param.put("beginDate", beginDate);
        param.put("endDate", endDate);
        List<OrderSetting> orderSettings = orderSettingDao.getOrderSettingByMonth(param);
        List<Map> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (OrderSetting orderSetting : orderSettings) {
            Map map = new HashMap<>();
            calendar.setTime(orderSetting.getOrderDate());
            map.put("date", calendar.get(calendar.DAY_OF_MONTH));
            map.put("number", orderSetting.getNumber());
            map.put("reservations", orderSetting.getReservations());
            list.add(map);
        }
        return list;
    }

    @Override
    public void upload(List<OrderSetting> readExcel) {
        for (OrderSetting orderSetting : readExcel) {
            //判断orderSetting的日期是否在数据库中
            int count = orderSettingDao.findOrderSettingByOrderDate(orderSetting.getOrderDate());
            if(count > 0){
                orderSettingDao.edit(orderSetting);
            }else{
                orderSettingDao.add(orderSetting);
            }
        }
    }
}
