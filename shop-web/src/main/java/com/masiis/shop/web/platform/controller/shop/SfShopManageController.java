package com.masiis.shop.web.platform.controller.shop;

import com.masiis.shop.common.util.ImageUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.SfDistributionRecord;
import com.masiis.shop.dao.mall.order.SfDistributionRecordMapper;
import com.masiis.shop.dao.mall.order.SfOrderMapper;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.shop.JSSDKService;
import com.masiis.shop.web.platform.utils.DownloadImage;
import com.masiis.shop.web.platform.utils.qrcode.CreateParseCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cai_tb on 16/4/13.
 */
@Controller
@RequestMapping("shop/manage")
public class SfShopManageController extends BaseController {

    private final static Log log = LogFactory.getLog(SfShopManageController.class);

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private SfShopMapper sfShopMapper;
    @Resource
    private SfOrderMapper sfOrderMapper;
    @Resource
    private SfDistributionRecordMapper sfDistributionRecordMapper;
    @Resource
    private SkuService skuService;
    @Resource
    private ComSkuImageMapper comSkuImageMapper;
    @Resource
    private JSSDKService jssdkService;

    /**
     * 店铺管理首页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("platform/shop/manage/index");
        ComUser comUser = null;
        SfShop sfShop = null;

        try {
            comUser = getComUser(request);
            comUser = comUserMapper.selectByPrimaryKey(comUser.getId());
            sfShop = sfShopMapper.selectByUserId(comUser.getId());

            if(sfShop == null) return mav;

            Integer orderCount = sfOrderMapper.countByShopId(sfShop.getId()); //总订单数

            SfDistributionRecord sfCount = sfDistributionRecordMapper.selectCountByUserId(comUser.getId());
            Integer sumLevel = sfCount.getSumLevel(); //总参与人数

            String shopUrl = PropertiesUtils.getStringValue("mall.domain.name.address") + "/" + sfShop.getId()+"/"+comUser.getId()+"/shop.shtml";

            mav.addObject("comUser", comUser);
            mav.addObject("sfShop", sfShop);
            mav.addObject("orderCount", orderCount);
            mav.addObject("sumLevel", sumLevel);
            mav.addObject("shopUrl", shopUrl);

            return mav;
        } catch (Exception e) {
            log.error("加载店铺首页数据失败![comUser="+comUser+"][sfShop="+sfShop+"]");
            e.printStackTrace();
        }
        return mav;
    }

    /**
     * 店铺设置
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/setupShop")
    public ModelAndView setupShop(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("platform/shop/manage/setupShop");
        ComUser comUser = null;
        SfShop sfShop = null;
        try {
            comUser = getComUser(request);
            comUser = comUserMapper.selectByPrimaryKey(comUser.getId());
            sfShop = sfShopMapper.selectByUserId(comUser.getId());

            mav.addObject("comUser", comUser);
            mav.addObject("sfShop", sfShop);

            return mav;
        } catch (Exception e) {
            log.error("店铺设置页面失败![comUser="+comUser+"][sfShop="+sfShop+"]");
            e.printStackTrace();
        }

        return mav;
    }

    /**
     * 更新店铺
     * @param request
     * @param response
     * @param sfShop
     * @return
     */
    @RequestMapping("/updateShop")
    public String updateShop(HttpServletRequest request, HttpServletResponse response, SfShop sfShop){

        try {
            sfShopMapper.updateByPrimaryKey(sfShop);
        } catch (Exception e) {
            log.error("设置店铺失败![sfShop="+sfShop+"]");
        }

        return "redirect:/shop/manage/index";
    }

