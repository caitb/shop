package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.platform.certificate.CertificateMapper;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    @Resource
    private PfUserSkuMapper userSkuMapper;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;


    public void addUserCertificate(PfUserCertificate pfUserCertificate) {
        pfUserCertificateMapper.insert(pfUserCertificate);
    }

    /**
     * @Author 贾晶豪
     * @Date 2016/3/17 0017 下午 4:30
     * 个人中心授权书
     */
    public List<CertificateInfo> CertificateByUser(Integer userId) throws Exception{
        List<CertificateInfo> certificateInfoList = certificateMapper.getCertificatesByUser(userId.longValue());
        if (certificateInfoList != null) {
            String ctValue = PropertiesUtils.getStringValue("product_icon_url");
            for (CertificateInfo certificateInfo : certificateInfoList) {
                if (certificateInfo.getPid() != 0) {
                    certificateInfo.setUpperName(comUserMapper.selectByPrimaryKey(certificateInfo.getUserPid()).getRealName());
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
}
