package com.masiis.shop.web.platform.controller.system;

import com.masiis.shop.web.platform.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Date 2016/8/4
 * @Author lzh
 */
@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {


    @RequestMapping("/qixi")
    public String qixiActivity(){
        return "platform/activity/qixi";
    }

}
