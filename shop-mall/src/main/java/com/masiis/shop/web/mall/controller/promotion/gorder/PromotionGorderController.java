package com.masiis.shop.web.mall.controller.promotion.gorder;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.promotion.cpromotion.service.gorder.PromotionGorderService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 活动订单
 */
@Controller
public class PromotionGorderController extends BaseController {

    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PromotionGorderService promotionGorderService;

    /**
     * 获得活动订单页面信息
     * @param selectedAddressId 地址
     * @param promoId   活动id
     * @param promoRuleId  活动规则id
     * @param request      请求
     * @param model        model
     * @return map
     */
    @RequestMapping("/getPromotionGorderPageInfo.html")
    public String getPromotionGorderPageInfo(@RequestParam(required = false) Long selectedAddressId,@RequestParam(required = true) Integer promoId,
                                             @RequestParam(required = true) Integer promoRuleId,HttpServletRequest request,
                                             Model model) {
        log.info("获得活动订单页面信息---controller-----活动selectedAddressId-----"+selectedAddressId);
        log.info("获得活动订单页面信息---controller-----活动promoId-----"+promoId);
        log.info("获得活动订单页面信息---controller-----规则promoRuleId-----"+promoRuleId);
        if (promoId==null||promoRuleId==null){
            throw new BusinessException("参数不合法");
        }
        ComUser comUser = getComUser(request);
        Map<String, Object> map = promotionGorderService.getPromotionGorderPageInfo(comUser.getId(),selectedAddressId,promoId,promoRuleId);
        model.addAttribute("address",map.get("address"));
        model.addAttribute("gift",map.get("gift"));
        return null;
    }

    /**
     *
     * @param selectedAddressId  地址id
     * @param promoId           活动id
     * @param promoRuleId       活动规则id
     * @param personType        订单类型
     * @param request           请求
     * @return null
     */
    @RequestMapping("/receiveReward.do")
    @ResponseBody
    public String receiveReward(@RequestParam(required = false) Long selectedAddressId,
                                @RequestParam(required = true) Integer promoId,
                                @RequestParam(required = true) Integer promoRuleId,
                                @RequestParam(required = true) Integer personType,
                                HttpServletRequest request){
        if (promoId==null||promoRuleId==null||personType==null){
          log.info("参数不合法");
          throw new BusinessException("参数不合法");
        }
        ComUser comUser =  getComUser(request);
        promotionGorderService.receiveReward(comUser,selectedAddressId,promoId,promoRuleId,personType);
        return null;
    }
}
