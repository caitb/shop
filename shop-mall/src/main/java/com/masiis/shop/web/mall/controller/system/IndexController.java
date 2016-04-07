package com.masiis.shop.web.mall.controller.system;

import com.masiis.shop.web.mall.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Date:2016/4/7
 * @auth:lzh
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping("/index")
    @ResponseBody
    public String index(){
        return "index_controller";
    }
}
