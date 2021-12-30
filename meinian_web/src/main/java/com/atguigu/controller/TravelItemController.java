package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travelItem")
public class TravelItemController {

    @Reference
    TravelItemService travelItemService;

    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")
    public Result findAll(){
        try {
            List<TravelItem> travelItemList = travelItemService.findAll();
            return new Result(true, MessageConstant.QUERY_TRAVELITEM_SUCCESS, travelItemList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('TRAVELITEM_ADD')")
    public Result add(@RequestBody TravelItem travelItem){
        try {
            travelItemService.add(travelItem);
            return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_TRAVELITEM_FAIL);
        }
    }

    @PostMapping("/findPage")
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = travelItemService.findPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(), queryPageBean.getQueryString());
        return pageResult;
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('TRAVELITEM_DELETE123')")
    public Result delete(@RequestParam("id") Integer id){
        try {
            travelItemService.delete(id);
            return new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS);
        }catch (RuntimeException e){
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
    }

    @GetMapping("/getItemById")
    @PreAuthorize("hasAuthority('TRAVELITEM_QUERY')")
    public Result getItemById(@RequestParam("id") Integer id){
        try {
            TravelItem travelItem = travelItemService.getItemById(id);
            return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS, travelItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('TRAVELITEM_EDIT')")
    public Result edit(@RequestBody TravelItem travelItem){
        try {
            travelItemService.edit(travelItem);
            return new Result(true,MessageConstant.EDIT_TRAVELITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_TRAVELITEM_FAIL);
        }
    }

}
