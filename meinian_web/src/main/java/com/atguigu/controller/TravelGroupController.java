package com.atguigu.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travelGroup")
public class TravelGroupController {

    @Reference
    TravelGroupService travelGroupService;

    @GetMapping("/getAll")
    public Result getAll(){
        try {
            List<TravelGroup> travelGroupList = travelGroupService.getAll();
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS, travelGroupList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
    }

    @PostMapping("/edit")
    public Result edit(@RequestParam("travelItemIds") Integer[] travelItemIds, @RequestBody TravelGroup travelGroup){
        try {
            travelGroupService.edit(travelItemIds, travelGroup);
            return new Result(true,MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_TRAVELGROUP_FAIL);
        }
    }

    @GetMapping("/findItemIds")
    public Result findItemIds(@RequestParam("groupId") Integer groupId){
        try {
            List<Integer> itemIds = travelGroupService.findItemIds(groupId);
            return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS, itemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
    }

    @GetMapping("/getGroupById")
    public Result getGroupById(@RequestParam("id") Integer id){
        try {
            TravelGroup travelGroup = travelGroupService.getGroupById(id);
            return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS, travelGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
    }

    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = travelGroupService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }


    @PostMapping("/add")
    public Result add(@RequestParam("travelItemIds") Integer[] travelItemIds, @RequestBody TravelGroup travelGroup){
        try {
            travelGroupService.add(travelItemIds, travelGroup);
            return new Result(true,MessageConstant.ADD_TRAVELGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
    }
}
