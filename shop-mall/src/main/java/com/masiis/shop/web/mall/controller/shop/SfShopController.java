package com.masiis.shop.web.mall.controller.shop;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.ImageUtils;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mallBeans.SkuInfo;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.common.utils.DownloadImage;
import com.masiis.shop.web.common.utils.DrawPicUtil;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.qrcode.WeiXinSFQRCodeService;
import com.masiis.shop.web.mall.service.shop.JSSDKSFService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import com.masiis.shop.web.mall.utils.image.DrawImageUtil;
import com.masiis.shop.web.mall.utils.image.Element;
import com.masiis.shop.web.mall.utils.qrcode.CreateParseCode;
import com.masiis.shop.web.platform.service.user.ComPosterService;
import com.masiis.shop.web.platform.service.user.SfUserShareParamService;
import org.apache.commons.lang.StringUtils;
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
//    @Resource
//    private SkuImageService skuImageService;
//    @Resource
//    private UserService userService;
//    @Resource
//    private SfUserShopViewService sfUserShopViewService;
    @Resource
    private JSSDKSFService jssdkService;
    @Resource
    private SfUserRelationService sfUserRelationService;
    @Resource
    private WeiXinSFQRCodeService weiXinSFQRCodeService;
    @Resource
    private SfUserShareParamService sfUserShareParamService;
    @Resource
    private ComPosterService comPosterService;

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
    public ModelAndView getPoster(HttpServletRequest request, HttpServletResponse response,
                                  Long shopId,
                                  @RequestParam(value = "fromUserId", required = false)Long fromUserId
                                  ) {
        ModelAndView mav = new ModelAndView("mall/shop/exclusivePoster");

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

            /**
             * 获取海报
             */
            //获取海报1: 海报已存在,直接返回
            SfUserShareParam condition = new SfUserShareParam();
            condition.setfUserId(comUser.getId());
            condition.setShopId(shopId);
            SfUserShareParam sfUserShareParam = sfUserShareParamService.loadByCondition(condition);

            Long shareParamId = (sfUserShareParam != null) ? sfUserShareParam.getId() : -1L;

            ComPoster comPosterParam = new ComPoster();
            comPosterParam.setType(2);
            comPosterParam.setUserId(comUser.getId());
            comPosterParam.setShareParamId(shareParamId);

            ComPoster comPoster = comPosterService.findByCondition(comPosterParam);
            Long curTime = System.currentTimeMillis();
            Long monthTime = 1000L * 60 * 60 * 24 * 28;//28天
            if(comPoster != null && (curTime-comPoster.getCreateTime().getTime()) < monthTime) {
                mav.addObject("shopPoster", PropertiesUtils.getStringValue("index_user_poster_url") + comPoster.getPosterName());
                mav.addObject("shareMap", resultMap);

                return mav;
            }


            //获取海报2:如果海报不存在或已经过期,重新创建海报
            String headImg = "h-"+comUser.getId()+".png";
            String qrcodeName = "qrcode-shop-"+comUser.getId()+"-"+shopId+".png";
            String bgPoster = "shop-"+shopId+".png";
            String posterDirPath = request.getServletContext().getRealPath("/")+"static/images/poster";
            File  posterDir = new File(posterDirPath);
            if(!posterDir.exists()){
                posterDir.mkdirs();
            }

            //先删除旧的图片,再下载新的图片
            new File(posterDirPath+"/"+headImg).delete();
            new File(posterDirPath+"/"+bgPoster).delete();
            new File(posterDirPath+"/"+qrcodeName).delete();

            File headImgFile   = new File(posterDirPath+"/"+headImg);
            File bgImgFile     = new File(posterDirPath+"/"+bgPoster);
            //File qrcodeImgFile = new File(posterDirPath+"/"+qrcodeName);
            if(!headImgFile.exists() && StringUtils.isNotBlank(comUser.getWxHeadImg()))   DownloadImage.download(comUser.getWxHeadImg(), headImg, posterDirPath);
            if(!headImgFile.exists() && StringUtils.isBlank(comUser.getWxHeadImg()))      OSSObjectUtils.downloadFile("static/user/background_poster/h-default.png", headImgFile.getAbsolutePath());
            if(!bgImgFile.exists())     OSSObjectUtils.downloadFile("static/user/background_poster/exclusive.png", posterDirPath+"/"+bgPoster);
            String[] qrcodeResult = weiXinSFQRCodeService.createShopOrSkuQRCode(comUser.getId(), shopId, null);
            DownloadImage.download(qrcodeResult[1], qrcodeName, posterDirPath);

            //画图
            String fontPath = request.getServletContext().getRealPath("/")+"static/font";
            //Font font1 = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath+"/msyh.ttc"));
            //font1.deriveFont(Font.PLAIN, 32);
            Font font1 = new Font("华文细黑", Font.PLAIN, 24);
            Font font2 = new Font("华文细黑", Font.PLAIN, 30);
            Date curDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String startTime = sdf.format(curDate);
            curDate.setDate(curDate.getDate()+30);
            String endDate = sdf.format(curDate);

            Element headImgElement = new Element(316, 200, 120, 120, ImageIO.read(new File(posterDirPath+"/"+headImg)));
            Element bgPosterImgElement = new Element(0, 0, 750, 1334, ImageIO.read(new File(posterDirPath+"/"+bgPoster)));
            Element qrcodeImgElement = new Element(236, 446, 280, 280, ImageIO.read(new File(posterDirPath+"/"+qrcodeName)));
            String title = "Hi，我是"+comUser.getWxNkName();
            String limit = "该二维码有效期为";
            String limitDate = startTime+"-"+endDate;
            Element text1Element = new Element((750-new DrawPicUtil().getStringPointSize(title, font2))/2, 380,   font2, new Color(200,166,106), title);
            Element text2Element = new Element((750-new DrawPicUtil().getStringPointSize(limit, font1))/2, 976,   font1, new Color(200,166,106), limit);
            Element text3Element = new Element((750-new DrawPicUtil().getStringPointSize(limitDate, font1))/2, 1008,  font1, new Color(200,166,106), limitDate);
            text1Element.setLineStyle(0);
            text2Element.setLineStyle(0);
            text3Element.setLineStyle(0);
            List<Element> drawElements = new ArrayList<>();
            drawElements.add(headImgElement);
            drawElements.add(bgPosterImgElement);
            drawElements.add(qrcodeImgElement);
            drawElements.add(text1Element);
            drawElements.add(text2Element);
            drawElements.add(text3Element);


            int              random           = (int)((Math.random()*9+1)*100000);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date             createTime       = new Date();
            String           posterName       = simpleDateFormat.format(createTime)+random+".png";
            DrawImageUtil.drawImage(750, 1334, drawElements, "static/user/poster/"+posterName);

            //保存二维码海报图片地址
            ComPoster newComPoster = new ComPoster();
            newComPoster.setCreateTime(createTime);
            newComPoster.setShareParamId(Long.valueOf(qrcodeResult[0]));
            newComPoster.setType(2);
            newComPoster.setUserId(comUser.getId());
            newComPoster.setPosterName(posterName);
            if(comPoster == null){
                comPosterService.add(newComPoster);
            }else{
                newComPoster.setId(comPoster.getId());
                comPosterService.update(newComPoster);

                //删除旧海报
                OSSObjectUtils.deleteBucketFile("static/user/poster/"+comPoster.getPosterName());
            }

            //二维码获取成功,更新sfUserRelation成为代言人
            SfUserRelation sfUserRelation = sfUserRelationService.getSfUserRelationByUserIdAndShopId(comUser.getId(), shopId);
            if (sfUserRelation != null){
                sfUserRelation.setIsSpokesman(1);
                sfUserRelationService.updateUserRelation(sfUserRelation);
            }else {
                log.info("SfUserRelation为空");
            }

//            resultMap.put("appId", WxConsPF.APPID);
//            resultMap.put("shareTitle", "我是"+comUser.getWxNkName()+",我为朋友代言!");
//            resultMap.put("shareDesc", "这是ta在麦链商城认证的小店,识别二维码进店购买,获取自己的专属海报还可以赚钱");
//            resultMap.put("shareLink", shareLink);
//            resultMap.put("shareImg", comUser.getWxHeadImg());
//
            mav.addObject("shareMap", resultMap);
            mav.addObject("shopPoster", PropertiesUtils.getStringValue("index_user_poster_url") + newComPoster.getPosterName());

            return mav;
        } catch (Exception e) {
            log.error("获取专属海报失败![shopId=" + shopId + "][comUser=" + getComUser(request) + "]"+e);
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
     * jjh
     * c 端 商品详情
     * @param request
     * @param response
     * @param skuId 商品ID
     * @param shopId 小铺ID
     * @param isOwnShip 是否自己发货
     * @return
     * @throws Exception
     */
    @RequestMapping("/detail.shtml")
    public ModelAndView getSkuDetail(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(value = "skuId", required = true) Integer skuId,
                                     @RequestParam(value = "shopId", required = true) Long shopId,
                                     @RequestParam(value = "isOwnShip", required = true) Integer isOwnShip) throws Exception {
        SfShop sfShop = sfShopService.getSfShopById(shopId);
        if (sfShop == null) {
            throw new BusinessException("该店铺不存在！");
        }
        ComSku comSku = skuService.getSkuById(skuId);
        if (comSku == null) {
            throw new BusinessException("该Sku不存在！");
        }
        SkuInfo skuInfo = skuService.getSkuInfoBySkuId(shopId, skuId,isOwnShip);
        List<ComSkuImage> comSkuImageList = skuService.findComSkuImages(skuId);
        ComSkuImage comSkuImage = skuService.findDefaultComSkuImage(skuId);
        ComUser user = getComUser(request);
        //获取店主二维码
        sfShop.setWxQrCode(sfShopService.getWXQRImgByCode(sfShop.getWxQrCode()));
        ModelAndView mav = new ModelAndView("/mall/shop/shop_product");
        mav.addObject("skuInfo", skuInfo);//商品信息
        mav.addObject("SkuImageList", comSkuImageList);//图片列表
        mav.addObject("defaultSkuImage", comSkuImage);//默认图片
        mav.addObject("shopId", shopId);
        mav.addObject("loginUser", user);
        mav.addObject("sfShop", sfShop);
        mav.addObject("isOwnShip", isOwnShip);
        mav.addObject("userPid", request.getSession().getAttribute("userPid")==null?0:request.getSession().getAttribute("userPid"));
        return mav;
    }

}
