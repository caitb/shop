package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.ComAgentLevelService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 升级订单的确认和支付
 */
@Service
@Transactional
public class BUpgradeOrderService {

    private final static Logger log = Logger.getLogger(BUpgradeOrderService.class);

    @Resource
    private PfUserUpgradeNoticeService noticeService;
    @Resource
    private SkuService comSkuService;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private ComAgentLevelService comAgentLevelService;




    public void getUpgradeNoticeInfo(Long id){
        //升级订单通知信息
        if (id==null){
            throw new BusinessException("升级通知信息id为null");
        }
        log.info("获取升级通知信息------start");
        log.info("获取升级通知信息------id----"+id);
        PfUserUpgradeNotice upgradeNotice = noticeService.selectByPrimaryKey(id);
        if (upgradeNotice!=null){
            if (true){
                //商品信息
                ComSku comSku = getComSku(upgradeNotice.getSkuId());
                if (comSku!=null){

                }else{
                    log.info("商品信息为null-----id--"+upgradeNotice.getSkuId());
                    throw new BusinessException("商品信息为null-----id--"+upgradeNotice.getSkuId());
                }
                //级别
                PfSkuAgent oldSkuAgent = getPfSkuAgent(comSku.getId(),upgradeNotice.getOrgAgentLevelId());
                PfSkuAgent newSkuAgent = getPfSkuAgent(comSku.getId(),upgradeNotice.getWishAgentLevelId());
                if (oldSkuAgent!=null){
                    ComAgentLevel oldAgentLevel = getComAgentLeveal(oldSkuAgent.getAgentLevelId());
                }
                if (newSkuAgent!=null){
                    ComAgentLevel newAgentLevel = getComAgentLeveal(newSkuAgent.getAgentLevelId());
                }
                //拿货数量
                //总价
            }else{
                log.info("升级通知信息状态不正确-----");
                throw new BusinessException("升级通知信息状态不正确-----");
            }
        }else{
            log.info("升级通知信息为null------id----"+upgradeNotice);
        }
        log.info("获取升级通知信息------end");
    }

    /**
     * 商品信息
     * @param skuId
     * @return
     */
    private ComSku getComSku (Integer skuId){
        return comSkuService.getSkuById(skuId);
    }

    /**
     * 获得商品代理等级信息
     * @param skuId
     * @param levelId
     * @return
     */
    private PfSkuAgent getPfSkuAgent(Integer skuId,Integer levelId){
        return skuAgentService.getBySkuIdAndLevelId(skuId,levelId);
    }
    /**
     * 获得等级信息
     * @param levelId
     * @return
     */
    private ComAgentLevel getComAgentLeveal(Integer levelId){
        return comAgentLevelService.selectByPrimaryKey(levelId);
    }

    private BigDecimal totalPrice(Long userId,Integer skuId){
        try{
            PfUserSku pfUserSku =  userSkuService.getUserSkuByUserIdAndSkuId(userId,skuId);
        }catch (Exception e){
            throw new BusinessException("");
        }
        return null;
    }
}
