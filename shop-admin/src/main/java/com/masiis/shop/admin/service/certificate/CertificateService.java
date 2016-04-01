package com.masiis.shop.admin.service.certificate;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.platform.certificate.CertificateMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
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
     * @Date 2016/3/10  下午 12:04
     * 更改上级
     */
    public void updateUpperPartner(String id, String pId) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("pId", pId);
        certificateMapper.updateUpperPartnerById(param);
    }

    public String findById(Integer id) {
        String imgUrl = certificateMapper.findById(id);
        String idCardImg = PropertiesUtils.getStringValue("index_user_certificate_url");
        return idCardImg+imgUrl;
    }

    /**
     * 递归
     *  Jing Hao
     */
    public String getUpperInfoListById(Integer id) {
        CertificateInfo certificateInfo = certificateMapper.get(id);
        if (certificateInfo != null && certificateInfo.getPid() != 0) {
            String currentId = certificateInfo.getUserId().toString() + ",";
            String pid = getUpperInfoListById(certificateInfo.getPid());//上级
            return currentId + pid;
        } else {
            return certificateInfo.getUserId().toString();
        }
    }
}
