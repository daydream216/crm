package com.xxxx.crm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.enums.DevResult;
import com.xxxx.crm.enums.StateStatus;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.PhoneUtil;
import com.xxxx.crm.vo.SaleChance;
import com.xxxx.crm.mapper.SaleChanceMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.baomidou.mybatisplus.extension.toolkit.Db.page;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gkt
 * @since 2024-08-09
 */
@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    @Resource
    private SaleChanceMapper saleChanceMapper;

    /**
     * 多条件分页查询营销机会（BaseService 中有对应的方法）
     * @param query
     * @return
     */
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery query){
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(),query.getLimit());
        PageInfo<SaleChance> pageInfo = new PageInfo<>(saleChanceMapper.selectByParams(query));
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    /**
     * 营销机会数据添加
     * @param saleChance
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveSaleChance(SaleChance saleChance){
        //1.参数校验
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        //2.设置相关参数默认值
        //为选择分配人
        saleChance.setState(StateStatus.UNSTATE.getType());
        saleChance.setDevResult(DevResult.UNDEV.getStatus());
        //选择分配人
        //判断是否设置了指派人
        if(StringUtils.isNotBlank(saleChance.getAssignMan())){
            //如果为空，则表示未设置指派人
            //state分配状态  （0=未分配 1=已分配）  0 = 未分配
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
            saleChance.setAssignTime(new Date());
        }
        //isValid 是否有效 （0=无效，1=有效） 设置为有效 1
        saleChance.setIsValid(1);
        //createDate创建时间 默认是系统当前时间
        saleChance.setCreateDate(new Date());
        //updateDate 默认是系统当前时间
        saleChance.setUpdateDate(new Date());

        //3.执行添加  判断结果
        AssertUtil.isTrue(insertSelective(saleChance)<1,"营销机会数据添加失败");
    }

    /**
     * 基本参数校验
     * @param customerName
     * @param linkMan
     * @param linkPhone
     */
    private void checkParams(String customerName,String linkMan,String linkPhone){
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"请输入客户名！");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"请输入联系人！");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"请输入手机号！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone),"手机号格式不正确！");
    }

    /**
     * 营销机会数据更新
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChance(SaleChance saleChance){
        //1.参数校验
        //通过id查询记录
        SaleChance temp = saleChanceMapper.selectByPrimaryKey(saleChance.getId());
        //判断是否为空
        AssertUtil.isTrue(null == temp , "待更新记录不存在");
        //校验基础参数
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());

        //2.设置相关参数值
        saleChance.setUpdateDate(new Date());
        if(StringUtils.isBlank(temp.getAssignMan()) && StringUtils.isBlank(saleChance.getAssignMan())){
            //如果原始记录未分配，修改后改为已分配
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setAssignTime(new Date());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
        }else if(StringUtils.isNotBlank(temp.getAssignMan()) && StringUtils.isBlank(saleChance.getAssignMan())){
            //如果原始记录已分配，修改后改为未分配
            saleChance.setAssignMan("");
            saleChance.setState(StateStatus.UNSTATE.getType());
            saleChance.setAssignMan(null);
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
        }

        //3.执行更新  判断结果
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance)<1,"营销机会数据更新失败！");
    }

    /**
     * 营销机会数据删除
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSaleChance (Integer[] ids){
        //判断要删除的id是否为空
        AssertUtil.isTrue(null == ids || ids.length == 0,"请选择需要删除的数据！");
        //删除数据
        AssertUtil.isTrue(saleChanceMapper.deleteBatch(ids)<0,"营销机会数据和删除失败！");
    }

    /**
     * 更新营销机会的状态
     *       成功 = 2
     *       失败 = 3
     * @param id
     * @param devResult
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSaleChanceDevResult(Integer id,Integer devResult){
        AssertUtil.isTrue(null == id,"待更新记录不存在!");
        SaleChance temp = selectByPrimaryKey(id);
        AssertUtil.isTrue(null == temp,"待更新记录不存在!");
        temp.setDevResult(devResult);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"机会数据更新失败!");
    }

}
