package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.beans.api.agent.OtherSkuInfo;
import com.masiis.shop.dao.beans.user.upgrade.UpgradePackageInfo;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.PfSkuAgent;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.dao.beans.user.upgrade.UserSkuAgent;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import org.apache.ibatis.javassist.convert.TransformWriteField;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Autowired
    private SkuService skuService;
    @Autowired
    private SkuAgentService skuAgentService;

    /**
     * 通过userId查询代理商品关系
     *
     * @param userId
     * @return
     */
    public List<PfUserSku> getPfUserSkuByUserId(Long userId) {
        PfUserSku pfUserSku = new PfUserSku();
        pfUserSku.setUserId(userId);
        return pfUserSkuMapper.selectByCondition(pfUserSku);
    }

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

    /**
     * 获取可以升级的代理信息 api接口
     *
     * @param skuId        商品id
     * @param agentLevelId 用户代理等级
     * @param pLevelId     上级代理等级
     * @return
     */
    public List<UpgradePackageInfo> getUpgradePackageInfo15(Integer skuId, Integer agentLevelId, Integer pLevelId) {
        logger.info("获取可以升级的代理信息");
        logger.info("skuId:" + skuId);
        logger.info("agentLevelId:" + agentLevelId);
        logger.info("pLevelId:" + pLevelId);
        List<UpgradePackageInfo> infos = pfSkuAgentMapper.selectUpgradePackage(skuId, agentLevelId, pLevelId);
        List<UpgradePackageInfo> returnInfos = new ArrayList<>();
        for (UpgradePackageInfo info : infos) {
            //获取sku
            ComSku comSku = skuService.getSkuById(skuId);
            BigDecimal highProfit = (comSku.getPriceRetail().subtract(info.getUnitPrice()))
                    .multiply(BigDecimal.valueOf(info.getQuantity()))
                    .setScale(2, BigDecimal.ROUND_DOWN);//最高利润
            info.setHighProfit(highProfit);
            Integer lowerAgentLevelId = agentLevelId + 1;
            //获取下级代理信息
            PfSkuAgent lowerSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, lowerAgentLevelId);
            BigDecimal lowProfit = BigDecimal.ZERO;
            if (lowerSkuAgent == null) {
                lowProfit = highProfit;
            } else {
                lowProfit = (lowerSkuAgent.getUnitPrice().subtract(info.getUnitPrice()))
                        .multiply(BigDecimal.valueOf(info.getQuantity()))
                        .setScale(2, BigDecimal.ROUND_DOWN);//最低利润
            }
            info.setLowProfit(lowProfit);
            info.setLevelCount(getAgentNumByLevelAndSku(info.getAgentLevel(), skuId));
            returnInfos.add(info);
        }
        return returnInfos;
    }

    public PfUserSku getPfUserSkuByUserIdAndSkuId(Long userId, Integer skuId) {
        return pfUserSkuMapper.selectByUserIdAndSkuId(userId, skuId);
    }

    public List<PfUserSku> getPfUserSkuInfoByUserId(Long UserId) {
        return pfUserSkuMapper.selectByUserId(UserId);
    }

    /**
     * 查询这个人的boss的团队的所有成员
     *
     * @param treeCode
     * @return
     */
    public List<PfUserSku> getBossTeamInfoByTreeCode(String treeCode) {
        return pfUserSkuMapper.getBossTeamInfoByTreeCode(treeCode);
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
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> listAgentSku(Long userId) {
        return pfUserSkuMapper.selectAgentSku(userId);
    }

    public List<PfUserSku> getPfUserSkuInfoByUserIdNotPid(Long UserId) {
        return pfUserSkuMapper.selectByUserIdNotPid(UserId);
    }

    /**
     * 根据父用户id查询下级代理人数
     *
     * @param userPid
     * @return
     */
    public Integer getNumsByUserPid(Long userPid) {
        return pfUserSkuMapper.selectChildNumByUserPid(userPid);
    }

    /**
     * 根据userPid查询直接下级
     *
     * @param userId
     * @return
     */
    public List<Long> getChildUserIdByUserPid(Long userId) {
        return pfUserSkuMapper.selectChildByUserPid(userId);
    }

    /**
     * 查询当前等级 代理人数
     *
     * @param level 代理登记
     * @param skuId 商品id
     * @return
     */
    public Integer getAgentNumByLevelAndSku(Integer level, Integer skuId) {
        return pfUserSkuMapper.selectAgentNumByLevelAndSku(level, skuId);
    }

    public List<PfSkuAgent> getOthersByUserIdAndDefaultSkuId(Long userPid, Integer skuId) {
        return pfUserSkuMapper.selectOthersByUserIdAndDefaultSkuId(userPid, skuId);
    }

    public int insert(PfUserSku pfUserSku) {
        return pfUserSkuMapper.insert(pfUserSku);
    }

    public int updateTreeCodeById(Integer id, String treeCode) {
        return pfUserSkuMapper.updateTreeCodeById(id, treeCode);
    }

    public List<Map<String,Number>> getUpBrand(Long userId){
        return pfUserSkuMapper.selectUpBrand(userId);
    }

    public Integer getGTLastLevelNumByUserId(Long userId) {
        return pfUserSkuMapper.selectGTLastLevelNumByUserId(userId);
    }

    public List<Long> getChildsByUserIdAndBrandId(Long userId, Integer brandId) {
        return pfUserSkuMapper.selectChildsByUserIdAndBrandId(userId, brandId);
    }
}
