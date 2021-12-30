package com.atguigu.dao;

import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;

import java.util.List;

public interface TravelItemDao {
    void add(TravelItem travelItem);

    Page findPage(String queryString);

    void delete(Integer id);

    TravelItem getItemById(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();

    long findCountByTravelItemId(Integer id);

    List<TravelItem> findTravelItemById(Integer id);
}
