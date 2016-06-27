package com.masiis.shop.admin.service.order;

import com.masiis.shop.admin.service.user.PfUserRecommendRelationService;
import com.masiis.shop.admin.service.user.PfUserStatisticsService;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by hzz on 2016/6/9.
 */
@Service
@Transactional
public class BOrderStatisticsService {

    private final static Logger logger = Logger.getLogger(BOrderStatisticsService.class);

    @Resource
    private PfBorderItemMapper pfBorderItemMapper;
    @Resource
    private PfUserStatisticsService userStatisticsService;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private PfBorderMapper pfBorderMapper;
    @Resource
    private PfUserRecommendRelationService pfUserRecommendRelationService;

    /**
     * 订单显示实时统计
     * @author hanzengzhi
     * @date 2016/6/9 16:55
     */
    public void statisticsOrder(Long orderId){
        PfBorder pfBorder = getPfBorderById(orderId);
        if (pfBorder!=null){
            logger.info("---实时显示统计-------------------------start");
            List<PfBorderItem> ordItems =  pfBorderItemMapper.getPfBorderItemDetail(pfBorder.getId());
            logger.info("---实时显示统计---------------------------end");
            statisticsByOrder(pfBorder,ordItems);
        }
    }
    private void statisticsByOrder(PfBorder order, List<PfBorderItem> ordItems) {
        statisticsUserInfo(order, ordItems);
        statisticsPidUserInfo(order, ordItems);
        statisticsRecommenUserInfo(order, ordItems);
    }

    private void statisticsUserInfo(PfBorder order, List<PfBorderItem> ordItems) {
        logger.info("代理人统计-----start");
        //代理人的统计信息
        Long userId = order.getUserId();
        logger.info("代理人统计-----userId----" + order.getUserId());
        if (userId != null) {
            for (PfBorderItem pfBorderItem : ordItems) {
                logger.info("代理人统计-----userId----" + order.getUserId() + "----skuId---" + pfBorderItem.getSkuId());
                PfUserStatistics statistics = userStatisticsService.selectByUserIdAndSkuId(userId, pfBorderItem.getSkuId());
                if (statistics != null) {
                    updateStatisticsUserInfo(statistics, order, pfBorderItem, userId);
                } else {
                    insertStatisticsUserInfo(statistics, order, pfBorderItem);
                }
            }
        } else {
            throw new BusinessException("代理人id为null");
        }
        logger.info("代理人统计-----end");
    }

    private void updateStatisticsUserInfo(PfUserStatistics statistics, PfBorder order, PfBorderItem pfBorderItem, Long userId) {
        //总成本(订单金额-保证金)
        BigDecimal ordAmount = order.getOrderAmount();
        BigDecimal bailAmount = order.getBailAmount();
        logger.info("总成本---之前----" + statistics.getCostFee());
        if (statistics.getCostFee() != null) {
            statistics.setCostFee(statistics.getCostFee().add(ordAmount.subtract(bailAmount)));
        } else {
            statistics.setCostFee(ordAmount.subtract(bailAmount));
        }
        logger.info("总成本---之后----" + statistics.getCostFee());
        logger.info("总成本---增加了----" + ordAmount.subtract(bailAmount).intValue());
        //进货订单数量
        logger.info("进货订单数量----之前----" + statistics.getUpOrderCount());
        if (statistics.getUpOrderCount() != null) {
            statistics.setUpOrderCount(statistics.getUpOrderCount() + 1);
        } else {
            statistics.setUpOrderCount(1);
        }
        logger.info("进货订单数量----之后----" + statistics.getUpOrderCount());
        //进货商品数量
        logger.info("进货商品数量----之前---" + statistics.getUpProductCount());
        if (statistics.getUpProductCount() != null) {
            statistics.setUpProductCount(statistics.getUpProductCount() + pfBorderItem.getQuantity());
        } else {
            statistics.setUpProductCount(pfBorderItem.getQuantity());
        }
        logger.info("进货商品数量----之后---" + statistics.getUpProductCount());
        logger.info("进货商品数量----增加了---" + pfBorderItem.getQuantity());
        statistics.setCreateTime(new Date());
        int i = userStatisticsService.updateByIdAndVersion(statistics);
        if (i != 1) {
            logger.info("更新代理人的统计信息----userId---" + userId + "-----skuId---" + pfBorderItem.getSkuId());
            throw new BusinessException("更新代理人的统计信息----userId---" + userId + "-----skuId---" + pfBorderItem.getSkuId());
        }
    }

