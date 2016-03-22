package com.masiis.shop.admin.service.certificate;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.platform.certificate.CertificateMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComAgentLevel;
import com.masiis.shop.dao.po.ComUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
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
 * Created by JingHao on 2016/3/7 0007.
 */
@Service
@Transactional
public class CertificateService {


    @Resource
    private CertificateMapper certificateMapper;

    @Resource
    private PfBorderMapper pfBorderMapper;

    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;

    @Resource
    private ComUserMapper comUserMapper;

    /**
     * @Author 贾晶豪
     * @Date 2016/3/7  下午 5:51
     * 授权书列表
     */
    public List<CertificateInfo> getCertificates(Map<String, Object> params) throws Exception {
        List<CertificateInfo> certificateInfoList = certificateMapper.getCertificateInfo(params);
        if (certificateInfoList != null && certificateInfoList.size() > 0) {
            for (CertificateInfo certificateInfo : certificateInfoList) {
                if (certificateInfo != null && certificateInfo.getPfUserCertificateInfo() != null) {
                    String beginTime = DateUtil.Date2String(certificateInfo.getPfUserCertificateInfo().getBeginTime(), "yyyy-MM-dd", null);
                    certificateInfo.setBeginTime(beginTime);
                }
                if (certificateInfo != null && certificateInfo.getPid() != 0) {
                    certificateInfo.setApproveType("合伙人审核");
                    certificateInfo.setUpperName(comUserMapper.findByPid(certificateInfo.getPid()));
                } else {
                    certificateInfo.setApproveType("平台审核");
                }
            }
        }
        return certificateInfoList;
    }

    /**
     * @Author 贾晶豪
     * @Date 2016/3/9  上午 10:22
     * 审核授权书
     */
    public void approveCertificate(HttpServletRequest request, Integer status, Integer id,String reason) throws Exception {
        String bgPic = null;
        CertificateInfo certificateInfo = certificateMapper.getApproveInfo(id);
        if (certificateInfo != null) {
            Map<String, Object> param = new HashMap<>();
            param.put("status", status);
            param.put("id", certificateInfo.getPfUserCertificateInfo().getId());
            param.put("reason", reason);
            certificateMapper.updateCertificateStatus(param);//审核
            if (status == 1) { //审核成功
                //生成授权书,并上传至OSS服务器
                ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(certificateInfo.getAgentLevelId());
                if (comAgentLevel != null) {
                    bgPic = comAgentLevel.getImgUrl();//代理商等级的模板
                }
                String name = certificateInfo.getComUser().getRealName();//申请人
                String beginTime = DateUtil.Date2String(certificateInfo.getPfUserCertificateInfo().getBeginTime(), "yyyy-MM-dd", null);
                String endTime = DateUtil.Date2String(certificateInfo.getPfUserCertificateInfo().getEndTime(), "yyyy-MM-dd", null);
                String value1 = "授权书编号：" + getCertificateCode(certificateInfo) + "，手机：" + certificateInfo.getPfUserCertificateInfo().getMobile() + "，微信：" + certificateInfo.getPfUserCertificateInfo().getWxId();
                String value2 = "授权期限：" + beginTime + "至" + endTime;
                String rootPath = request.getServletContext().getRealPath("/");
                String webappPath = rootPath.substring(0, rootPath.lastIndexOf(File.separator));
                String picName = uploadFile(webappPath + "/static/images/certificate/" + bgPic, new String[]{name, value1, value2});
                Map<String, Object> UrlParam = new HashMap<>();
                UrlParam.put("imgUrl", picName + ".jpg");
                UrlParam.put("id", certificateInfo.getPfUserCertificateInfo().getId());
                UrlParam.put("code", getCertificateCode(certificateInfo));
                certificateMapper.updateCertificateImgUrl(UrlParam);//更新证书的URL
                Map<String, Object> Param = new HashMap<>();
                Param.put("id", id);
                Param.put("code", getCertificateCode(certificateInfo));
                certificateMapper.updateCertificateFlag(Param);//授权书生成的flag
            }
        }
    }

    /**
     * @Author 贾晶豪
     * @Date 2016/3/9  上午 11:11
     * 申请详情信息
     * Param Id
     */
    public CertificateInfo getApproveInfoById(Integer id) throws Exception {
        CertificateInfo certificateInfo = certificateMapper.getApproveInfo(id);
        if (certificateInfo != null) {
            String beginTime = DateUtil.Date2String(certificateInfo.getPfUserCertificateInfo().getBeginTime(), "yyyy-MM-dd hh:mm:ss", null);
            certificateInfo.setBeginTime(beginTime);
            certificateInfo.setPfBorder(pfBorderMapper.selectByPrimaryKey(certificateInfo.getPfBorderId()));
            certificateInfo.setUpperName(comUserMapper.findByPid(certificateInfo.getPid()));
            String idCardImg = PropertiesUtils.getStringValue("index_user_idCard_url");
            certificateInfo.getComUser().setIdCardFrontUrl(idCardImg + certificateInfo.getComUser().getIdCardFrontUrl());
            certificateInfo.getComUser().setIdCardBackUrl(idCardImg + certificateInfo.getComUser().getIdCardBackUrl());

        }
        return certificateInfo;
    }

    /**
     * @Author 贾晶豪
     * @Date 2016/3/10  下午 12:01
     * 上级合伙人列表
     */
    public CertificateInfo getUpperPartner(Integer id) {
        CertificateInfo certificateInfo = certificateMapper.getApproveInfo(id);
        //上级合伙人列表
        if (certificateInfo != null) {
            certificateInfo.setUpperName(comUserMapper.findByPid(certificateInfo.getPid()));
            if (certificateInfo.getPid() != 0) {
                List<ComUser> comUsers = certificateMapper.getUpperPartnerByUserId(certificateInfo.getPid());
                certificateInfo.setComUserList(comUsers);
            }
        }
        return certificateInfo;
    }

    /**
     * @Author 贾晶豪
     * @Date 2016/3/10  下午 12:04
     * 更改上级
     */
    public void updateUpperPartner(String id, String pId) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("pId", pId);
        certificateMapper.updateUpperPartnerById(param);
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
            OSSObjectUtils.uploadFile("mmshop", "static/user/certificate/" + pname + ".jpg", is);
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

    /**
     * @Author 贾晶豪
     * @Date 2016/3/12 0012 上午 11:34
     * 证书编号
     */

    private String getCertificateCode(CertificateInfo certificateInfo) throws Exception{
        String certificateCode = null;
        int num = 10000;
        StringBuffer Code = new StringBuffer("MASIIS");
        String value = DateUtil.Date2String(certificateInfo.getPfUserCertificateInfo().getBeginTime(), "yyyy", null).substring(2);//时间
        String value1 = certificateInfo.getAgentLevelId().toString();
        String value2 = String.format("%04d", certificateInfo.getSkuId());
        int value3 = num + certificateInfo.getId();
        certificateCode = Code.append(value1).append(value2).append(value).append(String.valueOf(value3)).toString();
        return certificateCode;
    }

    public String findById(Integer id) {
        String imgUrl = certificateMapper.findById(id);
        String idCardImg = PropertiesUtils.getStringValue("index_user_certificate_url");
        return idCardImg+imgUrl;
    }


}
