package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.query.CustomerReprQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.CustomerReprieve;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerReprService extends BaseService<CustomerReprieve,Integer> {

    @Resource
    private CustomerLossService customerLossService;

    public Map<String,Object> queryCustomerReprsByParams(CustomerReprQuery customerReprQuery){
        Map<String,Object> map=new HashMap<String,Object>();
        PageHelper.startPage(customerReprQuery.getPage(),customerReprQuery.getLimit());
        PageInfo<CustomerReprieve> pageInfo=new PageInfo<CustomerReprieve>(selectByParams(customerReprQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }


    public void saveCustomerRepr(CustomerReprieve customerReprieve){
        /**
         * 1.参数校验
         *    流失客户id 非空 记录必须存在
         *    暂缓错误  measure 非空
         * 2.参数默认值设置
         *    isValid  createDate updateDate
         *  3.执行添加 判断结果
         */
        AssertUtil.isTrue(null==customerReprieve.getLossId()
                ||null==customerLossService.selectByPrimaryKey(customerReprieve.getLossId()),"请指定流失客户id");
        AssertUtil.isTrue(StringUtils.isBlank(customerReprieve.getMeasure()),"请指定措施项!");
        customerReprieve.setIsValid(1);
        customerReprieve.setCreateDate(new Date());
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(customerReprieve)<1,"暂缓措施添加失败!");
    }

    public void updateCustomerRepr(CustomerReprieve customerReprieve){
        /**
         * 1.参数校验
         *    id 记录必须存在
         *    流失客户id 非空 记录必须存在
         *    暂缓错误  measure 非空
         * 2.参数默认值设置
         *     updateDate
         *  3.执行更新 判断结果
         */
        AssertUtil.isTrue(null==selectByPrimaryKey(customerReprieve.getId()),"待更新的暂缓措施不存在!");
        AssertUtil.isTrue(null==customerReprieve.getLossId()
                ||null==customerLossService.selectByPrimaryKey(customerReprieve.getLossId()),"请指定流失客户id");
        AssertUtil.isTrue(StringUtils.isBlank(customerReprieve.getMeasure()),"请指定措施项!");
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(customerReprieve)<1,"暂缓措施更新失败!");
    }

    public void deleteCustomerRepr(Integer id){
        CustomerReprieve temp =selectByPrimaryKey(id);
        AssertUtil.isTrue(null== temp,"待删除的暂缓措施不存在!");
        temp.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"记录删除失败!");
    }
}
