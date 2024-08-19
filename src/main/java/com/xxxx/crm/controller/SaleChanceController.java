package com.xxxx.crm.controller;


import com.xxxx.crm.base.BaseController;
import com.xxxx.crm.base.ResultInfo;
import com.xxxx.crm.query.SaleChanceQuery;
import com.xxxx.crm.service.SaleChanceService;
import com.xxxx.crm.utils.CookieUtil;
import com.xxxx.crm.utils.LoginUserUtil;
import com.xxxx.crm.vo.SaleChance;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
 * @since 2024-08-09
 */
@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @GetMapping("/list")
    @ResponseBody
    //101001
//    @RequirePermission(code = "101001")
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery query,Integer flag,HttpServletRequest req){
        //查询参数 flag=1 代表当前查询为开发计划数据，设置查询分配人参数
        if(null != flag && flag == 1){
            //获取当前登录用户的ID
            Integer userId = LoginUserUtil.releaseUserIdFromCookie(req);
            query.setAssignMan(userId);
        }
        return saleChanceService.querySaleChanceByParams(query);
    }

    /**
     * 进⼊营销机会⻚⾯
     * @return
     */
    //1010
    @RequestMapping("/index")
//    @RequirePermission(code = "1010")
    public String index () {
        return "saleChance/sale_chance";
    }

    /**
     * 添加营销机会数据
     * @param req
     * @param saleChance
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    //101002
//    @RequirePermission(code = "101002")
    public ResultInfo saveSaleChance(HttpServletRequest req, SaleChance saleChance){
        //从cookie中获取用户姓名
        String userName = CookieUtil.getCookieValue(req,"userName");
        //设置营销机会的创建人
        saleChance.setCreateMan(userName);
        //添加营销机会的数据
        saleChanceService.saveSaleChance(saleChance);
        return success("营销机会数据添加成功");

    }

    /**
     * 机会数据添加与更新页面视图转发
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("addOrUpdateSaleChancePage")
    public String addOrUpdateSaleChancePage(Integer id, Model model){
        //如果id不为空，表示是修改操作，修改操作需要查询被修改的数据
        if(null != id){
            //通过主键查询营销机会数据
            SaleChance saleChance = saleChanceService.selectByPrimaryKey(id);
            //将数据存到作用域中
            model.addAttribute("saleChance",saleChance);
        }
        return "saleChance/add_update";
    }

    @RequestMapping("update")
    @ResponseBody
    //101004
//    @RequirePermission(code = "101004")
    public ResultInfo updateSaleChance(SaleChance saleChance){
        //更新营销机会的数据
        saleChanceService.updateSaleChance(saleChance);
        return success("营销机会数据更新成功");
    }

    /**
     * 删除营销机会数据
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    //101003
//    @RequirePermission(code = "101003")
    public ResultInfo deleteSaleChance(Integer[] ids){
        //删除营销机会的数据
        saleChanceService.deleteBatch(ids);
        return success("营销机会数据删除成功！");
    }

    /**
     * 更新营销机会的开发状态
     * @param id
     * @param devResult
     * @return
     */
    @RequestMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer id,Integer devResult){
        saleChanceService.updateSaleChanceDevResult(id,devResult);
        return success("开发状态更新成功!");
    }

}
