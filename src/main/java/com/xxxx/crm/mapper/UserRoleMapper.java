package com.xxxx.crm.mapper;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {

    public int countUserRoleByUserId(Integer userId);

    public int deleteUserRoleByUserId(Integer userId);

}
