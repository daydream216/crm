package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.CustomerLossQuery;
import com.xxxx.crm.service.CustomerLossService;
import com.xxxx.crm.service.CustomerReprService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer_loss")
public class CustomerLossController extends BaseController {

    @Resource
    private CustomerLossService customerLossService;

    @Resource
    private CustomerReprService customerReprService;

    @RequestMapping("index")
    public String index(){
        return "customerLoss/customer_loss";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerLossByParams(CustomerLossQuery customerLossQuery){
        return customerLossService.queryCustomerLosssByParams(customerLossQuery);
    }

    @RequestMapping("toCustomerReprPage")
    public String toCustomerReprPage(Integer id, Model model){
        model.addAttribute("customerLoss",customerLossService.selectByPrimaryKey(id));
        return "customerLoss/customer_rep";
    }

    @RequestMapping("addOrUpdateCustomerReprPage")
    public String addOrUpdateCustomerReprPage(Integer id,Integer lossId,Model model){
        model.addAttribute("customerRep",customerReprService.selectByPrimaryKey(id));
        model.addAttribute("lossId",lossId);
        return "customerLoss/customer_rep_add_update";
    }


    @RequestMapping("updateCustomerLossStateById")
    @ResponseBody
    public ResultInfo updateCustomerLossStateById(Integer id, String lossReason){
        customerLossService.updateCustomerLossStateById(id,lossReason);
        return success("客户确认流失成功!");
    }
}
