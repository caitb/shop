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
     * 获取有效的代理绑定关系
     *
     * @param userId
     */
    public PfUserRelation selectEnableByUserId(Long userId, Integer skuId) {
        return pfUserRelationMapper.selectEnableByUserId(userId, skuId);
    }

}
