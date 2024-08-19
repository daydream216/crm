package com.xxxx.crm.query;

import com.xxxx.crm.base.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerOrderQuery extends BaseQuery {
    private Integer cusId;

    private String orderNo;

    private Integer state;
}
