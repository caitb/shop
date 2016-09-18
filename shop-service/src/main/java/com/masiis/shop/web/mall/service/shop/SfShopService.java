package com.masiis.shop.web.mall.service.shop;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.common.util.RootPathUtils;
import com.masiis.shop.common.util.image.DrawImageUtil;
import com.masiis.shop.common.util.image.Element;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.shop.SfShopShoutLogMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.common.utils.DownloadImage;
import com.masiis.shop.web.common.utils.DrawPicUtil;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import com.masiis.shop.web.platform.service.qrcode.WeiXinPFQRCodeService;
import com.masiis.shop.web.platform.service.user.ComPosterService;
import com.masiis.shop.web.platform.service.user.SfUserShareParamService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by cai_tb on 16/4/8.
 */
@Service
@Transactional
public class SfShopService {
    private Logger log = Logger.getLogger(this.getClass());

    private static Font font30 = null;
    private static Font font25 = null;
    private static Font font20 = null;

    static {
        //宋体
        Font simsun_font = null;
        //微软雅黑
        Font msyh_font = null;
        try {
            simsun_font = Font.createFont(Font.TRUETYPE_FONT, new File(RootPathUtils.getRootPath() + "/static/font/simsun.ttc"));
            msyh_font = Font.createFont(Font.TRUETYPE_FONT, new File(RootPathUtils.getRootPath() + "/static/font/msyh.ttc"));
        } catch (Exception e) {
            throw new BusinessException("创建字体异常");
        }

        font20 = msyh_font.deriveFont(Font.PLAIN, 20);
        font25 = msyh_font.deriveFont(Font.PLAIN, 25);
        font30 = msyh_font.deriveFont(Font.PLAIN, 30);

    }

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private SfShopMapper sfShopMapper;
    @Resource
    private SfShopShoutLogMapper sfShopShoutLogMapper;
    @Resource
    private UserService userService;
    @Resource
    private ComPosterService comPosterService;
    @Resource
    private SfUserShareParamService sfUserShareParamService;
    @Resource
    private WeiXinPFQRCodeService weiXinPFQRCodeService;
    @Resource
    private SfUserRelationService sfUserRelationService;

    /**
     * 呐喊
     *
     * @param shopId 小铺id
     * @param userId 呐喊人id
     */
    public boolean shout(Long shopId, Long userId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> condition = new HashMap<>();
        condition.put("userId", userId);
        condition.put("shopId", shopId);
        condition.put("createTime", "%" + sdf.format(new Date()).toString() + "%");

        SfShopShoutLog sfShopShoutLog = sfShopShoutLogMapper.selectByCondition(condition);
        if (sfShopShoutLog == null) {
            SfShop sfShop = sfShopMapper.selectByPrimaryKey(shopId);
            sfShop.setShoutNum(sfShop.getShoutNum() + 1);

            sfShopShoutLog = new SfShopShoutLog();
            sfShopShoutLog.setCreateTime(new Date());
            sfShopShoutLog.setNum(1);
            sfShopShoutLog.setShopId(shopId);
            sfShopShoutLog.setShopUserId(sfShop.getUserId());
            sfShopShoutLog.setUserId(userId);

            sfShopMapper.updateByPrimaryKey(sfShop);
            sfShopShoutLogMapper.insert(sfShopShoutLog);

            return true;
        }

        return false;
    }

    /**
     * 根据id获得小铺信息
     *
     * @author hanzengzhi
     * @date 2016/4/9 12:04
     */
    public SfShop getSfShopById(Long id) {
        return sfShopMapper.selectByPrimaryKey(id);
    }

