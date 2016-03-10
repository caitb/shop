package com.masiis.shop.admin.service.user;

import com.masiis.shop.dao.beans.user.PfUserSkuCertificate;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
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
}
