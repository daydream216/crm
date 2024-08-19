package com.xxxx.crm.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Permission {
    private Integer id;

    private Integer roleId;

    private Integer moduleId;

    private String aclValue;

    private Date createDate;

    private Date updateDate;
}
