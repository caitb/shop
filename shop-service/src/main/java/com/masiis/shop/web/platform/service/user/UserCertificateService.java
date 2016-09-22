package com.masiis.shop.web.platform.service.user;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.platform.certificate.CertificateMapper;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.ComBrandMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
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

    private final static String[] charArrs = {"A", "D", "E", "C", "H", "Y", "6", "7", "8", "9",
            "M", "N", "O", "Z", "1", "5", "P", "Q", "R", "S", "2", "3", "4", "T", "I", "J", "F",
            "G", "B", "K", "L", "W", "X", "U", "V", "0"};

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
    @Resource
    private PfUserSkuMapper userSkuMapper;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private ComBrandMapper comBrandMapper;

    private static Integer pageSize = 10;

    public void addUserCertificate(PfUserCertificate pfUserCertificate) {
        pfUserCertificateMapper.insert(pfUserCertificate);
    }
    /**
     * @Author 贾晶豪
     * @Date 2016/3/17 0017 下午 4:30
     * 个人中心授权书
     */
    public List<CertificateInfo> CertificateByUser(Long userId) throws Exception{
        List<CertificateInfo> certificateInfoList = certificateMapper.getCertificatesByUser(userId);
        if (certificateInfoList != null) {
            String ctValue = PropertiesUtils.getStringValue("product_icon_url");
            for (CertificateInfo certificateInfo : certificateInfoList) {
                if (certificateInfo.getPid() != 0) {
                    certificateInfo.setUpperName(comUserMapper.selectByPrimaryKey(certificateInfo.getUserPid()).getRealName());
                }else{
                    certificateInfo.setUpperName("平台");
                }
                PfUserCertificate pfUserCertificate = pfUserCertificateMapper.selectByUserSkuId(certificateInfo.getId());
                if (pfUserCertificate == null) {
                    certificateInfo.setPfUserCertificateInfo(pfUserCertificate);
                }
                //获取背景等级图片
                PfSkuAgent pfSkuAgent = pfSkuAgentMapper.selectBySkuIdAndLevelId(certificateInfo.getSkuId(), certificateInfo.getAgentLevelId());
                String basePath ="/static/images/certificate/backgroundimg/";
                certificateInfo.setBackimg(basePath + pfSkuAgent.getBackImg());
                ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(certificateInfo.getAgentLevelId());
                certificateInfo.setLevelName(comAgentLevel.getName());
                ComSku comSku = comSkuMapper.selectByPrimaryKey(certificateInfo.getSkuId());
                certificateInfo.setSkuIcon(ctValue + comSku.getIcon());
            }
        }
        return certificateInfoList;
    }

    /**
     * jjh
     * for APP 授权书
     * @param userId
     * @return
     * @throws Exception
     */
    public List<CertificateInfo> CertificateByUserForApp(Long userId,Integer pageNum) throws Exception{
        PageHelper.startPage(pageNum, pageSize, false);
        List<CertificateInfo> certificateInfoList = certificateMapper.getCertificatesByUserAPP(userId);
        if (certificateInfoList != null) {
            for (CertificateInfo certificateInfo : certificateInfoList) {
                if (certificateInfo.getPid() != 0) {
                    certificateInfo.setUpperName(comUserMapper.selectByPrimaryKey(certificateInfo.getUserPid()).getRealName());
                }else{
                    certificateInfo.setUpperName("平台");
                }
                PfUserCertificate pfUserCertificate = pfUserCertificateMapper.selectByUserSkuId(certificateInfo.getId());
                if (pfUserCertificate == null) {
                    certificateInfo.setPfUserCertificateInfo(pfUserCertificate);
                }
                ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(certificateInfo.getAgentLevelId());
                certificateInfo.setLevelName(comAgentLevel.getName());
                ComBrand comBrand = comBrandMapper.selectById(certificateInfo.getBrandId());
                certificateInfo.setBrandIcon(comBrand.getLogoUrl());
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
        if(certificateInfo!=null && certificateInfo.getPid()!=0){
            ComUser comUser = comUserMapper.selectByPrimaryKey(certificateMapper.get(certificateInfo.getPid()).getUserId());
            pfc.setSjName(comUser.getRealName());
        }else{
            pfc.setSjName("平台");
        }
        String ctValue = PropertiesUtils.getStringValue("index_user_certificate_url");
        pfc.setImgUrl(ctValue + pfc.getImgUrl());
        String beginTime = DateUtil.Date2String(pfc.getBeginTime(), "yyyy-MM-dd hh:mm:ss", null);
        pfc.setTjDate(beginTime);
        return pfc;
    }
    /**
     *  证书
     * @param pfuId
     * Jing Hao
     */
    public PfUserCertificate getCertificateBypfuId(Integer pfuId){
        return pfUserCertificateMapper.selectByUserSkuId(pfuId);
    }


    public PfUserCertificate getCertificateByuserskuId(Long userId,Integer skuId){
        return pfUserCertificateMapper.selectByUserAndSkuId(userId,skuId);
    }
    /**
     * 个人信息查询微信号信息
     * @author hanzengzhi
     * @date 2016/3/29 11:56
     */
    public List<PfUserCertificate> selectSkuWeChatInfo(Long userId){
        return pfUserCertificateMapper.selectSkuWeChatInfo(userId);
    }

    /**
     * 获取证书等级名称
     */
    public String getCtname(Integer agentLevelId){
        return comAgentLevelMapper.selectByPrimaryKey(agentLevelId).getName();
    }
    public String uploadImageToOss(MultipartFile idCardImg,ComUser comUser,Integer i){
        try {
            String contentType = idCardImg.getContentType();
            String imageType= null;
            if (contentType.contains(";")){
                imageType = contentType.substring(contentType.indexOf("/")+1,contentType.indexOf(";"));
            }else{
                imageType = contentType.substring(contentType.indexOf("/")+1);
            }
            String fileName = null;
            switch (i){
                case 1:// 上传身份证
                    fileName = comUser.getId()+"_"+"certificate_"+ createGenerateStr()+"."+imageType;
                    OSSObjectUtils.uploadFile(fileName,idCardImg.getSize(),idCardImg.getInputStream(),OSSObjectUtils.OSS_CERTIFICATE_TEMP );
                    break;
                case 2://上传微信头像
                    fileName = comUser.getId()+"_"+"headImage_"+ createGenerateStr()+"."+imageType;
                    OSSObjectUtils.uploadFile(fileName,idCardImg.getSize(),idCardImg.getInputStream(),OSSObjectUtils.OSS_HEADIMAGE_HEADIMAGE);
                    break;
                default:
                    break;
            }
            if (fileName==null){
                throw new BusinessException("上传图片名字为空");
            }
            return fileName;
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }

    /**
     * 生成32位随机字符串
     *
     * @return
     */
    private static String createGenerateStr(){
        int len = 10;
        StringBuilder res = new StringBuilder();
        for(int i = 0; i < len; i++){
            res.append(charArrs[(int)(Math.random() * charArrs.length)]);
        }
        return res.toString();
    }
}
