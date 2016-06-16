package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.enums.upgrade.UpGradeStatus;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.dao.platform.user.PfUserUpgradeNoticeMapper;
import com.masiis.shop.dao.po.PfUserUpgradeNotice;
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
            upgradeNotice.setUpStatus(UpGradeStatus.UP_STATUS_Untreated.getCode());
        }else {
            upgradeNotice.setUpStatus(UpGradeStatus.UP_STATUS_Complete.getCode());
            logger.info("代理用户申请代理等级小于上级代理等级");
        }
        upgradeNotice.setRemark("升级提交申请");
        pfUserUpgradeNoticeMapper.insert(upgradeNotice);
        return upgradeNotice.getId();
    }

}
