package com.masiis.shop.web.mall.controller.promotion.gorder;

import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.promotion.cpromotion.service.gorder.TurnTableGorderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
                                       @RequestParam(required = true) Long userTurnTableRecordId,
                                       HttpServletRequest request,
                                       Model model) {
        Map<String,Object> map =  turnTableGorderService.getTurnTableGiftInfo(getComUser(request).getId(),selectedAddressId,turnTableId,giftId,userTurnTableRecordId);
        model.addAttribute("comUserAddress",map.get("address"));
        model.addAttribute("turnTableGiftInfo",map.get("turnTableGiftInfo"));
        return "promotion/gorder/turnTableGiftReceive";
    }

    /**
     * 领取奖品
     * @param selectedAddressId
     * @param turnTableId
     * @param giftId
     * @param request
     * @return
     */
    @RequestMapping("/receiveGift.json")
    @ResponseBody
    public String receiveGift(@RequestParam(required = false) Long selectedAddressId,
                                       @RequestParam(required = true) Integer turnTableId,
                                       @RequestParam(required = true) Integer giftId,
                                        @RequestParam(required = true) Long userTurnTableRecordId,
                                       HttpServletRequest request) {
       Integer i =  turnTableGorderService.receiveGift(getComUser(request),selectedAddressId,turnTableId,giftId,userTurnTableRecordId);
        return i+"";
    }

    /**
     * 抽奖后减少用户的抽奖次数和奖品数量
     * @param request
     * @param turnTableId
     * @param turnTableRuleId
     * @param giftId
     * @return
     */
    @RequestMapping("/receiveGiftUpdateTimesAndQuantity.json")
    @ResponseBody
    public String receiveGiftUpdateTimesAndQuantity(
                            HttpServletRequest request,
                            @RequestParam(required = true) Integer turnTableId,
                            @RequestParam(required = false) Integer turnTableRuleId,
                            @RequestParam(required = false) Integer turnTableGiftId,
                            @RequestParam(required = true) Integer giftId){
      Long userTurnTableRecordId = turnTableGorderService.receiveGiftUpdateTimesAndQuantity(getComUser(request),1,getComUser(request).getId(),turnTableId,turnTableRuleId,giftId,turnTableGiftId);
        if (userTurnTableRecordId!=null){
            return userTurnTableRecordId+"";
        }else {
            return "";
        }
    }

    /**
     *  抽奖前验抽奖条件是否满足
     * @param request
     * @param turnTableId
     * @param giftId
     * @return
     */
    @RequestMapping("/validateReceiveGiftCondition.json")
    @ResponseBody
    public String validateReceiveGiftCondition(
            HttpServletRequest request,
            @RequestParam(required = true) Integer turnTableId,
            @RequestParam(required = true) Integer giftId){
        Integer i =  turnTableGorderService.validateReceiveGiftCondition(getComUser(request),turnTableId,giftId);
        return i+"";
    }



    @RequestMapping("/skipToReceiveSuccessPage.html")
    public String skipToReceiveSuccessPage(){
        return "promotion/gorder/turnTableGiftReceiveSuccess";
    }
}
