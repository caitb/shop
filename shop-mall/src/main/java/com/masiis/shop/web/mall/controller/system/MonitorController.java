package com.masiis.shop.web.mall.controller.system;

import com.masiis.shop.web.mall.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Date 2016/5/16
 * @Auther lzh
 */
@Controller
@RequestMapping("/monitor")
public class MonitorController extends BaseController {


    @RequestMapping("/mallmonitor")
    @ResponseBody
    public String webMonitor(){
        return "success";
    }
}
