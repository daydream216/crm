package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.CustomerReprQuery;
import com.xxxx.crm.service.CustomerReprService;
import com.xxxx.crm.vo.CustomerReprieve;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer_rep")
public class CustomerReprController extends BaseController {

    @Resource
    private CustomerReprService customerReprService;

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerReprsByParams(CustomerReprQuery customerReprQuery){
        return  customerReprService.queryCustomerReprsByParams(customerReprQuery);
    }


    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCustomerRepr(CustomerReprieve customerReprieve){
        customerReprService.saveCustomerRepr(customerReprieve);
        return  success("暂缓记录添加成功!");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCustomerRepr(CustomerReprieve customerReprieve){
        customerReprService.updateCustomerRepr(customerReprieve);
        return  success("暂缓记录更新成功!");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteCustomerRepr(Integer id){
        customerReprService.deleteCustomerRepr(id);
        return  success("暂缓记录删除成功!");
    }
}
