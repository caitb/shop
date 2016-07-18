package com.masiis.shop.web.mall.service.user;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.user.BfSpokesManDetailPo;
import com.masiis.shop.dao.beans.user.SfSpokenAndFansPageViewPo;
import com.masiis.shop.dao.beans.user.SfSpokesAndFansInfo;
import com.masiis.shop.dao.mall.user.SfUserRelationMapper;
import com.masiis.shop.dao.po.SfUserRelation;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
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
     * 根据shopId查询代言人活粉丝数量
     * @param shopId        小铺id
     * @param isSpoken      是否为代言人  true：代言人  false：粉丝
     * @return
     */
    public Integer getFansOrSpokesMansNum(Long shopId, boolean isSpoken){
        Integer num = 0;
        if (isSpoken){
            num = sfUserRelationMapper.selectAllSopkesManCountByShopId(shopId, 1);
        }else {
            num = sfUserRelationMapper.selectAllSopkesManCountByShopId(shopId, null);
        }
        return num;
    }

    /**
     * 通过userId获取粉丝总数量
     * @param userId    用户id
     * @param shopId    小铺id 可以为null
     * @return          Integer
     */
    public Integer getFansNumByUserId(Long userId, Long shopId){
        List<SfUserRelation> sfUserRelations = sfUserRelationMapper.getSfUserRelationByUserId(userId);
        Integer num = 0;
        if (sfUserRelations == null || sfUserRelations.size() == 0){
            return num;
        }else {
            Integer fansNum = 0;
            if (shopId == null){
                for (SfUserRelation relation : sfUserRelations){
                    fansNum = sfUserRelationMapper.selectFansNum(relation.getTreeCode(), relation.getShopId()).get("num").intValue() - 1;
                    if (fansNum > 0){
                        logger.info("粉丝数量：" + num);
                        num += fansNum;
                    }
                }
            }else {
                for (SfUserRelation relation : sfUserRelations){
                    if (shopId.longValue() == relation.getShopId().longValue()){
                        fansNum = sfUserRelationMapper.selectFansNum(relation.getTreeCode(), relation.getShopId()).get("num").intValue() - 1;
                        if (fansNum > 0){
                            num += fansNum;
                        }
                    }
                }
            }
        }
        return num;
    }

    /**
     * 通过userId获取所有分享过的店铺的粉丝数量
     * @param userId    用户id
     * @return  map
     */
    public Map<String, Integer> getFansNumByUserId(Long userId){
        List<SfUserRelation> sfUserRelations = sfUserRelationMapper.getSfUserRelationByUserId(userId);
        Integer num;
        Integer maxNum = 0;
        Map<String, Integer> map = new HashMap<>();
        for (SfUserRelation relation : sfUserRelations){
            num = sfUserRelationMapper.selectFansNum(relation.getTreeCode(), relation.getShopId()).get("num").intValue() - 1;
            if (num < 0){
                num = 0;
            }
            logger.info("treeCode----"+relation.getTreeCode()+"-----shopId-----"+relation.getShopId()+"---num----"+num);
            if (maxNum < num){
                maxNum = num;
            }
            map.put(String.valueOf(relation.getShopId()), num);
        }
        logger.info("maxNum------"+maxNum);
        map.put("maxNum",maxNum);
        return map;
    }
    /**
     * 查询代言人总数
     * @param userId 用户id
     * @param shopId 小铺id  可以为null
     * @return      Integer
     */
    public Integer getSpokesManNumByUserId(Long userId, Long shopId){
        List<SfUserRelation> sfUserRelations = sfUserRelationMapper.getSfUserRelationByUserId(userId);
        Integer num = 0;
        if (sfUserRelations == null || sfUserRelations.size() == 0){
            return num;
        }else {
            if (shopId == null){
                for (SfUserRelation relation : sfUserRelations){
                    num += sfUserRelationMapper.selectSpokesManNum(relation.getTreeCode(), userId, relation.getShopId()).get("num").intValue();
                }
            }else {
                for (SfUserRelation relation : sfUserRelations){
                    if (shopId.longValue() == relation.getShopId().longValue()){
                        num += sfUserRelationMapper.selectSpokesManNum(relation.getTreeCode(), userId, relation.getShopId()).get("num").intValue();
                    }
                }
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
        if (sfUserRelationMapper.selectFansNum(sfUserRelation.getTreeCode(), shopId).get("num").intValue() - 1 < 0){
            return 0;
        }else {
            return sfUserRelationMapper.selectFansNum(sfUserRelation.getTreeCode(), shopId).get("num").intValue() - 1;
        }
    }

    /**
     * 通过userId和shopId获得代言人数量
     * @param userId    用户id
     * @param shopId    小铺id
     * @return
     */
    public Integer getSpokesManNumByUserIdAndShopId(Long userId, Long shopId){
        SfUserRelation sfUserRelation = sfUserRelationMapper.selectSfUserRelationByUserIdAndShopId(userId, shopId);
        return sfUserRelationMapper.selectSpokesManNum(sfUserRelation.getTreeCode(), userId, shopId).get("num").intValue();
    }

    /**
     * 查询用户下 一级、二级、三级粉丝数量
     * @param userId    用户id
     * @param shopId    归宿小铺id(根据业务shopId可以null)
     * @return  List map
     */
    public List<Map<String, Number>> getFansNumGroupByLevel(Long userId, Integer userLevel, Long shopId){
        return sfUserRelationMapper.selectFansNumGroupByLevel(userId, userLevel, shopId);
    }

    /**
     * 查询用户下 一级、二级代言人数量
     * @param userId    用户id

     * @return
     */
    public List<Map<String, Number>> getSpokesManNumGroupByLevel(Long userId, Integer userLevel, Long shopId){
        return sfUserRelationMapper.selectSpokesManNumGroupByLevel(userId, userLevel, shopId);
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
        Integer totalCount = this.getFansNumByUserId(userId, shopId);
        pageViewPo.setTotalCount(totalCount);
        logger.info("粉丝总数量："+totalCount);
        //查询三级粉丝数量
        List<Map<String, Number>> maps = this.getFansNumGroupByLevel(userId, null, shopId);
        for (Map<String, Number> map : maps){
            switch (map.get("userLevel").intValue()) {
                case 1 : {
                    logger.info("一级粉丝数量：" + map.get("num"));
                    pageViewPo.setFirstCount(map.get("num").intValue());
                    break;
                }
                case 2 : {
                    logger.info("二级粉丝数量：" + map.get("num"));
                    pageViewPo.setSecondCount(map.get("num").intValue());
                    break;
                }
                case 3 : {
                    logger.info("三级粉丝数量：" + map.get("num"));
                    pageViewPo.setThirdCount(map.get("num").intValue());
                    break;
                }
            }
        }
        //查询展示列表
        List<SfSpokesAndFansInfo> infos = this.getSfFansInfos(isPaging, currentPage, pageSize, userId, userLevel, shopId);
        pageViewPo.setSfSpokesAndFansInfos(infos);
        return pageViewPo;
    }

    /**
     * 查询获取粉丝列表展示信息（只查询三级粉丝）
     * @param isPaging      是否分页标识   true 分页，false 不分页
     * @param currentPage   查询当前页
     * @param pageSize      每页展示条数
     * @param userId        用户ID
     * @param fansLevel     粉丝级别    可以为null
     * @param shopId        小铺id    可以为null
     * @return
     */
    public List<SfSpokesAndFansInfo> getSfFansInfos(boolean isPaging, Integer currentPage, Integer pageSize, Long userId, Integer fansLevel, Long shopId){
        if (isPaging){
            PageHelper.startPage(currentPage,pageSize); //分页插件
        }
        return sfUserRelationMapper.selectFansPageView(userId, fansLevel, shopId);
    }

    /**
     * 查询获取代言人列表展示信息  (只查询两级代言人)
     * @param isPaging      是否分页标识   true 分页，false 不分页
     * @param currentPage   查询当前页
     * @param pageSize      每页展示条数
     * @param userId        用户ID
     * @param fansLevel     粉丝级别    可以为null
     * @param shopId        小铺id    可以为null
     * @param isSpokesMan   是否为代言人（当为1时查询的是代言人）
     * @return
     */
    public List<SfSpokesAndFansInfo> getSfSpokesManInfos(boolean isPaging, Integer currentPage, Integer pageSize, Long userId, Integer fansLevel, Long shopId, Integer isSpokesMan){
        if (isPaging){
            PageHelper.startPage(currentPage,pageSize); //分页插件
        }
        return sfUserRelationMapper.selectSpokesManPageView(userId, fansLevel, shopId, isSpokesMan);
    }

    /**
     * 查询店铺所有代言人信息
     * @param isPaging      是否分页  true 分页，false 不分页
     * @param currentPage   当前页
     * @param pageSize      每页展示条数
     * @param shopId        小铺id
     * @return
     */
    public List<SfSpokesAndFansInfo> getAllSfSpokesManInfos(boolean isPaging, int currentPage, int pageSize, Long shopId){
        logger.info("查询店铺所有代言人信息");
        logger.info("shopId = " + shopId);
        if (isPaging){
            logger.info("currentPage = " + currentPage);
            logger.info("pageSize = " + pageSize);
            PageHelper.startPage(currentPage,pageSize); //分页插件
        }
        List<SfSpokesAndFansInfo> infos = sfUserRelationMapper.selectAllSpokesManByShopId(shopId,null);
        List<SfSpokesAndFansInfo> list = new LinkedList<>();
        for (SfSpokesAndFansInfo info : infos){
            info.setFansNum(getFansNumByUserIdAndShopId(info.getUserId(), shopId));
            info.setSpokesManNum(getSpokesManNumByUserIdAndShopId(info.getUserId(), shopId));
            list.add(info);
        }
        return list;
    }

    /**
     * 根据shopid查询代言人数量
     * @param shopId    小铺id
     * @return
     */
    public Integer getAllSfSpokesManNum(Long shopId){
        return sfUserRelationMapper.selectAllSopkesManCountByShopId(shopId,null);
    }

    /**
     * 查询代言人列表展示页面信息
     * @param userId        用户id
     * @param userLevel     粉丝级别 可以为null
     * @param shopId        小铺id   可以为null
     * @param isPaging      是否分页  true：分页  false：不分页
     * @param currentPage   当前页
     * @param pageSize      每页展示条数
     * @param isSpokesMan   是否为代言人 1
     * @return
     */
    public SfSpokenAndFansPageViewPo dealWithSpokesManPageView(Long userId, Integer userLevel, Long shopId, boolean isPaging, Integer currentPage, Integer pageSize, Integer isSpokesMan){
        logger.info("查询代言人列表展示页面信息");
        logger.info("用户id：" + userId);
        logger.info("代言人级别：" + userLevel);
        logger.info("小铺id：" + shopId);
        SfSpokenAndFansPageViewPo pageViewPo = new SfSpokenAndFansPageViewPo();
        //查询粉丝总数量
        Integer totalCount = this.getSpokesManNumByUserId(userId, shopId);  //获取总的粉丝数量
        pageViewPo.setTotalCount(totalCount);
        logger.info("代言人总数量："+totalCount);
        //查询三级粉丝数量
        List<Map<String, Number>> maps = this.getSpokesManNumGroupByLevel(userId, null, shopId);
        for (Map<String, Number> map : maps){
            switch (map.get("userLevel").intValue()) {
                case 1 : {
                    logger.info("一级代言人数量：" + map.get("num"));
                    pageViewPo.setFirstCount(map.get("num").intValue());
                    break;
                }
                case 2 : {
                    logger.info("二级代言人数量：" + map.get("num"));
                    pageViewPo.setSecondCount(map.get("num").intValue());
                    break;
                }
                case 3 : {
                    logger.info("三级代言人数量：" + map.get("num"));
                    pageViewPo.setThirdCount(map.get("num").intValue());
                    break;
                }
            }
        }
        //查询展示列表
        List<SfSpokesAndFansInfo> infos = this.getSfSpokesManInfos(isPaging, currentPage, pageSize, userId, userLevel, shopId, isSpokesMan);
        pageViewPo.setSfSpokesAndFansInfos(infos);
        return pageViewPo;
    }

    /**
     * 通过ID查询小铺中的代言人信息
     * @param isPaging  是否分页  true 分页  false 不分页
     * @param currentPage 查询当前页数
     * @param pageSize  每页展示条数
     * @param shopId    小铺id
     * @param ID        代言人ID
     * @return
     */
    public List<SfSpokesAndFansInfo> getShopSpokesManByID(boolean isPaging, Integer currentPage, Integer pageSize, Long shopId, String ID){
        logger.info("通过ID查询小铺中的代言人信息");
        logger.info("shopId = " + shopId);
        logger.info("ID = " + ID);
        if (isPaging){
            PageHelper.startPage(currentPage,pageSize); //分页插件
        }
        return sfUserRelationMapper.selectSpokesManByID(shopId, ID, null);
    }

    /**
     * 通过ID查询小铺中的代言人数量
     * @param shopId    小铺id
     * @param ID        代言人ID
     * @return
     */
    public Integer getSpokesManNumByID(Long shopId, String ID, boolean spokesMan){
        if (spokesMan){
            return sfUserRelationMapper.selectSpokesManNumByID(shopId, ID, 1);
        }else {
            return sfUserRelationMapper.selectSpokesManNumByID(shopId, ID, null);
        }
    }

    /**
     * 查询代言人或粉丝详情
     * @param userId
     * @param shopId
     * @return
     */
    public SfSpokesAndFansInfo getSfSpokesAndFansInfo(Long userId, Long shopId){
        return sfUserRelationMapper.selectSfSpokesAndFansInfo(shopId, userId);
    }

    /**
     * 查询展示代言人详情
     * @param showUserId    展示人id
     * @param shopId        小铺id
     * @return
     */
    public BfSpokesManDetailPo getSpokesManDetail(Long showUserId, Long shopId) throws Exception{
        BfSpokesManDetailPo detailPo = new BfSpokesManDetailPo();
        logger.info("查询店铺三级粉丝");
        logger.info("shopId = " + shopId);
        List<Map<String, Number>> fans = this.getFansNumGroupByLevel(showUserId, null, shopId);
        for (Map<String, Number> map : fans) {
            switch (map.get("userLevel").intValue()) {
                case 1: {
                    logger.info("一级粉丝数量：" + map.get("num"));
                    detailPo.setFirstFansNum(map.get("num").intValue());
                    break;
                }
                case 2: {
                    logger.info("二级粉丝数量：" + map.get("num"));
                    detailPo.setSecondFansNum(map.get("num").intValue());
                    break;
                }
                case 3: {
                    logger.info("三级粉丝数量：" + map.get("num"));
                    detailPo.setThirdFansNum(map.get("num").intValue());
                    break;
                }
            }
        }
        List<Map<String, Number>> spokesMan = this.getSpokesManNumGroupByLevel(showUserId, null, shopId);
        for (Map<String, Number> map : spokesMan){
            switch (map.get("userLevel").intValue()) {
                case 1 : {
                    logger.info("一级代言人数量：" + map.get("num"));
                    detailPo.setFirstSpokesManNum(map.get("num").intValue());
                    break;
                }
                case 2 : {
                    logger.info("二级代言人数量：" + map.get("num"));
                    detailPo.setSecondSpokesManNum(map.get("num").intValue());
                    break;
                }
            }
        }
        SfSpokesAndFansInfo info = this.getSfSpokesAndFansInfo(showUserId,shopId);
        Integer fansCount = this.getFansNumByUserIdAndShopId(showUserId,shopId);
        logger.info("粉丝总数量："+fansCount);
        Integer spokesManCount = this.getSpokesManNumByUserIdAndShopId(showUserId,shopId);
        logger.info("代言人总数量："+spokesManCount);
        info.setFansNum(fansCount);
        info.setSpokesManNum(spokesManCount);
        if (info == null){
            throw new BusinessException("代言人信息不存在");
        }
        detailPo.setInfo(info);
        return detailPo;
    }

    /**
     * 查询某个店铺所有粉丝数量(排除店主自己)
     *
     * @param shopId    店铺id
     * @param userId
     * @return
     */
    public Integer getFansNumsByShopId(Long shopId, Long userId){
        return sfUserRelationMapper.selectFansNumsByShopId(shopId, userId);
    }

    /**
     * 查询某个店铺所有代言人数量(排除店主自己)
     *
     * @param shopId
     * @param userId
     * @return
     */
    public Integer getSfSpokesManNumByShopId(Long shopId, Long userId) {
        return sfUserRelationMapper.selectSfSpokesManNumByShopId(shopId, userId);
    }

    /**
     * 根据店铺id查找所有粉丝关系(排除店主)
     *
     * @param shopId
     * @param userId
     * @return
     */
    public List<SfUserRelation> findAllFansRelationByShopId(Long shopId, Long userId){
        return sfUserRelationMapper.selectAllFansRelationByShopId(shopId, userId);
    }

    /**
     * 根据店铺id查找所有代言人关系(排除店主)
     *
     * @param shopId
     * @param userId
     * @return
     */
    public List<SfUserRelation> findAllSpokeManRelationByShopId(Long shopId, Long userId) {
        return sfUserRelationMapper.selectAllSpokeManRelationByShopId(shopId, userId);
    }
}
