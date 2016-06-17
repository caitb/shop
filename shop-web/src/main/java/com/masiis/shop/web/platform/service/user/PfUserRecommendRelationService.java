package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.beans.user.UserRecommend;
import com.masiis.shop.dao.platform.user.PfUserRecommenRelationMapper;
import com.masiis.shop.dao.platform.user.PfUserRelationMapper;
import com.masiis.shop.dao.po.PfUserRecommenRelation;
import com.masiis.shop.dao.po.PfUserRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import sun.rmi.transport.StreamRemoteCall;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户推荐关系表
 *
 * @author muchaofeng
 * @date 2016/6/15 14:14
 */

@Service
public class PfUserRecommendRelationService {
    @Resource
    private PfUserRecommenRelationMapper pfUserRecopmmenRelationMapper;


    public int insert(PfUserRecommenRelation recommenRelation) {
        return pfUserRecopmmenRelationMapper.insert(recommenRelation);
    }

    /**
     * 推荐我的人
     *
     * @author muchaofeng
     * @date 2016/6/15 14:24
     */

    public int findNumByUserId(Long userId) {
        return pfUserRecopmmenRelationMapper.selectNumByUserId(userId);
    }

    /**
     * 我推荐的人
     *
     * @author muchaofeng
     * @date 2016/6/15 14:24
     */
    public int findNumByUserPid(Long userId) {
        return pfUserRecopmmenRelationMapper.selectNumByUserPid(userId);
    }

    /**
     * 推荐给我的详情列表
     *
     * @author muchaofeng
     * @date 2016/6/15 17:42
     */
    public List<UserRecommend> findSumByUserId(Long userId) {
        return pfUserRecopmmenRelationMapper.selectSumByUserId(userId);
    }

    /**
     * 我推荐的详情列表
     *
     * @author muchaofeng
     * @date 2016/6/15 21:02
     */
    public List<UserRecommend> findSumByUserPid(Long userId) {
        return pfUserRecopmmenRelationMapper.selectSumByUserId(userId);
    }

    /**
     * 条件查询我推荐的详情列表
     * @author muchaofeng
     * @date 2016/6/17 10:30
     */

    public List<UserRecommend> findSumByLike(Integer skuId, Long userId, Integer agentLevelIdLong ) {
        return pfUserRecopmmenRelationMapper.selectSumByLike(skuId,userId,agentLevelIdLong);
    }

    /**
     * 获取推荐关系
     *
     * @param userId 被推荐人id
     * @param skuId  商品id
     * @return
     */
    public PfUserRecommenRelation selectRecommenRelationByUserIdAndSkuId(Long userId, Integer skuId) {
        return pfUserRecopmmenRelationMapper.selectRecommenRelationByUserIdAndSkuId(userId, skuId);
    }

    /**
     * 修改树形编码
     *
     * @param id
     * @param treeCode
     * @return
     */
    public int updateTreeCodeById(Integer id, String treeCode) {
        return pfUserRecopmmenRelationMapper.updateTreeCodeById(id, treeCode);
    }
}
