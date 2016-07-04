package com.masiis.shop.web.mall.service.user;

import com.masiis.shop.dao.mall.user.SfUserRelationMapper;
import com.masiis.shop.dao.po.SfUserRelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 获得唯一分销用户关系
     * @param userId    userId
     * @param shopId    shopId
     * @return  SfUserRelation
     */
    public SfUserRelation getSfUserRelationByUserIdAndShopId(Long userId, Long shopId){
        return sfUserRelationMapper.selectSfUserRelationByUserIdAndShopId(userId, shopId);
    }

    /**
     * 根据userPid获得分销账户关系
     * @author hanzengzhi
     * @date 2016/4/12 11:46
     */
    public SfUserRelation getSfUserRelationByUserPid(Long userPid){
        return sfUserRelationMapper.getSfUserRelationByUserPid(userPid);
    }

    public List<SfUserRelation> threeDistributionList(Long userPid){
        return sfUserRelationMapper.getThreeDistributionList(userPid);
    }


}
