package com.masiis.shop.admin.service.user;


import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.po.PfUserCertificate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/6/16.
 */
@Service
@Transactional
public class PfUserCertificateService {

    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;

    public int update(PfUserCertificate po){
        return  pfUserCertificateMapper.updateById(po);
    }

    public PfUserCertificate selectByUserIdAndSkuId(Long userId,Integer skuId){
        return pfUserCertificateMapper.selectByUserIdAndSkuId(userId,skuId);
    }

    /**
     * 生成证书编码
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
}