    private void insertStatisticsUserInfo(PfUserStatistics statistics, PfBorder order, PfBorderItem pfBorderItem) {
        statistics = new PfUserStatistics();
        statistics.setCreateTime(new Date());
        statistics.setUserId(order.getUserId());
        statistics.setSkuId(pfBorderItem.getSkuId().longValue());
        statistics.setIncomeFee(new BigDecimal(0));
        statistics.setProfitFee(new BigDecimal(0));
        BigDecimal ordAmount = order.getOrderAmount();
        BigDecimal bailAmount = order.getBailAmount();
        statistics.setCostFee(ordAmount.subtract(bailAmount));
        statistics.setUpOrderCount(1);
        statistics.setUpProductCount(pfBorderItem.getQuantity());
        statistics.setDownOrderCount(0);
        statistics.setDownProductCount(0);
        statistics.setTakeOrderCount(0);
        statistics.setTakeProductCount(0);
        statistics.setTakeFee(new BigDecimal(0));
        statistics.setVersion(0L);
        statistics.setRecommenGetFee(BigDecimal.ZERO);
        statistics.setRecommenSendFee(BigDecimal.ZERO);
        userStatisticsService.insert(statistics);
        logger.info("插入代理人的统计信息-------");
    }

    private void statisticsPidUserInfo(PfBorder order, List<PfBorderItem> ordItems) {
        logger.info("代理人上级统计-------start");
        //获得代理人的上级统计信息
        logger.info("代理人上级统计-------userPid---" + order.getUserPid());
        Long userPid = order.getUserPid();
        PfUserStatistics statistics = null;
        if (userPid != null && userPid != 0) {
            for (PfBorderItem pfBorderItem : ordItems) {
                logger.info("代理人上级统计-------userPid---" + order.getUserPid() + "----skuId----" + pfBorderItem.getSkuId());
                statistics = userStatisticsService.selectByUserIdAndSkuId(userPid, pfBorderItem.getSkuId());
                if (statistics != null) {
                    updateStatisticsPidUserInfo(statistics, order, pfBorderItem, userPid);
                } else {
                    insertStatisticsPidUserInfo(statistics, order, pfBorderItem, userPid);
                }
            }
        } else {
            logger.info("代理上级的id为null或者为平台-------userPid----" + userPid + "-----本级userId-----" + order.getUserId());
        }
        logger.info("代理人上级统计-------end");
    }

    private void updateStatisticsPidUserInfo(PfUserStatistics statistics, PfBorder order, PfBorderItem pfBorderItem, Long userPid) {
        //总销售额
        BigDecimal ordAmount = order.getOrderAmount();
        BigDecimal bailAmount = order.getBailAmount();
        BigDecimal recommenAmount = order.getRecommenAmount();
        logger.info("总销售额-----之前----" + statistics.getIncomeFee());
        if (statistics.getIncomeFee() != null) {
            statistics.setIncomeFee(statistics.getIncomeFee().add(ordAmount.subtract(bailAmount)));
        } else {
            statistics.setIncomeFee(ordAmount.subtract(bailAmount));
        }
        logger.info("总销售额-----之后----" + statistics.getIncomeFee());
        logger.info("总销售额-----增加了----" + ordAmount.subtract(bailAmount).intValue());
        //利润
        logger.info("利润-----之前----" + statistics.getProfitFee());
        BigDecimal sumProfitFee = getSumProfitFee(userPid, pfBorderItem.getSkuId(), pfBorderItem.getUnitPrice(), pfBorderItem.getQuantity(),recommenAmount);
        if (statistics.getProfitFee() != null) {
            statistics.setProfitFee(statistics.getProfitFee().add(sumProfitFee));
        } else {
            statistics.setProfitFee(sumProfitFee);
        }
        logger.info("利润-----之后----" + statistics.getProfitFee());
        logger.info("利润-----增加了----" + sumProfitFee);
        //出货订单数量
        if (statistics.getDownOrderCount() != null) {
            statistics.setDownOrderCount(statistics.getDownOrderCount() + 1);
        } else {
            statistics.setDownOrderCount(1);
        }
        //出货商品数量
        logger.info("出货商品数量----之前------" + statistics.getDownProductCount());
        if (statistics.getDownProductCount() != null) {
            statistics.setDownProductCount(statistics.getDownProductCount() + pfBorderItem.getQuantity());
        } else {
            statistics.setDownProductCount(pfBorderItem.getQuantity());
        }
        logger.info("出货商品数量----之后-----" + statistics.getDownProductCount());
        logger.info("出货商品数量----增加-----" + pfBorderItem.getQuantity());
        //v1.2 推荐发出奖励金额
        if (recommenAmount.compareTo(BigDecimal.ZERO) > 0) {
            logger.info("推荐发出奖励金额----之前------" + statistics.getRecommenSendFee());
            statistics.setRecommenSendFee(statistics.getRecommenSendFee().add(recommenAmount));
            logger.info("推荐发出奖励金额----之后-----" + statistics.getRecommenSendFee());
            logger.info("推荐发出奖励金额----增加-----" + recommenAmount);
        }
        statistics.setCreateTime(new Date());
        int i = userStatisticsService.updateByIdAndVersion(statistics);
        if (i != 1) {
            throw new BusinessException("更新代理人上级统计信息失败-----pidUserId---" + order.getUserPid() + "---skuId----" + pfBorderItem.getSkuId());
        }
    }

