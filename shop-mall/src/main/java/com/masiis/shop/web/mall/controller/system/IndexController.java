package com.masiis.shop.web.mall.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.shop.SfShopShoutLogMapper;
import com.masiis.shop.dao.mallBeans.SfShopDetail;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.order.SfOrderManageService;
import com.masiis.shop.web.mall.service.product.SkuService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.shop.SfShopSkuService;
import com.masiis.shop.web.mall.service.user.SfUserShopViewService;
import com.masiis.shop.web.mall.service.user.UserService;
import com.masiis.shop.web.mall.utils.wx.WxUserUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Date:2016/4/7
 * @auth:lzh
 */
@Controller
public class IndexController extends BaseController {
    @Resource
    private SfShopMapper sfShopMapper;
    @Resource
    private UserService userService;
    @Resource
    private SfShopService sfShopService;
    @Resource
    private SkuService skuService;
    @Resource
    private SfShopSkuService sfShopSkuService;
    @Resource
    private SfShopShoutLogMapper sfShopShoutLogMapper;
    @Resource
    private SfUserShopViewService sfUserShopViewService;

    @RequestMapping("/{shopId}/{userPid}/shop.shtml")
    public ModelAndView index(HttpServletRequest req,
                              @PathVariable("shopId") Long shopId,
                              @PathVariable("userPid") Long userPid) throws Exception {
        ComUser user = getComUser(req);
        if (user == null) {
            throw new BusinessException("user不能为空");
        }
        req.getSession().setAttribute("userPid", userPid);
        req.getSession().setAttribute("shopId", shopId);

        userService.getShareUser(user.getId(), userPid);//分销关系
        ComUser pUser = userService.getUserById(userPid);

        sfUserShopViewService.addShopView(user.getId(), shopId);
        SfShop sfShop = null;
        List<SfShopSku> sfShopSkus = null;
//        BigDecimal ShipAmount=new BigDecimal(0);
//        boolean ok = true;
        if (shopId == null) {
            throw new BusinessException("shopId不能为空");
        } else {
            sfShop = sfShopService.getSfShopById(shopId);
//            if (sfShop.getShipAmount().longValue() == 0.00) {
//                ok = false;
//            }
            if (sfShop == null) {
                throw new BusinessException("进入方式异常，请联系管理员");
            }
            sfShopSkus = skuService.getSfShopSkuByShopId(shopId);
        }

        List<SfShopDetail> SfShopDetails = new ArrayList<>();
//        BigDecimal bail=new BigDecimal(0);
        for (SfShopSku sfShopSku : sfShopSkus) {
            ComSku comSku = skuService.getComSkuBySkuId(sfShopSku.getSkuId());
            ComSpu comSpu = skuService.getSpuById(comSku.getSpuId());
            ComSkuImage comSkuImage = skuService.findDefaultComSkuImage(sfShopSku.getSkuId());
            SfShopDetail sfShopDetail = new SfShopDetail();
            SfShopSku shopSku = sfShopSkuService.findShopSkuByShopIdAndSkuId(sfShopSku.getShopId(), sfShopSku.getSkuId());
            sfShopDetail.setSkuUrl(comSkuImage.getFullImgUrl());
            sfShopDetail.setSkuName(comSku.getName());
            sfShopDetail.setPriceRetail(comSku.getPriceRetail());//销售价
            sfShopDetail.setAgentLevelName(shopSku.getAgentName());//代理等级名称
            SfShopSku sfSkuLevelImage = skuService.findSfSkuLevelImage(shopId, sfShopSku.getSkuId());
            sfShopDetail.setIcon(sfSkuLevelImage.getIcon());//商品代理图标
            sfShopDetail.setSkuId(comSku.getId());
            sfShopDetail.setSlogan(comSpu.getSlogan());//一句话介绍
//            bail=sfShopSku.getBail().add(bail);//保证金

            SfShopDetails.add(sfShopDetail);
        }
        ModelAndView modelAndView = new ModelAndView();
//        Boolean forcusSF = WxUserUtils.getInstance().isUserForcusSF(user);
//        modelAndView.addObject("forcusSF",forcusSF);
        modelAndView.addObject("pUser", pUser);
        modelAndView.addObject("user", user);
        modelAndView.addObject("userPid", userPid);
        modelAndView.addObject("sfShop", sfShop);
//        modelAndView.addObject("ok", ok);//保证金
        modelAndView.addObject("SfShopDetails", SfShopDetails);
        modelAndView.setViewName("shouye");
        return modelAndView;
    }

