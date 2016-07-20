package com.masiis.shop.admin.service.user;

import com.masiis.shop.admin.service.order.PfBorderRecommenRewardService;
import com.masiis.shop.admin.service.product.PfUserSkuStockService;
import com.masiis.shop.admin.service.product.SkuAgentService;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.dao.beans.order.BOrderUpgradeDetail;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 升级发送短信
 */
@Service
public class UpgradeMobileMessageService {

    private Logger log = Logger.getLogger(UpgradeMobileMessageService.class);

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private PfBorderRecommenRewardService pfBorderRecommenRewardService;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;

    /**
     * 升级发送短信
     * @param comUser
     * @param pfBorder
     * @param upgradeDetail
     * @return
     */
    public Boolean upgradeSuccssSendMoblieMessage(ComUser comUser, PfBorder pfBorder, List<PfBorderItem> pfBorderItems,BOrderUpgradeDetail upgradeDetail,Boolean isWaitingOrder){
        //给上级发短信
        ComUser newPComUser =  comUserMapper.selectByPrimaryKey(pfBorder.getUserPid());
        log.info("是否处于排单-------"+isWaitingOrder);
        if (newPComUser!=null){
            log.info("上级电话号码-------"+newPComUser.getMobile());
            log.info("商品名称-------"+upgradeDetail.getSkuName());
            log.info("当前等级名-------"+upgradeDetail.getCurrentAgentLevelName());
            log.info("昵称-------"+comUser.getRealName());
            log.info("升级后的等级名-------"+upgradeDetail.getApplyAgentLevelName());
            PfSkuAgent newPfSkuAgent = skuAgentService.getBySkuIdAndLevelId(upgradeDetail.getSkuId(),upgradeDetail.getApplyAgentLevel());
/*            PfUserSkuStock pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(newPComUser.getId(), upgradeDetail.getSkuId());
            Boolean isStock = true;
            if (pfUserSkuStock == null) {
                isStock = false;
            }
            if (pfUserSkuStock.getCustomStock() <= 0) {
                isStock = false;
            }*/
            log.info("收入-------"+newPfSkuAgent.getTotalPrice());
            BigDecimal incomeAmout = pfBorder.getOrderAmount();
            log.info("合伙人上级获得收入----------"+incomeAmout.toString()+"----------订单id----------"+pfBorder.getId());
            Boolean bl = MobileMessageUtil.getInitialization("B").lowerGroupUpRemind(newPComUser.getMobile(),
                    upgradeDetail.getSkuName(),
                    upgradeDetail.getCurrentAgentLevelName(),
                    comUser.getRealName(),
                    upgradeDetail.getApplyAgentLevelName(),
                    incomeAmout,
                    !isWaitingOrder);
            if (bl){
                log.info("升级给上级发送短信成功");
            }
            //给推荐人发短信(获得佣金)
            log.info("pfborderItem的id-------------------"+pfBorderItems.get(0).getId());
            PfBorderRecommenReward pfBorderRecommenReward = pfBorderRecommenRewardService.getByPfBorderItemId(pfBorderItems.get(0).getId());
            log.info("推荐人id-----------------"+pfBorderRecommenReward.getRecommenUserId());
            ComUser recommenRewardUser =  comUserMapper.selectByPrimaryKey(pfBorderRecommenReward.getRecommenUserId());
            log.info("推荐人电话------"+recommenRewardUser.getMobile());
            log.info("被推荐人姓名------"+comUser.getRealName());
            log.info("升级后的奖励------"+pfBorderRecommenReward.getRewardTotalPrice().toString());
            Boolean _bl = MobileMessageUtil.getInitialization("B").refereeUpgradeRecommendRemind(recommenRewardUser.getMobile(),
                    upgradeDetail.getSkuName(),
                    upgradeDetail.getCurrentAgentLevelName(),
                    comUser.getRealName(),
                    upgradeDetail.getApplyAgentLevelName(),
                    pfBorderRecommenReward.getRewardTotalPrice().toString()
            );
            if (_bl){
                log.info("给推荐人发短信(获得佣金)-----成功");
            }
        }
        return true;
    }
}
