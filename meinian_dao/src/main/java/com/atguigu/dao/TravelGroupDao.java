package com.atguigu.dao;


import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TravelGroupDao {

    void add(TravelGroup travelGroup);

    void addTravelGroupAndTravelItem(Map<String, Integer> param);

    Page findPage(@Param("queryString") String queryString2);

    TravelGroup getGroupById(Integer id);

    List<Integer> findItemIds(Integer groupId);

    void edit(TravelGroup travelGroup);

    void delete(Integer travelGroupId);

    List<TravelGroup> getAll();

    List<TravelGroup> findTravelGroupByid(Integer id);
}
