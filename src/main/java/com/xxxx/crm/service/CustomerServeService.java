package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.enums.CustomerServeStatus;
import com.xxxx.crm.mapper.CustomerMapper;
import com.xxxx.crm.mapper.UserMapper;
import com.xxxx.crm.query.CustomerServeQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.CustomerServe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerServeService extends BaseService<CustomerServe,Integer> {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private UserMapper userMapper;

    public Map<String,Object> queryCustomerServesByParams(CustomerServeQuery customerServeQuery){
        Map<String,Object> map=new HashMap<String,Object>();
        PageHelper.startPage(customerServeQuery.getPage(),customerServeQuery.getLimit());
        PageInfo<CustomerServe> pageInfo=new PageInfo<CustomerServe>(selectByParams(customerServeQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }


    public void saveCustomerServe(CustomerServe customerServe){
        /**
         * 1.参数校验
         *    客户名  非空  客户记录在客户表中必须存在
         *    服务类型  非空
         *    服务请求内容非空  serviceRequest
         *2. 默认值添加
         *      state  已创建 状态
         *      isValid  createDate  updateDate
         * 3.执行添加  判断结果
         */
        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getCustomer()),"请指定客户!");
        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServeType()),"请指定服务类型!");
        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceRequest()),"请指定服务请求内容!");
        AssertUtil.isTrue(null==customerMapper.queryCustomerByName(customerServe.getCustomer()),"客户记录不存在!");
        customerServe.setState(CustomerServeStatus.CREATED.getState());
        customerServe.setIsValid(1);
        customerServe.setCreateDate(new Date());
        customerServe.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(customerServe)<1,"客户服务记录创建失败!");
    }



    public void updateCustomerServe(CustomerServe customerServe){
        /**
         * 1.参数校验
         *     id 记录必须存在
         * 2.如果状态为分配状态 fw_002
         *    分配人必须存在
         *    设置服务更新时间
         *    设置分配时间
         * 3.如果状态为 服务处理  fw_003
         *     服务处理内容非空
         *     设置服务处理时间
         *     服务更新时间
         * 4.如果服务状态为反馈状态 fw_004
         *     反馈内容非空
         *     满意度非空
         *     更新时间
         *     设置服务状态为归档状态
         * 5.执行更新操作 判断结果
         */
        AssertUtil.isTrue(null==selectByPrimaryKey(customerServe.getId()),"待更新的服务记录不存在!");
        if(customerServe.getState().equals(CustomerServeStatus.ASSIGNED.getState())){
            // 执行分配
            AssertUtil.isTrue(null==userMapper.selectByPrimaryKey(Integer.parseInt(customerServe.getAssigner())),"待分配的用户不存在!");
            customerServe.setAssignTime(new Date());
        }else if(customerServe.getState().equals(CustomerServeStatus.PROCED.getState())){
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProce()),"请指定处理内容!");
            customerServe.setServiceProceTime(new Date());
        }else if(customerServe.getState().equals(CustomerServeStatus.FEED_BACK.getState())){
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProceResult()),"请指定反馈内容!");
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getMyd()),"请指定满意度!");
            customerServe.setState(CustomerServeStatus.ARCHIVED.getState());
        }
        customerServe.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(customerServe)<1,"服务记录更新失败!");
    }


}
