package com.masiis.shop.web.mall.controller.promotion.guser;

import com.masiis.shop.dao.beans.promotion.PromotionInfo;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.promotion.cpromotion.service.guser.PromotionDetailShowService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.soap.MTOM;
import java.util.List;

/**
 * 活动页面展示
 */
@Controller
public class PromotionDetailShowController extends BaseController{

    private Logger log = Logger.getLogger(this.getClass());

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
        List<PromotionInfo> promotionInfos = promotionDetailShowService.getAllPromoDetail(getComUser(request));
        model.addAttribute("promotionInfos",promotionInfos);
        return null;
    }
}
