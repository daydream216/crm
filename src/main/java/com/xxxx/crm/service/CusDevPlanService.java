package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.mapper.SaleChanceMapper;
import com.xxxx.crm.query.CusDevPlanQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.CusDevPlan;;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {

    @Resource
    private SaleChanceMapper saleChanceMapper;
    /**
     * 多条件查询计划项列表
     * @param query
     * @return
     */
    public Map<String,Object> queryCusDevPlansByParams(CusDevPlanQuery query){
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(),query.getLimit());
        PageInfo<CusDevPlan> pageInfo = new PageInfo<>(selectByParams(query));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    /**
     * 添加计划项
     * 1. 参数校验
     *      营销机会ID ⾮空 记录必须存在
     *      计划项内容 ⾮空
     *      计划项时间 ⾮空
     * 2. 设置参数默认值
     *      is_valid
     *      crateDate
     *      updateDate
     * 3. 执⾏添加，判断结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCusDevPlan(CusDevPlan cusDevPlan){
        System.out.println(cusDevPlan);
        //1.参数校验
        checkParams(cusDevPlan.getSaleChanceId(),cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        //2.设置参数默认值
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());

        //3.执行添加
        AssertUtil.isTrue(insertSelective(cusDevPlan)<1,"计划项记录添加失败!");
    }

    /**
     * 更新计划项
     * @param cusDevPlan
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCusDevPlan(CusDevPlan cusDevPlan){
        AssertUtil.isTrue(null == cusDevPlan.getId() || null == selectByPrimaryKey(cusDevPlan.getId()),"待更新记录不存在!");
        //1.参数校验
        checkParams(cusDevPlan.getSaleChanceId(), cusDevPlan.getPlanItem(),cusDevPlan.getPlanDate());
        //2.设置参数默认值
        cusDevPlan.setUpdateDate(new Date());
        //3.执行添加，判断结果
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"计划项记录更新失败!");
    }

    /**
     * 验证参数
     * @param saleChanceId
     * @param planItem
     * @param planDate
     */
    private void checkParams(Integer saleChanceId, String planItem, Date planDate){
        AssertUtil.isTrue(null == saleChanceId || saleChanceMapper.selectByPrimaryKey(saleChanceId) == null,"请设置营销机会ID!");
        AssertUtil.isTrue(StringUtils.isBlank(planItem),"请输入计划项内容!");
        AssertUtil.isTrue(null == planDate,"请指定计划项日期!");
    }

    /**
     * 删除计划项
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void delCusDevPlan(Integer id){
        CusDevPlan cusDevPlan = selectByPrimaryKey(id);
        AssertUtil.isTrue(null == id || null == cusDevPlan,"待删除记录不存在!");
        cusDevPlan.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(cusDevPlan)<1,"计划项记录删除失败!");
    }

}
