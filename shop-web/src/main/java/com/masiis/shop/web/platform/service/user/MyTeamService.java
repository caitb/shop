package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.util.CCPRestSmsSDK;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.ComBrandMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by cai_tb on 16/3/16.
 */
@Service
public class MyTeamService {

    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private ComSpuMapper comSpuMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;
    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;
    @Resource
    private ComBrandMapper comBrandMapper;


    /**
     * 获取用户代理的所有产品
     * @param userId
     * @return
     */
    public List<Map<String, Object>> listAgentSku(Long userId){
        PfUserSku pfUserSku = new PfUserSku();
        pfUserSku.setUserId(userId);

        List<PfUserSku> pfUserSkus = pfUserSkuMapper.selectByCondition(pfUserSku);

        List<Map<String, Object>> agentSkuMaps = new ArrayList<>();
        for(PfUserSku pus : pfUserSkus){
            ComSku comSku = comSkuMapper.selectById(pus.getSkuId());
            ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
            ComBrand comBrand = comBrandMapper.selectById(comSpu.getBrandId());

            Map<String, Object> agentSkuMap = new HashMap<>();
            agentSkuMap.put("userSkuId", pus.getId());
            agentSkuMap.put("skuId", comSku.getId());
            agentSkuMap.put("skuName", comSku.getName());
            agentSkuMap.put("brandLogo", comBrand.getLogoUrl());

            agentSkuMaps.add(agentSkuMap);
        }

        return agentSkuMaps;
    }

    /**
     * 获取团队列表
     * @param userSkuId
     * @param skuId
     * @return
     */
    public Map<String, Object> findTeam(Integer userSkuId, Integer skuId){
        PfUserSku pfUserSku = new PfUserSku();
        pfUserSku.setPid(userSkuId);
        pfUserSku.setSkuId(skuId);

        List<PfUserSku> pfUserSkus = pfUserSkuMapper.selectByCondition(pfUserSku);

        List<Map<String, Object>> isAuditTeamMaps = new ArrayList<>();
        List<Map<String, Object>> noAuditTeamMaps = new ArrayList<>();
        for(PfUserSku pus : pfUserSkus){
            ComUser comUser = comUserMapper.selectByPrimaryKey(pus.getUserId());
            ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(pus.getAgentLevelId());

            Map<String, Object> teamMap = new HashMap<>();
            teamMap.put("comUserId", comUser.getId());
            teamMap.put("comUserName", comUser.getRealName());
            teamMap.put("comUserImg", comUser.getWxHeadImg());
            teamMap.put("skuId", pus.getSkuId());
            teamMap.put("agentLevelId", comAgentLevel.getId());
            teamMap.put("agentLevelName", comAgentLevel.getName());
            teamMap.put("userSkuId", pus.getId());
            teamMap.put("code", pus.getCode());

            if(pus.getIsCertificate() == 1) isAuditTeamMaps.add(teamMap);
            if(pus.getIsCertificate() == 0) noAuditTeamMaps.add(teamMap);
        }

        Map<String, Object> teamMaps = new HashMap<>();
        teamMaps.put("isAuditTeamMaps", isAuditTeamMaps);
        teamMaps.put("noAuditTeamMaps", noAuditTeamMaps);

        return teamMaps;
    }

    /**
     *
     * @param comUserId
     * @param skuId
     * @param agentLevelId
     * @return
     */
    public Map<String, Object> viewMember(Long comUserId, Integer skuId, Integer agentLevelId, Integer userSkuId){
        PfUserCertificate pfUserCertificate = pfUserCertificateMapper.selectByUserSkuId(userSkuId);
        if(pfUserCertificate == null){//合伙人未填写证书申请
            return null;
        }

        ComUser comUser = comUserMapper.selectByPrimaryKey(comUserId);
        ComSku comSku = comSkuMapper.selectById(skuId);
        ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(agentLevelId);


        Map<String, Object> memberMap = new HashMap<>();
        memberMap.put("comUserId", comUser.getId());
        memberMap.put("comUserName", comUser.getRealName());
        memberMap.put("mobile", comUser.getMobile());
        memberMap.put("idCardFrontImg", PropertiesUtils.getStringValue("index_user_idCard_url") + comUser.getIdCardFrontUrl());
        memberMap.put("idCardBackImg", PropertiesUtils.getStringValue("index_user_idCard_url") + comUser.getIdCardBackUrl());
        memberMap.put("weixin", comUser.getWxId());
        memberMap.put("idCard", comUser.getIdCard());
        memberMap.put("frontImg", comUser.getIdCardFrontUrl());
        memberMap.put("backImg", comUser.getIdCardBackUrl());
        memberMap.put("skuId", comSku.getId());
        memberMap.put("skuName", comSku.getName());
        memberMap.put("agentLevelId", comAgentLevel.getId());
        memberMap.put("agentLevelName", comAgentLevel.getName());
        memberMap.put("certificateImg", pfUserCertificate.getImgUrl());
        memberMap.put("pfUserCertificateId", pfUserCertificate.getId());
        memberMap.put("joinTime", pfUserCertificate.getBeginTime());
        memberMap.put("applyTime", pfUserCertificate.getCreateTime());
        if(userSkuId != null){
            PfUserSku pfUserSku = pfUserSkuMapper.selectByPrimaryKey(userSkuId);
            memberMap.put("payStatus", pfUserSku.getIsPay());
            memberMap.put("userSkuId", pfUserSku.getId());
        }

        return memberMap;
    }

