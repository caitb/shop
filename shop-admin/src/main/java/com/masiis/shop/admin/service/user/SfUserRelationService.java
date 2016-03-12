package com.masiis.shop.admin.service.user;

import com.masiis.shop.dao.platform.user.SfUserRelationMapper;
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
     * 根据用户id查找记录
     * @param userId
     * @return
     */
    public SfUserRelation findByUserId(Long userId){
        return sfUserRelationMapper.selectByUserId(userId);
    }

}
