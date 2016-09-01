package com.masiis.shop.admin.service.user;

import com.masiis.shop.dao.beans.user.UserRecommend;
import com.masiis.shop.dao.platform.user.PfUserRecommenRelationMapper;
import com.masiis.shop.dao.po.PfUserRecommenRelation;
import org.springframework.stereotype.Service;

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

    public int update(PfUserRecommenRelation recommenRelation) {
        return pfUserRecopmmenRelationMapper.updateByPrimaryKey(recommenRelation);
    }

    /**
     * 帮我推荐的人
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
     * 帮我推荐的详情列表
     *
     * @author muchaofeng
     * @date 2016/6/15 17:42
     */
    public List<UserRecommend> findGiveSum(Long userId, Integer skuId) {
        return pfUserRecopmmenRelationMapper.selectGiveSum(userId, skuId);
    }
    
    /**
     * 帮我推荐的单人单品推荐人数
     *
     * @author muchaofeng
     * @date 2016/6/17 13:57
     */
    public Integer findGiveNum(Long userId, Integer skuId) {
        return pfUserRecopmmenRelationMapper.selectGiveNum(userId, skuId);
    }

    /**
     * 帮我推荐的人id集合
     *
     * @author muchaofeng
     * @date 2016/6/17 15:11
     */

    public List<Long> findGiveList(Long userId, Integer skuId) {
        return pfUserRecopmmenRelationMapper.selectGiveList(userId, skuId);
    }

    /**
     * 条件查询帮我推荐的人详情列表
     *
     * @author muchaofeng
     * @date 2016/6/17 10:30
     */
    public List<UserRecommend> findGiveSumByLike(Integer skuId, Long userId) {
        return pfUserRecopmmenRelationMapper.selectGiveSumByLike(skuId, userId);
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
     *
     * @author muchaofeng
     * @date 2016/6/17 10:30
     */

    public List<UserRecommend> findSumByLike(Integer skuId, Long userId, Integer agentLevelIdLong) {
        return pfUserRecopmmenRelationMapper.selectSumByLike(skuId, userId, agentLevelIdLong);
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

    /**
     * 批量修改团队树结构
     *
     * @param treeCode
     * @param parentTreeCode
     * @param idIndex
     * @param treeLevelDiff
     * @return
     */
    public int updateTreeCodes(String treeCode, String parentTreeCode, Integer idIndex, Integer treeLevelDiff) {
        return pfUserRecopmmenRelationMapper.updateTreeCodes(treeCode, parentTreeCode, idIndex, treeLevelDiff);
    }
}
