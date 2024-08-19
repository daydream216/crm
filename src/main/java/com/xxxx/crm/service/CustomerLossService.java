package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.mapper.CustomerLossMapper;
import com.xxxx.crm.mapper.CustomerMapper;
import com.xxxx.crm.mapper.CustomerOrderMapper;
import com.xxxx.crm.query.CustomerLossQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.Customer;
import com.xxxx.crm.vo.CustomerLoss;
import com.xxxx.crm.vo.CustomerOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerLossService extends BaseService<CustomerLoss,Integer> {

    private final CustomerLossMapper customerLossMapper;
    private final CustomerMapper customerMapper;
    private final CustomerOrderMapper customerOrderMapper;

    public Map<String,Object> queryCustomerLosssByParams(CustomerLossQuery customerLossQuery){
        Map<String,Object> map=new HashMap<String,Object>();
        PageHelper.startPage(customerLossQuery.getPage(),customerLossQuery.getLimit());
        PageInfo<CustomerLoss> pageInfo=new PageInfo<CustomerLoss>(selectByParams(customerLossQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }

//    @Transactional(propagation = Propagation.REQUIRED)
//    public void updateCustomerState(){
//        List<Customer> lossCustomers = customerMapper.queryLossCustomers();
//        if(null != lossCustomers && lossCustomers.size()>0){
//            List<CustomerLoss> customerLosses = new ArrayList<>();
//            List<Integer> lossCusIds = new ArrayList<>();
//            lossCustomers.forEach(customer -> {
//                CustomerLoss customerLoss = new CustomerLoss();
//                //设置最后下单时间
//                CustomerOrder lastCustomerOrder = customerOrderMapper.queryLastCustomerOrderByCusId(customer.getId());
//                if(null != lastCustomerOrder){
//                    customerLoss.setLastOrderTime(lastCustomerOrder.getOrderDate());
//                }
//                customerLoss.setCreateDate(new Date());
//                customerLoss.setCusManager(customer.getCusManager());
//                customerLoss.setCusName(customer.getName());
//                customerLoss.setCusNo(customer.getKhno());
//                customerLoss.setIsValid(1);
//                //设置客户流失状态为暂缓流失状态
//                customerLoss.setState(0);
//                customerLoss.setUpdateDate(new Date());
//                customerLosses.add(customerLoss);
//                lossCusIds.add(customer.getId());
//            });
//            AssertUtil.isTrue(customerLossMapper.insertBatch(customerLosses)<customerLosses.size(),"客户流失数据流转失败!");
//            AssertUtil.isTrue(customerMapper.updateCustomerStateByIds(lossCusIds)<lossCusIds.size(),"客户流失数据流转失败!");
//
//        }
//    }
        public void updateCustomerLossStateById(Integer id, String lossReason) {
            CustomerLoss customerLoss =selectByPrimaryKey(id);
            AssertUtil.isTrue(null==customerLoss,"待流失的客户记录不存在!");
            customerLoss.setState(1);// 确认流失
            customerLoss.setLossReason(lossReason);
            customerLoss.setConfirmLossTime(new Date());
            customerLoss.setUpdateDate(new Date());
            AssertUtil.isTrue(updateByPrimaryKeySelective(customerLoss)<1,"确认流失失败!");
    }
}
