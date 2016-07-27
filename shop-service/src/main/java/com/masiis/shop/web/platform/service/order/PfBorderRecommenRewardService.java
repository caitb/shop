package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.dao.platform.order.PfBorderRecommenRewardMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    private PfBorderRecommenRewardMapper pfBorderRecommenRewardMapper;

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
}
