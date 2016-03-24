package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.platform.certificate.CertificateMapper;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.po.ComAgentLevel;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserCertificate;
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
 * UserCertificateService
 *
 * @author ZhaoLiang
 * @date 2016/3/11
 */
@Service
@Transactional
public class UserCertificateService {

    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private CertificateMapper certificateMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;

    public void addUserCertificate(PfUserCertificate pfUserCertificate) {
        pfUserCertificateMapper.insert(pfUserCertificate);
    }

    /**
     * @Author 贾晶豪
     * @Date 2016/3/17 0017 下午 4:30
     * 个人中心授权书
     */
    public List<CertificateInfo> CertificateByUser(Integer userId){
        List<CertificateInfo> certificateInfoList = certificateMapper.getCertificatesByUser(userId.longValue());
        if (certificateInfoList != null) {
            //先找该用户的证书是否生成过
            Boolean applyFlag = false;//没生成证书
            for (CertificateInfo certificateInfo : certificateInfoList) {
                if (certificateInfo.getIsCertificate() == 1) {
                    applyFlag = true;//该用户已经生成过授权书，跳出循环
                    break;
                }
            }
            if (applyFlag) {
                for (CertificateInfo certificateInfo : certificateInfoList) {
                    PfUserCertificate pfUserCertificate = pfUserCertificateMapper.selectByUserSkuId(certificateInfo.getId());
                    if (pfUserCertificate == null) {
                        certificateInfo.setReceivect(0);//证书已经生成，未领取
                    } else {
                        certificateInfo.setPfUserCertificateInfo(pfUserCertificate);//走完流程,证书生成
                    }
                }
            } else {
                //第一次
                for (CertificateInfo certificateInfo : certificateInfoList) {
                    PfUserCertificate pfUserCertificate = pfUserCertificateMapper.selectByUserSkuId(certificateInfo.getId());
                    if (pfUserCertificate == null) { //流程中断，未申请
                        certificateInfo.setIsApply(0);
                    } else {
                        certificateInfo.setPfUserCertificateInfo(pfUserCertificate);//走完流程,证书生成
                    }
                }
            }
        }
        return certificateInfoList;
    }
    /**
     * @Author 贾晶豪
     * @Date 2016/3/17 0017 下午 6:40
     * 商品证书详情
     */
    public PfUserCertificate CertificateDetailsByUser(Integer pfuId){
        PfUserCertificate pfc = pfUserCertificateMapper.selectByUserSkuId(pfuId);
        CertificateInfo certificateInfo = certificateMapper.get(pfuId);
        if(certificateInfo!=null){
            pfc.setSjName(comUserMapper.findByPid(certificateInfo.getPid()));
        }
        String ctValue = PropertiesUtils.getStringValue("index_user_certificate_url");
        pfc.setImgUrl(ctValue + pfc.getImgUrl());
        String beginTime = DateUtil.Date2String(pfc.getBeginTime(), "yyyy-MM-dd hh:mm:ss", null);
        pfc.setTjDate(beginTime);
        return pfc;
    }
    /**
     * @Author 贾晶豪
     * @Date 2016/3/19 0019 下午 3:58
     * 领取证书
     */
    public void receiveCertificate(HttpServletRequest request,Integer pfuId) throws Exception{
        CertificateInfo ctInfo = certificateMapper.get(pfuId);
        ComUser comUser = comUserMapper.selectByPrimaryKey(ctInfo.getUserId());
        ComSku comSku = comSkuMapper.selectByPrimaryKey(ctInfo.getSkuId());
        PfUserCertificate pfc = new PfUserCertificate();
        pfc.setCreateTime(new Date());
        pfc.setCode(getCertificateCode(ctInfo));
        pfc.setPfUserSkuId(pfuId);
        pfc.setUserId(comUser.getId());
        pfc.setSpuId(comSku.getSpuId());
        pfc.setSkuId(ctInfo.getSkuId());
        pfc.setIdCard(comUser.getIdCard());
        pfc.setMobile(comUser.getMobile());
        pfc.setWxId(comUser.getWxId());
        pfc.setBeginTime(new Date());
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(System.currentTimeMillis());
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 2);
        date = calendar.getTime();
        pfc.setEndTime(date);
        pfc.setAgentLevelId(ctInfo.getAgentLevelId());
        pfc.setStatus(0);
        pfUserCertificateMapper.insert(pfc);
        approveCertificate(request,1,pfuId,null);
    }

    /**
     *  证书
     * @param pfuId
     * Jing Hao
     */
    public PfUserCertificate getCertificateBypfuId(Integer pfuId){
        return pfUserCertificateMapper.selectByUserSkuId(pfuId);
    }

    /**
     * @Author 贾晶豪
     * @Date 2016/3/12 0012 上午 11:34
     * 证书编号
     */

    private String getCertificateCode(CertificateInfo certificateInfo) throws Exception{
        String certificateCode = null;
        int num = 10000;
        String value = null;
        StringBuffer Code = new StringBuffer("MASIIS");
        if(certificateInfo.getPfUserCertificateInfo()!=null && certificateInfo.getPfUserCertificateInfo().getBeginTime()!=null){
            value = DateUtil.Date2String(certificateInfo.getPfUserCertificateInfo().getBeginTime(), "yyyy", null).substring(2);//时间
        }else{
            value = DateUtil.Date2String(new Date(), "yyyy", null).substring(2);//时间
        }
        String value1 = certificateInfo.getAgentLevelId().toString();
        String value2 = String.format("%04d", certificateInfo.getSkuId());
        int value3 = num + certificateInfo.getId();
        certificateCode = Code.append(value1).append(value2).append(value).append(String.valueOf(value3)).toString();
        return certificateCode;
    }

    /**
     * 审核证书
     * @param request
     * @param status
     * @param id
     * @param reason
     * @throws Exception
     * Jing Hao
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
}
