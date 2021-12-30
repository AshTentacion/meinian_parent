package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetMealService;
import com.atguigu.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    SetMealService setMealService;

    @Autowired
    JedisPool jedisPool;

    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setMealService.findPage(queryPageBean);
        return pageResult;
    }

    @PostMapping("/add")
    public Result add(Integer[] travelgroupIds, @RequestBody Setmeal setmeal){
        try {
            setMealService.add(travelgroupIds, setmeal);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile){
        try {
            //获取原始文件名称
            String originalFilename = imgFile.getOriginalFilename();
            //生成新的文件名称
            int i = originalFilename.lastIndexOf("."); //sss.jpg
            String suffix = originalFilename.substring(i); //.jpg
            String filename = UUID.randomUUID().toString() + suffix;
            //调用工具类上传到七牛云
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),filename);

            //上传到redis
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,filename);
            //返回结果
            return new Result(true, MessageConstant.UPLOAD_SUCCESS, filename);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }
}
