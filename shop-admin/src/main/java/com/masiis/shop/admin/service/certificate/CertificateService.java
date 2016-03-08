package com.masiis.shop.admin.service.certificate;

import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.platform.certificate.CertificateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by JingHao on 2016/3/7 0007.
 */
@Service
@Transactional
public class CertificateService {


    @Resource
    private CertificateMapper certificateMapper;

    /**
      * @Author 贾晶豪
      * @Date 2016/3/7 0007 下午 5:51
      * 授权书列表
      */
    public List<CertificateInfo> getCertificates(Map<String, Object> params) {
        List<CertificateInfo> certificateInfoList = certificateMapper.getCertificateInfo(params);
        if (certificateInfoList != null && certificateInfoList.size() > 0) {
            for (CertificateInfo certificateInfo : certificateInfoList) {
                if (certificateInfo != null && certificateInfo.getParentUserId() != null) {
                    certificateInfo.setApproveType("合伙人审核");
                } else {
                    certificateInfo.setApproveType("平台审核");
                }
            }
        }
        return certificateInfoList;
    }
}
