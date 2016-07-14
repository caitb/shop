package com.masiis.shop.web.mall.controller.promotion.gift;

import com.masiis.shop.dao.po.ComGift;
import com.masiis.shop.web.common.service.ComGiftService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 *  奖品
 */
@Controller
public class ComGiftController {

    @Resource
    private ComGiftService comGiftService;

    @RequestMapping("/getGiftDetailInfo.do")
    @ResponseBody
    public String getGiftDetailInfo(@RequestParam(required = false) Integer giftId, Model model){
        ComGift comGift = comGiftService.getComGiftById(giftId);
        model.addAttribute("comGift",comGift);
        return null;
    }
}
