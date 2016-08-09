package com.masiis.shop.web.mall.controller.activity;

import com.masiis.shop.web.mall.controller.base.BaseController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Date 2016/8/8
 * @Author lzh
 */
@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @RequestMapping("/qixi/seckill.shtml")
    public String qixiSeckill(){
        return "mall/activity/qixi_seckill_end";
    }
}
