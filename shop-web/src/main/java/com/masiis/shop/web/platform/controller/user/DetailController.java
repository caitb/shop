package com.masiis.shop.web.platform.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lzh on 2016/2/27.
 */
@Controller
@RequestMapping("/detail")
public class DetailController {

    @RequestMapping("/tDetail")
    public String toDetail(){
        return "platform/details";
    }
}
