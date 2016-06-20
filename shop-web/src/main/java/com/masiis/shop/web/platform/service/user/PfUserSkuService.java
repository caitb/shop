package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.PfSkuAgent;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.dao.beans.user.upgrade.UserSkuAgent;
import org.apache.ibatis.javassist.convert.TransformWriteField;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 用户商品代理关系Service
 * Created by wangbingjian on 2016/6/14.
 */
@Service
@Transactional
public class PfUserSkuService {

    private static final Logger logger = Logger.getLogger(PfUserSkuService.class);
    @Autowired
    private PfUserSkuMapper pfUserSkuMapper;
    @Autowired
    private PfSkuAgentMapper pfSkuAgentMapper;


    public int update(PfUserSku po) {
        return pfUserSkuMapper.updateByPrimaryKey(po);
    }

    /**
     * 根据用户id获取当前代理等级信息
     *
     * @param userId
     * @return
     */
    public List<UserSkuAgent> getCurrentAgentLevel(Long userId) {
        logger.info("查询用户当前商品代理等级");
        return pfUserSkuMapper.selectCurrentAgentLevel(userId);
    }

    /**
     * 获取商品代理信息
     *
     * @param skuId
     * @param agentLevelId
     * @return
     */
    public PfSkuAgent getCurrentSkuAgent(Integer skuId, Integer agentLevelId) {
        logger.info("获取商品代理信息");
        return pfSkuAgentMapper.selectBySkuIdAndLevelId(skuId, agentLevelId);
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
        logger.info("获取可以升级的代理信息");
        logger.info("skuId:" + skuId);
        logger.info("agentLevelId:" + agentLevelId);
        logger.info("pLevelId:" + pLevelId);
        return pfSkuAgentMapper.selectUpgradeAgents(skuId, agentLevelId, pLevelId);
    }

    public PfUserSku getPfUserSkuByUserIdAndSkuId(Long userId, Integer skuId) {
        return pfUserSkuMapper.selectByUserIdAndSkuId(userId, skuId);
    }

    public List<PfUserSku> getPfUserSkuInfoByUserId(Long UserId) {
        return pfUserSkuMapper.selectByUserId(UserId);
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

    /**
     * 查询代理商品信息
     * @param userId
     * @return
     */
    public List<Map<String, Object>> listAgentSku(Long userId){
        return pfUserSkuMapper.selectAgentSku(userId);
    }
}
