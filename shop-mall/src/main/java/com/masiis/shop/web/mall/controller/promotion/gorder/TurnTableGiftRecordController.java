package com.masiis.shop.web.mall.controller.promotion.gorder;

import com.masiis.shop.dao.beans.promotion.UserTurnTableRecordInfo;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserTurnTableRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *  大转盘中奖纪录
 */
@Controller
@RequestMapping("/turnTableGiftRecordController")
public class TurnTableGiftRecordController extends BaseController {

    @Resource
    private SfUserTurnTableRecordService userTurnTableRecordService;

    @RequestMapping("/getPromotionGorderPageInfo.html")
    public String getGiftedRecordInfo(HttpServletRequest request,Model model) {
        ComUser comUser =  getComUser(request);
        List<UserTurnTableRecordInfo> records =  userTurnTableRecordService.getRecordInfoByUserId(comUser.getId());
        model.addAttribute("records",records);
        return "promotion/gorder/turnTableGiftRecord";
    }




    @RequestMapping("/getPage.html")
    public String getPage(HttpServletRequest request) {
        return "promotion/gorder/turnTableRecordDemo";
    }

    public static void main(String[] ags){
        String s = "12,1211,12111";
        int i = s.indexOf("121");
       System.out.println("----------i----tttttt"+i);

    }
}
