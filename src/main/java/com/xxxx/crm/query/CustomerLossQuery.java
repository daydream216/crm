package com.xxxx.crm.query;

import com.xxxx.crm.base.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerLossQuery extends BaseQuery {
    private String cusNo;

    private String cusName;

    private Integer state;
}