    /**
     * 设置运费
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/setupFreight")
    public ModelAndView setupFreight(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("platform/shop/manage/setupFreight");

        SfShop sfShop = null;
        try {
            ComUser comUser = getComUser(request);
            sfShop = sfShopMapper.selectByUserId(comUser.getId());

            mav.addObject("sfShop", sfShop);

            return mav;
        } catch (Exception e) {
            log.error("去运费设置页面失败![sfShop="+sfShop+"]");
            e.printStackTrace();
        }

        return mav;
    }

    /**
     * 获取店铺海报
     * @param request
     * @param response
     * @param shopId
     * @return
     */
    @RequestMapping("/getPoster")
    public ModelAndView getPoster(HttpServletRequest request, HttpServletResponse response, Long shopId){
        ModelAndView mav = new ModelAndView("platform/shop/exclusivePoster");

        try {
            ComUser comUser = getComUser(request);
            comUser = comUserMapper.selectByPrimaryKey(comUser.getId());
            SfShop sfShop = sfShopMapper.selectByPrimaryKey(shopId);
            String realPath = request.getServletContext().getRealPath("/");
            String posterName = comUser.getId() + ".jpg";

            File posterDir = new File(realPath + "static/images/shop/poster/");
            if(!posterDir.exists()) posterDir.mkdirs();

            //二维码
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
            String qrCodePath = posterDir.getAbsolutePath()+"/"+posterName;
            String shopUrl = PropertiesUtils.getStringValue("mall.domain.name.address") + "/" + shopId+"/"+comUser.getId()+"/shop.shtml";
            CreateParseCode.createCode(200, 200, shopUrl, qrCodePath);

            //用户头像
            String headImgPath = posterDir.getAbsolutePath()+"/h-"+comUser.getId()+".jpg";
            DownloadImage.download(comUser.getWxHeadImg(), "h-"+comUser.getId()+".jpg", posterDir.getAbsolutePath());
            ImageUtils.scale2(headImgPath, headImgPath, 90, 90, false);

            //画专属海报
            String bgPath = realPath + "static/images/shop/background-img/bg-shop.png";
            String shopPosterPath = realPath + "static/images/shop/poster/shop-poster-"+comUser.getId()+".jpg";
            String content = "我是"+comUser.getWxNkName();
            Map<String, Integer> positionMap = new HashMap<>();
            positionMap.put("headImg-left", 72);
            positionMap.put("headImg-top", 328);
            positionMap.put("bgImg-left", 0);
            positionMap.put("bgImg-top", 0);
            positionMap.put("qrCodeImg-left", 212);
            positionMap.put("qrCodeImg-top", 470);
            positionMap.put("content-left", 186);
            positionMap.put("content-top", 330);
            drawPoster(headImgPath, qrCodePath, bgPath, new String[]{content}, shopPosterPath, positionMap, new Font("微软雅黑", Font.PLAIN, 28), new Color(51,51,51));

            //jssdk
            String curUrl = request.getRequestURL().toString()+"?shopId="+shopId;
            /** 获取调用JSSDK所需要的数据 **/
            Map<String, String> shareMap = jssdkService.requestJSSDKData(curUrl);
            //要分享的数据
            shareMap.put("shareTitle", "我是"+comUser.getRealName()+",我为朋友呐喊!");
            shareMap.put("shareDesc", "在家靠父母，出外靠朋友。我为朋友呐喊，分享赚佣金。");
            shareMap.put("shareImg", sfShop.getLogo());
            shareMap.put("shareLink", shopUrl);

            mav.addObject("shopPoster", basePath + "static/images/shop/poster/shop-poster-"+comUser.getId()+".jpg");
            mav.addObject("shareMap", shareMap);
            return mav;
        } catch (Exception e) {
            log.error("获取专属海报失败![shopId=" + shopId + "][comUser=" + getComUser(request) + "]");
            e.printStackTrace();
        }

        mav.setViewName("error");
        return mav;
    }

    private void drawPoster(String headImgPath, String qrCodePath, String bgPath, String[] content, String shopPosterPath, Map<String, Integer> positionMap, Font font, Color color){
        ImageIcon headImgIcon = new ImageIcon(headImgPath);
        ImageIcon qrCodeIcon = new ImageIcon(qrCodePath);
        ImageIcon bgIcon = new ImageIcon(bgPath);
        Image headImage = headImgIcon.getImage();
        Image qrCodeImage = qrCodeIcon.getImage();
        Image bgImage = bgIcon.getImage();

        int width = bgImage.getWidth(null) == -1 ? 520 : bgImage.getWidth(null);
        int height = bgImage.getHeight(null) == -1 ? 710 : bgImage.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.setBackground(new Color(255,255,255));

        g.drawImage(headImage, positionMap.get("headImg-left"), positionMap.get("headImg-top"), null);
        g.drawImage(bgImage, positionMap.get("bgImg-left"), positionMap.get("bgImg-top"), null);
        g.drawImage(qrCodeImage, positionMap.get("qrCodeImg-left"), positionMap.get("qrCodeImg-top"), null);

        g.setFont(font);
        g.setColor(color);
        for(int i=0; i<content.length; i++){
            g.drawString(content[i], positionMap.get("content-left"), positionMap.get("content-top")+(font.getSize()+15)*i);
        }
        g.dispose();

        try {
            ImageIO.write(bufferedImage, "jpg", new File(shopPosterPath));
        } catch (Exception e) {
            log.error("画海报出错了!");
            e.printStackTrace();
            return;
        }
    }

