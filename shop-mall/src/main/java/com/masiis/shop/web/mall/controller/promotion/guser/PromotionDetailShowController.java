package com.masiis.shop.web.mall.controller.promotion.guser;

import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.promotion.cpromotion.service.guser.PromotionDetailShowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 活动页面展示
 */
@Controller
@RequestMapping("/showPromotion")
public class PromotionDetailShowController extends BaseController{

    @Resource
    private PromotionDetailShowService promotionDetailShowService;

    /**
     * 活动页面的展示
     * @param request  请求
     * @param model    页面model
     * @return 返回页面路径
     */
    @RequestMapping("/getAllPromoDetail.html")
    public String getAllPromoDetail(HttpServletRequest request, Model model) {
        Map<String,Object> map = promotionDetailShowService.getAllPromoDetail(getComUser(request));
        model.addAttribute("promotionInfos",map.get("promotionInfos"));
        model.addAttribute("fansQuantity",map.get("fansQuantity"));
        return "promotion/guser/promotionShow";
    }
}
