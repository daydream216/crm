package com.xxxx.crm.mapper;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.vo.User;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gkt
 * @since 2024-08-08
 */
public interface UserMapper extends BaseMapper<User,Integer> {

    //根据用户名查询用户对象
    User queryUserByUserName(String userName);

    //查询所有的销售人员
    public List<Map<String,Object>> queryAllSales();



}
