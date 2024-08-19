package com.xxxx.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xxxx.crm.base.BaseService;
import com.xxxx.crm.mapper.UserRoleMapper;
import com.xxxx.crm.model.UserModel;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.utils.AssertUtil;
import com.xxxx.crm.utils.Md5Util;
import com.xxxx.crm.utils.PhoneUtil;
import com.xxxx.crm.utils.UserIDBase64;
import com.xxxx.crm.vo.User;
import com.xxxx.crm.mapper.UserMapper;
import com.xxxx.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gkt
 * @since 2024-08-08
 */
@Service
public class UserService extends BaseService<User,Integer>{

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserModel userLogin(String userName,String userPwd){
        //1.验证参数
        checkLoginParams(userName,userPwd);
        //2.根据用户名，查询用户对象
        User user = userMapper.queryUserByUserName(userName);
        //3.判断⽤户是否存在 (⽤户对象为空，记录不存在，⽅法结束)
        AssertUtil.isTrue(null == user,"用户不存则或已注销");
        //4.⽤户对象不为空（⽤户存在，校验密码。密码不正确，⽅法结束）
        checkLoginPwd(userPwd, user.getUserPwd());
        // 5. 密码正确（⽤户登录成功，返回⽤户的相关信息）
        return buildUserInfo(user);
    }

    /**
     * 构建返回的⽤户信息
     * @param user
     * @return
     */
    private UserModel buildUserInfo(User user){
        UserModel userModel = new UserModel();
        //设置用户信息
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());

