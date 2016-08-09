package com.masiis.shop.web.mall.controller.activity;

import com.masiis.shop.web.mall.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Date 2016/8/9
 * @Author lzh
 */
@Controller
@RequestMapping("/popularize")
public class PopularizeController extends BaseController {

    @RequestMapping("/shop/{qrCodeName}")
    public String shopQrCodePopularize(@PathVariable("qrCodeName") String qrCodeName,
                                       Model model){
        model.addAttribute("qrCodeName", qrCodeName);
        return "mall/popularize/shop_popularize";
    }
}
