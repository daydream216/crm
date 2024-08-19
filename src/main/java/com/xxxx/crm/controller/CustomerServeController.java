package com.xxxx.crm.controller;


import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.CustomerServeQuery;

import com.xxxx.crm.service.CustomerServeService;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.CustomerServe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("customer_serve")
public class CustomerServeController extends BaseController {

    @Resource
    private CustomerServeService customerServeService;


    @RequestMapping("index/{type}")
    public String index(@PathVariable Integer type){
        if(null !=type){
            if(type==1){
                return "customerServe/customer_serve";
            }else if(type==2){
                return "customerServe/customer_serve_assign";
            }else if(type==3){
                return "customerServe/customer_serve_proce";
            } else if(type==4){
                return "customerServe/customer_serve_feed_back";
            }else if(type==5){
                return "customerServe/customer_serve_archive";
            } else{
                return "";
            }
        }else {
            return "";
        }
    }


    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerServesByParams(Integer flag, HttpServletRequest request, CustomerServeQuery customerServeQuery){
        if(null !=flag && flag==1){
            // 按照分配人执行查询
           customerServeQuery.setAssigner(LoginUserUtil.releaseUserIdFromCookie(request));
        }
        return  customerServeService.queryCustomerServesByParams(customerServeQuery);
    }




    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCustomerServe(CustomerServe customerServe){
        customerServeService.saveCustomerServe(customerServe);
        return success("客户服务记录创建成功");
    }

    @RequestMapping("addCustomerServePage")
    public String addCustomerServePage(){
        return "customerServe/customer_serve_add";
    }


    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCustomerServe(CustomerServe customerServe){
        customerServeService.updateCustomerServe(customerServe);
        return success("客户服务记录更新成功");
    }


    @RequestMapping("addCustomerServeAssignPage")
    public String addCustomerServeAssignPage(Integer id, Model model){
        model.addAttribute("customerServe",customerServeService.selectByPrimaryKey(id));
        return "customerServe/customer_serve_assign_add";
    }


    @RequestMapping("addCustomerServeProcePage")
    public String addCustomerServeProcePage(Integer id, Model model){
        model.addAttribute("customerServe",customerServeService.selectByPrimaryKey(id));
        return "customerServe/customer_serve_proce_add";
    }

    @RequestMapping("addCustomerServeBackPage")
    public String addCustomerServeBackPage(Integer id, Model model){
        model.addAttribute("customerServe",customerServeService.selectByPrimaryKey(id));
        return "customerServe/customer_serve_feed_back_add";
    }

}
