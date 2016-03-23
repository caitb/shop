package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.platform.certificate.CertificateMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserCertificate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            for (CertificateInfo certificateInfo : certificateInfoList) {
                if (certificateInfo != null) {
                    //获取代理状态
                    ComUser comUser = comUserMapper.selectByPrimaryKey(certificateInfo.getUserId());
                    PfUserCertificate pfUserCertificate = pfUserCertificateMapper.selectByUserSkuId(certificateInfo.getId());
                    certificateInfo.setPfUserCertificateInfo(pfUserCertificate);
                    if (comUser != null && comUser.getIsAgent() == 0) {//未代理
                        if (pfUserCertificate != null) {
                            certificateInfo.setReceivect(1);//领取
                        } else {
                            certificateInfo.setIsApply(0);//流程中断，未申请
                        }
                    } else {
                        //已经代理过商品
                        certificateInfo.setReceivect(0);//证书已经生成，未领取
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
    public void receiveCertificate(Integer pfuId) {
        CertificateInfo ctInfo = certificateMapper.get(pfuId);
        List<PfUserCertificate> uct = pfUserCertificateMapper.selectByUser(ctInfo.getUserId());
        if (uct != null) {
            PfUserCertificate pfc = uct.get(0);
            pfc.setCreateTime(new Date());
            pfc.setId(null);
            pfc.setPfUserSkuId(pfuId);
            pfc.setReason(null);
            pfc.setSkuId(ctInfo.getSkuId());
            pfc.setStatus(1);//审核成功状态
            pfUserCertificateMapper.insert(pfc);
            Map<String, Object> param = new HashMap<>();
            param.put("id", pfuId);
            param.put("code", pfc.getCode());//证书编号
            certificateMapper.updateCertificateFlag(param);//更新证书审核状态
        }
    }

    /**
     *  证书
     * @param pfuId
     * Jing Hao
     */
    public PfUserCertificate getCertificateBypfuId(Integer pfuId){
        return pfUserCertificateMapper.selectByUserSkuId(pfuId);
    }
}
