package com.masiis.shop.web.platform.controller.system;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.shop.SfShopService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Date 2016/5/17
 * @Auther lzh
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfShopService sfShopService;

    @RequestMapping("/pmnshop")
    public String promotionShop(HttpServletRequest request, Model model){
        ComUser user = getComUser(request);
        String urlDomain = PropertiesUtils.getStringValue("web.domain.name.address");
        if(user.getIsAgent().intValue() == 1){
            // 是合伙人,去到店铺页面
            SfShop shop = sfShopService.getSfShopByUserId(user.getId());
            return "redirect:" + urlDomain + "/shop/getPoster?shopId=" + shop.getId();
        }
        // 非合伙人,提示页面
        model.addAttribute("redirectUrl", urlDomain);
        return "platform/system/promotion";
    }

    @RequestMapping("/pmnpartner")
    public String promotionPartner(HttpServletRequest request, Model model){
        ComUser user = getComUser(request);
        String urlDomain = PropertiesUtils.getStringValue("web.domain.name.address");
        if(user.getIsAgent().intValue() == 1){
            // 是合伙人,去到店铺页面
            return "redirect:" + urlDomain + "/developing/ui";
        }
        // 非合伙人,提示页面
        model.addAttribute("redirectUrl", urlDomain);
        return "platform/system/promotion";
    }

}
