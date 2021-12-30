package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderService;
import org.apache.poi.ss.usermodel.DateUtil;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderSettingDao orderSettingDao;

    @Autowired
    MemberDao memberDao;

    @Override
    public Map<String, Object> findById(Integer id) {
        Map<String, Object> map = orderDao.findById(id);
        Date date = (Date) map.get("orderDate");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = simpleDateFormat.format(date);
        map.put("orderDate", formatDate);
        return map;
    }

    @Override
    public Result submit(Map map) throws ParseException {
        //1.判断当前日期是否可以预约(根据orderDate查询t_ordersetting),能查询出来可以预约，查询不出来不能预约
        Integer setmealId = Integer.parseInt((String) map.get("setmealId"));
        String orderStr = (String) map.get("orderDate");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(orderStr);
        OrderSetting orderSetting = orderSettingDao.getOrderSettingByOrderDate(date);
        if(orderSetting == null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //2.判断当前日期是否预约已满(判断已经预约人数)是否等于number
        if(orderSetting.getReservations() >= orderSetting.getNumber()){
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        //3.判断是否是会员(根据手机号码查询t_member)
//            -如果是会员，防止重复预约
//            -如果不是会员，自动注册为会员
        String telephone = (String) map.get("telephone");
        Member member = memberDao.getMemberByTelephone(telephone);
        if(member == null){
            member = new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setIdCard((String) map.get("idCard"));
            member.setPhoneNumber((String) map.get("telephone"));
            member.setRegTime(new Date());
            memberDao.add(member);//主键回填
        }else{
            Order orderParam = new Order();
            orderParam.setSetmealId(setmealId);
            orderParam.setOrderDate(date);
            orderParam.setMemberId(member.getId());
            List<Order> orders = orderDao.findOrderByCondition(orderParam);
            if(orders != null && orders.size() > 0){
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }
        //4.进行预约
//            -向t_order表插入一条记录
//            -t_ordersetting表里预约人数+1
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(date);
        order.setOrderType("微信预约");
        order.setOrderStatus("已预约");
        order.setSetmealId(setmealId);
        orderDao.add(order); //主键回填

        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS, order);
    }
}
