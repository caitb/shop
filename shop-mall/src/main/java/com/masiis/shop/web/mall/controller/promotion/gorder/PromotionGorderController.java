package com.masiis.shop.web.mall.controller.promotion.gorder;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.promotion.cpromotion.service.gorder.PromotionGorderService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 */
public class PromotionGorderController extends BaseController {

    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PromotionGorderService promotionGorderService;

    @RequestMapping("/getPromotionGorderPageInfo.html")
    public String getPromotionGorderPageInfo(@RequestParam(required = false) Long selectedAddressId,@RequestParam(required = true) Integer promoId, @RequestParam(required = true) Integer promoRuleId,HttpServletRequest request) {
        if (promoId==null||promoRuleId==null){
            throw new BusinessException("参数不合法");
        }
        ComUser comUser = getComUser(request);
        Map<String, Object> map = promotionGorderService.getPromotionGorderPageInfo(comUser.getId(),selectedAddressId,promoId,promoRuleId);
        return null;
    }
}
