package com.masiis.shop.web.mall.controller.promotion.guser;

import com.masiis.shop.common.enums.promotion.SfTurnTableRuleStatusEnum;
import com.masiis.shop.common.enums.promotion.SfTurnTableRuleTypeEnum;
import com.masiis.shop.common.enums.promotion.SfTurnTableStatusEnum;
import com.masiis.shop.dao.beans.promotion.TurnTablelInfo;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.promotion.cpromotion.service.guser.TurnTableDetailShowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hzzh on 2016/8/1.
 */
@Controller
@RequestMapping("/turnTableDetailShow")
public class TurnTableDetailShowController extends BaseController {

    @Resource
    private TurnTableDetailShowService turnTableDetailShowService;

    /**
     * 获取转盘信息
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/getTurnTableInfo.html")
    public String getTurnTableInfo(HttpServletRequest request, Model model) {
        List<TurnTablelInfo> turnTablelInfos =  turnTableDetailShowService.getTurnTableInfo(
                getComUser(request),
                SfTurnTableRuleTypeEnum.C.getCode(),
                SfTurnTableRuleStatusEnum.EFFECT.getCode(),
                SfTurnTableStatusEnum.ING.getCode());
        if (turnTablelInfos!=null&&turnTablelInfos.size()>0){
            model.addAttribute("turnTablelInfos",turnTablelInfos);
            model.addAttribute("turnTableId",turnTablelInfos.get(0).getTurnTableId());
            model.addAttribute("turnTableRule",turnTablelInfos.get(0).getTurnTableRule());
/*        model.addAttribute("giftIdMap",turnTablelInfos.get(0).getGiftIdMap());
        model.addAttribute("giftNameMap",turnTablelInfos.get(0).getGiftNameMap());
        model.addAttribute("giftImgMap",turnTablelInfos.get(0).getGiftImgMap());*/
            if (turnTablelInfos.get(0).getUserTurnTable()!=null){
                model.addAttribute("userTurnTable",turnTablelInfos.get(0).getUserTurnTable());
                model.addAttribute("noUsedTimes",turnTablelInfos.get(0).getUserTurnTable().getNotUsedTimes());
                model.addAttribute("isPurchaseSku","true");
            }else{
                model.addAttribute("isPurchaseSku","false");
                model.addAttribute("noUsedTimes",0);
            }
        }

        return "promotion/guser/turnTableGiftShow";
    }

    /**
     * 获取大转盘的
     * @param turnTableId
     * @return
     */
    @RequestMapping("/getRandomByGiftRate.json")
    @ResponseBody
    public String getRandomByGiftRate(@RequestParam(required = false) Integer turnTableId) {
       int i = turnTableDetailShowService.getRandomByGiftRate(turnTableId);
        return i+"";
    }
}
