package com.masiis.shop.web.mall.controller.shop;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.ImageUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mallBeans.SkuInfo;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComSkuImage;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.product.SkuImageService;
import com.masiis.shop.web.mall.service.product.SkuService;
import com.masiis.shop.web.mall.service.product.SpuService;
import com.masiis.shop.web.mall.service.shop.JSSDKService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.user.SfUserShopViewService;
import com.masiis.shop.web.mall.service.user.UserService;
import com.masiis.shop.web.mall.utils.DownloadImage;
import com.masiis.shop.web.mall.utils.qrcode.CreateParseCode;
import com.masiis.shop.web.mall.utils.wx.WxUserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import java.util.List;
import java.util.Map;

/**
 * @Date:2016/4/7
 * @auth:lzh
 */
@Controller
@RequestMapping("/shop")
public class SfShopController extends BaseController {

    private Log log = LogFactory.getLog(SfShopController.class);

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private SfShopService sfShopService;
    @Resource
    private SkuService skuService;
    @Resource
    private SkuImageService skuImageService;
    @Resource
    private SpuService spuService;
    @Resource
    private UserService userService;
    @Resource
    private SfUserShopViewService sfUserShopViewService;
    @Resource
    private JSSDKService jssdkService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("mall/shop/index");

        return mav;
    }

    /**
     * 呐喊
     * @param request
     * @param response
     * @param shopId
     * @return
     */
    @RequestMapping("/shout")
    @ResponseBody
    public Object shout(HttpServletRequest request, HttpServletResponse response, Long shopId){

        try {
            ComUser comUser = getComUser(request);

            boolean result = sfShopService.shout(shopId, comUser.getId());

            return result;
        } catch (Exception e) {
            log.error("呐喊失败![shopId="+shopId+"][comUser="+getComUser(request)+"]");
            e.printStackTrace();
        }

        return false;
    }

    @RequestMapping("/getPoster")
    public ModelAndView getPoster(HttpServletRequest request, HttpServletResponse response, Long shopId){
        ModelAndView mav = new ModelAndView("mall/shop/exclusivePoster");

        try {
            ComUser comUser = getComUser(request);
                    comUser = comUserMapper.selectByPrimaryKey(comUser.getId());
            SfShop sfShop = sfShopService.getSfShopById(shopId);
            String realPath = request.getServletContext().getRealPath("/");
            String posterName = comUser.getId() + ".jpg";

            File posterDir = new File(realPath + "static/images/shop/poster/");
            if(!posterDir.exists()) posterDir.mkdirs();

            //二维码
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
            String qrCodePath = posterDir.getAbsolutePath()+"/"+posterName;
            String shopUrl = basePath+shopId+"/"+comUser.getId()+"/shop.shtml";
            CreateParseCode.createCode(200, 200, shopUrl, qrCodePath);

            //用户头像
            String headImgPath = posterDir.getAbsolutePath()+"/h-"+comUser.getId()+".jpg";
            DownloadImage.download(comUser.getWxHeadImg(), "h-"+comUser.getId()+".jpg", posterDir.getAbsolutePath());
            ImageUtils.scale2(headImgPath, headImgPath, 130, 130, false);

            //画专属海报
            String bgPath = realPath + "static/images/shop/background-img/bg-shop.png";
            String shopPosterPath = realPath + "static/images/shop/poster/shop-poster-"+comUser.getId()+".jpg";
            String content = "我是"+comUser.getWxNkName();
            Map<String, Integer> positionMap = new HashMap<>();
            positionMap.put("headImg-left", 195);
            positionMap.put("headImg-top", 130);
            positionMap.put("bgImg-left", 0);
            positionMap.put("bgImg-top", 0);
            positionMap.put("qrCodeImg-left", 160);
            positionMap.put("qrCodeImg-top", 368);
            positionMap.put("content-left", 520/2-content.length()/2*28-(content.length()%2*14));
            positionMap.put("content-top", 306);
            drawPoster(headImgPath, qrCodePath, bgPath, new String[]{content}, shopPosterPath, positionMap, new Font("微软雅黑", Font.PLAIN, 28), new Color(247,60,140));


            String curUrl = request.getRequestURL().toString()+"?shopId="+shopId;
            /** 获取调用jssdk所需数据 **/
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
            CreateParseCode.createCode(300, 300, basePath+"shop/detail.shtml?skuId="+skuId+"&shopId="+shopId+"&fromUserId="+comUser.getId(), qrCodePath);

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

            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("skuPoster", basePath + "static/images/shop/poster/sku-poster-"+comUser.getId()+".jpg");
            return dataMap;
        } catch (Exception e) {
            log.error("获取专属海报失败![shopId=" + shopId + "][skuId="+skuId+"][comUser=" + getComUser(request) + "]");
            e.printStackTrace();
        }

        //mav.setViewName("error");
        return "error";
    }

    @RequestMapping("/sharePlan")
    public String sharePlan(HttpServletRequest request, HttpServletResponse response, Model model, Integer shopId){
        model.addAttribute("shopId", shopId);

        return "mall/shop/sharePlan";
    }

    /**
     * @Author jjh
     * @Date 2016/4/8 0008 下午 5:50
     * 商品详情
     */
    @RequestMapping("/detail.shtml")
    public ModelAndView getSkuDetail(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(value="skuId",required = true) Integer skuId,
                                     @RequestParam(value="shopId",required = true) Long shopId,
                                     @RequestParam(value="fromUserId",required = false) Long fromUserId) throws Exception {
        SfShop sfShop =sfShopService.getSfShopById(shopId);
        if(sfShop==null){
            throw new BusinessException("该店铺不存在！");
        }
        ComSku comSku = skuService.getSkuById(skuId);
        if(comSku==null){
            throw new BusinessException("该商品不存在！");
        }
        SkuInfo skuInfo = skuService.getSkuInfoBySkuId(shopId, skuId);
        List<ComSkuImage> comSkuImageList =  skuService.findComSkuImages(skuId);
        ComSkuImage comSkuImage = skuService.findDefaultComSkuImage(skuId);
        ComUser user = getComUser(request);
        //店铺浏览量
        if (fromUserId != null) {
            sfUserShopViewService.addShopView(user.getId(), shopId);
        }
        ComUser fromUser = userService.getUserById(fromUserId);
        userService.getShareUser(user.getId(),fromUserId);//来自分享人的信息
        //是否关注
        Boolean forcusSF = WxUserUtils.getInstance().isUserForcusSF(user);
        //jssdk
        String curUrl = request.getRequestURL().toString()+"?skuId="+skuId+"&shopId="+shopId;
               curUrl += fromUser==null ? "" : "&fromUserId="+fromUserId;
        /** 获取调用jssdk所需数据 **/
        Map<String, String> shareMap = jssdkService.requestJSSDKData(curUrl);
        //要分享的数据
        shareMap.put("shareTitle", "我是"+user.getWxNkName()+",我为"+skuInfo.getComSku().getName()+"代言!");
        shareMap.put("shareDesc", spuService.loadSpu(skuInfo.getComSku().getSpuId()).getSlogan());
        shareMap.put("shareImg", PropertiesUtils.getStringValue("index_product_220_220_url") + skuImageService.loadBySkuId(skuId).get(0).getImgUrl());
        shareMap.put("shareLink", curUrl);


        ModelAndView mav = new ModelAndView("/mall/shop/shop_product");
        mav.addObject("skuInfo", skuInfo);//商品信息
        mav.addObject("SkuImageList", comSkuImageList);//图片列表
        mav.addObject("defaultSkuImage", comSkuImage);//默认图片
        mav.addObject("shopId", shopId);
        mav.addObject("fromUser", fromUser);//分享链接人信息
        mav.addObject("fromUserId", fromUserId);
        mav.addObject("loginUser", user);
        mav.addObject("shareMap", shareMap);
        mav.addObject("forcusSF",forcusSF);
        return mav;
    }

}
