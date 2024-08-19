package com.xxxx.crm.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Module {
    private Integer id;

    private String moduleName;

    private String moduleStyle;

    private String url;

    private Integer parentId;

    private String parentOptValue;

    private Integer grade;

    private String optValue;

    private Integer orders;

    private Byte isValid;

    private Date createDate;

    private Date updateDate;
}
