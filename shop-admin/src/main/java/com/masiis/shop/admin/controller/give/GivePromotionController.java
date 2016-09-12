package com.masiis.shop.admin.controller.give;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/givePromotion")
public class GivePromotionController {

    @RequestMapping("/add.shtml")
    public String add() {
        return "givePromotion/addGivePromotion";
    }





}
