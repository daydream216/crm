package com.xxxx.crm.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CustomerServe {
    private Integer id;

    private String serveType;

    private String overview;

    private String customer;

    private String state;

    private String serviceRequest;

    private String createPeople;

    private String assigner;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date assignTime;

    private String serviceProce;

    private String serviceProcePeople;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date serviceProceTime;

    private String serviceProceResult;

    private String myd;

    private Integer isValid;

    private Date updateDate;

    private Date createDate;

    // 服务类型
    private String dicValue;

}