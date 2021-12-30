package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelItemDao;
import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = TravelItemService.class)
@Transactional
public class TravelItemServiceImpl implements TravelItemService {

    @Autowired
    TravelItemDao travelItemDao;

    @Override
    public List<TravelItem> findAll() {
        return travelItemDao.findAll();
    }

    @Override
    public void edit(TravelItem travelItem) {
        travelItemDao.edit(travelItem);
    }

    @Override
    public TravelItem getItemById(Integer id) {
        return travelItemDao.getItemById(id);
    }

    @Override
    public void delete(Integer id) {
        long count = travelItemDao.findCountByTravelItemId(id);
        if(count > 0){
            throw new RuntimeException("删除自由行失败，因为存在关联数据，请解除关联后再进行删除");
        }
        travelItemDao.delete(id);
    }

    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page page = travelItemDao.findPage(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
