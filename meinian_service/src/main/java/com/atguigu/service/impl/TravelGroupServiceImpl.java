package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelGroupDao;
import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService {

    @Autowired
    TravelGroupDao travelGroupDao;

    @Override
    public List<TravelGroup> getAll() {
        return travelGroupDao.getAll();
    }

    @Override
    public void edit(Integer[] travelItemIds, TravelGroup travelGroup) {
        travelGroupDao.edit(travelGroup);
        Integer travelGroupId = travelGroup.getId();
        //删除所有何travelGroupId相关的items
        travelGroupDao.delete(travelGroupId);
        //插入所有Ids,用travelGroup.id
        addTravelGroupAndTravelItem(travelItemIds,travelGroupId);
    }

    @Override
    public List<Integer> findItemIds(Integer groupId) {
        return travelGroupDao.findItemIds(groupId);
    }

    @Override
    public TravelGroup getGroupById(Integer id) {
        return travelGroupDao.getGroupById(id);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page page = travelGroupDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(Integer[] travelItemIds, TravelGroup travelGroup) {
        travelGroupDao.add(travelGroup);
        Integer travelGroupId = travelGroup.getId();
        addTravelGroupAndTravelItem(travelItemIds, travelGroupId);
    }

    private void addTravelGroupAndTravelItem(Integer[] travelItemIds, Integer travelGroupId) {
        if(travelItemIds != null && travelItemIds.length > 0){
            for (Integer travelItemId : travelItemIds) {
                Map<String, Integer> param = new HashMap<>();
                param.put("travelGroupId", travelGroupId);
                param.put("travelItemId", travelItemId);
                travelGroupDao.addTravelGroupAndTravelItem(param);
            }
        }

    }
}
