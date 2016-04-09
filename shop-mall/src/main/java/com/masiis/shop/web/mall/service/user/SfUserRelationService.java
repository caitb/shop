package com.masiis.shop.web.mall.service.user;

import com.masiis.shop.dao.mall.user.SfUserRelationMapper;
import com.masiis.shop.dao.po.SfUserRelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/4/9.
 */
@Service
public class SfUserRelationService {

    @Resource
    private SfUserRelationMapper sfUserRelationMapper;

    /**
     * 根据userId获得分销账户关系
     * @author hanzengzhi
     * @date 2016/4/9 15:45
     */
    public SfUserRelation getSfUserRelationByUserId(Long userId){
        return sfUserRelationMapper.getSfUserRelationByUserId(userId);
    }
}