    /**
     * 判断呐喊
     *
     * @author muchaofeng
     * @date 2016/4/10 10:56
     */
    public boolean mallShout(Long userId, Long shopId) {
        try {
            Map<String, Object> map = new HashMap<>();

            map.put("userId", userId);
            map.put("shopId", shopId);
            map.put("createTime", new Date());
            Date minDay = DateUtil.getMinTimeofDay(new Date());
            Date maxDay = DateUtil.getMaxTimeofDay(new Date());
            map.put("minDay", minDay);
            map.put("maxDay", maxDay);

            log.info("mallShout进入了。。。");
            SfShopShoutLog sfShopShoutLog = sfShopShoutLogMapper.selectByMap(map);
            if (sfShopShoutLog != null) {
                log.info("用户已经呐喊过。。。");
                return false;
            } else {
                log.info("用户未呐喊。。。");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
        return true;
    }

    /**
     * 根据小铺归属人查询小铺信息
     *
     * @param userId
     * @return
     */
    public SfShop getSfShopByUserId(Long userId) {
        return sfShopMapper.selectByUserId(userId);
    }

    /**
     * 更新店铺信息
     *
     * @param sfShop
     */
    public int updateById(SfShop sfShop) {
        return sfShopMapper.updateByPrimaryKey(sfShop);
    }

    /**
     * jjh
     *
     * @param code 微信二维码文件名
     * @return
     */
    public String getWXQRImgByCode(String code) {
        String WXQRImg = null;
        String baseValue = PropertiesUtils.getStringValue("oss.BASE_URL");
        String shopValue = PropertiesUtils.getStringValue("oss.OSS_SHOPMAN_WX_QRCODE");
        WXQRImg = baseValue + "/" + shopValue + code;
        return WXQRImg;
    }

    /**
     * 查询分享过的小铺列表
     *
     * @param userId 用户id
     * @return list
     */
    public List<SfShop> getSharedShops(Long userId) {
        return sfShopMapper.selectSharedShops(userId);
    }

    /**
     * 创建海报
     * @param comUser
     * @param sfShop
     * @return
     */
    public String createShopPoster(ComUser comUser, SfShop sfShop) {
        try {
            String rootPath = RootPathUtils.getRootPath();
            /**
             * 获取海报
             */
            //获取海报1: 海报已存在,直接返回
            SfUserShareParam condition = new SfUserShareParam();
            condition.setfUserId(0L);//合伙人获取店铺二维码userId固定值为0
            condition.setShopId(sfShop.getId());
            SfUserShareParam sfUserShareParam = sfUserShareParamService.loadByCondition(condition);

            log.info("获取海报参数(查询参数[condition=" + condition + "]): [sfUserShareParam=" + sfUserShareParam + "]");

            Long shareParamId = (sfUserShareParam != null) ? sfUserShareParam.getId() : -1L;

            ComPoster comPosterParam = new ComPoster();
            comPosterParam.setType(1);
            comPosterParam.setUserId(comUser.getId());
            comPosterParam.setShareParamId(shareParamId);

            ComPoster comPoster = comPosterService.findByCondition(comPosterParam);
            log.info("获取海报图片信息(查询参数[comPosterParam=" + comPosterParam + "]): [comPoster=" + comPoster + "]");
            if (comPoster != null) {

                return PropertiesUtils.getStringValue("index_user_poster_url") + comPoster.getPosterName();
            }


            //获取海报2:如果海报不存在或已经过期,重新创建海报
            String headImg = "h-" + comUser.getId() + ".png";
            String qrcodeName = "qrcode-shop-" + comUser.getId() + "-" + sfShop.getId() + ".png";
            String bgPoster = "shop-" + sfShop.getId() + ".png";
            String posterDirPath = rootPath + "static/images/poster"; //basePath:request.getServletContext().getRealPath("/")
            File posterDir = new File(posterDirPath);
            if (!posterDir.exists()) {
                posterDir.mkdirs();
            }
            //先删除旧的图片,再下载新的图片
            new File(posterDirPath + "/" + headImg).delete();
            new File(posterDirPath + "/" + bgPoster).delete();
            new File(posterDirPath + "/" + qrcodeName).delete();

            File headImgFile = new File(posterDirPath + "/" + headImg);
            File bgImgFile = new File(posterDirPath + "/" + bgPoster);
            File qrcodeFile = new File(posterDirPath + "/" + qrcodeName);
            //File qrcodeImgFile = new File(posterDirPath+"/"+qrcodeName);
            if (!headImgFile.exists() && StringUtils.isNotBlank(comUser.getWxHeadImg()))
                DownloadImage.download(comUser.getWxHeadImg(), headImg, posterDirPath);
            if (!headImgFile.exists() && StringUtils.isBlank(comUser.getWxHeadImg()))
                OSSObjectUtils.downloadFile("static/user/background_poster/h-default.png", headImgFile.getAbsolutePath());
            if (!bgImgFile.exists())
                OSSObjectUtils.downloadFile("static/user/background_poster/bg-shop.png", bgImgFile.getAbsolutePath());

            if (sfShop == null) {
                throw new BusinessException("店铺不存在[comUser=" + comUser + "][shopId=" + sfShop.getId() + "]");
            }
            String[] qrcodeResult = null;
            if (StringUtils.isBlank(sfShop.getQrCode())) {
                log.info("到微信端获取店铺永久二维码.........![sfShop=" + sfShop + "]");
                qrcodeResult = weiXinPFQRCodeService.createShopOrSkuQRCode(0L, sfShop.getId(), null);
                if (qrcodeResult[1] == null)
                    throw new BusinessException("合伙人获取店铺二维码失败![comUser=" + comUser + "][shopId=" + sfShop.getId() + "]");
                DownloadImage.download(qrcodeResult[1], qrcodeName, posterDirPath);
                OSSObjectUtils.uploadFile(qrcodeFile, "static/shop/shop_qrcode/");
                sfShop.setQrCode(qrcodeFile.getName());
                updateById(sfShop);
                log.info("成功获取并保存店铺永久二维码........[sfShop=" + sfShop + "]");
            }
            if (!qrcodeFile.exists())
                OSSObjectUtils.downloadFile("static/shop/shop_qrcode/" + sfShop.getQrCode(), qrcodeFile.getAbsolutePath());

            //画图
            Date curDate = new Date();
            curDate.setDate(curDate.getDate() + 30);

            Element headImgElement = new Element(317, 201, 120, 120, ImageIO.read(new File(posterDirPath + "/" + headImg)));
            Element bgPosterImgElement = new Element(0, 0, 750, 1334, ImageIO.read(new File(posterDirPath + "/" + bgPoster)));
            Element qrcodeImgElement = new Element(236, 602, 280, 280, ImageIO.read(new File(posterDirPath + "/" + qrcodeName)));
            //Element text1Element = new Element(186, 304,   font1, new Color(51, 51, 51), "我是"+comUser.getWxNkName()+",正品特供,好友专享价,尽在我的麦链小店,不是好友看不到哦。长按二维码识别进入麦链小店。");
            String title = "Hi，我是" + comUser.getWxNkName();
            String desc1 = "正品特供，好友专享价，尽在我的麦链";
            String desc2 = "商店，不是好友看不到哦~";
            String desc3 = "长按二维码进来逛逛吧！";
            Element text1Element = new Element((750 - new DrawPicUtil().getStringPointSize(title, font30)) / 2, 376, font30, new Color(200, 166, 106), title);
            Element text2Element = new Element((750 - new DrawPicUtil().getStringPointSize(desc1, font30)) / 2, 420, font30, new Color(200, 166, 106), desc1);
            Element text3Element = new Element((750 - new DrawPicUtil().getStringPointSize(desc2, font30)) / 2, 464, font30, new Color(200, 166, 106), desc2);
            Element text4Element = new Element((750 - new DrawPicUtil().getStringPointSize(desc3, font30)) / 2, 508, font30, new Color(200, 166, 106), desc3);
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

            int random = (int) ((Math.random() * 9 + 1) * 100000);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date createTime = new Date();
            String posterName = simpleDateFormat.format(createTime) + random + ".png";
            DrawImageUtil.drawImage(750, 1334, drawElements, "static/user/poster/" + posterName);

            //保存二维码海报图片地址
            ComPoster newComPoster = new ComPoster();
            newComPoster.setCreateTime(createTime);
            if (qrcodeResult != null) {
                newComPoster.setShareParamId(Long.valueOf(qrcodeResult[0]));
            } else {
                newComPoster.setShareParamId(shareParamId);
            }
            log.info("二维码参数ID[qrcodeResult=" + qrcodeResult + "][shareParamId=" + shareParamId + "]");
            newComPoster.setType(1);
            newComPoster.setUserId(comUser.getId());
            newComPoster.setPosterName(posterName);

            comPosterService.add(newComPoster);

            //二维码获取成功,更新com_user成为代言人
            SfUserRelation sfUserRelation = sfUserRelationService.getSfUserRelationByUserIdAndShopId(comUser.getId(), sfShop.getId());
            if (sfUserRelation != null) {
                sfUserRelation.setIsSpokesman(1);
                sfUserRelationService.updateUserRelation(sfUserRelation);
            } else {
                log.info("sfUserRelation为null");
            }


            return PropertiesUtils.getStringValue("index_user_poster_url") + newComPoster.getPosterName();
        } catch (Exception e) {
            log.error("获取专属海报失败![comUser=" + comUser + "][sfShop=sfShop]" + e);
            e.printStackTrace();
        }

        return null;
    }
}
