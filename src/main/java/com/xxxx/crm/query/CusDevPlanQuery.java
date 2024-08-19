package com.xxxx.crm.query;

import com.xxxx.crm.base.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CusDevPlanQuery extends BaseQuery {
    //营销机会id
    private Integer sid;
}
