package com.masiis.shop.admin.controller.order;

import com.masiis.shop.admin.service.order.OrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 店铺订单
 * Created by cai_tb on 16/4/14.
 */
@Controller
@RequestMapping("/order/order")
public class SfOrderController {

    private final static Log log = LogFactory.getLog(SfOrderController.class);

    @Resource
    private OrderService orderService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "order/order/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortOrder){

        Map<String, Object> conditionMap = new HashMap<>();
        try {

            Map<String, Object> pageMap = orderService.listByCondition(pageNumber, pageSize, conditionMap);
            return pageMap;
        } catch (Exception e) {
            log.error("获取店铺订单列表失败![conditionMap="+conditionMap+"]");
            e.printStackTrace();
        }

        return "error";
    }
}
