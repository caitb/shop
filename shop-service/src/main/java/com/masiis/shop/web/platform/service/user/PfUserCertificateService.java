package com.masiis.shop.web.platform.service.user;


import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.RootPathUtils;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.platform.certificate.ComBrandCertificateMapper;
import com.masiis.shop.dao.platform.product.ComBrandMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.BrandService;
import com.masiis.shop.web.common.service.ComAgentLevelService;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.common.utils.DrawPicUtil;
import com.masiis.shop.web.platform.utils.AsyncUploadCertUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.*;
import java.util.List;

/**
 * Created by hzz on 2016/6/16.
 */
@Service
@Transactional
public class PfUserCertificateService {
    private static Logger logger = Logger.getLogger(AsyncUploadCertUtil.class);
    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;
    @Resource
    private ComAgentLevelService comAgentLevelService;
    @Resource
    private PfUserSkuService pfUserSkuService;
    @Resource
    private BrandService comBrandService;
    @Resource
    private ComBrandCertificateService comBrandCertificateService;
    @Resource
    private SkuService skuService;
    @Resource
    private UserService userService;

    public int update(PfUserCertificate po) {
        return pfUserCertificateMapper.updateById(po);
    }

    public PfUserCertificate selectByUserIdAndSkuId(Long userId, Integer skuId) {
        return pfUserCertificateMapper.selectByUserIdAndSkuId(userId, skuId);
    }

    public PfUserCertificate selectByUserSkuId(Integer pfUserSkuId) {
        return pfUserCertificateMapper.selectByUserSkuId(pfUserSkuId);
    }

    /**
     * 生成证书编码
     *
     * @param certificateInfo
     * @return
     */
    public String getCertificateCode(PfUserCertificate certificateInfo) {
        String certificateCode = "";
        String value = "";
        StringBuffer Code = new StringBuffer("MASIIS");
        value = DateUtil.Date2String(certificateInfo.getBeginTime(), "yyyy", null).substring(2);//时间
        String value1 = certificateInfo.getAgentLevelId().toString();
        String value2 = String.format("%04d", certificateInfo.getSkuId());
        String value3 = String.format("%05d", certificateInfo.getUserId());
        certificateCode = Code.append(value1).append(value2).append(value).append(value3).toString();
        return certificateCode;
    }

    public int insert(PfUserCertificate pfUserCertificate) {
        return pfUserCertificateMapper.insert(pfUserCertificate);
    }

    /**
     * 添加合伙证书
     *
     * @param comUser   用户对象
     * @param comSku    SKU对象
     * @param pfUserSku 用户合伙关系对象
     */
    private void addUserCertificate(ComUser comUser, ComSku comSku, PfUserSku pfUserSku) {
        String rootPath = RootPathUtils.getRootPath();
        PfUserCertificate pfUserCertificate = selectByUserSkuId(pfUserSku.getId());
        if (pfUserCertificate == null && comUser.getAuditStatus() == 2) {
            pfUserCertificate = new PfUserCertificate();
            pfUserCertificate.setCreateTime(new Date());
            pfUserCertificate.setUserId(comUser.getId());
            pfUserCertificate.setSpuId(comSku.getSpuId());
            pfUserCertificate.setSkuId(comSku.getId());
            pfUserCertificate.setIdCard(comUser.getIdCard());
            pfUserCertificate.setMobile(comUser.getMobile());
            pfUserCertificate.setWxId(comUser.getWxId());
            Calendar calendar = Calendar.getInstance();
            pfUserCertificate.setBeginTime(calendar.getTime());
            calendar.set(Calendar.MONTH, 11);
            calendar.set(Calendar.DAY_OF_MONTH, 31);
            pfUserCertificate.setEndTime(calendar.getTime());
            pfUserCertificate.setAgentLevelId(pfUserSku.getAgentLevelId());
            pfUserCertificate.setStatus(1);
            pfUserCertificate.setRemark("");
            pfUserCertificate.setPfUserSkuId(pfUserSku.getId());
            pfUserCertificate.setCode(getCertificateCode(pfUserCertificate));
            ComAgentLevel comAgentLevel = comAgentLevelService.selectByPrimaryKey(pfUserCertificate.getAgentLevelId());
            String picName = uploadUserCertificate(rootPath,
                    pfUserCertificate.getCode(),//certificateCode - 证书编号
                    comUser,
                    comSku,
                    comAgentLevel.getName(),//levelName - 代理等级名称
                    DateUtil.Date2String(pfUserCertificate.getBeginTime(), "yyyy-MM-dd", null),//beginDate - 开始日期
                    DateUtil.Date2String(pfUserCertificate.getEndTime(), "yyyy-MM-dd", null),
                    pfUserCertificate.getAgentLevelId());//endDate - 结束日期
            pfUserCertificate.setImgUrl(picName + ".jpg");
            pfUserCertificateMapper.insert(pfUserCertificate);
            pfUserSku.setCode(pfUserCertificate.getCode());
            pfUserSkuService.update(pfUserSku);
        }
    }

