package com.masiis.shop.web.platform.service.order;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.user.CountGroup;
import com.masiis.shop.dao.beans.recommend.MyRecommendPo;
import com.masiis.shop.dao.beans.recommend.RecommenOrder;
import com.masiis.shop.dao.platform.order.PfBorderRecommenRewardMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.CountGroupMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.user.PfUserRecommendRelationService;
import com.masiis.shop.web.platform.service.user.PfUserSkuService;
import com.masiis.shop.web.platform.service.user.PfUserStatisticsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * BOrderAddService
 *
 * @author ZhaoLiang
 * @date 2016/4/14
 */
@Service
@Transactional
public class PfBorderRecommenRewardService {

    private Logger log = Logger.getLogger(PfBorderRecommenRewardService.class);

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private CountGroupMapper countGroupMapper;
    @Resource
    private PfUserStatisticsService pfUserStatisticsService;
    @Resource
    private PfUserSkuService pfUserSkuService;
    @Resource
    private PfBorderRecommenRewardMapper pfBorderRecommenRewardMapper;
    @Resource
    private PfUserRecommendRelationService pfUserRecommendRelationService;

    private static final Integer pageSize = 10;

    /**
     * 获取推荐人
     * @param pfBorderItemId
     * @return
     */
    public ComUser getRecommenRewardUser(Long pfBorderItemId){
        PfBorderRecommenReward pfBorderRecommenReward = getByPfBorderItemId(pfBorderItemId);
        ComUser recommenRewardUser =null;
        if(pfBorderRecommenReward!=null){
            log.info("推荐人id-----------------"+pfBorderRecommenReward.getRecommenUserId());
             recommenRewardUser =  comUserMapper.selectByPrimaryKey(pfBorderRecommenReward.getRecommenUserId());
        }
        return recommenRewardUser;
    }

    public PfBorderRecommenReward getByPfBorderItemId(Long pfBorderItemId) {
        return pfBorderRecommenRewardMapper.selectByBorderItemId(pfBorderItemId);
    }

    public int insert(PfBorderRecommenReward po) {
        return pfBorderRecommenRewardMapper.insert(po);
    }

    /**
     * 获得奖励订单
     *
     * @author muchaofeng
     * @date 2016/6/15 15:42
     */

    public Integer findBorders(Long userId) throws Exception {
        return pfBorderRecommenRewardMapper.selectBorders(userId);
    }

    /**
     * 发出奖励订单
     *
     * @author muchaofeng
     * @date 2016/6/15 15:42
     */

    public Integer findPBorders(Long userId) throws Exception {
        return pfBorderRecommenRewardMapper.selectPBorders(userId);
    }

    public PfBorderRecommenReward getRewardByOrderIdAndOrderItemIdAndSkuId(Long orderId,Long orderItemId,Integer skuId){
        return pfBorderRecommenRewardMapper.getRewardByOrderIdAndOrderItemIdAndSkuId(orderId,orderItemId,skuId);
    }

    public List<PfBorderRecommenReward> selectByPfBorderId(Long orderId){
        return pfBorderRecommenRewardMapper.selectByPfBorderId(orderId);
    }


    /**
     * 查询订单列表
     * @param userId        用户id
     * @param pageNum      当前页码
     * @param tab           查询tab  0：收入奖励订单  1：发出奖励订单
     * @return
     */
    public MyRecommendPo getRecommenRewardOrder(Long userId, Integer pageNum, Integer tab) throws Exception{
        log.info("查询订单列表....................");
        log.info("pageNum = " + pageNum);
        log.info("tab = " + tab);
        log.info("userId = " + userId);
        MyRecommendPo myRecommendPo = new MyRecommendPo();
        Page pageHelp = PageHelper.startPage(pageNum, pageSize);
        List<RecommenOrder> recommenOrders = null;
        switch (tab.intValue()){
            case 0 : {
                //获得奖励订单
                recommenOrders = pfBorderRecommenRewardMapper.selectIncomeRecommenOrder(userId);
                break;
            }
            case 1 : {
                //发出奖励订单
                recommenOrders = pfBorderRecommenRewardMapper.selectSendRecommenOrder(userId);
                break;
            }
        }
        log.info("总数量：" + pageHelp.getTotal());
        log.info("总页数：" + pageHelp.getPages());
        log.info("当前页：" + pageHelp.getPageNum());
        log.info("每页展示条数：" + pageHelp.getPageSize());
        if (pageHelp.getPages() > 0){
            if (pageHelp.getPages() < pageNum.intValue()){
                throw new BusinessException("1");
            }
        }
        myRecommendPo.setTotalCount(pageHelp.getTotal());
        myRecommendPo.setTotalPages(pageHelp.getPages());
        myRecommendPo.setCurrentPage(pageHelp.getPageNum());
        myRecommendPo.setRecommenOrders(recommenOrders);
        return myRecommendPo;
    }
}
