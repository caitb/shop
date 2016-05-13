package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.PfUserRelationMapper;
import com.masiis.shop.dao.po.PfUserRelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * PfUserRelationService
 *
 * @author ZhaoLiang
 * @date 2016/4/18
 */
@Service
public class PfUserRelationService {
    @Resource
    private PfUserRelationMapper pfUserRelationMapper;

    public void insert(PfUserRelation pfUserRelation) {
        pfUserRelationMapper.insert(pfUserRelation);
    }

    /**
     * 获取用户上级代理关系
     * @param userId
     * @param skuId
     * @return
     */
    public Long getPUserId(Long userId, Integer skuId) {
        PfUserRelation pfUserRelation = pfUserRelationMapper.selectEnableByUserId(userId, skuId);
        if (pfUserRelation == null) {
            return 0l;
        } else {
            return pfUserRelation.getUserPid();
        }
    }

    /**
     * 获取当前用户的代理关系
     * @param userId
     * @param skuId
     */
    public PfUserRelation getRelation(Long userId, Integer skuId) {
        return pfUserRelationMapper.selectEnableByUserId(userId, skuId);
    }
}
