package com.masiis.shop.web.platform.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Date 2016/8/29
 * @Author lzh
 */
@Controller
@RequestMapping("/apppro")
public class AppPromotionController {

    @RequestMapping("/download.shtml")
    public String toDownLoadView(){
        return "platform/activity/download_app";
    }
}
