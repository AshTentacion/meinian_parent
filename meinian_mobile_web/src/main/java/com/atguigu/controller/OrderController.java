package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    OrderService orderService;

    @GetMapping("/findById")
    public Result findById(Integer id){
        try {
            Map<String, Object> map = orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    @PostMapping("/submit")
    public Result submit(@RequestBody Map map){
        try {
            return orderService.submit(map);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Result(false, "内部错误");
        }
    }

}
