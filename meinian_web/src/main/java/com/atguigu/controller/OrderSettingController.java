package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import com.atguigu.utils.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orderSetting")
public class OrderSettingController {

    @Reference
    OrderSettingService orderSettingService;

    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody Map map){
        try {
            orderSettingService.editNumberByDate(map);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        try {
            List<Map> list = orderSettingService.getOrderSettingByMonth(date);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile){
        try {
            List<String[]> readExcel = POIUtils.readExcel(excelFile);
            List<OrderSetting> orderSettings = new ArrayList<>();
            for (String[] strings : readExcel) {
                String dateStr = strings[0];
                String numberStr = strings[1];
                OrderSetting orderSetting = new OrderSetting();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                orderSetting.setOrderDate(simpleDateFormat.parse(dateStr));
                orderSetting.setNumber(Integer.parseInt(numberStr));
                orderSetting.setReservations(0);
                orderSettings.add(orderSetting);
            }
            orderSettingService.upload(orderSettings);
            return new Result(true, MessageConstant.UPLOAD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.UPLOAD_FAIL);
        }
    }

}
