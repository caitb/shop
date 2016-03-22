package com.masiis.shop.admin.controller.order;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.beans.order.Order;
import com.masiis.shop.admin.service.order.COrderService;
import com.masiis.shop.dao.po.PfCorder;
import com.masiis.shop.dao.po.PfCorderFreight;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

    private final static Log log = LogFactory.getLog(PfCorderController.class);

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

        try {
            Map<String, Object> pageMap = cOrderService.listByCondition(pageNumber, pageSize, pfCorder);

            return pageMap;
        } catch (Exception e) {
            log.error("查询试用订单列表出错![pageNumber="+pageNumber+"][pageSize="+pageSize+"][pfCorder="+pfCorder+"]");
            e.printStackTrace();

            return "查询试用订单列表出错!";
        }
    }

    @RequestMapping("/detail.shtml")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, Long corderId){

        ModelAndView mav = new ModelAndView("order/corder/detail");

        Order order = cOrderService.find(corderId);

        mav.addObject("order", order);

        return mav;
    }

    @RequestMapping("/delivery.do")
    @ResponseBody
    public Object delivery(HttpServletRequest request, HttpServletResponse response,
                           PfCorderFreight pfCorderFreight){

        if (pfCorderFreight.getShipManId() == null){
            return "请选择一个快递";
        }
        if(StringUtils.isBlank(pfCorderFreight.getFreight())){
            return "请填写运单号";
        }

        cOrderService.delivery(pfCorderFreight);

        return "success";
    }
}
