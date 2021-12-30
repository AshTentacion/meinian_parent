package com.atguigu.service;

import com.atguigu.entity.Result;

import java.text.ParseException;
import java.util.Map;

public interface OrderService {
    Result submit(Map map) throws ParseException;

    Map<String, Object> findById(Integer id);
}