        return userModel;
    }

    /**
     * 验证登录密码
     * @param userPwd 前台传递的密码
     * @param upwd    数据库中查询到的密码
     */
    private void checkLoginPwd(String userPwd,String upwd){
        // 数据库中的密码是经过加密的，将前台传递的密码先加密，再与数据库中的密码作⽐较
//        userPwd = Md5Util.encode(userPwd);
        // ⽐较密码
        AssertUtil.isTrue(!userPwd.equals(upwd),"用户密码不正确！");
    }

    /**
     * 验证⽤户登录参数
     * @param userName
     * @param userPwd
     */
    private void checkLoginParams(String userName,String userPwd){
        // 判断姓名
        AssertUtil.isTrue(StringUtils.isBlank(userName), "⽤户姓名不能为空！");
        // 判断密码
        AssertUtil.isTrue(StringUtils.isBlank(userPwd), "⽤户密码不能为空！");
    }

    /**
     * ⽤户密码修改
     * 1. 参数校验
     * ⽤户ID：userId ⾮空 ⽤户对象必须存在
     * 原始密码：oldPassword ⾮空 与数据库中密⽂密码保持⼀致
     * 新密码：newPassword ⾮空 与原始密码不能相同
     * 确认密码：confirmPassword ⾮空 与新密码保持⼀致
     * 2. 设置⽤户新密码
     * 新密码进⾏加密处理
     * 3. 执⾏更新操作
     * 受影响的⾏数⼩于1，则表示修改失败
     *
     * 注：在对应的更新⽅法上，添加事务控制
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserPassword(Integer userId,String oldPassword,String newPassword,String confirmPassword){
        //通过userId获取用户对象
        User user = userMapper.selectByPrimaryKey(userId);
        //1.参数校验
        checkPasswordParams(user,oldPassword,newPassword,confirmPassword);
        //2.设置用户新密码
        user.setUserPwd(newPassword);
        //3.执行更新操作
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "⽤户密码更新失败！");
    }

    /**
     * 验证⽤户密码修改参数
     * * ⽤户ID：userId ⾮空 ⽤户对象必须存在
     * * 原始密码：oldPassword ⾮空 与数据库中密⽂密码保持⼀致
     * * 新密码：newPassword ⾮空 与原始密码不能相同
     * * 确认密码：confirmPassword ⾮空 与新密码保持⼀致
     * @param user
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     */
    private void checkPasswordParams(User user,String oldPassword,String newPassword,String confirmPassword){
        //user对象 非空验证
        AssertUtil.isTrue(null == user,"用户未登录或不存在");
        //原始密码 非空验证
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"请输入原始密码！");
        //原始密码要与数据库的密文密码报持一致
        AssertUtil.isTrue(!(user.getUserPwd().equals(oldPassword)),"原始密码不正确！");
        // 新密码 ⾮空校验
        AssertUtil.isTrue(StringUtils.isBlank(newPassword), "请输⼊新密码！");
        // 新密码与原始密码不能相同
        AssertUtil.isTrue(oldPassword.equals(newPassword), "新密码不能与原始密码相同！");
        // 确认密码 ⾮空校验
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword), "请输⼊确认密码！");
        // 新密码要与确认密码保持⼀致
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)), "新密码与确认密码不⼀致！");
    }

    /**
     * 查询所有的销售人员
     * @return
     */
    public List<Map<String,Object>> queryAllSales(){
        return userMapper.queryAllSales();
    }

    /**
     * 多条件分页查询用户数据
     * @param query
     * @return
     */
    public Map<String,Object> queryUserByParams(UserQuery query){
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(query.getPage(),query.getLimit());
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectByParams(query));
        map.put("code",0);
        map.put("msg","");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }

    /**
     * 添加用户
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(User user){
        //1.参数校验
        checkParams(user.getUserName(),user.getEmail(), user.getPhone());
        AssertUtil.isTrue(null !=userMapper.queryUserByUserName(user.getUserName()),"用户名不能重复!");
        //2，设置默认参数
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd("123456");
        //3.执行添加，判断结果
        AssertUtil.isTrue(userMapper.insertSelective(user)<1,"用户添加失败!");

        // 用户角色管理(t_user_role)    user_id   role_id
        // 获取添加的用户id 主键
        Integer userId = userMapper.queryUserByUserName(user.getUserName()).getId();
        // 10,20,30
        String roleIds = user.getRoleIds();
        /**
         * 批量添加用户角色记录到用户角色表
         */
        relationUserRoles(userId,roleIds);
    }

    /**
     * 用户角色管理
     * @param userId
     * @param roleIds
     */
    private void relationUserRoles(int userId,String roleIds){
        /**
         *  用户修改(添加同样适用)时
         *     用户原始的角色记录
         *       存在
         *          *          81    (1,2)-->81  null
         *          *          81   (1,2)  -->81  1,2,3,4
         *          *          81  (1,2)-->81 2
         *       不存在
         *          直接执行批量添加(选择角色记录)
         *   推荐方案--> 首先将用户原始用户角色记录删除(存在情况)  然后加入修改后的用户角色记录(选择角色记录)
         */
       int count = userRoleMapper.countUserRoleByUserId(userId);
       if(count>0){
           AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId)!=count,"用户角色记录关联失败!");
       }

       if(StringUtils.isNotBlank(roleIds)){
           List<UserRole> userRoles = new ArrayList<>();
           for(String s : roleIds.split(",")){
               UserRole userRole = new UserRole();
               userRole.setCreateDate(new Date());
               userRole.setRoleId(Integer.parseInt(s));
               userRole.setUpdateDate(new Date());
               userRole.setUserId(userId);
               userRoles.add(userRole);
           }
           AssertUtil.isTrue(userRoleMapper.insertBatch(userRoles)!=userRoles.size(),"用户角色记录管理失败!");
       }
    }

    /**
     * 参数校验
     * @param userName
     * @param email
     * @param phone
     */
    private void checkParams(String userName,String email,String phone){
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空!");
        //验证用户名是否存在
        User temp = userMapper.queryUserByUserName(userName);
        AssertUtil.isTrue(StringUtils.isBlank(email),"请输入邮箱地址!");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"手机号码格式不正确!");
    }

    /**
     * 更新用户
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user){
        /**
         * 1.参数校验
         *     id 记录存在
         *     用户名 非空 值唯一
         *     email  非空  格式合法
         *     手机号非空  格式合法
         * 2.默认参数设置
         *     updateDate 系统时间
         * 3.执行更新
         */
        User temp =selectByPrimaryKey(user.getId());
        AssertUtil.isTrue(null==temp,"待更新多的用户记录不存在!");
        checkParams(user.getUserName(),user.getEmail(),user.getPhone());
        temp = userMapper.queryUserByUserName(user.getUserName());
        AssertUtil.isTrue(null !=temp && !(temp.getId().equals(user.getId())),"该用户已存在1!");
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"用户记录更新失败!");

        /**
         *  用户修改时
         *     用户原始的角色记录
         *       存在
         *          *          81    (1,2)-->81  null
         *          *          81   (1,2)  -->81  1,2,3,4
         *          *          81  (1,2)-->81 2
         *       不存在
         *          直接执行批量添加(选择角色记录)
         *   推荐方案--> 首先将用户原始用户角色记录删除(存在情况)  然后加入修改后的用户角色记录(选择角色记录)
         */
        relationUserRoles(user.getId(),user.getRoleIds());
    }

    /**
     * 删除用户
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserByIds(Integer[] ids){
        AssertUtil.isTrue(null == ids || ids.length == 0,"请选择待删除的用户记录!");
        AssertUtil.isTrue(deleteBatch(ids) != ids.length,"用户记录删除失败!");
    }
}
