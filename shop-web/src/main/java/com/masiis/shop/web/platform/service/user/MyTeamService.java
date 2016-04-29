package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.util.CCPRestSmsSDK;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.ComBrandMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    @Resource
    private PfBorderMapper pfBorderMapper;


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

            Map<String, String> curMap = countChild(pus.getId()); //下级userSkuId和userId数量
            Integer countChild = StringUtils.isEmpty(curMap.get("childIds").toString())?0:curMap.get("childIds").split(",").length;
            Double countSales = pfBorderMapper.countSales(curMap.get("userIds"));
            agentSkuMap.put("countChild", countChild);      //团队人数(不包括自己)
            agentSkuMap.put("countSales", countSales==null?0:countSales);

            agentSkuMaps.add(agentSkuMap);
        }

        return agentSkuMaps;
    }

    /**
     * 统计团队人数
     * @param userSkuId
     * @return
     */
    public Map<String, String> countChild(Integer userSkuId){
        String curPIds = userSkuId.toString();
        String curUserIds = "";
        StringBuilder childIds = new StringBuilder(4000);
        StringBuilder userIds = new StringBuilder(4000);


        while (curPIds != null){
           Map<String, String> curMap = pfUserSkuMapper.countChild(curPIds);
           if(curMap == null) curMap = new HashMap<>();
           curPIds = curMap.get("sPIds");
           curUserIds = curMap.get("sUserIds");

           if(curPIds != null)    childIds.append("," + curPIds);
           if(curUserIds != null) userIds.append("," + curUserIds);
        }

        if(childIds.length()>0) childIds.deleteCharAt(0);
        if(userIds.length()>0)  userIds.deleteCharAt(0);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("childIds", childIds.toString());
        resultMap.put("userIds", userIds.toString());

        return resultMap;
    }

    /**
     * 获取团队列表
     * @param userSkuId
     * @return
     */
    public Map<String, Object> findTeam(Integer userSkuId){
        PfUserSku pfUserSku = pfUserSkuMapper.selectByPrimaryKey(userSkuId);
        List<Long> userIds = pfUserSkuMapper.selectChildrenByPId(pfUserSku.getId());
        List<ComUser> comUsers = new ArrayList<>();
        if(userIds != null && userIds.size() > 0){
            comUsers = comUserMapper.selectByIds(userIds);
        }
        ComSku comSku = comSkuMapper.selectById(pfUserSku.getSkuId());

        //下级userSkuId和userId数量
        Map<String, String> curMap = countChild(pfUserSku.getId());
        //下级人数
        Integer countChild = StringUtils.isEmpty(curMap.get("childIds").toString())?0:curMap.get("childIds").split(",").length;
        //销售额
        Double countSales = pfBorderMapper.countSales(curMap.get("userIds"));

        Map<String, Object> teamMap = new HashMap<>();
        teamMap.put("skuName", comSku.getName());//商品名称
        teamMap.put("totalChildren", userIds.size());//直接下级人数
        teamMap.put("countChild", countChild - userIds.size());//间接下级人数
        teamMap.put("countSales", countSales==null?0:countSales);//总销售额

        List<Map<String, Object>> userAgentMaps = new ArrayList<>();
        for(ComUser comUser : comUsers){
            PfUserSku userSku = pfUserSkuMapper.selectByUserIdAndSkuId(comUser.getId(), pfUserSku.getSkuId());
            ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(userSku.getAgentLevelId());

            Map<String, Object> userAgentMap = new HashMap<>();
            userAgentMap.put("userId", comUser.getId());
            userAgentMap.put("userName", comUser.getRealName());
            userAgentMap.put("agentLevelName", comAgentLevel.getName());
            userAgentMap.put("code", userSku.getCode());

            userAgentMaps.add(userAgentMap);
        }

        teamMap.put("userAgentMaps", userAgentMaps);


        return teamMap;
    }

    /**
     *
     * @param code
     * @return
     */
    public Map<String, Object> viewMember(String code){
        PfUserCertificate pfUserCertificate = pfUserCertificateMapper.selectByCode(code);
        if(pfUserCertificate == null){//合伙人未填写证书申请
            return null;
        }

        Map<String, Double> statisticsBuy = pfBorderMapper.statisticsBuy(pfUserCertificate.getUserId());
        ComUser comUser = comUserMapper.selectByPrimaryKey(pfUserCertificate.getUserId());
        ComSku comSku = comSkuMapper.selectById(pfUserCertificate.getSkuId());
        ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(pfUserCertificate.getAgentLevelId());
        PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(comUser.getId(), comSku.getId());
        Map<String, String> curMap = countChild(pfUserSku.getId());


        Map<String, Object> memberMap = new HashMap<>();
        memberMap.put("stock", statisticsBuy.get("stock"));
        memberMap.put("totalAmount", statisticsBuy.get("totalAmount"));
        memberMap.put("countChild", curMap.get("childIds").split(",").length);
        memberMap.put("comUserId", comUser.getId());
        memberMap.put("comUserName", comUser.getRealName());
        memberMap.put("mobile", comUser.getMobile());
        memberMap.put("auditStatus", comUser.getAuditStatus());
        memberMap.put("idCardImg", comUser.getIdCardFrontUrl());
        //memberMap.put("idCardFrontImg", PropertiesUtils.getStringValue("index_user_idCard_url") + comUser.getIdCardFrontUrl());
        //memberMap.put("idCardBackImg", PropertiesUtils.getStringValue("index_user_idCard_url") + comUser.getIdCardBackUrl());
        memberMap.put("weixin", pfUserCertificate.getWxId());
        memberMap.put("idCard", pfUserCertificate.getIdCard());
        memberMap.put("frontImg", comUser.getIdCardFrontUrl());
        memberMap.put("backImg", comUser.getIdCardBackUrl());
        memberMap.put("skuId", comSku.getId());
        memberMap.put("skuName", comSku.getName());
        memberMap.put("agentLevelId", comAgentLevel.getId());
        memberMap.put("agentLevelName", comAgentLevel.getName());
        memberMap.put("status", pfUserCertificate.getStatus());
        memberMap.put("certificateImg", PropertiesUtils.getStringValue("index_user_certificate_url") + pfUserCertificate.getImgUrl());
        memberMap.put("applyTime", pfUserCertificate.getBeginTime());
        memberMap.put("pid", pfUserSku.getPid());

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
            OSSObjectUtils.uploadFile("static/user/certificate/" + pname + ".jpg", is);
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
