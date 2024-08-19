package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.mapper.ModuleMapper;
import com.xxxx.crm.mapper.PermissionMapper;
import com.xxxx.crm.mapper.RoleMapper;
import com.xxxx.crm.mapper.UserRoleMapper;
import com.xxxx.crm.query.RoleQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.vo.Permission;
import com.xxxx.crm.vo.Role;
import com.xxxx.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RoleService extends BaseService<Role,Integer> {
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private ModuleMapper moduleMapper;
    /**
     * 查询角色列表
     * @return
     */
    public List<Map<String,Object>> queryAllRoles(Integer id){
        return roleMapper.queryAllRoles(id);
    }

    public Map<String,Object> queryRolesByParams(RoleQuery roleQuery){
        Map<String,Object> map=new HashMap<String,Object>();
        PageHelper.startPage(roleQuery.getPage(),roleQuery.getLimit());
        PageInfo<Role> pageInfo=new PageInfo<Role>(selectByParams(roleQuery));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return  map;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRole(Role role){
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输入角色名!");
        Role temp = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(null != temp,"该角色已存在!");
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(role)<1,"角色记录添加失败!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRole(Role role){
        AssertUtil.isTrue(null == role.getId() || null == selectByPrimaryKey(role.getId()),"待修改的记录不存在!");
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输入角色名！");
        Role temp = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(null == temp && !(temp.getId().equals(role.getId())),"该角色已存在!");
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(role)<1,"角色记录更新失败!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRole(Integer roleId){
        Role temp = selectByPrimaryKey(roleId);
        AssertUtil.isTrue(null == roleId || null == temp,"待删除的记录不存在!");
        temp.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"角色记录删除失败!");
    }

    public void addGrant(Integer[] mids, Integer roleId) {
        /**
         *   授权思路  核心表 t_permission
         *      直接批量添加  不适合 有可能是对角色权限进行更新(权限更新后有可能添加新的菜单 删除原始菜单  甚至情况权限)
         *  推荐: 角色存在原始权限时  先删除原始权限记录  然后批量添加新的角色权限
         */
        int total = permissionMapper.countPermissionByRoleId(roleId);
        if(total>0){
            AssertUtil.isTrue(permissionMapper.deletePermissionByRoleId(roleId)!=total,"角色授权失败!");
        }

        if(null !=mids && mids.length>0){
            List<Permission> permissions=new ArrayList<Permission>();
            for(Integer mid:mids){
                Permission permission=new Permission();
                permission.setCreateDate(new Date());
                permission.setModuleId(mid);
                permission.setRoleId(roleId);
                permission.setUpdateDate(new Date());
                permission.setAclValue(moduleMapper.selectByPrimaryKey(mid).getOptValue());
                permissions.add(permission);
            }
            AssertUtil.isTrue(permissionMapper.insertBatch(permissions)!=permissions.size(),"角色授权失败!");
        }
    }
}
