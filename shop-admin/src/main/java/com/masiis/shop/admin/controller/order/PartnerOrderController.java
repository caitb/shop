package com.masiis.shop.admin.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by cai_tb on 16/3/12.
 */
@Controller
@RequestMapping("/order/partner")
public class PartnerOrderController {

    @RequestMapping("/list.shtml")
    public String list(){
        return "order/partner/list";
    }
}