    /**
     * 获取商品海报
     * @param request
     * @param response
     * @param shopId
     * @param skuId
     * @return
     */
    @RequestMapping("/getSkuPoster")
    @ResponseBody
    public Object getSkuPoster(HttpServletRequest request, HttpServletResponse response, Long shopId, Integer skuId){
        //ModelAndView mav = new ModelAndView("mall/shop/skuPoster");

        try {
            ComUser comUser = getComUser(request);
            comUser = comUserMapper.selectByPrimaryKey(comUser.getId());
            String realPath = request.getServletContext().getRealPath("/");
            String posterName = comUser.getId() + "-" + shopId + "-" + skuId + ".jpg";

            File posterDir = new File(realPath + "static/images/shop/poster/");
            if(!posterDir.exists()) posterDir.mkdirs();

            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
            String qrCodePath = posterDir.getAbsolutePath()+"/"+posterName;
            String skuDetailUrl = basePath+"shop/detail.shtml?shopId="+shopId+"&skuId="+skuId+"&fromUserId="+comUser.getId();
            CreateParseCode.createCode(300, 300, skuDetailUrl, qrCodePath);

            //用户头像
            String headImgPath = posterDir.getAbsolutePath()+"/h-"+comUser.getId()+".jpg";
            DownloadImage.download(comUser.getWxHeadImg(), "h-"+comUser.getId()+".jpg", posterDir.getAbsolutePath());
            ImageUtils.scale2(headImgPath, headImgPath, 90, 90, false);

            //画专属海报
            String bgPath = realPath + "static/images/shop/background-img/sku-"+skuId+".png";
            String skuPosterPath = realPath + "static/images/shop/poster/sku-poster-"+comUser.getId()+".jpg";
            Map<String, Integer> positionMap = new HashMap<>();
            positionMap.put("headImg-left", 46);
            positionMap.put("headImg-top", 44);
            positionMap.put("bgImg-left", 0);
            positionMap.put("bgImg-top", 0);
            positionMap.put("qrCodeImg-left", 304);
            positionMap.put("qrCodeImg-top", 314);
            positionMap.put("content-left", 170);
            positionMap.put("content-top", 76);
            String skuName = skuService.getSkuById(skuId).getName();
            drawPoster(headImgPath, qrCodePath, bgPath, new String[]{"我是"+comUser.getWxNkName(),"我为"+skuName+"代言!"}, skuPosterPath, positionMap, new Font("微软雅黑", Font.PLAIN, 28), new Color(51,51,51));

            //Map<String, Object> dataMap = new HashMap<String, Object>();
            //jssdk
            String curUrl = request.getRequestURL().toString()+"?shopId="+shopId;
            /** 获取调用JSSDK所需要的数据 **/
            Map<String, String> shareMap = jssdkService.requestJSSDKData(curUrl);
            //要分享的数据
            shareMap.put("shareTitle", "我是"+comUser.getRealName()+",我为朋友呐喊!");
            shareMap.put("shareDesc", "在家靠父母，出外靠朋友。我为朋友呐喊，分享赚佣金。");
            shareMap.put("shareImg", PropertiesUtils.getStringValue("index_product_220_220_url") + comSkuImageMapper.selectBySkuId(skuId).get(0).getImgUrl());
            shareMap.put("shareLink", skuDetailUrl);

            shareMap.put("skuPoster", basePath + "static/images/shop/poster/sku-poster-"+comUser.getId()+".jpg");

            return shareMap;
        } catch (Exception e) {
            log.error("获取专属海报失败![shopId=" + shopId + "][skuId="+skuId+"][comUser=" + getComUser(request) + "]");
            e.printStackTrace();
        }

        //mav.setViewName("error");
        return "error";
    }
}
