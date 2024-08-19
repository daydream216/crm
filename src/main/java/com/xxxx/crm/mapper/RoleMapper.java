package com.xxxx.crm.mapper;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {
    //查询角色列表
    public List<Map<String,Object>> queryAllRoles(Integer id);


    public Role  queryRoleByRoleName(String roleName);
}
