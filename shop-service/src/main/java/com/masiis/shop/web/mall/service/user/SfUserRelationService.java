package com.masiis.shop.web.mall.service.user;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.dao.beans.user.SfSpokenAndFansPageViewPo;
import com.masiis.shop.dao.beans.user.SfSpokesAndFansInfo;
import com.masiis.shop.dao.mall.user.SfUserRelationMapper;
import com.masiis.shop.dao.po.SfUserRelation;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 小铺分销关系Service
 * Created by hzz on 2016/4/9.
 */
@Service
public class SfUserRelationService {
    private static final Logger logger = Logger.getLogger(SfUserRelationService.class);
    @Resource
    private SfUserRelationMapper sfUserRelationMapper;

    public int updateUserRelation(SfUserRelation userRelation){
        return sfUserRelationMapper.updateByPrimaryKey(userRelation);
    }

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
                num += sfUserRelationMapper.selectFansNum(relation.getTreeCode()).get("num").intValue();
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
        return sfUserRelationMapper.selectFansNum(sfUserRelation.getTreeCode()).get("num").intValue();
    }

    /**
     * 查询用户下 一级、二级、三级粉丝数量
     * @param userId    用户id
     * @return  List map
     */
    public List<Map<String, Integer>> getFansNumGroupByLevel(Long userId){
        return sfUserRelationMapper.selectFansNumGroupByLevel(userId);
    }

    /**
     * 查询粉丝列表展示页面信息
     * @param userId    用户id
     * @param userLevel 粉丝级别  可以为null
     * @param shopId    小铺id    可以为null
     * @param isPaging  是否分页  true 分页，false 不分页
     * @param currentPage 当前页
     * @param pageSize  每页条数
     * @return  list
     */
    public SfSpokenAndFansPageViewPo dealWithFansPageView(Long userId, Integer userLevel, Long shopId, boolean isPaging, Integer currentPage, Integer pageSize){
        logger.info("查询粉丝列表展示页面信息");
        logger.info("用户id：" + userId);
        logger.info("粉丝级别：" + userLevel);
        logger.info("小铺id：" + shopId);
        SfSpokenAndFansPageViewPo pageViewPo = new SfSpokenAndFansPageViewPo();
        //查询粉丝总数量
        Integer totalCount = this.getFansNumByUserId(userId);
        logger.info("粉丝总数量："+totalCount);
        //查询三级粉丝数量
        List<Map<String, Integer>> maps = this.getFansNumGroupByLevel(userId);
        for (Map<String, Integer> map : maps){
            switch (map.get("userLevel")) {
                case 1 : {
                    logger.info("一级粉丝数量：" + map.get("num"));
                    pageViewPo.setFirstCount(map.get("num"));
                    break;
                }
                case 2 : {
                    logger.info("二级粉丝数量：" + map.get("num"));
                    pageViewPo.setSecondCount(map.get("num"));
                    break;
                }
                case 3 : {
                    logger.info("三级粉丝数量：" + map.get("num"));
                    pageViewPo.setThirdCount(map.get("num"));
                    break;
                }
            }
        }
        //查询展示列表
        List<SfSpokesAndFansInfo> infos = this.getSfSpokesAndFansInfos(isPaging, currentPage, pageSize, userId, userLevel, shopId);
        pageViewPo.setSfSpokesAndFansInfos(infos);
        return pageViewPo;
    }

    /**
     * 查询获取粉丝列表展示信息
     * @param isPaging      是否分页标识
     * @param currentPage   查询当前页
     * @param pageSize      每页展示条数
     * @param userId        用户ID
     * @param fansLevel     粉丝级别
     * @param shopId        小铺id
     * @return
     */
    public List<SfSpokesAndFansInfo> getSfSpokesAndFansInfos(boolean isPaging, Integer currentPage, Integer pageSize, Long userId, Integer fansLevel, Long shopId){
        if (isPaging){
            PageHelper.startPage(currentPage,pageSize); //分页插件
        }
        return sfUserRelationMapper.selectFansPageView(userId, fansLevel, shopId);
    }
}
