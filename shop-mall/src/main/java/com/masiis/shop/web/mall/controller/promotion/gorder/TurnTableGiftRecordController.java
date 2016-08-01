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


    /**
     * 0出现的概率为%50
     */
    public static double rate0 = 0.00;
    /**
     * 1出现的概率为%20
     */
    public static double rate1 = 0.00;
    /**
     * 2出现的概率为%15
     */
    public static double rate2 = 0.00;
    /**
     * 3出现的概率为%10
     */
    public static double rate3 = 0.00;
    /**
     * 4出现的概率为%4
     */
    public static double rate4 = 0.00;
    /**
     * 5出现的概率为%1
     */
    public static double rate5 = 1.00;

    /**
     * Math.random()产生一个double型的随机数，判断一下
     * 例如0出现的概率为%50，则介于0到0.50中间的返回0
     * @return int
     *
     */
    public static  int percentageRandom()
    {
        double randomNumber;
        randomNumber = Math.random();
        System.out.println("------------randomNumber--------"+randomNumber);
        if (randomNumber >= 0 && randomNumber <= rate0)
        {
            return 0;
        }
        else if (randomNumber >= rate0 / 100 && randomNumber <= rate0 + rate1)
        {
            return 1;
        }
        else if (randomNumber >= rate0 + rate1
                && randomNumber <= rate0 + rate1 + rate2)
        {
            return 2;
        }
        else if (randomNumber >= rate0 + rate1 + rate2
                && randomNumber <= rate0 + rate1 + rate2 + rate3)
        {
            return 3;
        }
        else if (randomNumber >= rate0 + rate1 + rate2 + rate3
                && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4)
        {
            return 4;
        }
        else if (randomNumber >= rate0 + rate1 + rate2 + rate3 + rate4
                && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4
                + rate5)
        {
            return 5;
        }
        return -1;
    }

    @RequestMapping("/getPage.html")
    public String getPage(HttpServletRequest request) {
        return "promotion/gorder/turnTableRecordDemo";
    }


    @RequestMapping("/getRandomNumber.json")
    @ResponseBody
    public String getRandomNumber(HttpServletRequest request) {
        int i  = percentageRandom();
        System.out.println("-----------i--------------"+i);
        return i+"";
    }

    public static void main(String[] ags){
        String s = "12,1211,12111";
        int i = s.indexOf("121");
       System.out.println("----------i----tttttt"+i);

    }
}
