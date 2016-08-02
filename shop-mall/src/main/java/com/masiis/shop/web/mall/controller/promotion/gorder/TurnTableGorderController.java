package com.masiis.shop.web.mall.controller.promotion.gorder;

import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.promotion.cpromotion.service.gorder.TurnTableGorderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by hzz on 2016/8/2.
 */
@Controller
@RequestMapping("/turnTableGorder")
public class TurnTableGorderController extends BaseController {

    @Resource
    private TurnTableGorderService turnTableGorderService;

    @RequestMapping("/getTurnTableGiftInfo.html")
    public String getTurnTableGiftInfo(@RequestParam(required = false) Long selectedAddressId,
                                       @RequestParam(required = true) Integer turnTableId,
                                       @RequestParam(required = true) Integer giftId,
                                       HttpServletRequest request,
                                       Model model) {
        Map<String,Object> map =  turnTableGorderService.getTurnTableGiftInfo(getComUser(request).getId(),selectedAddressId,turnTableId,giftId);
        model.addAttribute("comUserAddress",map.get("address"));
        model.addAttribute("turnTableGiftInfo",map.get("turnTableGiftInfo"));
        return "promotion/gorder/turnTableGiftReceive";
    }
}
