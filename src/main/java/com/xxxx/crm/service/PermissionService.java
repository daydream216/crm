package com.xxxx.crm.service;

import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.mapper.PermissionMapper;
import com.xxxx.crm.vo.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionService extends BaseService<Permission,Integer> {
    @Resource
    private PermissionMapper permissionMapper;

    public List<String> queryUserHasRolesHasPermissions(Integer userId){
        return permissionMapper.queryUserHasRoleIdsHasPermissions(userId);
    }
}
