package com.xxxx.crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDetails {
    private Integer id;

    private Integer orderId;

    private String goodsName;

    private Integer goodsNum;

    private String unit;

    private Float price;

    private Float sum;

    private Integer isValid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateDate;
}
