package com.xxxx.crm.mapper;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.CustomerOrder;

public interface CustomerOrderMapper extends BaseMapper<CustomerOrder,Integer> {
    public CustomerOrder  queryLastCustomerOrderByCusId(Integer cusId);
}
