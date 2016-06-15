package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.beans.user.UserRecommend;
import com.masiis.shop.dao.platform.user.PfUserRecommenRelationMapper;
import com.masiis.shop.dao.platform.user.PfUserRelationMapper;
import com.masiis.shop.dao.po.PfUserRelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
     * 推荐给我的详情列表
     * @author muchaofeng
     * @date 2016/6/15 17:42
     */
    public List<UserRecommend> findSumByUserId(Long userId) {
        return pfUserRecopmmenRelationMapper.selectSumByUserId(userId);
    }

    /**
     * 我推荐的详情列表
     * @author muchaofeng
     * @date 2016/6/15 21:02
     */
    public List<UserRecommend> findSumByUserPid(Long userId) {
        return pfUserRecopmmenRelationMapper.selectSumByUserId(userId);
    }
}
