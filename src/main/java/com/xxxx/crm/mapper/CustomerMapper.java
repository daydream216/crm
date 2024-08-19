package com.xxxx.crm.mapper;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.query.CustomerQuery;
import com.xxxx.crm.vo.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {

    Customer  queryCustomerByName(String name);

    public List<Customer> queryLossCustomers();

    int  updateCustomerStateByIds(List<Integer> lossCusIds);

    public List<Map<String,Object>> queryCustomerContributionByParams(CustomerQuery customerQuery);

    public List<Map<String,Object>>  countCustomerMake();
}
