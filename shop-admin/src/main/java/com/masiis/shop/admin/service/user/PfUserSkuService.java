package com.masiis.shop.admin.service.user;

import com.masiis.shop.dao.beans.user.PfUserSkuCertificate;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.PfUserCertificate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by 49134 on 2016/3/9.
 */
@Service
public class PfUserSkuService {

    @Resource
    private PfUserSkuMapper pfUserSkuMapper;

    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;

    /**
     * 查询合伙人列表
     * @param searchParam
     * @return
     */
    public List<PfUserSkuCertificate> getUserSkuList(Map<String, Object> searchParam) {
        List<PfUserSkuCertificate> puscList = pfUserSkuMapper.getUserSkuList(searchParam);
        return puscList;
    }

    /**
     * 查询下级人数
     * @param pid
     * @return
     */
    public Integer findLowerCount(Integer pid) {
        return pfUserSkuMapper.findLowerCount(pid);
    }

    /**
     * 合伙信息
     * @param id
     * @return
     */
    public List<PfUserSkuCertificate> getUserSkuById(Integer id) {
        return pfUserSkuMapper.getUserSkuListById(id);
    }

    public Integer findCertificateBySkuId(Integer userSkuId) {
        PfUserCertificate pfUserCertificate = pfUserCertificateMapper.selectByUserSkuId(userSkuId);
        Integer status=null;
        if (pfUserCertificate!=null){
            status = pfUserCertificate.setStatus(pfUserCertificate.getStatus());
        }
        return status;
    }
}
