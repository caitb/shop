package com.masiis.shop.admin.controller.promotion;

import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.service.promotion.SfUserPromotionGiftService;
import com.masiis.shop.admin.service.promotion.SfUserPromotionRuleService;
import com.masiis.shop.admin.service.promotion.SfUserPromotionService;
import com.masiis.shop.admin.utils.DbUtils;
import com.masiis.shop.dao.po.SfUserPromotion;
import com.masiis.shop.dao.po.SfUserPromotionGift;
import com.masiis.shop.dao.po.SfUserPromotionRule;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 活动 Controller
 */
@Controller
@RequestMapping("/promotion")
public class PromotionContorller {

    @Resource
    private SfUserPromotionService sfUserPromotionService;
    @Resource
    private SfUserPromotionRuleService sfUserPromotionRuleService;
    @Resource
    private SfUserPromotionGiftService sfUserPromotionGiftService;

    @RequestMapping("/add.shtml")
    public String addPage() {
        return "promotion/addPromotion";
    }

    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public Object saveOrUpdate(HttpServletRequest request,
                               @RequestParam Integer promotionId,
                               @RequestParam String promotionName,
                               @RequestParam String promotionRemark,
                               @RequestParam String promotionIntroduction,
                               @RequestParam Integer promotionPersonType,
                               @RequestParam String promotionBeginTime,
                               @RequestParam String promotionEndTime,
                               @RequestParam("ruleId")    Integer[] ruleIdArray,
                               @RequestParam("promotionGiftId") Integer[] promotionGiftIdArray,
                               @RequestParam("ruleValue") Integer[] ruleValueArray,
                               @RequestParam("giftValue") Integer[] giftValueArray,
                               @RequestParam("quantity")  Integer[] quantityArray,
                               @RequestParam("upperQuantity") Integer[] upperQuantityArray) {

        SfUserPromotion promotion = new SfUserPromotion(promotionId, promotionName, promotionRemark, promotionIntroduction, promotionPersonType, promotionBeginTime, promotionEndTime);

        List<SfUserPromotionRule> ruleList = sfUserPromotionRuleService.createRuleList(ruleValueArray, ruleIdArray);
        List<SfUserPromotionGift> promotionGiftList = sfUserPromotionGiftService.createPromotionGiftList(giftValueArray, quantityArray, upperQuantityArray, promotionGiftIdArray);

        sfUserPromotionService.savePromotion(promotion, ruleList, promotionGiftList, request);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("status", "success");
        resultMap.put("promotionId", promotion.getId());
        return resultMap;
    }

    @RequestMapping("/getPromotion")
    @ResponseBody
    public Object getPromotion(Integer promotionId) {
        return sfUserPromotionService.getPromotion(promotionId);
    }

    @RequestMapping("/list.shtml")
    public String list() {
        return "promotion/listPromotion";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(Integer pageSize,Integer pageNumber,
                       String beginTime,
                       String endTime) {

        Map<String,Object> conditionMap = DbUtils.createTimeCondition(beginTime, endTime, null);
        List<SfUserPromotion> promotions = sfUserPromotionService.listByCondition(pageNumber, pageSize, conditionMap);
        PageInfo<SfUserPromotion> promotionPageInfo = new PageInfo<>(promotions);

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("total", promotionPageInfo.getTotal());
        dataMap.put("rows", promotions);
        return dataMap;
    }
}
