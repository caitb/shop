package com.masiis.shop.admin.service.certificate;

import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.platform.certificate.CertificateMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
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

    @Resource
    private PfBorderMapper pfBorderMapper;
    /**
      * @Author 贾晶豪
      * @Date 2016/3/7  下午 5:51
      * 授权书列表
      */
    public List<CertificateInfo> getCertificates(Map<String, Object> params) throws Exception{
        List<CertificateInfo> certificateInfoList = certificateMapper.getCertificateInfo(params);
        if (certificateInfoList != null && certificateInfoList.size() > 0) {
            for (CertificateInfo certificateInfo : certificateInfoList) {
                if (certificateInfo != null && certificateInfo.getParentUserId() ==1) {
                    certificateInfo.setApproveType("合伙人审核");
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
    public void approveCertificate(Integer status,Integer id) throws Exception{
        CertificateInfo certificateInfo = certificateMapper.get(id);
        if(certificateInfo!=null){
            Map<String, Object> param = new HashMap<>();
            param.put("status",status);
            param.put("status",certificateInfo.getPfUserCertificateInfo().getPfUserSkuId());
            certificateMapper.updateCertificateStatus(param);//审核
            if(status==1){ //审核成功
                certificateMapper.updateCertificateFlag(id);//授权书生成的flag
            }
        }
    }

    /**
      * @Author 贾晶豪
      * @Date 2016/3/9  上午 11:11
      * 申请详情信息
      * Param Id
      */
    public CertificateInfo getApproveInfoById(Integer id)throws Exception{
        CertificateInfo certificateInfo = certificateMapper.getApproveInfo(id);
        if(certificateInfo!=null){
            certificateInfo.setPfBorder(pfBorderMapper.selectByPrimaryKey(certificateInfo.getPfBorderId()));
        }
        return certificateInfo;
    }

}
