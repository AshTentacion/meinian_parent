package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetMealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    SetMealService setMealService;

    @GetMapping("findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal = setMealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        try {
            List<Setmeal> list = setMealService.getSetmeal();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
