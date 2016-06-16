package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.enums.upgrade.UpGradeStatus;
import com.masiis.shop.common.enums.upgrade.UpGradeUpStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.dao.platform.user.PfUserRebateMapper;
import com.masiis.shop.dao.platform.user.PfUserUpgradeNoticeMapper;
import com.masiis.shop.dao.po.PfUserRebate;
import com.masiis.shop.dao.po.PfUserUpgradeNotice;
import com.masiis.shop.dao.po.extendPo.UpGradeInfoPo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by JingHao on 2016/6/15
 * 升级管理service
 */
@Service
@Transactional
public class UpgradeNoticeService {
    private static final Logger logger = Logger.getLogger(UpgradeNoticeService.class);
    @Resource
    private PfUserUpgradeNoticeMapper pfUserUpgradeNoticeMapper;

    @Resource
    private PfUserRebateMapper pfUserRebateMapper;
	/**
     * 通过id查询申请单数据
     * @param id            申请单id
     * @return
     * @throws Exception
     */
    public PfUserUpgradeNotice getUpgradeNoticeById(Long id) throws Exception{
        return pfUserUpgradeNoticeMapper.selectByPrimaryKey(id);
    }
    /**
     * jjh
     * 我的升级申请记录
     * @param userId
     * @return
     */
    public List<PfUserUpgradeNotice> getPfUserUpGradeInfoByUserId(Long userId) throws Exception{
        return pfUserUpgradeNoticeMapper.selectByUserId(userId);
    }

    /**
     * jjh
     * 我的下级申请记录
     * @param userId
     * @return
     */
    public List<PfUserUpgradeNotice> getPfUserUpGradeInfoByUserPId(Long userId) throws Exception {
        return pfUserUpgradeNoticeMapper.selectByUserPId(userId);
    }

    /**
     * jjh
     * 一次性返利<获得的返利>
     * @param userId
     * @return
     */
    public List<PfUserRebate> getPfUserRebateByUserId(Long userId) throws Exception{
        return pfUserRebateMapper.selectByUserId(userId);
    }

    /**
     * jjh
     * 一次性返利<支付的返利>
     * @param userId
     * @return
     */
    public List<PfUserRebate> getPfUserRebateByUserPId(Long userId) throws Exception{
        return pfUserRebateMapper.selectByUserPId(userId);
    }

    /**
     * jjh
     * 根据主键获取申请信息
     * @param id
     * @return
     */
    public PfUserUpgradeNotice getPfUserUpGradeInfoByPrimaryKey(Long id) throws Exception{
        return pfUserUpgradeNoticeMapper.selectByPrimaryKey(id);
    }

    /**
     * jjh
     * 组合查询
     * @param UserPId 我作为上级
     * @param skuId 商品
     * @param upStatus 申请状态
     * @return
     * @throws Exception
     */
    public List<PfUserUpgradeNotice> getPfUserUpGradeInfoByParam(Long UserPId, Integer skuId, Integer upStatus) throws Exception {
        return pfUserUpgradeNoticeMapper.selectByParam(UserPId, skuId, upStatus);
    }

    /**
     * jjh
     * 组合查询
     * @param skuId 商品
     * @param userPid 支付返利
     * @param userId 获取返利
     * @return
     */
    public List<PfUserUpgradeNotice> getPfUserUpGradeInfoByRebateAndSkuId(Integer skuId, Long userPid, Long userId) {
        return pfUserUpgradeNoticeMapper.selectBySkuIdAndRebateType(skuId, userPid, userId);
    }

    public String coverCodeByMyUpgrade(Integer upStatus) {
        String value = null;
        if (upStatus == 0) {
            value = UpGradeStatus.STATUS_Untreated.getMessage();
        } else if (upStatus == 1) {
            value = UpGradeStatus.STATUS_Processing.getMessage();
        } else if (upStatus == 2) {
            value = UpGradeStatus.STATUS_NoPayment.getMessage();
        } else if (upStatus == 3) {
            value = UpGradeStatus.STATUS_Complete.getMessage();
        } else if (upStatus == 4) {
            value = UpGradeStatus.STATUS_Revocation.getMessage();
        }
        return value;
    }

    public String coverCodeByLowerUpgrade(Integer upStatus) {
        String value = null;
        if (upStatus == 0) {
            value = UpGradeUpStatus.UP_STATUS_Untreated.getMessage();
        } else if (upStatus == 1) {
            value = UpGradeUpStatus.UP_STATUS_NotUpgrade.getMessage();
        } else if (upStatus == 2) {
            value = UpGradeUpStatus.UP_STATUS_Upgrade.getMessage();
        } else if (upStatus == 3) {
            value = UpGradeUpStatus.UP_STATUS_Complete.getMessage();
        }
        return value;
    }
	/**
     * 处理代理用户升级
     * @param userId        代理用户id
     * @param userPid       代理用户上级id
     * @param curAgentLevel 代理用户当前代理等级
     * @param upgradeLevel  代理用户申请代理等级
     * @param pAgentLevel   代理用户上级代理等级
     * @param skuId         合伙skuId
     * @return              主键
     * @auth:wbj
     * @throws Exception
     */
    public Long dealAgentUpGrade(Long userId, Long userPid, Integer curAgentLevel, Integer upgradeLevel, Integer pAgentLevel, Integer skuId) throws Exception{
        PfUserUpgradeNotice upgradeNotice = new PfUserUpgradeNotice();
        logger.info("生成申请单号begin");
        String code = OrderMakeUtils.makeOrder("U");
        upgradeNotice.setCode(code);
        upgradeNotice.setCreateTime(new Date());
        upgradeNotice.setUserId(userId); //申请人id
        upgradeNotice.setUserPid(userPid);  //申请人上级合伙人id
        upgradeNotice.setSkuId(skuId);      //申请升级skuid
        upgradeNotice.setOrgAgentLevelId(curAgentLevel);    //原合伙代理等级
        upgradeNotice.setWishAgentLevelId(upgradeLevel);    //申请合伙代理等级
        logger.info("生成申请单号end");
        upgradeNotice.setStatus(UpGradeStatus.STATUS_Untreated.getCode());
        if (upgradeLevel.intValue() == pAgentLevel.intValue()){
            logger.info("代理用户申请代理等级等于上级代理等级");
            upgradeNotice.setUpStatus(UpGradeUpStatus.UP_STATUS_Untreated.getCode());
        }else {
            upgradeNotice.setUpStatus(UpGradeUpStatus.UP_STATUS_Complete.getCode());
            logger.info("代理用户申请代理等级小于上级代理等级");
        }
        upgradeNotice.setRemark("升级提交申请");
        if (pfUserUpgradeNoticeMapper.insert(upgradeNotice) == 0){
            throw new BusinessException("创建升级申请数据失败");
        }
        return upgradeNotice.getId();
    }

    /**
     * 根据id查询申请信息
     * @param id 申请信息表id
     * @return
     */
    public UpGradeInfoPo getUpGradeInfo(Long id){
        logger.info("根据id查询申请信息 id=" + id);
        return pfUserUpgradeNoticeMapper.selectUpGradeInfoPoById(id);
    }

    /**
     * 更新升级申请信息
     * @param pfUserUpgradeNotice
     * @return
     */
    public int updateUpgradeNotice(PfUserUpgradeNotice pfUserUpgradeNotice) throws Exception{
        int a = pfUserUpgradeNoticeMapper.updateByPrimaryKey(pfUserUpgradeNotice);
        if (a == 0){
            throw new BusinessException("处理失败");
        }
        return a;
    }
}
