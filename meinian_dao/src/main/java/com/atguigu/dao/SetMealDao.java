package com.atguigu.dao;

import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetMealDao {
    void add(Setmeal setmeal);

    void addSetmealAndTravelGroup(Map<String, Integer> param);

    Page findPage(@Param("queryString") String queryString);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);

    List<Map> getSetmealReport();
}
