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
    public List<SfUserRelation> getSfUserRelationByUserId(Long userId){
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

    public List<SfUserRelation> threeDistributionList(Long userPid){
        return sfUserRelationMapper.getThreeDistributionList(userPid);
    }

    /**
     * 通过userId获取粉丝数量
     * @param userId    用户id
     * @return          Integer
     */
    public Integer getFansNum(Long userId){
        List<SfUserRelation> sfUserRelations = sfUserRelationMapper.getSfUserRelationByUserId(userId);
        Integer num = 0;
        if (sfUserRelations == null || sfUserRelations.size() == 0){
            return num;
        }else {
            for (SfUserRelation relation : sfUserRelations){
                num += sfUserRelationMapper.selectFansNum(relation.getTreeCode());
            }
        }
        return num;
    }
}