    private void insertStatisticsPidUserInfo(PfUserStatistics statistics, PfBorder order, PfBorderItem pfBorderItem, Long userPid) {
        statistics = new PfUserStatistics();
        statistics.setCreateTime(new Date());
        statistics.setUserId(userPid);
        statistics.setSkuId(pfBorderItem.getSkuId().longValue());
        BigDecimal ordAmount = order.getOrderAmount();
        BigDecimal bailAmount = order.getBailAmount();
        BigDecimal recommenAmount = order.getRecommenAmount();
        statistics.setIncomeFee(ordAmount.subtract(bailAmount));
        BigDecimal sumProfitFee = getSumProfitFee(userPid, pfBorderItem.getSkuId(), pfBorderItem.getUnitPrice(), pfBorderItem.getQuantity(),recommenAmount);
        statistics.setProfitFee(sumProfitFee);
        statistics.setCostFee(new BigDecimal(0));
        statistics.setUpOrderCount(0);
        statistics.setUpProductCount(0);
        statistics.setDownOrderCount(1);
        statistics.setDownProductCount(pfBorderItem.getQuantity());
        statistics.setTakeOrderCount(0);
        statistics.setTakeProductCount(0);
        statistics.setTakeFee(new BigDecimal(0));
        statistics.setVersion(0L);
        //v1.2 新增推荐奖励金额
        statistics.setRecommenSendFee(recommenAmount);
        statistics.setRecommenGetFee(BigDecimal.ZERO);
        userStatisticsService.insert(statistics);
        logger.info("插入代理人上级统计信息-------");
    }

