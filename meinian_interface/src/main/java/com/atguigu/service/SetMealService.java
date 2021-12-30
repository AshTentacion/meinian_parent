package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetMealService {
    void add(Integer[] travelgroupIds, Setmeal setmeal);

    PageResult findPage(QueryPageBean queryPageBean);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);

    List<Map> getSetmealReport();
}
