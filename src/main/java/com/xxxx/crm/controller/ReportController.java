package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("report")
public class ReportController extends BaseController {


    @RequestMapping("/{type}")
    public String index(@PathVariable Integer type){
        if(type==0){
            // 客户贡献分析页面
            return "report/customer_contri";
        }else if(type==1 ){
            // 客户构成页面
            return "report/customer_make";
        }else if(type==3 ){
            // 客户流失分析页面
            return "report/customer_loss";
        }else{
            return "";
        }
    }
}
