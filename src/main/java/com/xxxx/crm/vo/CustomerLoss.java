package com.xxxx.crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CustomerLoss {
    private Integer id;

    private String cusNo;

    private String cusName;

    private String cusManager;

    private Date lastOrderTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date confirmLossTime;

    private Integer state;

    private String lossReason;

    private Integer isValid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateDate;
}
