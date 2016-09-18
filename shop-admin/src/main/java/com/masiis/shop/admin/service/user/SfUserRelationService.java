package com.masiis.shop.admin.service.user;

import com.masiis.shop.dao.mall.user.SfUserRelationMapper;
import com.masiis.shop.dao.po.SfUserRelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cai_tb on 16/3/12.
 */
@Service
public class SfUserRelationService {

    @Resource
    private SfUserRelationMapper sfUserRelationMapper;

    /**
     * 获得唯一分销用户关系
     * @param userId    userId
     * @param shopId    shopId
     * @return  SfUserRelation
     */
    public SfUserRelation getSfUserRelationByUserIdAndShopId(Long userId, Long shopId){
        return sfUserRelationMapper.selectSfUserRelationByUserIdAndShopId(userId, shopId);
    }

    public int updateUserRelation(SfUserRelation userRelation){
        return sfUserRelationMapper.updateByPrimaryKey(userRelation);
    }

}
