package com.xxxx.crm.query;

import com.xxxx.crm.base.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerQuery extends BaseQuery {
    private String cusName;

    private String cusNo;

    private String level;

    private String myd;

    //1 [0-1000] 2 [1000-3000] 3 [3000-5000] 4 [5000]
    private String type;

    private String time;

}
