package com.xxxx.crm.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.mapper.CustomerMapper;
import com.xxxx.crm.query.CustomerQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.PhoneUtil;
import com.xxxx.crm.vo.Customer;
import com.xxxx.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CustomerService extends BaseService<Customer,Integer> {

    @Resource
    private CustomerMapper customerMapper;
//     进入页面并执行一次分页
    public Map<String,Object> queryByParamsForTable(CustomerQuery query){
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(),query.getLimit());
        PageInfo<Customer> pageInfo = new PageInfo<>(customerMapper.selectByParams(query));
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }
//     添加客户
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCustomer(Customer customer){
        checkParams(customer.getName(),customer.getPhone(),customer.getFr());
        AssertUtil.isTrue(null != customerMapper.queryCustomerByName(customer.getName()),"该客户已存在!");
        customer.setIsValid(1);
        customer.setState(0);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());

        String khno = "KH_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        customer.setKhno(khno);
        AssertUtil.isTrue(customerMapper.insertSelective(customer)<1,"客户添加失败!");
    }

    private void checkParams(String name,String phone,String fr){
        AssertUtil.isTrue(StringUtils.isBlank(name),"请指定客户名称!");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(phone)),"手机号格式非法!");
        AssertUtil.isTrue(StringUtils.isBlank(fr),"请指定公司法人!");
    }
//    更新客户
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomer(Customer customer){
        AssertUtil.isTrue(null == customer.getId() || null == selectByPrimaryKey(customer.getId()),"待更新记录不存在!");
        checkParams(customer.getName(),customer.getPhone(),customer.getFr());

        Customer temp = customerMapper.queryCustomerByName(customer.getName());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(customer.getId())),"该客户已存在!");
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(customer)<1,"客户更新失败!");
    }

//    删除客户
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCustomer(Integer cid){
        Customer customer = selectByPrimaryKey(cid);
        AssertUtil.isTrue(null==cid||null==customer,"待删除记录不存在!");
        /**
         * 如果客户被删除
         * 级联 客户联系人 客户交往记录 客户订单 被删除
         *
         * 如果客户被删除
         * 如果子表存在记录 不支持删除
         */
        customer.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(customer)<1,"客户删除失败!");
    }

    /**
     * 客户贡献度分析
     * @param query
     * @return
     */
    public Map<String,Object> queryCustomerContributionByParams(CustomerQuery query){
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(),query.getLimit());
        List<Map<String,Object>> list = customerMapper.queryCustomerContributionByParams(query);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(list);
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    //折线图数据处理
    public Map<String,Object> countCustomerMake(){
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> list = customerMapper.countCustomerMake();
        List<String> data1List = new ArrayList<>();
        List<Integer> data2List = new ArrayList<>();
        list.forEach(m->{
            data1List.add(m.get("level").toString());
            data2List.add(Integer.parseInt(m.get("total")+""));
        });
        map.put("data1",data1List);
        map.put("data2",data2List);
        return map;
    }


    //饼状图数据处理
    public Map<String,Object> countCustomerMake02(){
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> list = customerMapper.countCustomerMake();
        List<String> data1List = new ArrayList<>();
        List<Map<String,Object>> data2List = new ArrayList<>();
        list.forEach(m->{
            data1List.add(m.get("level").toString());
            Map<String,Object> temp = new HashMap<>();
            temp.put("name",m.get("level"));
            temp.put("value",m.get("total"));
            data2List.add(temp);
        });
        map.put("data1",data1List);
        map.put("data2",data2List);
        return map;
    }
}
