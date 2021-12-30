package com.atguigu.service;

import com.atguigu.pojo.OrderSetting;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void upload(List<OrderSetting> readExcel);

    List<Map> getOrderSettingByMonth(String date);

    void editNumberByDate(Map map) throws ParseException;
}