    /**
     * 给jpg添加文字并上传
     *
     * @param certificateCode 证书编号
     * @param comUser         用户对象
     * @param comSku          sku对象
     * @param levelName       合伙等级名称
     * @param beginDate       证书有效开始日期
     * @param endDate         证书有效结束日期
     * @return
     */
    public String uploadUserCertificate(String rootPath,
                                        String certificateCode,
                                        ComUser comUser,
                                        ComSku comSku,
                                        String levelName,
                                        String beginDate,
                                        String endDate,
                                        Integer agentLevelId) {
        ComBrand comBrand = comBrandService.getBySkuId(comSku.getId());
        if (comBrand == null) {
            throw new BusinessException("------根据skuId---获得品牌信息出错-----skuId-----" + comSku.getId());
        }
        ComBrandCertificate comBrandCertificate = comBrandCertificateService.getBrandCertificateByBrandIdAndLevelId(comBrand.getId(), agentLevelId);
        if (comBrandCertificate == null) {
            throw new BusinessException("-----根据品牌id和等级id查询证书信息为null----brandId----" + comBrand.getId() + "-----agentLevelId----" + agentLevelId);
        }
        //原图的物理路径
        String filePath = rootPath + "/static/images/certificate/backgroundimg/" + comBrandCertificate.getBackimgUrl();
        //字体路径
        String fontPath = rootPath + "/static/font/";
        String newIdCard = comUser.getIdCard().substring(0, 4) + "**********" + comUser.getIdCard().substring(comUser.getIdCard().length() - 4, comUser.getIdCard().length());
        DrawPicUtil drawPicUtil = new DrawPicUtil();
        BufferedImage bufferedImage = drawPicUtil.loadImageLocal(filePath);
        //宋体
        Font simsun_font = null;
        //微软雅黑
        Font msyh_font = null;
        try {
            simsun_font = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath + "simsun.ttc"));
            msyh_font = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath + "msyh.ttc"));
        } catch (Exception e) {
            throw new BusinessException("创建字体异常");
        }
        Font msyh_font_25 = msyh_font.deriveFont(Font.PLAIN, 25);
        Font msyh_font_30 = msyh_font.deriveFont(Font.PLAIN, 30);
        Font msyh_font_100 = msyh_font.deriveFont(Font.PLAIN, 100);
        Font simsun_font_30 = simsun_font.deriveFont(Font.PLAIN, 30);
        Font simsun_font_45 = simsun_font.deriveFont(Font.PLAIN, 45);
        Font simsun_font_50 = simsun_font.deriveFont(Font.PLAIN, 50);
        java.util.List<DrawPicUtil.DrawPicParam> drawPicParams = new ArrayList<>();
        //授权书编号
        DrawPicUtil.DrawPicParam drawPicParam1 = drawPicUtil.getDrawPicParam();
        drawPicParam1.setX(comBrandCertificate.getCertificateCodeX());
        drawPicParam1.setY(comBrandCertificate.getCertificateCodeY());
        drawPicParam1.setContent(certificateCode);
        drawPicParam1.setFont(msyh_font_30);
        drawPicParams.add(drawPicParam1);
        //代理等级名称
        DrawPicUtil.DrawPicParam drawPicParam5 = drawPicUtil.getDrawPicParam();
        drawPicParam5.setY(comBrandCertificate.getLevelNameY());
        drawPicParam5.setContent(levelName);
        drawPicParam5.setFont(msyh_font_100);
        drawPicParam5.setColor(Color.WHITE);
        drawPicParams.add(drawPicParam5);
        //代理人姓名
        DrawPicUtil.DrawPicParam drawPicParam3 = drawPicUtil.getDrawPicParam();
        drawPicParam3.setX(comBrandCertificate.getUserRealnameX());
        drawPicParam3.setY(comBrandCertificate.getUserRealnameY());
        drawPicParam3.setContent(comUser.getRealName());
        drawPicParam3.setFont(simsun_font_50);
        drawPicParams.add(drawPicParam3);
        //英文介绍
        DrawPicUtil.DrawPicParam drawPicParam4 = drawPicUtil.getDrawPicParam();
        drawPicParam4.setY(comBrandCertificate.getSkuEnameY());
        drawPicParam4.setContent("Has the right to sell the product of Beijing Masiis Biotech Co., Ltd.(" + comSku.geteName() + ")");
        drawPicParam4.setFont(msyh_font_25);
        drawPicParams.add(drawPicParam4);
        DrawPicUtil.DrawPicParam drawPicParam11 = drawPicUtil.getDrawPicParam();
        drawPicParam11.setY(comBrandCertificate.getSkuEnameY() + 35);
        drawPicParam11.setContent("via both e-commerce channel and physical store. ");
        drawPicParam11.setFont(msyh_font_25);
        drawPicParams.add(drawPicParam11);
        //代理商品名称
        DrawPicUtil.DrawPicParam drawPicParam9 = drawPicUtil.getDrawPicParam();
        drawPicParam9.setY(comBrandCertificate.getSkuNameY());
        drawPicParam9.setContent(comSku.getName());
        drawPicParam9.setFont(simsun_font_30);
        drawPicParam9.setColor(Color.decode("#FF4B21"));
        drawPicParams.add(drawPicParam9);
        //证件号等
        DrawPicUtil.DrawPicParam drawPicParam6 = drawPicUtil.getDrawPicParam();
        drawPicParam6.setY(comBrandCertificate.getUserIdcardY());
        drawPicParam6.setContent("证件号：" + newIdCard + "，手机：" + comUser.getMobile() + "，微信：" + comUser.getWxId());
        drawPicParam6.setFont(simsun_font_45);
        drawPicParams.add(drawPicParam6);
        //授权期限
        DrawPicUtil.DrawPicParam drawPicParam2 = drawPicUtil.getDrawPicParam();
        drawPicParam2.setY(comBrandCertificate.getCertificateDataY());
        drawPicParam2.setContent("授权期限：" + beginDate + "至" + endDate);
        drawPicParam2.setFont(simsun_font_45);
        drawPicParams.add(drawPicParam2);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        try {
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(drawPicUtil.modifyImage(bufferedImage, drawPicParams), "png", imOut);
        } catch (Exception ex) {
            throw new BusinessException("生成证书异常");
        }
        InputStream is = new ByteArrayInputStream(bs.toByteArray());
        String pname = SysBeanUtils.getRandomFileName();
        OSSObjectUtils.uploadFile("static/user/certificate/" + pname + ".jpg", is);
        return pname;
    }

    /**
     * 异步上传证书
     *
     * @param comUser
     */
    public void asyncUploadUserCertificate(ComUser comUser) {
        List<PfUserSku> pfUserSkus = pfUserSkuService.getPfUserSkuByUserId(comUser.getId());
        for (PfUserSku pfUserSku : pfUserSkus) {
            logger.info("处理pfUserSku，id：" + pfUserSku.getId());
            if (StringUtils.isBlank(pfUserSku.getCode())) {
                asyncUploadUserCertificateItem(pfUserSku);
            }
        }
    }

    /**
     * 异步上传证书
     *
     * @param pfUserSku
     */
    public void asyncUploadUserCertificateItem(PfUserSku pfUserSku) {
        ComSku comSku = skuService.getSkuById(pfUserSku.getSkuId());
        ComUser comUser = userService.getUserById(pfUserSku.getUserId());
        addUserCertificate(comUser, comSku, pfUserSku);
    }
}

