package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Address;
import com.atguigu.service.AddressService;
import com.github.pagehelper.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    AddressService addressService;

    @PostMapping("/deleteById")
    public Result deleteById(@RequestParam("id") Integer id){
        try {
            addressService.deleteById(id);
            return new Result(true, "删除地址成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除地址失败");
        }
    }


    @PostMapping("/addAddress")
    public Result addAddress(@RequestBody Address address){
        try {
            addressService.addAddress(address);
            return new Result(true, MessageConstant.ADD_ADDRESS_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_ADDRESS_FAIL);
        }

    }


    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = addressService.findPage(queryPageBean);
        return pageResult;
    }

    @GetMapping("/findAllMaps")
    public Map findAllMaps(){
        Map map = new HashMap();
        List<Map> gridnMaps = new ArrayList<>();
        List<Map> nameMaps = new ArrayList<>();

        List<Address> addresses = addressService.findAllMaps();

        for (Address address : addresses) {
            Map gridMap = new HashMap();
            gridMap.put("lng",address.getLng());
            gridMap.put("lat",address.getLat());
            gridnMaps.add(gridMap);

            Map nameMap = new HashMap();
            nameMap.put("addressName", address.getAddressName());
            nameMaps.add(nameMap);
        }

        map.put("gridnMaps", gridnMaps);
        map.put("nameMaps", nameMaps);
        return map;
    }
}
