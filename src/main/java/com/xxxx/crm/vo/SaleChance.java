package com.xxxx.crm.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author gkt
 * @since 2024-08-09
 */
@TableName("t_sale_chance")
@Data
public class SaleChance implements Serializable {

    private static final long serialVersionUID = 1L;

//    id主键
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

//    机会来源
    private String chanceSource;

//    客⼾名称
    private String customerName;

//    @ApiModelProperty("成功⼏率")
    private Integer cgjl;

//    @ApiModelProperty("概要")
    private String overview;

//    @ApiModelProperty("联系⼈")
    private String linkMan;

//    @ApiModelProperty("⼿机号")
    private String linkPhone;

//    @ApiModelProperty("描述")
    private String description;

//    @ApiModelProperty("创建⼈")
    private String createMan;

//    @ApiModelProperty("分配⼈")
    private String assignMan;

//    @ApiModelProperty("分配时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date assignTime;

//    @ApiModelProperty("分配状态")
    private Integer state;

//    @ApiModelProperty("开发结果")
    private Integer devResult;

//    @ApiModelProperty("有效状态")
    private Integer isValid;

//    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDate;

//    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateDate;

    //指派人
    private String uname;

}
