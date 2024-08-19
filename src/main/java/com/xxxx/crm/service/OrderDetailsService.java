package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.mapper.OrderDetailsMapper;
import com.xxxx.crm.query.OrderDetailsQuery;
import com.xxxx.crm.vo.OrderDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderDetailsService extends BaseService<OrderDetails,Integer> {
    @Resource
    private OrderDetailsMapper orderDetailsMapper;

    public Map<String,Object> queryOrderDetailsByParams(OrderDetailsQuery orderDetailsQuery){
        Map<String,Object> map=new HashMap<String,Object>();
        PageHelper.startPage(orderDetailsQuery.getPage(),orderDetailsQuery.getLimit());
        PageInfo<OrderDetails> pageInfo=new PageInfo<OrderDetails>(orderDetailsMapper.selectByParams(orderDetailsQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }


}