    @RequestMapping("/index")
    public ModelAndView myindex(HttpServletRequest req,
                                Long shopId, Long userPid) throws Exception {
        ComUser user = getComUser(req);
        if (user == null) {
            throw new BusinessException("user不能为空");
        }
        shopId =131L;
        userPid = 247L;
        req.getSession().setAttribute("userPid", userPid);
        req.getSession().setAttribute("shopId", shopId);

        userService.getShareUser(user.getId(), userPid);//分销关系
        ComUser pUser = userService.getUserById(userPid);

        sfUserShopViewService.addShopView(user.getId(), shopId);
        SfShop sfShop = null;
        List<SfShopSku> sfShopSkus = null;
//        BigDecimal ShipAmount=new BigDecimal(0);
        boolean ok = true;
        if (shopId == null) {
            throw new BusinessException("shopId不能为空");
        } else {
            sfShop = sfShopService.getSfShopById(shopId);
            if (sfShop.getShipAmount().longValue() == 0.00) {
                ok = false;
            }
            if (sfShop == null) {
                throw new BusinessException("进入方式异常，请联系管理员");
            }
            sfShopSkus = skuService.getSfShopSkuByShopId(shopId);
        }

        List<SfShopDetail> SfShopDetails = new ArrayList<>();
//        BigDecimal bail=new BigDecimal(0);
        for (SfShopSku sfShopSku : sfShopSkus) {
            ComSku comSku = skuService.getComSkuBySkuId(sfShopSku.getSkuId());
            ComSpu comSpu = skuService.getSpuById(comSku.getSpuId());
            ComSkuImage comSkuImage = skuService.findDefaultComSkuImage(sfShopSku.getSkuId());
            SfShopDetail sfShopDetail = new SfShopDetail();
            SfShopSku shopSku = sfShopSkuService.findShopSkuByShopIdAndSkuId(sfShopSku.getShopId(), sfShopSku.getSkuId());
            sfShopDetail.setSkuUrl(comSkuImage.getFullImgUrl());
            sfShopDetail.setSkuName(comSku.getName());
            sfShopDetail.setPriceRetail(comSku.getPriceRetail());//销售价
            sfShopDetail.setAgentLevelName(shopSku.getAgentName());//代理等级名称
            SfShopSku sfSkuLevelImage = skuService.findSfSkuLevelImage(shopId, sfShopSku.getSkuId());
            sfShopDetail.setIcon(sfSkuLevelImage.getIcon());//商品代理图标
            sfShopDetail.setSkuId(comSku.getId());
            sfShopDetail.setSlogan(comSpu.getSlogan());//一句话介绍
//            bail=sfShopSku.getBail().add(bail);//保证金

            SfShopDetails.add(sfShopDetail);
        }
        ModelAndView modelAndView = new ModelAndView();
//        Boolean forcusSF = WxUserUtils.getInstance().isUserForcusSF(user);
//        modelAndView.addObject("forcusSF",forcusSF);
        modelAndView.addObject("pUser", pUser);
        modelAndView.addObject("user", user);
        modelAndView.addObject("userPid", userPid);
        modelAndView.addObject("sfShop", sfShop);
        modelAndView.addObject("ok", ok);//保证金
        modelAndView.addObject("SfShopDetails", SfShopDetails);
        modelAndView.setViewName("shouye");
        return modelAndView;
    }

    /**
     * 呐喊
     *
     * @author muchaofeng
     * @date 2016/4/12 17:05
     */

    @RequestMapping("/shout.do")
    @ResponseBody
    public String shout(HttpServletRequest req, Long shopId) {
        JSONObject json = new JSONObject();
        ComUser user = getComUser(req);
        if (user == null) {
            user = userService.getUserById(1l);
            req.getSession().setAttribute("comUser", user);
        }
        try {
            boolean mallShout = sfShopService.mallShout(user.getId(), shopId);
            SfShop sfShop = null;
            if (mallShout) {
                sfShop = sfShopMapper.selectByPrimaryKey(shopId);
                sfShop.setShoutNum(sfShop.getShoutNum() + 1);

                SfShopShoutLog sfShopShoutLog = new SfShopShoutLog();
                sfShopShoutLog.setCreateTime(new Date());
                sfShopShoutLog.setNum(1);
                sfShopShoutLog.setUserId(user.getId());
                sfShopShoutLog.setShopId(shopId);
                sfShopShoutLog.setShopUserId(sfShop.getUserId());

                sfShopMapper.updateByPrimaryKey(sfShop);
                sfShopShoutLogMapper.insert(sfShopShoutLog);
            }
            json.put("mallShout", mallShout);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return json.toJSONString();
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
