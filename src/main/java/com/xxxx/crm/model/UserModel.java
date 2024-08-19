package com.xxxx.crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 定义 UserModel 实体类，⽤来返回登录成功后的⽤⼾信息
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private String userIdStr;
    private String userName;
    private String trueName;

}