    /**
     * 获得商品的利润  = 订单的商品单价 - （商品的成本 * 数量） - 推荐奖
     * @param userPid
     * @param skuId
     * @param unitPrice
     * @param quantity
     * @return
     */
    private BigDecimal getSumProfitFee(Long userPid,Integer skuId,BigDecimal unitPrice,Integer quantity,BigDecimal recommenAmount){
        PfUserSku pUserSku = null;
        PfSkuAgent pSkuAgent = null;
        BigDecimal sumProfitFee = BigDecimal.ZERO;
        logger.info("用户id-------------"+userPid);
        logger.info("商品的skuId-------------"+skuId);
        logger.info("商品的购买价格-------------"+unitPrice);
        logger.info("商品的购买数量-------------"+quantity);
        logger.info("商品的推荐奖-------------"+recommenAmount);
        pUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(userPid, skuId);
        if (pUserSku!=null){
            logger.info("代理的商品等级表id------------"+pUserSku.getAgentLevelId());
            pSkuAgent = pfSkuAgentMapper.selectBySkuIdAndLevelId(skuId, pUserSku.getAgentLevelId());
            BigDecimal unit_profit = BigDecimal.ZERO;
            if (pSkuAgent!=null&&pSkuAgent.getUnitPrice()!=null){
                logger.info("商品的购买价格-----------"+unitPrice);
                logger.info("商品的成本拿货价格-----------"+pSkuAgent.getUnitPrice());
                if (unitPrice.compareTo(pSkuAgent.getUnitPrice())<0){
                    logger.info("商品的购买价格小于商品的代理价格,利润小于0------userPid---"+userPid+"-----skuId----"+skuId);
                    throw new BusinessException("商品的购买价格小于商品的代理价格,利润小于0------userPid---"+userPid+"-----skuId----"+skuId);
                }
                unit_profit= unitPrice.subtract(pSkuAgent.getUnitPrice());
            }else{
                unit_profit= unitPrice;
            }
            sumProfitFee = sumProfitFee.add(unit_profit.multiply(BigDecimal.valueOf(quantity))).subtract(recommenAmount);
        }else{
            logger.info("平台sku代理设置表为null-------用户id------"+userPid+"----------skuId----"+skuId);
        }
        logger.info("总利润--------"+sumProfitFee);
        return sumProfitFee;
    }

    private void statisticsRecommenUserInfo(PfBorder border, List<PfBorderItem> ordItems) {
        if (border.getRecommenAmount().compareTo(BigDecimal.ZERO) > 0) {
            for (PfBorderItem orderItem : ordItems) {
                logger.info("推荐获得的奖励-------userId----" + border.getBailAmount() + "-----skuId----" + orderItem.getSkuId());
                PfUserRecommenRelation userRecommenRelation = pfUserRecommendRelationService.selectRecommenRelationByUserIdAndSkuId(border.getUserId(), orderItem.getSkuId());
                PfUserStatistics _pfUserStatistics = userStatisticsService.selectByUserIdAndSkuId(userRecommenRelation.getUserPid(), orderItem.getSkuId());
                if (_pfUserStatistics != null) {
                    logger.info("推荐获得的奖励之前-----" + _pfUserStatistics.getRecommenGetFee());
                    _pfUserStatistics.setRecommenGetFee(_pfUserStatistics.getRecommenGetFee().add(border.getRecommenAmount()));
                    logger.info("推荐获得的奖励之后-----" + _pfUserStatistics.getRecommenGetFee());
                    int i = userStatisticsService.updateByIdAndVersion(_pfUserStatistics);
                    if (i != 1) {
                        logger.info("更新推荐获得的奖励失败");
                        throw new BusinessException("更新推荐获得的奖励失败");
                    }
                }
            }
        }
    }
    /**
     * 获取订单
     *
     * @author ZhaoLiang
     * @date 2016/3/9 11:07
     */
    public PfBorder getPfBorderById(Long id) {
        return pfBorderMapper.selectByPrimaryKey(id);
    }

    /**
     *  获得推荐单价
     * @param pUserId   border里新上级puserId
     * @param skuId     商品的skuId
     * @return
     */
    public BigDecimal getRewardUnitPrice(Long pUserId, Integer skuId){
        BigDecimal rewardUnitPrice = null;
        try {
            PfUserSku userSku = getUserSkuByUserIdAndSkuId(pUserId,skuId);
            if (userSku!=null){
                rewardUnitPrice = userSku.getRewardUnitPrice();
            }else {
                logger.info("获得奖励单价出错----pfuserSku为null---userId--"+pUserId+"----skuId--"+skuId);
                throw new BusinessException("获得奖励单价出错----pfuserSku为null---userId--"+pUserId+"----skuId--"+skuId);
            }
        }catch (Exception e){
            logger.info("获得奖励单价出错----"+e.getMessage());
            throw new BusinessException("获得奖励单价出错----"+e.getMessage());
        }
        return rewardUnitPrice;
    }


    /**
     * 获取用户产品代理关系
     *
     * @author ZhaoLiang
     * @date 2016/3/8 16:16
     */
    public PfUserSku getUserSkuByUserIdAndSkuId(Long userId, Integer SkuId) throws Exception {
        return pfUserSkuMapper.selectByUserIdAndSkuId(userId, SkuId);
    }
}
