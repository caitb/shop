package com.masiis.shop.admin.controller.order;

import com.masiis.shop.admin.controller.base.BaseController;
import com.masiis.shop.admin.service.order.OrderQueueDealService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单排单处理controller
 * Created by wangbingjian on 2016/4/1.
 */
@Controller
@RequestMapping(value = "/orderQueue")
public class OrderQueueController extends BaseController{
    private static final Logger log = Logger.getLogger(OrderQueueController.class);

    @Autowired
    private OrderQueueDealService orderQueueDealService;

    @RequestMapping(value = "deal")
    public void queueDeal(HttpServletRequest request) throws Exception {

        Map<Long,String> map = new HashMap<>();
        map.put(Long.valueOf(115),"1");
        map.put(Long.valueOf(116),"2");
        try{
            map = orderQueueDealService.commonQueuingOrder(map);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("================="+map.get(Long.valueOf(115)));
        System.out.println("================="+map.get(Long.valueOf(116)));
    }
}
