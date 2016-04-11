package com.masiis.shop.web.mall.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.dao.mallBeans.SfShopDetail;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.order.SfOrderManageService;
import com.masiis.shop.web.mall.service.product.SkuService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.shop.SfShopSkuService;
import com.masiis.shop.web.mall.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date:2016/4/7
 * @auth:lzh
 */
@Controller
public class IndexController extends BaseController {
    @Resource
    private SfOrderManageService sfOrderManageService;
    @Resource
    private UserService userService;
    @Resource
    private SfShopService sfShopService;
    @Resource
    private SkuService skuService;
    @Resource
    private SfShopSkuService sfShopSkuService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest req,Long shopId,Long userPid)throws Exception{
        ComUser user = getComUser(req);
        if (user == null) {
            user = userService.getUserById(1l);
            req.getSession().setAttribute("comUser", user);
        }
        userService.getShareUser(user.getId(),userPid);//分销关系
//        ComUser pUser = userService.getUserById(userPid);
        ComUser pUser = new ComUser();
        SfShop sfShop = sfShopService.getSfShopById(shopId);
        String planation =null;
//        planation = new String(sfShop.getExplanation(), "UTF-8");
        List<SfShopSku> sfShopSkus = skuService.getSfShopSkuByShopId(shopId);
        List<SfShopDetail> SfShopDetails = new ArrayList<>();
        BigDecimal bail=null;
        for (SfShopSku sfShopSku:sfShopSkus) {
            ComSku comSku = skuService.getComSkuBySkuId(sfShopSku.getSkuId());
            ComSkuImage comSkuImage = skuService.findDefaultComSkuImage(sfShopSku.getSkuId());
            SfShopDetail sfShopDetail= new SfShopDetail();
            SfShopSku shopSku = sfShopSkuService.findShopSkuByShopIdAndSkuId(sfShopSku.getShopId(), sfShopSku.getSkuId());
            sfShopDetail.setSkuUrl(comSkuImage.getFullImgUrl());
            sfShopDetail.setSkuName(comSku.getName());
            sfShopDetail.setPriceRetail(comSku.getPriceRetail());//销售价
//            sfShopDetail.setShipAmount(sfShopSku.getShipAmount());//邮费
            sfShopDetail.setAgentLevelName(shopSku.getAgentName());//代理等级名称
            sfShopDetail.setIcon(shopSku.getIcon());//商品代理图标
            sfShopDetail.setSkuId(comSku.getId());
            bail=bail.add(sfShopSku.getBail());//保证金

            SfShopDetails.add(sfShopDetail);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pUser", pUser);
        modelAndView.addObject("sfShop",sfShop);
        modelAndView.addObject("bail",bail);//保证金
        modelAndView.addObject("SfShopDetails",SfShopDetails);
        modelAndView.addObject("size",SfShopDetails.size());
        modelAndView.addObject("planation",planation);
        modelAndView.setViewName("shouye");
        return modelAndView;
    }

    @RequestMapping("/shout.do")
    @ResponseBody
    public String shout(HttpServletRequest req,Long shopId)throws Exception{
        JSONObject json = new JSONObject();
        ComUser user = getComUser(req);
        if (user == null) {
            user = userService.getUserById(1l);
            req.getSession().setAttribute("comUser", user);
        }
        boolean mallShout = sfShopService.mallShout(user.getId(), shopId);
        json.put("mallShout",mallShout);
        return json.toJSONString();
    }

}
