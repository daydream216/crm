package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.exceptions.ParamsException;
import com.xxxx.crm.model.UserModel;
import com.xxxx.crm.query.UserQuery;
import com.xxxx.crm.service.UserService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.utils.Md5Util;
import com.xxxx.crm.vo.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gkt
 * @since 2024-08-08
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * ⽤户登录
     * @param userName
     * @param userPwd
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ResultInfo userLogin(String userName, String userPwd){
        ResultInfo resultInfo = new ResultInfo();

        System.out.println(userName + "---" + userPwd);
        // 调⽤Service层的登录⽅法，得到返回的⽤户对象
        UserModel userModel = userService.userLogin(userName,userPwd);
            /**
             * 登录成功后，有两种处理：
             * 1. 将⽤户的登录信息存⼊ Session （ 问题：重启服务器，Session 失效，客户端需要重复登录 ）
             * 2. 将⽤户信息返回给客户端，由客户端（Cookie）保存
             */
        // 将返回的UserModel对象设置到 ResultInfo 对象中
        resultInfo.setResult(userModel);

        return resultInfo;
    }

    /**
     * ⽤户密码修改
     * @param req
     * @param oldPassword
     * @param newPassword
     * @param confirmPassword
     * @return
     */
    @PostMapping("/updatePassword")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest req,String oldPassword,String newPassword,String confirmPassword){
        ResultInfo resultInfo = new ResultInfo();
        //获取userId
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(req);
        //调用Service层的密码修改方法
        userService.updateUserPassword(userId,oldPassword,newPassword,confirmPassword);
        return resultInfo;
    }

    @RequestMapping("/toPasswordPage")
    public String toPasswordPage(){

        return "user/password";
    }

    /**
     * 查询所有销售人员
     * @return
     */
    @RequestMapping("/queryAllSales")
    @ResponseBody
    public List<Map<String,Object>> queryAllSales(){
        return userService.queryAllSales();
    }

    @RequestMapping("/queryAllCustomerManager")
    @ResponseBody
    public List<Map<String,Object>> queryAllCustomerManager(){
        return userService.queryAllSales();
    }

    /**
     * 多条件查询用户数据
     * @param userQuery
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> queryUserByParams(UserQuery userQuery){
        return userService.queryUserByParams(userQuery);
    }

    /**
     * 进入用户界面
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "user/user";
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResultInfo saveUser(User user){
        userService.saveUser(user);
        return success("用户添加成功!");
    }


    /**
     * 更新用户
     * @param user
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResultInfo updateUser(User user){
        userService.updateUser(user);
        return success("用户更新成功!");
    }

    /**
     * 进入用户添加或更新页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateUserPage")
    public String addOrUpdate(Integer id, Model model){
        if(null != id){
            model.addAttribute("newUser",userService.selectByPrimaryKey(id));
        }
        return "user/add_update";
    }

    /**
     * 删除用户
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteUserByIds(ids);
        return success("用户记录删除成功!");
    }


}
