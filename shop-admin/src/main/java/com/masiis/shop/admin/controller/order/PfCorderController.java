package com.masiis.shop.admin.controller.order;

import com.masiis.shop.admin.service.order.COrderService;
import com.masiis.shop.dao.po.PfCorder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by cai_tb on 16/3/12.
 */
@Controller
@RequestMapping("/order/corder")
public class PfCorderController {

    @Resource
    private COrderService cOrderService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "order/corder/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       PfCorder pfCorder){

        Map<String, Object> pageMap = cOrderService.listByCondition(pageNumber, pageSize, pfCorder);

        return pageMap;
    }
}
