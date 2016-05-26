package com.masiis.shop.web.mall.controller.shop;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.ImageUtils;
import com.masiis.shop.common.util.OSSObjectUtils;
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
import com.masiis.shop.web.mall.service.qrcode.WeiXinQRCodeService;
import com.masiis.shop.web.mall.service.shop.JSSDKService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.user.SfUserShopViewService;
import com.masiis.shop.web.mall.service.user.UserService;
import com.masiis.shop.web.mall.utils.DownloadImage;
import com.masiis.shop.web.mall.utils.image.DrawImageUtil;
import com.masiis.shop.web.mall.utils.image.Element;
import com.masiis.shop.web.mall.utils.qrcode.CreateParseCode;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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
    @Resource
    private WeiXinQRCodeService weiXinQRCodeService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("mall/shop/index");

        return mav;
    }

    /**
     * 呐喊
     *
     * @param request
     * @param response
     * @param shopId
     * @return
     */
    @RequestMapping("/shout")
    @ResponseBody
    public Object shout(HttpServletRequest request, HttpServletResponse response, Long shopId) {

        try {
            ComUser comUser = getComUser(request);

            boolean result = sfShopService.shout(shopId, comUser.getId());

            return result;
        } catch (Exception e) {
            log.error("呐喊失败![shopId=" + shopId + "][comUser=" + getComUser(request) + "]");
            e.printStackTrace();
        }

        return false;
    }

    @RequestMapping("/getPoster")
    public ModelAndView getPoster(HttpServletRequest request, HttpServletResponse response, Long shopId) {
        ModelAndView mav = new ModelAndView("mall/shop/exclusivePoster");

        try {
            ComUser comUser = getComUser(request);
            comUser = comUserMapper.selectByPrimaryKey(comUser.getId());


            String headImg = "h-"+comUser.getId()+".png";
            String qrcodeName = "qrcode-shop-"+comUser.getId()+"-"+shopId+".png";
            String bgPoster = "shop-"+shopId+".png";
            String posterDirPath = request.getServletContext().getRealPath("/")+"static/images/poster";
            File  posterDir = new File(posterDirPath);
            if(!posterDir.exists()){
                posterDir.mkdirs();
            }
            DownloadImage.download(comUser.getWxHeadImg(), headImg, posterDirPath);
            DownloadImage.download(weiXinQRCodeService.createShopOrSkuQRCode(comUser.getId(), shopId, null), qrcodeName, posterDirPath);
            OSSObjectUtils.downloadFile("static/user/background_poster/exclusive.png", posterDirPath+"/"+bgPoster);

            //画图
            String fontPath = request.getServletContext().getRealPath("/")+"static/font";
            //Font font1 = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath+"/msyh.ttc"));
            //font1.deriveFont(Font.PLAIN, 32);
            Font font1 = new Font("华文细黑", Font.PLAIN, 20);
            Font font2 = new Font("华文细黑", Font.PLAIN, 28);
            Date curDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String startTime = sdf.format(curDate);
            curDate.setDate(curDate.getDate()+30);
            String endDate = sdf.format(curDate);

            Element headImgElement = new Element(195, 100, 130, 130, ImageIO.read(new File(posterDirPath+"/"+headImg)));
            Element bgPosterImgElement = new Element(0, 0, 520, 710, ImageIO.read(new File(posterDirPath+"/"+bgPoster)));
            Element qrcodeImgElement = new Element(160, 338, 220, 200, ImageIO.read(new File(posterDirPath+"/"+qrcodeName)));
            Element text1Element = new Element((520-("我是"+comUser.getWxNkName()).length()*font2.getSize())/2, 245,   font2, new Color(247, 60, 140), "我是"+comUser.getWxNkName());
            Element text2Element = new Element(190, 530, font1, new Color(51, 51, 51), "该二维码有效期为");
            Element text3Element = new Element(165, 585, font1, new Color(51, 51, 51), startTime+"-"+endDate);
            text1Element.setLineStyle(0);
            text3Element.setLineStyle(0);
            java.util.List<Element> drawElements = new ArrayList<>();
            drawElements.add(headImgElement);
            drawElements.add(bgPosterImgElement);
            drawElements.add(qrcodeImgElement);
            drawElements.add(text1Element);
            drawElements.add(text2Element);
            drawElements.add(text3Element);

            DrawImageUtil.drawImage(520, 710, drawElements, "static/user/poster/exclusive-"+comUser.getId()+"-"+shopId+".png");

            mav.addObject("shopPoster", "http://file.masiis.com/static/user/poster/exclusive-"+comUser.getId()+"-"+shopId+".png");

            return mav;
        } catch (Exception e) {
            log.error("获取专属海报失败![shopId=" + shopId + "][comUser=" + getComUser(request) + "]");
            e.printStackTrace();
        }

        return mav;
    }

    private void drawPoster(String headImgPath, String qrCodePath, String bgPath, String[] content, String shopPosterPath, Map<String, Integer> positionMap, Font font, Color color) {
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

        g.drawImage(headImage, positionMap.get("headImg-left"), positionMap.get("headImg-top"), 130, 130, null);
        g.drawImage(bgImage, positionMap.get("bgImg-left"), positionMap.get("bgImg-top"), null);
        g.drawImage(qrCodeImage, positionMap.get("qrCodeImg-left"), positionMap.get("qrCodeImg-top"), 200, 200, null);

        g.setFont(font);
        g.setColor(color);
        for (int i = 0; i < content.length; i++) {
            g.drawString(content[i], positionMap.get("content-left"), positionMap.get("content-top") + (font.getSize() + 15) * i);
        }

        Date curDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String startTime = sdf.format(curDate);
        curDate.setDate(curDate.getDate()+30);
        String endDate = sdf.format(curDate);

        g.setFont(new Font("华文细黑", Font.PLAIN, 20));
        g.setColor(new Color(51, 51, 51));
        g.drawString("该二维码有效期为", 190, 550);
        g.drawString(startTime+"-"+endDate, 165, 580);

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
    public Object getSkuPoster(HttpServletRequest request, HttpServletResponse response, Long shopId, Integer skuId) {
        //ModelAndView mav = new ModelAndView("mall/shop/skuPoster");

        try {
            ComUser comUser = getComUser(request);
            comUser = comUserMapper.selectByPrimaryKey(comUser.getId());
            String realPath = request.getServletContext().getRealPath("/");
            String posterName = comUser.getId() + "-" + shopId + "-" + skuId + ".jpg";

            File posterDir = new File(realPath + "static/images/shop/poster/");
            if (!posterDir.exists()) posterDir.mkdirs();

            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
            String qrCodePath = posterDir.getAbsolutePath() + "/" + posterName;
            CreateParseCode.createCode(300, 300, basePath + "shop/detail.shtml?skuId=" + skuId + "&shopId=" + shopId + "&fromUserId=" + comUser.getId(), qrCodePath);

            //用户头像
            String headImgPath = posterDir.getAbsolutePath() + "/h-" + comUser.getId() + ".jpg";
            DownloadImage.download(comUser.getWxHeadImg(), "h-" + comUser.getId() + ".jpg", posterDir.getAbsolutePath());
            ImageUtils.scale2(headImgPath, headImgPath, 90, 90, false);

            //画专属海报
            String bgPath = realPath + "static/images/shop/background-img/sku-" + skuId + ".png";
            String skuPosterPath = realPath + "static/images/shop/poster/sku-poster-" + comUser.getId() + ".jpg";
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
            drawPoster(headImgPath, qrCodePath, bgPath, new String[]{"我是" + comUser.getWxNkName(), "我为" + skuName + "代言!"}, skuPosterPath, positionMap, new Font("微软雅黑", Font.PLAIN, 28), new Color(51, 51, 51));

            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("skuPoster", basePath + "static/images/shop/poster/sku-poster-" + comUser.getId() + ".jpg");
            return dataMap;
        } catch (Exception e) {
            log.error("获取专属海报失败![shopId=" + shopId + "][skuId=" + skuId + "][comUser=" + getComUser(request) + "]");
            e.printStackTrace();
        }

        //mav.setViewName("error");
        return "error";
    }

    @RequestMapping("/sharePlan")
    public String sharePlan(HttpServletRequest request, HttpServletResponse response, Model model, Integer shopId) {
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
                                     @RequestParam(value = "skuId", required = true) Integer skuId,
                                     @RequestParam(value = "shopId", required = true) Long shopId,
                                     @RequestParam(value = "fromUserId", required = false) Long fromUserId) throws Exception {
        SfShop sfShop = sfShopService.getSfShopById(shopId);
        if (sfShop == null) {
            throw new BusinessException("该店铺不存在！");
        }
        ComSku comSku = skuService.getSkuById(skuId);
        if (comSku == null) {
            throw new BusinessException("该Sku不存在！");
        }
        SkuInfo skuInfo = skuService.getSkuInfoBySkuId(shopId, skuId);
        List<ComSkuImage> comSkuImageList = skuService.findComSkuImages(skuId);
        ComSkuImage comSkuImage = skuService.findDefaultComSkuImage(skuId);
        ComUser user = getComUser(request);
        //店铺浏览量
        if (fromUserId != null) {
            sfUserShopViewService.addShopView(user.getId(), shopId);
        }
        ModelAndView mav = new ModelAndView("/mall/shop/shop_product");
        mav.addObject("skuInfo", skuInfo);//商品信息
        mav.addObject("SkuImageList", comSkuImageList);//图片列表
        mav.addObject("defaultSkuImage", comSkuImage);//默认图片
        mav.addObject("shopId", shopId);
        mav.addObject("loginUser", user);
        mav.addObject("sfShop", sfShop);
        return mav;
    }

}
