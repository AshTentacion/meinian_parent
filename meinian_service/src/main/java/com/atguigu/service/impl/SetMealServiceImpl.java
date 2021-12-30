package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.RedisConstant;
import com.atguigu.dao.SetMealDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetMealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    SetMealDao setMealDao;

    @Autowired
    JedisPool jedisPool;

    @Override
    public List<Map> getSetmealReport() {
        return setMealDao.getSetmealReport();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);
    }

    @Override
    public List<Setmeal> getSetmeal() {
        return setMealDao.getSetmeal();
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page page = setMealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(Integer[] travelgroupIds, Setmeal setmeal) {
        setMealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        addSetmealAndTravelGroup(setmealId, travelgroupIds);

        //用于删除垃圾图片
        String pic = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, pic);
    }

    private void addSetmealAndTravelGroup(Integer setmealId, Integer[] travelgroupIds) {
        if(travelgroupIds != null && travelgroupIds.length > 0){
            for (Integer travelgroupId : travelgroupIds) {
                Map<String, Integer> param = new HashMap<>();
                param.put("setmeal_id", setmealId);
                param.put("travelgroup_id", travelgroupId);
                setMealDao.addSetmealAndTravelGroup(param);
            }
        }
    }
}
