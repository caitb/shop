package com.masiis.shop.web.mall.controller.system;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mallBeans.SfShopDetail;
import com.masiis.shop.dao.mallBeans.SfShopInfo;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.product.SkuBackGroupImageService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import com.masiis.shop.web.mall.service.user.SfUserShopViewService;
import com.masiis.shop.web.promotion.cpromotion.service.guser.SfUserPromotionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Resource
    private SfUserPromotionService promoService;

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
        Integer allSfSpokesManNum = sfUserRelationService.getFansOrSpokesMansNum(shopId, false, user.getId());
        //获取所有进行中的活动
        List<SfUserPromotion> userPromotions = promoService.getPromotionByStatus(0);
        SfShop sfShop = null;
        boolean isUpload = true; //没上传
        if (shopId != null) {
            sfShop = sfShopService.getSfShopById(shopId);
            if (sfShop == null) {
                throw new BusinessException("进入方式异常，请联系管理员");
            }else{
                if(StringUtils.isNotBlank(sfShop.getWxQrCode())){
                    String productImgValue = PropertiesUtils.getStringValue("oss.BASE_URL");
                    String ImgValue = PropertiesUtils.getStringValue("oss.OSS_SHOPMAN_WX_QRCODE");
                    sfShop.setWxQrCode(productImgValue+"/"+ImgValue+sfShop.getWxQrCode());
                    isUpload = false;
                }
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
        modelAndView.addObject("isUpload", isUpload);
        modelAndView.addObject("userPromotions", userPromotions);
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
    public SfShopInfo findProduct(HttpServletRequest req){
        SfShopInfo sfShopInfo = new SfShopInfo();
        List<SfShopDetail> SfShopDetails = new ArrayList<>();
        Long shopId = (Long) req.getSession().getAttribute("shopId");
        try {
            List<SfShopSku> sfShopSkus = skuService.getSfShopSkuByShopId(shopId);
            for (SfShopSku sfShopSku : sfShopSkus) {
                ComSku comSku = skuService.getComSkuBySkuId(sfShopSku.getSkuId());
                ComSpu comSpu = skuService.getSpuById(comSku.getSpuId());
                ComSkuImage comSkuImage = skuService.findDefaultComSkuImage(sfShopSku.getSkuId());
                SfShopDetail sfShopDetail = new SfShopDetail();
                sfShopDetail.setIsWunShip(sfShopSku.getIsOwnShip());//自己发货
                sfShopDetail.setSkuImageUrl(comSkuImage.getFullImgUrl());
                sfShopDetail.setSkuName(comSku.getName());
                sfShopDetail.setSkuAssia(comSku.getAlias());
                sfShopDetail.setPriceRetail(comSku.getPriceRetail());//销售价
//                SfShopSku sfSkuLevelImage = skuService.findSfSkuLevelImage(shopId, sfShopSku.getSkuId());
//                sfShopDetail.setIcon(sfSkuLevelImage.getIcon());//商品代理图标
                sfShopDetail.setSkuId(comSku.getId());
                sfShopDetail.setSlogan(comSpu.getSlogan());//一句话介绍
                SfShopDetails.add(sfShopDetail);
            }
            sfShopInfo.setSfShopDetailList(SfShopDetails);//业务数据
            sfShopInfo.setSkuUrlList(skuBackGroupImageService.getSfShopSkuImgByShopId(shopId)); // 业务图片
        }catch (Exception ex){
        if (StringUtils.isNotBlank(ex.getMessage())) {
            throw new BusinessException(ex.getMessage(), ex);
        } else {
            throw new BusinessException("网络错误", ex);
        }
    }
    return sfShopInfo;
    }


    /**
     * 获取代理等级
     *
     * @author muchaofeng
     * @date 2016/4/12 17:05
     */
    @RequestMapping("/findSfSkuLevelImage.do")
    @ResponseBody
    public  List<String> findSfSkuLevelImage(HttpServletRequest req){
        List<String> list = new ArrayList<>();
        Set set = new HashSet();
        List<String> newList = new ArrayList<>();
        Long shopId = (Long) req.getSession().getAttribute("shopId");
        try {
            List<SfShopSku> sfShopSkus = skuService.getSfShopSkuByShopId(shopId);
            for (SfShopSku sfShopSku : sfShopSkus) {
                SfShopSku sfSkuLevelImage = skuService.findSfSkuLevelImage(shopId, sfShopSku.getSkuId());
                list.add(sfSkuLevelImage.getIcon());
            }
            set.addAll(list);
            newList.addAll(set);
        }catch (Exception ex){
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return newList;
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
        userPid = 538L;
        req.getSession().setAttribute("userPid", userPid);
        req.getSession().setAttribute("shopId", shopId);

        ComUser pUser = userService.getUserById(userPid);
        sfUserShopViewService.addShopView(user.getId(), shopId);
//        Integer countByShopId = sfUserShopViewService.findCountByShopId(shopId);//浏览量
        Integer allSfSpokesManNum = sfUserRelationService.getFansOrSpokesMansNum(shopId, false, userPid);
        //获取所有进行中的活动
        List<SfUserPromotion> userPromotions = promoService.getPromotionByStatus(0);
        SfShop sfShop = null;
        boolean isUpload = true; //没上传
        if (shopId == null) {
            throw new BusinessException("shopId不能为空");
        } else {
            sfShop = sfShopService.getSfShopById(shopId);
            if (sfShop == null) {
                throw new BusinessException("进入方式异常，请联系管理员");
            }else{
                if(StringUtils.isNotBlank(sfShop.getWxQrCode())){
                    String productImgValue = PropertiesUtils.getStringValue("oss.BASE_URL");
                    String ImgValue = PropertiesUtils.getStringValue("oss.OSS_SHOPMAN_WX_QRCODE");
                    sfShop.setWxQrCode(productImgValue+"/"+ImgValue+sfShop.getWxQrCode());
                    isUpload = false;
                }
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pUser", pUser);
        modelAndView.addObject("user", user);
        modelAndView.addObject("allSfSpokesManNum", allSfSpokesManNum);
        modelAndView.addObject("userPid", userPid);
        modelAndView.addObject("sfShop", sfShop);
        modelAndView.addObject("isUpload", isUpload);
        modelAndView.addObject("userPromotions", userPromotions);
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
