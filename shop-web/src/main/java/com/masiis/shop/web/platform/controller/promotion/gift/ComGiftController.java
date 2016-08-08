package com.masiis.shop.web.platform.controller.promotion.gift;

import com.masiis.shop.dao.beans.promotion.PromotionGiftInfo;
import com.masiis.shop.web.common.service.ComGiftService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 *  奖品
 */
@Controller
@RequestMapping("/comGift")
public class ComGiftController {

    @Resource
    private ComGiftService comGiftService;

    @RequestMapping("/getPromotionGiftDetailInfo.shtml")
    public String getPromotionGiftDetailInfo(@RequestParam(required = true) Integer giftId,
                                    @RequestParam(required = true) Integer promoId,
                                    @RequestParam(required = true) Integer promoRuleId,
                                    @RequestParam(required = true) Integer isMayReceive,
                                    Model model){
        PromotionGiftInfo promotionGiftInfo = comGiftService.getPromoGiftInfoByPromoIdAndRuleId(promoId,promoRuleId,giftId);
        model.addAttribute("promotionGiftInfo",promotionGiftInfo);
        model.addAttribute("promoId",promoId);
        model.addAttribute("promoRuleId",promoRuleId);
        model.addAttribute("giftId",giftId);
        model.addAttribute("isMayReceive",isMayReceive);
        return "promotion/guser/promotionGiftDetail";
    }
}
