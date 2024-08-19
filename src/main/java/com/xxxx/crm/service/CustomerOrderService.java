package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.mapper.CustomerOrderMapper;
import com.xxxx.crm.query.CustomerOrderQuery;
import com.xxxx.crm.vo.CustomerOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerOrderService extends BaseService<CustomerOrder,Integer> {
    @Resource
    private CustomerOrderMapper customerOrderMapper;

    public Map<String,Object> queryCustomerOrdersByParams(CustomerOrderQuery customerQuery){
        Map<String,Object> map=new HashMap<String,Object>();
        PageHelper.startPage(customerQuery.getPage(),customerQuery.getLimit());
        PageInfo<CustomerOrder> pageInfo=new PageInfo<CustomerOrder>(selectByParams(customerQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }
}
