package com.xxxx.crm.controller;

import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.CustomerQuery;
import com.xxxx.crm.service.CustomerOrderService;
import com.xxxx.crm.service.CustomerService;
import com.xxxx.crm.service.OrderDetailsService;
import com.xxxx.crm.vo.Customer;
import com.xxxx.crm.vo.Module;
import com.xxxx.crm.vo.OrderDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {
    @Resource
    private CustomerService customerService;

    @Resource
    private CustomerOrderService customerOrderService;

    @Resource
    private OrderDetailsService orderDetailsService;

    @RequestMapping("index")
    public String index(){
        return "customer/customer";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomersByParams(CustomerQuery customerQuery){
        return customerService.queryByParamsForTable(customerQuery);
    }

    @RequestMapping("addOrUpdateCustomerPage")
    public String addOrUpdateCustomerPage(Integer id, Model model){
        model.addAttribute("customer",customerService.selectByPrimaryKey(id));
        return "customer/add_update";
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveCustomer(Customer customer){
        customerService.saveCustomer(customer);
        return success("客户添加成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateCustomer(Customer customer){
        customerService.updateCustomer(customer);
        return success("客户更新成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteCustomer(Integer cid){
        customerService.deleteCustomer(cid);
        return success("客户删除成功");
    }

    @RequestMapping("orderInfoPage")
    public String showOrderInfo(Integer cid, Model model){
        model.addAttribute("customer",customerService.selectByPrimaryKey(cid));
        System.out.println(customerService.selectByPrimaryKey(cid));
        return "customer/customer_order";
    }

    @RequestMapping("orderDetailPage")
    public String orderDetailPage(Integer orderId,Model model){

        model.addAttribute("order",customerOrderService.selectByPrimaryKey(orderId));
        return "customer/customer_order_detail";
    }

    /**
     * 客户贡献分析
     */
    @RequestMapping("queryCustomerContributionByParams")
    @ResponseBody
    public Map<String,Object> queryCustomerContributionByParams(CustomerQuery customerQuery){
        return customerService.queryCustomerContributionByParams(customerQuery);
    }

    @RequestMapping("countCustomerMake")
    @ResponseBody
    //折线图接口地址
    public Map<String,Object> countCustomerMake(){
        return customerService.countCustomerMake();
    }

    @RequestMapping("countCustomerMake02")
    @ResponseBody
    //饼状图接口地址
    public Map<String,Object> countCustomerMake02(){
        return customerService.countCustomerMake02();
    }
}
