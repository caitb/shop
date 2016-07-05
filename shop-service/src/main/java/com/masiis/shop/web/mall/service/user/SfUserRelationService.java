package com.masiis.shop.web.mall.service.user;

import com.masiis.shop.dao.beans.user.SfSopkenAndFansPageViewPo;
import com.masiis.shop.dao.mall.user.SfUserRelationMapper;
import com.masiis.shop.dao.po.SfUserRelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 小铺分销关系Service
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
     * 通过userId获取粉丝总数量
     * @param userId    用户id
     * @return          Integer
     */
    public Integer getFansNumByUserId(Long userId){
        List<SfUserRelation> sfUserRelations = sfUserRelationMapper.getSfUserRelationByUserId(userId);
        Integer num = 0;
        if (sfUserRelations == null || sfUserRelations.size() == 0){
            return num;
        }else {
            for (SfUserRelation relation : sfUserRelations){
                num += sfUserRelationMapper.selectFansNum(relation.getTreeCode()).get("num");
            }
        }
        return num;
    }

    /**
     * 通过userId和shopId获得粉丝数量
     * @param userId    用户id
     * @param shopId    小铺id
     * @return  Integer
     */
    public Integer getFansNumByUserIdAndShopId(Long userId, Long shopId){
        SfUserRelation sfUserRelation = sfUserRelationMapper.selectSfUserRelationByUserIdAndShopId(userId, shopId);
        return sfUserRelationMapper.selectFansNum(sfUserRelation.getTreeCode()).get("num");
    }

    public List<SfSopkenAndFansPageViewPo> dealWithFansPageView(Long userId, Integer userLevel, Long shopId){

        List<SfSopkenAndFansPageViewPo> pos = sfUserRelationMapper.selectFansPageView(userId, userLevel, shopId);

        return pos;
    }
}
