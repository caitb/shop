package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.PfUserRecommenRelationMapper;
import com.masiis.shop.dao.platform.user.PfUserRelationMapper;
import com.masiis.shop.dao.po.PfUserRecommenRelation;
import com.masiis.shop.dao.po.PfUserRelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户推荐关系表
 * @author muchaofeng
 * @date 2016/6/15 14:14
 */

@Service
public class PfUserRecommendRelationService {
    @Resource
    private PfUserRecommenRelationMapper pfUserRecopmmenRelationMapper;

    /**
     * 推荐我的人
     * @author muchaofeng
     * @date 2016/6/15 14:24
     */

    public int findNumByUserId(Long userId) {
        return pfUserRecopmmenRelationMapper.selectNumByUserId(userId);
    }

    /**
     * 我推荐的人
     * @author muchaofeng
     * @date 2016/6/15 14:24
     */

    public int findNumByUserPid(Long userId) {
        return pfUserRecopmmenRelationMapper.selectNumByUserPid(userId);
    }

    /**
     * 根据skuId和userId获得推荐信息
     * @param userId
     * @param skuId
     * @return
     */
    public PfUserRecommenRelation selectRecommenRelationByUserIdAndSkuId(Long userId,Integer skuId){
        return pfUserRecopmmenRelationMapper.selectRecommenRelationByUserIdAndSkuId(userId,skuId);
    }
}
