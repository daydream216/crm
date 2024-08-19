package com.xxxx.crm.query;

import com.xxxx.crm.base.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQuery extends BaseQuery {
    //用户名
    private String userName;
    //邮箱
    private String email;
    //电话
    private String phone;
}
