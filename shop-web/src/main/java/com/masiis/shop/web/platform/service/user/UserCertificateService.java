package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.platform.certificate.CertificateMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.po.PfUserCertificate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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

    public void addUserCertificate(PfUserCertificate pfUserCertificate) {
        pfUserCertificateMapper.insert(pfUserCertificate);
    }

    /**
      * @Author 贾晶豪
      * @Date 2016/3/17 0017 下午 4:30
      * 个人中心授权书
      */
    public List<CertificateInfo> CertificateByUser(Integer userId){
        List<CertificateInfo> certificateInfoList = certificateMapper.getCertificatesByUser(userId);
        if(certificateInfoList!=null){
           for(CertificateInfo certificateInfo :certificateInfoList){
              if(certificateInfo!=null && certificateInfo.getIsCertificate()!=0){ //授权书已经生成
                  certificateInfo.setPfUserCertificateInfo(pfUserCertificateMapper.selectByUserSkuId(certificateInfo.getId()));
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
        String ctValue = PropertiesUtils.getStringValue("index_user_certificate_url");
        pfc.setImgUrl(ctValue + pfc.getImgUrl());
        String beginTime = DateUtil.Date2String(pfc.getBeginTime(), "yyyy-MM-dd hh:mm:ss", null);
        pfc.setBeginTime(new Date(beginTime));
        return pfc;
    }
}
