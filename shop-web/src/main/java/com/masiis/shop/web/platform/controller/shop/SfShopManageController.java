package com.masiis.shop.web.platform.controller.shop;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.ImageUtils;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.order.SfOrderMapper;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.user.SfUserShopViewMapper;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.dao.po.SfShopStatistics;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.mall.service.shop.SfShopStatisticsService;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.platform.service.qrcode.WeiXinPFQRCodeService;
import com.masiis.shop.web.platform.service.shop.JSSDKPFService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.common.utils.DownloadImage;
import com.masiis.shop.web.common.utils.DrawPicUtil;
import com.masiis.shop.web.platform.service.shop.SfShopManQrCodeService;
import com.masiis.shop.web.platform.utils.image.DrawImageUtil;
import com.masiis.shop.web.platform.utils.image.Element;
import com.masiis.shop.web.platform.utils.qrcode.CreateParseCode;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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
    private SkuService skuService;
    @Resource
    private ComSkuImageMapper comSkuImageMapper;
    @Resource
    private JSSDKPFService jssdkService;
    @Resource
    private SfUserShopViewMapper sfUserShopViewMapper;
    @Resource
    private WeiXinPFQRCodeService weiXinPFQRCodeService;
    @Resource
    private SfShopStatisticsService sfShopStatisticsService;
    @Resource
    private SfShopService sfShopService;
    @Resource
    private SfShopManQrCodeService sfShopManQrCodeService;
    @Resource
    private SfUserRelationService sfUserRelationService;

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
        SfShopStatistics sfShopStatistics = null;

        try {
            comUser = getComUser(request);
            comUser = comUserMapper.selectByPrimaryKey(comUser.getId());
            sfShop = sfShopMapper.selectByUserId(comUser.getId());
            sfShopStatistics = sfShopStatisticsService.selectByShopUserId(comUser.getId());

            if(sfShop == null) return mav;

            Integer orderCount = sfOrderMapper.countByShopId(sfShop.getId()); //总订单数

//            Integer shopView = sfUserShopViewMapper.countByShopId(sfShop.getId()); //店铺浏览量
            //获取店铺粉丝
            Integer fansNum = sfUserRelationService.getFansOrSpokesMansNum(sfShop.getId(), false);

            String shopUrl = PropertiesUtils.getStringValue("mall.domain.name.address") + "/" + sfShop.getId()+"/"+comUser.getId()+"/shop.shtml";

            mav.addObject("comUser", comUser);
            mav.addObject("sfShop", sfShop);
            mav.addObject("saleAmount", NumberFormat.getCurrencyInstance(Locale.CHINA).format(sfShopStatistics.getIncomeFee()));
            mav.addObject("orderCount", sfShopStatistics.getOrderCount());
            mav.addObject("shopView", fansNum);
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
    public String updateShop(HttpServletRequest request, HttpServletResponse response, SfShop sfShop, MultipartFile qrImg){

        try {
            String fileName = sfShopManQrCodeService.uploadWxQrCodeImg(qrImg);
            if(StringUtils.isNotBlank(fileName)) {
                sfShop.setWxQrCode(fileName);
            }
            sfShopMapper.updateByPrimaryKey(sfShop);
        } catch (Exception e) {
            log.error("设置店铺失败![sfShop="+sfShop+"]");
        }

        return "redirect:"+request.getParameter("Referer");
    }

    /**
     * 设置运费
     * overwrite by JJH
     * @param request
     * @param shipAmount :0 包邮
     * @return
     */
    @RequestMapping("/setupFreight")
    @ResponseBody
    public String setupFreight(HttpServletRequest request,
                               @RequestParam(value = "shipAmount",required = true) String shipAmount){
        JSONObject object = new JSONObject();
        SfShop sfShop = null;
        try {
            ComUser comUser = getComUser(request);
            sfShop = sfShopMapper.selectByUserId(comUser.getId());
            BigDecimal bd = new BigDecimal(shipAmount);
            sfShop.setOwnShipAmount(bd);
            sfShopMapper.updateByPrimaryKey(sfShop);
            object.put("isError",false);
        } catch (Exception e) {
            object.put("isError",true);
            log.info(e.getMessage());
        }
        return object.toJSONString();
    }

    /**
     * 获取店铺海报
     * @param request
     * @param response
     * @param shopId
     * @return
     */
    @RequestMapping("/getPoster")
    public ModelAndView getPoster(HttpServletRequest request, HttpServletResponse response,
                                  Long shopId,
                                  @RequestParam(value = "fromUserId", required = false)Long fromUserId
    ){
        ModelAndView mav = new ModelAndView("platform/shop/exclusivePoster");

        try {
            ComUser comUser = null;
            String curUrl = null;
            String shareLink = null;
            if(fromUserId != null){
                comUser = comUserMapper.selectByPrimaryKey(fromUserId);
                curUrl = request.getRequestURL().toString()+"?shopId="+shopId+"&fromUserId="+comUser.getId();
            }else{
                comUser = getComUser(request);
                curUrl = request.getRequestURL().toString()+"?shopId="+shopId;
            }

            if(request.getParameter("from") != null){
                curUrl += "&from=" + request.getParameter("from");
            }
            if(request.getParameter("isappinstalled") != null){
                curUrl += "&isappinstalled=" + request.getParameter("isappinstalled");
            }

            /** 获取调用JSSDK所需要的数据 **/
            Map<String, String> resultMap = jssdkService.requestJSSDKData(curUrl);
            if(fromUserId != null){
                shareLink = curUrl;
            }else{
                shareLink = curUrl + "&fromUserId="+comUser.getId();
            }


            String headImg = "h-"+comUser.getId()+".png";
            String qrcodeName = "qrcode-shop-"+comUser.getId()+"-"+shopId+".png";
            String bgPoster = "shop-"+shopId+".png";
            String posterDirPath = request.getServletContext().getRealPath("/")+"static/images/poster";
            File  posterDir = new File(posterDirPath);
            if(!posterDir.exists()){
                posterDir.mkdirs();
            }
            //先删除旧的图片,再下载新的图片
//            new File(posterDirPath+"/"+headImg).delete();
//            new File(posterDirPath+"/"+bgPoster).delete();
//            new File(posterDirPath+"/"+qrcodeName).delete();
//            DownloadImage.download(comUser.getWxHeadImg(), headImg, posterDirPath);
//            DownloadImage.download(weiXinQRCodeService.createShopOrSkuQRCode(comUser.getId(), shopId, null), qrcodeName, posterDirPath);
//            OSSObjectUtils.downloadFile("static/user/background_poster/bg-shop.png", posterDirPath+"/"+bgPoster);
            File headImgFile   = new File(posterDirPath+"/"+headImg);
            File bgImgFile     = new File(posterDirPath+"/"+bgPoster);
            File qrcodeFile    = new File(posterDirPath+"/"+qrcodeName);
            //File qrcodeImgFile = new File(posterDirPath+"/"+qrcodeName);
            if(!headImgFile.exists() && StringUtils.isNotBlank(comUser.getWxHeadImg()))   DownloadImage.download(comUser.getWxHeadImg(), headImg, posterDirPath);
            if(!headImgFile.exists() && StringUtils.isBlank(comUser.getWxHeadImg()))      OSSObjectUtils.downloadFile("static/user/background_poster/h-default.png", headImgFile.getAbsolutePath());
            if(!bgImgFile.exists())     OSSObjectUtils.downloadFile("static/user/background_poster/bg-shop.png", bgImgFile.getAbsolutePath());

            SfShop sfShop = sfShopService.getSfShopById(shopId);
            if(sfShop == null){
                throw new BusinessException("店铺不存在[comUser="+comUser+"][shopId="+shopId+"]");
            }
            if(StringUtils.isBlank(sfShop.getQrCode())){
                String qr_code_url = weiXinPFQRCodeService.createShopOrSkuQRCode(0L, shopId, null);
                if(qr_code_url == null) throw new BusinessException("合伙人获取店铺二维码失败![comUser="+comUser+"][shopId="+shopId+"]");
                DownloadImage.download(qr_code_url, qrcodeName, posterDirPath);
                OSSObjectUtils.uploadFile(qrcodeFile, "static/shop/shop_qrcode/");
                sfShop.setQrCode(qrcodeFile.getName());
                sfShopService.updateById(sfShop);
            }
            if(!qrcodeFile.exists()) OSSObjectUtils.downloadFile("static/shop/shop_qrcode/"+sfShop.getQrCode(), qrcodeFile.getAbsolutePath());

            //画图
            String fontPath = request.getServletContext().getRealPath("/")+"static/font";
            //Font font1 = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath+"/msyh.ttc"));
            //font1.deriveFont(Font.PLAIN, 32);
            Font font1 = new Font("华文细黑", Font.BOLD, 30);
            Font font2 = new Font("华文细黑", Font.PLAIN, 20);
            Date curDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String startTime = sdf.format(curDate);
            curDate.setDate(curDate.getDate()+30);
            String endDate = sdf.format(curDate);

            Element headImgElement = new Element(317, 201, 120, 120, ImageIO.read(new File(posterDirPath+"/"+headImg)));
            Element bgPosterImgElement = new Element(0, 0, 750, 1334, ImageIO.read(new File(posterDirPath+"/"+bgPoster)));
            Element qrcodeImgElement = new Element(236, 602, 280, 280, ImageIO.read(new File(posterDirPath+"/"+qrcodeName)));
            //Element text1Element = new Element(186, 304,   font1, new Color(51, 51, 51), "我是"+comUser.getWxNkName()+",正品特供,好友专享价,尽在我的麦链小店,不是好友看不到哦。长按二维码识别进入麦链小店。");
            String title = "Hi，我是"+comUser.getWxNkName();
            String desc1 = "正品特供，好友专享价，尽在我的麦链";
            String desc2 = "商店，不是好友看不到哦~";
            String desc3 = "长按二维码进来逛逛吧！";
            Element text1Element = new Element((750-new DrawPicUtil().getStringPointSize(title, font1))/2, 376, font1, new Color(200, 166, 106), title);
            Element text2Element = new Element((750-new DrawPicUtil().getStringPointSize(desc1, font1))/2, 420, font1, new Color(200, 166, 106), desc1);
            Element text3Element = new Element((750-new DrawPicUtil().getStringPointSize(desc2, font1))/2, 464, font1, new Color(200, 166, 106), desc2);
            Element text4Element = new Element((750-new DrawPicUtil().getStringPointSize(desc3, font1))/2, 508, font1, new Color(200, 166, 106), desc3);
            text1Element.setLineStyle(0);
            text2Element.setLineStyle(0);
            text3Element.setLineStyle(0);
            text4Element.setLineStyle(0);
            //Element text2Element = new Element(34, 640, font2, new Color(102,102,102), "该二维码有效期:");
            //Element text3Element = new Element(34, 695, font2, new Color(102,102,102), startTime+"-"+endDate);

            List<Element> drawElements = new ArrayList<>();
            drawElements.add(headImgElement);
            drawElements.add(bgPosterImgElement);
            drawElements.add(qrcodeImgElement);
            drawElements.add(text1Element);
            drawElements.add(text2Element);
            drawElements.add(text3Element);
            drawElements.add(text4Element);

            DrawImageUtil.drawImage(750, 1334, drawElements, "static/user/poster/shop-"+comUser.getId()+"-"+shopId+".png");

            //二维码获取成功,更新com_user成为代言人
            comUser.setIsSpokesman(1);
            comUserMapper.updateByPrimaryKey(comUser);

            resultMap.put("appId", WxConsPF.APPID);
            resultMap.put("shareTitle", "我在麦链商城的小店");
            resultMap.put("shareDesc", "我是"+comUser.getWxNkName()+",邀请您来我的店铺逛逛~");
            resultMap.put("shareLink", shareLink);
            resultMap.put("shareImg", comUser.getWxHeadImg());

            mav.addObject("shareMap", resultMap);
            mav.addObject("shopPoster", PropertiesUtils.getStringValue("oss.BASE_URL") + "/static/user/poster/shop-"+comUser.getId()+"-"+shopId+".png");

            return mav;
        } catch (Exception e) {
            log.error("获取专属海报失败![shopId=" + shopId + "][comUser=" + getComUser(request) + "]");
            e.printStackTrace();
        }

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

        g.drawImage(headImage, positionMap.get("headImg-left"), positionMap.get("headImg-top"), 90, 90, null);
        g.drawImage(bgImage, positionMap.get("bgImg-left"), positionMap.get("bgImg-top"), null);
        g.drawImage(qrCodeImage, positionMap.get("qrCodeImg-left"), positionMap.get("qrCodeImg-top"), 200, 200, null);

        g.setFont(font);
        g.setColor(color);
        for(int i=0; i<content.length; i++){
            g.drawString(content[i], positionMap.get("content-left"), positionMap.get("content-top")+(font.getSize()+15)*i);
        }

        Date curDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String startTime = sdf.format(curDate);
        curDate.setDate(curDate.getDate()+30);
        String endDate = sdf.format(curDate);

        g.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        g.setColor(new Color(102, 102, 102));
        g.drawString("该二维码有效期为", 230, 674);
        g.drawString(startTime+"-"+endDate, 190, 700);

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
