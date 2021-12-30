package com.atguigu.job;

import com.atguigu.constant.RedisConstant;
import com.atguigu.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImage {

    @Autowired
    JedisPool jedisPool;

    public void clearImage(){
        Set<String> pics = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String pic : pics) {
            QiniuUtils.deleteFileFromQiniu(pic);
            System.out.println("删除垃圾图片:pic=" + pic);
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, pic);
        }
    }
}