    /**
     * 证书审核
     * @param userSkuId
     * @param pfUserCertificateId
     * @param status
     * @param reason
     * @param rootPath
     */
    public void audit(Integer userSkuId, Long pfUserCertificateId, Integer status, String reason, String rootPath){
        PfUserCertificate pfUserCertificate = pfUserCertificateMapper.selectByPrimaryKey(pfUserCertificateId);
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfUserCertificate.getUserId());
        ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(pfUserCertificate.getAgentLevelId());

        pfUserCertificate.setStatus(status);
        pfUserCertificate.setReason(reason);

        if(status == 1){
            pfUserCertificate.setCode(getCertificateCode(pfUserCertificate));
            Date curDate = new Date();
            pfUserCertificate.setBeginTime(curDate);
            curDate.setYear(curDate.getYear()+1);
            pfUserCertificate.setEndTime(curDate);

            String name = comUser.getRealName();
            String value1 = "证件号：" + comUser.getIdCard() + "，手机：" + comUser.getMobile() + "，微信：" + comUser.getWxId();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            String value2 = "授权期限：" + sdf.format(pfUserCertificate.getBeginTime()).toString() + "至" + sdf.format(pfUserCertificate.getEndTime()).toString() + "，证书编号" + pfUserCertificate.getCode();
            String webappPath = rootPath.substring(0, rootPath.lastIndexOf(File.separator));
            String picName = uploadFile(webappPath + "/static/images/certificate/" + comAgentLevel.getImgUrl(), new String[]{name, value1, value2});

            pfUserCertificate.setImgUrl(picName + ".jpg");

            CCPRestSmsSDK.sendSMS(comUser.getMobile(), "65446", new String[]{comAgentLevelMapper.selectByPrimaryKey(pfUserCertificate.getAgentLevelId()).getName()});
        }


        PfUserSku pfUserSku = new PfUserSku();
        pfUserSku.setId(userSkuId);
        pfUserSku.setIsCertificate(pfUserCertificate.getStatus());

        pfUserCertificateMapper.updateById(pfUserCertificate);
        pfUserSkuMapper.updateByPrimaryKey(pfUserSku);

    }

    private String getCertificateCode(PfUserCertificate pfUserCertificate){
        String certificateCode = null;
        int num = 10000;
        StringBuffer Code = new StringBuffer("MASIIS");
        String value = DateUtil.Date2String(pfUserCertificate.getBeginTime(), "yyyy", null).substring(2);//时间
        String value1 = pfUserCertificate.getAgentLevelId().toString();
        String value2 = String.format("%04d", pfUserCertificate.getSkuId());
        int value3 = num + pfUserCertificate.getId().intValue();
        certificateCode = Code.append(value1).append(value2).append(value).append(String.valueOf(value3)).toString();
        return certificateCode;
    }

    //给jpg添加文字并上传
    public static String uploadFile(String filePath, String[] markContent) {
        String pname = getRandomFileName();
        ImageIcon imgIcon = new ImageIcon(filePath);
        Image theImg = imgIcon.getImage();
        int width = theImg.getWidth(null) == -1 ? 200 : theImg.getWidth(null);
        int height = theImg.getHeight(null) == -1 ? 200 : theImg.getHeight(null);
        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bimage.createGraphics();
        g.setColor(Color.black);
        g.setBackground(Color.red);
        g.drawImage(theImg, 0, 0, null);
        int fs = 40;
        g.setFont(new Font("华文细黑", Font.BOLD, fs)); //字体、字型、字号
        //画文字
        if (markContent[0].length() == 3) {
            g.drawString(markContent[0], width / 2 - fs * 3 / 2, height / 2 + 40);//740 625
        } else {
            g.drawString(markContent[0], width / 2 - fs, height / 2 + 40);
        }
        g.setColor(Color.gray);
        g.setFont(new Font("华文细黑", Font.BOLD, 18)); //字体、字型、字号
        g.drawString(markContent[1], 150, 490);
        g.drawString(markContent[2], 180, 520);
        g.dispose();
        try {
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bimage, "png", imOut);
            InputStream is = new ByteArrayInputStream(bs.toByteArray());
            OSSObjectUtils.uploadFile("masiis", "user/certificate/" + pname + ".jpg", is);
//            FileOutputStream out = new FileOutputStream(outPath); //先用一个特定的输出文件名
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
//            param.setQuality(qualNum, true);
//            encoder.encode(bimage, param);
//            out.close();
        } catch (Exception e) {
            return "";
        }
        return pname;
    }

    /**
     * 生成随机文件名：当前年月日时分秒+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        Random random = new Random();

        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

        return rannum + str;// 当前时间
    }
}
