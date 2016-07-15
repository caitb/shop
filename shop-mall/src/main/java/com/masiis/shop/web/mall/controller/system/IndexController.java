package com.masiis.shop.web.mall.controller.system;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mallBeans.SfShopDetail;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.product.SkuBackGroupImageService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import com.masiis.shop.web.mall.service.user.SfUserShopViewService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页controller
 * @Date:2016/4/7
 * @auth:muchaofeng
 */
@Controller
public class IndexController extends BaseController {
    @Resource
    private UserService userService;
    @Resource
    private SfShopService sfShopService;
    @Resource
    private SkuService skuService;
    @Resource
    private SfUserShopViewService sfUserShopViewService;
    @Resource
    private SkuBackGroupImageService skuBackGroupImageService;
    @Resource
    private SfUserRelationService sfUserRelationService;

    /**
     * 小铺首页
     * @param shopId
     * @param userPid
     * @author muchaofeng
     * @date 2016/4/12 17:05
     */
    @RequestMapping("/{shopId}/{userPid}/shop.shtml")
    public ModelAndView index(HttpServletRequest req,
                              @PathVariable("shopId") Long shopId,
                              @PathVariable("userPid") Long userPid) throws Exception {
        ComUser user = getComUser(req);
        if (user == null) {
            throw new BusinessException("user不能为空");
        }

        if(req.getSession().getAttribute("userPid")==null){
            req.getSession().setAttribute("userPid", userPid);
        }else{
            req.getSession().removeAttribute("userPid");
            req.getSession().setAttribute("userPid", userPid);
        }
        if(req.getSession().getAttribute("shopId")==null){
            req.getSession().setAttribute("shopId", shopId);
        }else{
            req.getSession().removeAttribute("shopId");
            req.getSession().setAttribute("shopId", shopId);
        }
        ComUser pUser =null;
        if(userPid!=null){
            pUser = userService.getUserById(userPid);
        }


        sfUserShopViewService.addShopView(user.getId(), shopId);
//        Integer countByShopId = sfUserShopViewService.findCountByShopId(shopId);//浏览量
        Integer allSfSpokesManNum = sfUserRelationService.getAllSfSpokesManNum(shopId);
        SfShop sfShop = null;
        if (shopId != null) {
            sfShop = sfShopService.getSfShopById(shopId);
            if (sfShop == null) {
                throw new BusinessException("进入方式异常，请联系管理员");
            }else{
                String productImgValue = PropertiesUtils.getStringValue("shopman_wx_qrcode_url");
                sfShop.setWxQrCode(productImgValue+sfShop.getWxQrCode());
            }
        } else {
            throw new BusinessException("shopId不能为空");
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pUser", pUser);
        modelAndView.addObject("user", user);
        modelAndView.addObject("allSfSpokesManNum", allSfSpokesManNum);
        modelAndView.addObject("userPid", userPid);
        modelAndView.addObject("sfShop", sfShop);
        modelAndView.setViewName("newshouye");
        return modelAndView;
    }


    /**
     * 小铺首页代理商品
     * @author muchaofeng
     * @date 2016/4/12 17:05
     */

    @RequestMapping("/product.do")
    @ResponseBody
    public  List<SfShopDetail> findProduct(HttpServletRequest req){
        List<SfShopDetail> SfShopDetails = new ArrayList<>();
        Long shopId = (Long) req.getSession().getAttribute("shopId");
        try {
            List<SfShopSku> sfShopSkus = skuService.getSfShopSkuByShopId(shopId);
            for (SfShopSku sfShopSku : sfShopSkus) {
                ComSku comSku = skuService.getComSkuBySkuId(sfShopSku.getSkuId());
                ComSpu comSpu = skuService.getSpuById(comSku.getSpuId());
                ComSkuImage comSkuImage = skuService.findDefaultComSkuImage(sfShopSku.getSkuId());
                ComSkuExtension comSkuExtension = skuBackGroupImageService.backGroupImage(sfShopSku.getSkuId());
                SfShopDetail sfShopDetail = new SfShopDetail();
                sfShopDetail.setIsWunShip(sfShopSku.getIsOwnShip());//自己发货
                sfShopDetail.setSkuImageUrl(comSkuImage.getFullImgUrl());
                sfShopDetail.setSkuUrl(comSkuExtension.getSkuBackgroundImg());
                sfShopDetail.setSkuName(comSku.getName());
                sfShopDetail.setSkuAssia(comSku.getAlias());
                sfShopDetail.setPriceRetail(comSku.getPriceRetail());//销售价
                SfShopSku sfSkuLevelImage = skuService.findSfSkuLevelImage(shopId, sfShopSku.getSkuId());
                sfShopDetail.setIcon(sfSkuLevelImage.getIcon());//商品代理图标
                sfShopDetail.setSkuId(comSku.getId());
                sfShopDetail.setSlogan(comSpu.getSlogan());//一句话介绍

                SfShopDetails.add(sfShopDetail);
            }
        }catch (Exception ex){
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return SfShopDetails;
    }


    /**
     * 开发首页
     *
     * @author muchaofeng
     * @date 2016/4/12 17:05
     */
    @RequestMapping("/index")
    public ModelAndView myindex(HttpServletRequest req, Long shopId, Long userPid) throws Exception {
        ComUser user = getComUser(req);
        if (user == null) {
            throw new BusinessException("user不能为空");
        }
        shopId =336L;
        userPid = 540L;
        req.getSession().setAttribute("userPid", userPid);
        req.getSession().setAttribute("shopId", shopId);

        ComUser pUser = userService.getUserById(userPid);
        sfUserShopViewService.addShopView(user.getId(), shopId);
//        Integer countByShopId = sfUserShopViewService.findCountByShopId(shopId);//浏览量
        Integer allSfSpokesManNum = sfUserRelationService.getAllSfSpokesManNum(shopId);
        SfShop sfShop = null;
        if (shopId == null) {
            throw new BusinessException("shopId不能为空");
        } else {
            sfShop = sfShopService.getSfShopById(shopId);
            if (sfShop == null) {
                throw new BusinessException("进入方式异常，请联系管理员");
            }else{
                String productImgValue = PropertiesUtils.getStringValue("shopman_wx_qrcode_url");
                sfShop.setWxQrCode(productImgValue+sfShop.getWxQrCode());
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pUser", pUser);
        modelAndView.addObject("user", user);
        modelAndView.addObject("allSfSpokesManNum", allSfSpokesManNum);
        modelAndView.addObject("userPid", userPid);
        modelAndView.addObject("sfShop", sfShop);
        modelAndView.setViewName("newshouye");
        return modelAndView;
    }

    /**
     * 分享
     *
     * @author muchaofeng
     * @date 2016/4/12 17:05
     */
    @RequestMapping("/share.html")
    public String share(HttpServletRequest req) throws Exception {
        return "mall/shop/fenxiangjihua";
    }


}
