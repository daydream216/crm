package com.xxxx.crm.dto;

import lombok.Data;

@Data
public class TreeDto {
    private Integer id;
    private Integer pId;
    private String name;
    private Boolean checked=false;
}
