package com.xxxx.crm.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserRole {
    private Integer id;

    private Integer userId;

    private Integer roleId;

    private Date createDate;

    private Date updateDate;
}
