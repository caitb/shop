package com.masiis.shop.admin.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by cai_tb on 16/3/12.
 */
@Controller
@RequestMapping("/order/trial")
public class TrialOrderController {

    @RequestMapping("/list.shtml")
    public String list(){
        return "/order/trial/list";
    }
}
