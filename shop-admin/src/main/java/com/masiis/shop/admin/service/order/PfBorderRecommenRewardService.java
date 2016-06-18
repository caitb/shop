package com.masiis.shop.admin.service.order;

import com.masiis.shop.dao.platform.order.PfBorderRecommenRewardMapper;
import com.masiis.shop.dao.po.PfBorderRecommenReward;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * BOrderAddService
 *
 * @author ZhaoLiang
 * @date 2016/4/14
 */
@Service
@Transactional
public class PfBorderRecommenRewardService {

    @Resource
    private PfBorderRecommenRewardMapper pfBorderRecommenRewardMapper;

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
}
