package com.masiis.shop.admin.service.user;

import com.masiis.shop.dao.beans.user.PfUserSkuCertificate;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.PfSkuAgent;
import com.masiis.shop.dao.po.PfUserCertificate;
import com.masiis.shop.dao.po.PfUserSku;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PfSkuAgentMapper pfSkuAgentMapper;


    public int update(PfUserSku po) {
        return pfUserSkuMapper.updateByPrimaryKey(po);
    }

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


    public PfUserSku getPfUserSkuByUserIdAndSkuId(Long userId, Integer skuId) {
        return pfUserSkuMapper.selectByUserIdAndSkuId(userId, skuId);
    }

    /**
     * 获取可以升级的代理信息
     *
     * @param skuId
     * @param agentLevelId 用户代理等级
     * @param pLevelId     上级代理等级
     * @return
     */
    public List<PfSkuAgent> getUpgradeAgents(Integer skuId, Integer agentLevelId, Integer pLevelId) {
        return pfSkuAgentMapper.selectUpgradeAgents(skuId, agentLevelId, pLevelId);
    }

    /**
     * 批量修改团队树结构
     *
     * @param treeCode
     * @param parentTreeCode
     * @param idIndex
     * @param treeLevelDiff
     * @return
     */
    public int updateTreeCodes(String treeCode, String parentTreeCode, Integer idIndex, Integer treeLevelDiff) {
        return pfUserSkuMapper.updateTreeCodes(treeCode, parentTreeCode, idIndex, treeLevelDiff);
    }
}
