package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.po.PfUserCertificate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    public void addUserCertificate(PfUserCertificate pfUserCertificate) {
        pfUserCertificateMapper.insert(pfUserCertificate);
    }

    /**
      * @Author 贾晶豪
      * @Date 2016/3/17 0017 下午 4:30
      * 个人中心授权书
      */
    public List<PfUserCertificate> CertificateByUser(Integer userId){
        List<PfUserCertificate> pfUserCertificateList = pfUserCertificateMapper.selectByCode(userId);
        if(pfUserCertificateList!=null && pfUserCertificateList.size()>0){
            for(PfUserCertificate pfUserCertificate: pfUserCertificateList){
                pfUserCertificate.setComSku(comSkuMapper.selectByPrimaryKey(pfUserCertificate.getSkuId()));
            }
        }
       return pfUserCertificateList;
    }
    /**
      * @Author 贾晶豪
      * @Date 2016/3/17 0017 下午 6:40
      * 商品证书详情
      */
    public PfUserCertificate CertificateDetailsByUser(Integer skuId,Long userId){
        Map<String, Object> param = new HashMap<>();
        param.put("skuId",skuId);
        param.put("userId",userId);
        PfUserCertificate pfc = pfUserCertificateMapper.selectByUserSkuId(param);
        String ctValue = PropertiesUtils.getStringValue("index_user_certificate_url");
        pfc.setImgUrl(ctValue + pfc.getImgUrl());
        return pfUserCertificateMapper.selectByUserSkuId(param);
    }
}
