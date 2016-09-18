package com.masiis.shop.api.controller.shop;

import com.masiis.shop.api.bean.shop.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.utils.image.DrawImageUtil;
import com.masiis.shop.api.utils.image.Element;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.common.util.RootPathUtils;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.common.utils.DownloadImage;
import com.masiis.shop.web.common.utils.DrawPicUtil;
import com.masiis.shop.web.mall.service.order.SfOrderService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.shop.SfShopStatisticsService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import com.masiis.shop.web.platform.service.qrcode.WeiXinPFQRCodeService;
import com.masiis.shop.web.platform.service.shop.JSSDKPFService;
import com.masiis.shop.web.platform.service.shop.SfShopManQrCodeService;
import com.masiis.shop.web.platform.service.user.ComPosterService;
import com.masiis.shop.web.platform.service.user.SfUserShareParamService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
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

    private final static String urlRoot = PropertiesUtils.getStringValue("shopman_wx_qrcode_url");
    private final static String rootDir = PropertiesUtils.getStringValue("oss.OSS_SHOPMAN_WX_QRCODE");

    private static Font font30 = null;
    private static Font font25 = null;
    private static Font font20 = null;

    static {
        //宋体
        Font simsun_font = null;
        //微软雅黑
        Font msyh_font = null;
        try {
            simsun_font = Font.createFont(Font.TRUETYPE_FONT, new File(RootPathUtils.getRootPath()+"/static/font/simsun.ttc"));
            msyh_font = Font.createFont(Font.TRUETYPE_FONT, new File(RootPathUtils.getRootPath()+"/static/font/msyh.ttc"));
        } catch (Exception e) {
            throw new BusinessException("创建字体异常");
        }

        font20 = msyh_font.deriveFont(Font.PLAIN, 20);
        font25 = msyh_font.deriveFont(Font.PLAIN, 25);
        font30 = msyh_font.deriveFont(Font.PLAIN, 30);

    }

    @Resource
    private SfShopService sfShopService;
    @Resource
    private SfShopStatisticsService sfShopStatisticsService;
    @Resource
    private SfOrderService sfOrderService;
    @Resource
    private SfUserRelationService sfUserRelationService;
    @Resource
    private SfShopManQrCodeService sfShopManQrCodeService;
    @Resource
    private ComPosterService comPosterService;
    @Resource
    private JSSDKPFService jssdkService;
    @Resource
    private SfUserShareParamService sfUserShareParamService;
    @Resource
    private WeiXinPFQRCodeService weiXinPFQRCodeService;
    @Resource
    private UserService userService;

    /**
     * 零售 店铺管理首页
     * @param request
     * @param req
     * @return
     */
    @RequestMapping("/index.do")
    @ResponseBody
    @SignValid(paramType=IndexShopReq.class)
    public IndexShopRes index(HttpServletRequest request, IndexShopReq req, ComUser comUser){
        IndexShopRes res = new IndexShopRes();
        SfShop sfShop = null;
        try {
            sfShop = sfShopService.getSfShopByUserId(comUser.getId());
            if(sfShop == null){
                res.setResCode(SysResCodeCons.RES_CODE_SHOP_NULL);
                res.setResMsg(SysResCodeCons.RES_CODE_SHOP_NULL_MSG);
                log.info("该用户没有自己的店铺");
                return res;
            }
            SfShopStatistics sfShopStatistics = sfShopStatisticsService.selectByShopUserId(comUser.getId());
            Integer fansNum = sfUserRelationService.getFansOrSpokesMansNum(sfShop.getId(), false, comUser.getId());
            Integer unshippedOrderCount = sfOrderService.getUnshippedOrderCount(sfShop.getId(), comUser.getId());
            res.setComUser(comUser);
            res.setSfShop(sfShop);
            res.setSaleAmount(NumberFormat.getCurrencyInstance(Locale.CHINA).format(sfShopStatistics.getIncomeFee()));//总销售额
            res.setOrderCount(sfShopStatistics.getOrderCount());//总订单数
            res.setFansNum(fansNum);//粉丝数
            res.setUnshippedOrderCount(unshippedOrderCount);//待发货订单数
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.info("加载店铺首页数据失败![comUser=" + comUser + "][sfShop=" + sfShop+"]");
        }
        return res;
    }

    /**
     * 店铺设置
     * @param request
     * @param req
     * @return
     */
    @RequestMapping("/setupShop")
    @ResponseBody
    @SignValid(paramType=SetupShopInfoReq.class)
    public SetupShopInfoRes setupShop(HttpServletRequest request, SetupShopInfoReq req, ComUser comUser){
        SetupShopInfoRes res = new SetupShopInfoRes();
        res.setRootDir(rootDir);

        SfShop sfShop = null;
        try{
            sfShop = sfShopService.getSfShopByUserId(comUser.getId());
            res.setSfShop(sfShop);

            if(StringUtils.isNotBlank(sfShop.getWxQrCode())) {
                String url = urlRoot+sfShop.getWxQrCode();
                res.setImageUrl(url);
            } else {
                res.setImageUrl(null);
            }

            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            log.error("店铺设置页面失败![comUser="+comUser+"][sfShop="+sfShop+"]");
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(e.getMessage());
        }
        return res;
    }

    /**
     * 店铺设置
     * @param request
     * @param req
     * @return
     */
    @RequestMapping("/updateShop")
    @ResponseBody
    @SignValid(paramType=SetupShopInfoReq.class)
    public SetupShopInfoRes updateShop(HttpServletRequest request, SetupShopInfoReq req, ComUser comUser){
        SetupShopInfoRes res = new SetupShopInfoRes();
        try{
            SfShop sfShop = sfShopService.getSfShopByUserId(comUser.getId());
            sfShop.setName(req.getName());
            sfShop.setExplanation(req.getExplanation());
            sfShop.setWxQrCodeDescription(req.getWxQrCodeDescription());
            sfShop.setWxQrCode(req.getFileName());

            sfShopService.updateById(sfShop);
            log.info("设置店铺[sfShop=" + sfShop + "]");

            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            log.error("设置店铺失败!" + req.toString());
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(e.getMessage());
        }
        return res;
    }

    /**
     * 零售 运费设置 自己发货运费回显
     * @param request
     * @return
     */
    @RequestMapping("/showFreight.do")
    @ResponseBody
    @SignValid(paramType=FreightReq.class)
    public FreightRes showFreight(HttpServletRequest request, FreightReq req, ComUser comUser){
        FreightRes res = new FreightRes();
        SfShop sfShop = null;
        try{
            sfShop = sfShopService.getSfShopByUserId(comUser.getId());
            res.setOwnShipAmount(sfShop.getOwnShipAmount());

            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch(Exception e){
            log.error("自己发货运费显示失败!" + sfShop.toString());
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(e.getMessage());
        }
        return res;
    }

    /**
     * 零售 运费设置 自己发货运费更新
     * @param request
     * @return
     */
    @RequestMapping("/setupFreight.do")
    @ResponseBody
    @SignValid(paramType=FreightReq.class)
    public FreightRes setupFreight(HttpServletRequest request, FreightReq req, ComUser comUser){
        FreightRes res = new FreightRes();
        SfShop sfShop = null;
        try{
            sfShop = sfShopService.getSfShopByUserId(comUser.getId());
            sfShop.setOwnShipAmount(new BigDecimal(req.getShipAmount()));
            sfShopService.updateById(sfShop);

            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch(Exception e){
            log.error("自己发货运费设置失败!" + sfShop.toString());
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(e.getMessage());
        }
        return res;
    }

    @RequestMapping("/howGetQrImg")
    @ResponseBody
    public Object howGetQrImg() {
        Map map = new HashMap();
        map.put("url", PropertiesUtils.getStringValue("howGetQrImg"));
        return map;
    }

    public Font loadFont(File file){
        Font font = new Font("华文细黑", Font.PLAIN, 20);
        try {
            FileInputStream fi = new FileInputStream(file);
            BufferedInputStream fb = new BufferedInputStream(fi);
            font = Font.createFont(Font.TRUETYPE_FONT, fb);
        }
        catch (Exception e) {
            log.error("读取本地字体文件出错了"+e);
            e.printStackTrace();
        }

        return font;
    }

    /**
     * 获取店铺海报
     * @param request
     * @param indexShopReq
     * @param comUser
     * @return
     */
    @RequestMapping("/getPoster")
    @ResponseBody
    @SignValid(paramType=IndexShopReq.class)
    public IndexShopRes getPoster(HttpServletRequest request, IndexShopReq indexShopReq, ComUser comUser){
        IndexShopRes indexShopRes = new IndexShopRes();

        try {
//            if(indexShopReq.getFromUserId() != null){
//                comUser = userService.getUserById(indexShopReq.getFromUserId());
//            }else{
//                Long shopId = sfShopService.getSfShopByUserId(comUser.getId()).getId();
//                indexShopReq.setShopId(shopId);
//            }
//
//
//            /**
//             * 获取海报
//             */
//            //获取海报1: 海报已存在,直接返回
//            SfUserShareParam condition = new SfUserShareParam();
//            condition.setfUserId(0L);//合伙人获取店铺二维码userId固定值为0
//            condition.setShopId(indexShopReq.getShopId());
//            SfUserShareParam sfUserShareParam = sfUserShareParamService.loadByCondition(condition);
//
//            log.info("获取海报参数(查询参数[condition="+condition+"]): [sfUserShareParam=" + sfUserShareParam+"]");
//
//            Long shareParamId = (sfUserShareParam != null) ? sfUserShareParam.getId() : -1L;
//
//            ComPoster comPosterParam = new ComPoster();
//            comPosterParam.setType(1);
//            comPosterParam.setUserId(comUser.getId());
//            comPosterParam.setShareParamId(shareParamId);
//
//            ComPoster comPoster = comPosterService.findByCondition(comPosterParam);
//            log.info("获取海报图片信息(查询参数[comPosterParam="+comPosterParam+"]): [comPoster=" + comPoster+"]");
//            if(comPoster != null) {
//
//                indexShopRes.setShopPoster(PropertiesUtils.getStringValue("index_user_poster_url") + comPoster.getPosterName());
//                indexShopRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
//                indexShopRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
//                return indexShopRes;
//            }
//
//
//            //获取海报2:如果海报不存在或已经过期,重新创建海报
//            String headImg = "h-"+comUser.getId()+".png";
//            String qrcodeName = "qrcode-shop-"+comUser.getId()+"-"+indexShopReq.getShopId()+".png";
//            String bgPoster = "shop-"+indexShopReq.getShopId()+".png";
//            String posterDirPath = request.getServletContext().getRealPath("/")+"static/images/poster";
//            File posterDir = new File(posterDirPath);
//            if(!posterDir.exists()){
//                posterDir.mkdirs();
//            }
//            //先删除旧的图片,再下载新的图片
//            new File(posterDirPath+"/"+headImg).delete();
//            new File(posterDirPath+"/"+bgPoster).delete();
//            new File(posterDirPath+"/"+qrcodeName).delete();
//
//            File headImgFile   = new File(posterDirPath+"/"+headImg);
//            File bgImgFile     = new File(posterDirPath+"/"+bgPoster);
//            File qrcodeFile    = new File(posterDirPath+"/"+qrcodeName);
//            //File qrcodeImgFile = new File(posterDirPath+"/"+qrcodeName);
//            if(!headImgFile.exists() && StringUtils.isNotBlank(comUser.getWxHeadImg()))   DownloadImage.download(comUser.getWxHeadImg(), headImg, posterDirPath);
//            if(!headImgFile.exists() && StringUtils.isBlank(comUser.getWxHeadImg()))      OSSObjectUtils.downloadFile("static/user/background_poster/h-default.png", headImgFile.getAbsolutePath());
//            if(!bgImgFile.exists())     OSSObjectUtils.downloadFile("static/user/background_poster/bg-shop.png", bgImgFile.getAbsolutePath());
//
//            SfShop sfShop = sfShopService.getSfShopById(indexShopReq.getShopId());
//            if(sfShop == null){
//                throw new BusinessException("店铺不存在[comUser="+comUser+"][shopId="+indexShopReq.getShopId()+"]");
//            }
//            String[] qrcodeResult = null;
//            if(StringUtils.isBlank(sfShop.getQrCode())){
//                log.info("到微信端获取店铺永久二维码.........![sfShop="+sfShop+"]");
//                qrcodeResult = weiXinPFQRCodeService.createShopOrSkuQRCode(0L, indexShopReq.getShopId(), null);
//                if(qrcodeResult[1] == null) throw new BusinessException("合伙人获取店铺二维码失败![comUser="+comUser+"][shopId="+indexShopReq.getShopId()+"]");
//                DownloadImage.download(qrcodeResult[1], qrcodeName, posterDirPath);
//                OSSObjectUtils.uploadFile(qrcodeFile, "static/shop/shop_qrcode/");
//                sfShop.setQrCode(qrcodeFile.getName());
//                sfShopService.updateById(sfShop);
//                log.info("成功获取并保存店铺永久二维码........[sfShop="+sfShop+"]");
//            }
//            if(!qrcodeFile.exists()) OSSObjectUtils.downloadFile("static/shop/shop_qrcode/"+sfShop.getQrCode(), qrcodeFile.getAbsolutePath());
//
//            //画图
//            String fontPath = request.getServletContext().getRealPath("/")+"static/font/msyh.ttc";
//            Date curDate = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//            String startTime = sdf.format(curDate);
//            curDate.setDate(curDate.getDate()+30);
//            String endDate = sdf.format(curDate);
//
//            Element headImgElement = new Element(317, 201, 120, 120, ImageIO.read(new File(posterDirPath+"/"+headImg)));
//            Element bgPosterImgElement = new Element(0, 0, 750, 1334, ImageIO.read(new File(posterDirPath+"/"+bgPoster)));
//            Element qrcodeImgElement = new Element(236, 602, 280, 280, ImageIO.read(new File(posterDirPath+"/"+qrcodeName)));
//            //Element text1Element = new Element(186, 304,   font1, new Color(51, 51, 51), "我是"+comUser.getWxNkName()+",正品特供,好友专享价,尽在我的麦链小店,不是好友看不到哦。长按二维码识别进入麦链小店。");
//            String title = "Hi，我是"+comUser.getWxNkName();
//            String desc1 = "正品特供，好友专享价，尽在我的麦链";
//            String desc2 = "商店，不是好友看不到哦~";
//            String desc3 = "长按二维码进来逛逛吧！";
//            Element text1Element = new Element((750-new DrawPicUtil().getStringPointSize(title, font30))/2, 376, font30, new Color(200, 166, 106), title);
//            Element text2Element = new Element((750-new DrawPicUtil().getStringPointSize(desc1, font30))/2, 420, font30, new Color(200, 166, 106), desc1);
//            Element text3Element = new Element((750-new DrawPicUtil().getStringPointSize(desc2, font30))/2, 464, font30, new Color(200, 166, 106), desc2);
//            Element text4Element = new Element((750-new DrawPicUtil().getStringPointSize(desc3, font30))/2, 508, font30, new Color(200, 166, 106), desc3);
//            text1Element.setLineStyle(0);
//            text2Element.setLineStyle(0);
//            text3Element.setLineStyle(0);
//            text4Element.setLineStyle(0);
//
//            List<Element> drawElements = new ArrayList<>();
//            drawElements.add(headImgElement);
//            drawElements.add(bgPosterImgElement);
//            drawElements.add(qrcodeImgElement);
//            drawElements.add(text1Element);
//            drawElements.add(text2Element);
//            drawElements.add(text3Element);
//            drawElements.add(text4Element);
//
//            int              random           = (int)((Math.random()*9+1)*100000);
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//            Date             createTime       = new Date();
//            String           posterName       = simpleDateFormat.format(createTime)+random+".png";
//            DrawImageUtil.drawImage(750, 1334, drawElements, "static/user/poster/"+posterName);
//
//            //保存二维码海报图片地址
//            ComPoster newComPoster = new ComPoster();
//            newComPoster.setCreateTime(createTime);
//            if(qrcodeResult != null){
//                newComPoster.setShareParamId(Long.valueOf(qrcodeResult[0]));
//            }else{
//                newComPoster.setShareParamId(shareParamId);
//            }
//            log.info("二维码参数ID[qrcodeResult="+qrcodeResult+"][shareParamId="+shareParamId+"]");
//            newComPoster.setType(1);
//            newComPoster.setUserId(comUser.getId());
//            newComPoster.setPosterName(posterName);
//
//            comPosterService.add(newComPoster);
//
//            //二维码获取成功,更新com_user成为代言人
//            SfUserRelation sfUserRelation = sfUserRelationService.getSfUserRelationByUserIdAndShopId(comUser.getId(), indexShopReq.getShopId());
//            if (sfUserRelation != null){
//                sfUserRelation.setIsSpokesman(1);
//                sfUserRelationService.updateUserRelation(sfUserRelation);
//            }else {
//                log.info("sfUserRelation为null");
//            }
//
//            indexShopRes.setShopPoster(PropertiesUtils.getStringValue("index_user_poster_url") + newComPoster.getPosterName());

            comUser = userService.getUserById(comUser.getId());
            SfShop sfShop = sfShopService.getSfShopByUserId(comUser.getId());

            String posterUrl = sfShopService.createShopPoster(comUser, sfShop);
            indexShopRes.setShopPoster(posterUrl);
            indexShopRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            indexShopRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            indexShopRes.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            indexShopRes.setResMsg(e.getMessage());

            log.error("获取专属海报失败![indexShopReq=" + indexShopReq + "][comUser=" + comUser + "]"+e);
            e.printStackTrace();
        }

        return indexShopRes;
    }

}
