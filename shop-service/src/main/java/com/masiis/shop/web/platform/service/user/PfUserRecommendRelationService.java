package com.masiis.shop.web.platform.service.user;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.recommend.MyRecommendPo;
import com.masiis.shop.dao.beans.statistic.BrandStatistic;
import com.masiis.shop.dao.beans.statistic.RecommendBrandStatistic;
import com.masiis.shop.dao.beans.system.ComSkuSimple;
import com.masiis.shop.dao.beans.user.*;
import com.masiis.shop.dao.platform.user.PfUserBrandMapper;
import com.masiis.shop.dao.platform.user.PfUserRecommenRelationMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.ComAgentLevelService;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.service.statistics.RecommentBrandStatisticService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 用户推荐关系表
 *
 * @author muchaofeng
 * @date 2016/6/15 14:14
 */

@Service
public class PfUserRecommendRelationService {
    @Resource
    private PfUserRecommenRelationMapper pfUserRecopmmenRelationMapper;
    @Resource
    private ComAgentLevelService comAgentLevelService;
    @Resource
    private PfUserSkuService pfUserSkuService;
    @Resource
    private CountGroupService countGroupService;
    @Resource
    private SkuService skuService;
    @Resource
    private PfUserStatisticsService pfUserStatisticsService;
    @Resource
    private UserService userService;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private RecommentBrandStatisticService recommentBrandStatisticService;
    @Resource
    private PfUserBrandMapper pfUserBrandMapper;

    private Logger logger = Logger.getLogger(PfUserRecommendRelationService.class);

    private static final Integer pageSize = 10;

    public int insert(PfUserRecommenRelation recommenRelation) {
        return pfUserRecopmmenRelationMapper.insert(recommenRelation);
    }

    public int update(PfUserRecommenRelation recommenRelation) {
        return pfUserRecopmmenRelationMapper.updateByPrimaryKey(recommenRelation);
    }

    /**
     * 帮我推荐的人
     *
     * @author muchaofeng
     * @date 2016/6/15 14:24
     */

    public int findNumByUserId(Long userId) {
        return pfUserRecopmmenRelationMapper.selectNumByUserId(userId);
    }

    /**
     * 我推荐的人
     *
     * @author muchaofeng
     * @date 2016/6/15 14:24
     */
    public int findNumByUserPid(Long userId) {
        return pfUserRecopmmenRelationMapper.selectNumByUserPid(userId);
    }

    /**
     * 帮我推荐的详情列表
     *
     * @author muchaofeng
     * @date 2016/6/15 17:42
     */
    public List<UserRecommend> findGiveSum(Long userId, Integer skuId) {
        return pfUserRecopmmenRelationMapper.selectGiveSum(userId, skuId);
    }

    /**
     * 帮我推荐的单人单品推荐人数
     *
     * @author muchaofeng
     * @date 2016/6/17 13:57
     */
    public Integer findGiveNum(Long userId, Integer skuId) {
        return pfUserRecopmmenRelationMapper.selectGiveNum(userId, skuId);
    }

    /**
     * 帮我推荐的人id集合
     *
     * @author muchaofeng
     * @date 2016/6/17 15:11
     */

    public List<Long> findGiveList(Long userId, Integer skuId) {
        return pfUserRecopmmenRelationMapper.selectGiveList(userId, skuId);
    }

    /**
     * 帮我推荐的人页面列表查询
     *
     * @param userId  userId
     * @param skuId   skuId
     * @param pageNum 查询页码
     */
    public HelpRecommended findGiveListPaging(Long userId, Integer skuId, Integer pageNum) {
        logger.info("帮我推荐的人页面列表查询");
        logger.info("userId = " + userId);
        logger.info("skuId = " + skuId);
        logger.info("pageNum = " + pageNum);
        Page pageHelp = PageHelper.startPage(pageNum, pageSize);
        List<Long> giveList = pfUserRecopmmenRelationMapper.selectGiveList(userId, skuId);
        if (pageHelp.getPages() > 0) {
            if (pageHelp.getPages() < pageNum.intValue()) {
                throw new BusinessException("1");
            }
        }
        HelpRecommended recommended = new HelpRecommended();
        recommended.setPageSize(pageHelp.getPageSize());
        recommended.setCurrentPage(pageHelp.getPageNum());
        recommended.setTotoalCount(pageHelp.getTotal());
        recommended.setTotalPage(pageHelp.getPages());
        List<HelpRecommedPo> recommedPos = new LinkedList<>();
        HelpRecommedPo recommedPo;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Long id : giveList) {
            recommedPo = new HelpRecommedPo();
            PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(id, skuId);
            ComAgentLevel comAgentLevel = comAgentLevelService.selectByPrimaryKey(pfUserSku.getAgentLevelId());
            ComUser user = userService.getUserById(id);
            recommedPo.setLevelName(comAgentLevel.getName());
            recommedPo.setPhone(user.getMobile());
            recommedPo.setTime(sdf.format(pfUserSku.getCreateTime()));
            recommedPo.setWxNickName(user.getWxNkName());
            recommedPo.setUserName(user.getRealName());
            if (user.getAuditStatus() == 2) {
                recommedPo.setWxId(user.getWxId());
            }
            recommedPos.add(recommedPo);
        }
        recommended.setRecommedPos(recommedPos);
        return recommended;
    }

    /**
     * 条件查询帮我推荐的人详情列表
     *
     * @author muchaofeng
     * @date 2016/6/17 10:30
     */
    public List<UserRecommend> findGiveSumByLike(Integer skuId, Long userId) {
        return pfUserRecopmmenRelationMapper.selectGiveSumByLike(skuId, userId);
    }

    /**
     * 我推荐的详情列表
     *
     * @author muchaofeng
     * @date 2016/6/15 21:02
     */
    public List<UserRecommend> findSumByUserPid(Long userId) {
        return pfUserRecopmmenRelationMapper.selectSumByUserId(userId);
    }

    /**
     * 条件查询我推荐的详情列表
     *
     * @author muchaofeng
     * @date 2016/6/17 10:30
     */

    public List<UserRecommend> findSumByLike(Integer skuId, Long userId, Integer agentLevelIdLong) {
        return pfUserRecopmmenRelationMapper.selectSumByLike(skuId, userId, agentLevelIdLong);
    }

    /**
     * 获取推荐关系
     *
     * @param userId 被推荐人id
     * @param skuId  商品id
     * @return
     */
    public PfUserRecommenRelation selectRecommenRelationByUserIdAndSkuId(Long userId, Integer skuId) {
        return pfUserRecopmmenRelationMapper.selectRecommenRelationByUserIdAndSkuId(userId, skuId);
    }

    /**
     * 修改树形编码
     *
     * @param id
     * @param treeCode
     * @return
     */
    public int updateTreeCodeById(Integer id, String treeCode) {
        return pfUserRecopmmenRelationMapper.updateTreeCodeById(id, treeCode);
    }

    /**
     * 批量修改团队树结构
     *
     * @param treeCode
     * @param parentTreeCode
     * @param idIndex
     * @param treeLevelDiff
     * @return
     */
    public int updateTreeCodes(String treeCode, String parentTreeCode, Integer idIndex, Integer treeLevelDiff) {
        return pfUserRecopmmenRelationMapper.updateTreeCodes(treeCode, parentTreeCode, idIndex, treeLevelDiff);
    }

    /**
     * 我推荐的人 和 帮我推荐的人页面展示Service
     *
     * @param skuId       商品id
     * @param level       推荐级别
     * @param pageNum     查询页码
     * @param myRecommend true：我推荐的人  false：帮我推荐的人
     * @return
     */
    public RecommendedMen recommendedMenPage(Long userId, Integer skuId, Integer level, Integer pageNum, boolean myRecommend) {
        Page page = PageHelper.startPage(pageNum, pageSize);
        logger.info("userId = " + userId);
        List<UserRecommend> userRecommends;
        if (myRecommend) {
            userRecommends = pfUserRecopmmenRelationMapper.selectSumByLike(skuId, userId, level);
        } else {
            userRecommends = pfUserRecopmmenRelationMapper.selectGiveSum(userId, skuId);
        }
        RecommendedMen recommendedMen = new RecommendedMen();
        recommendedMen.setCurrentPage(page.getPageNum());
        recommendedMen.setTotalPage(page.getPages());
        recommendedMen.setTotoalCount(page.getTotal());
        recommendedMen.setPageSize(page.getPageSize());
        if (myRecommend) {
            for (UserRecommend userRecommend : userRecommends) {
                PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userRecommend.getUserId(), userRecommend.getSkuId());
                userRecommend.setCountGroup(countGroupService.countGroupInfo(pfUserSku.getTreeCode()));
            }
        } else {
            for (UserRecommend userRecommend : userRecommends) {
                userRecommend.setNumber(findGiveNum(userRecommend.getUserId(), userRecommend.getSkuId()));
            }
        }
        if (pageNum.intValue() <= page.getPages()) {
            recommendedMen.setUserRecommends(userRecommends);
        }
//        List<PfUserSku> pfUserSkuList = pfUserSkuService.getPfUserSkuInfoByUserId(userId);
        //只显示代理的主商品
        List<PfUserSku> pfUserSkuList = pfUserSkuMapper.selectPrimarySkuByUserId(userId);
        List<Integer> list = new ArrayList<>();
        if (pfUserSkuList == null) {
            throw new BusinessException("代理商品异常，初始化商品列表失败");
        } else {
            if (pageNum.intValue() == 1) {
                List<ComSkuSimple> skuList = new ArrayList();
                ComSkuSimple skuSimple;
                for (PfUserSku pfUserSku : pfUserSkuList) {
                    ComSku comSku = skuService.getSkuById(pfUserSku.getSkuId());
                    list.add(pfUserSku.getAgentLevelId());
                    skuSimple = new ComSkuSimple();
                    skuSimple.setSkuId(comSku.getId());
                    skuSimple.setSkuName(comSku.getName());
                    skuList.add(skuSimple);
                }
                skuSimple = new ComSkuSimple();
                skuSimple.setSkuId(-1);
                skuSimple.setSkuName("全部");
                skuList.add(0, skuSimple);
                recommendedMen.setSkuList(skuList);
            }
        }
        if (pageNum.intValue() == 1) {
            Collections.sort(list);
            //等级信息
            List<ComAgentLevel> agentLevels = comAgentLevelService.selectLastAll(list.get(0));
            ComAgentLevel agentLevel = new ComAgentLevel();
            agentLevel.setId(-1);
            agentLevel.setName("全部");
            agentLevels.add(0, agentLevel);
            recommendedMen.setLevelList(agentLevels);
        }
        return recommendedMen;
    }

    public PfUserRecommenRelation findById(Integer id) {
        return pfUserRecopmmenRelationMapper.selectByPrimaryKey(id);
    }

    /**
     * 我推荐的人首页service
     *
     * @param userId
     * @return
     */
    public MyRecommendPo myRecommen(Long userId, Integer pageNum) {
        logger.info("我推荐的人首页service");
        MyRecommendPo myRecommendPo = new MyRecommendPo();
        PfUserStatistics pfUserStatistics = pfUserStatisticsService.selectFee(userId);
        //我推荐的人数
        int myRecommen = findNumByUserPid(userId);
        //帮我推荐的人数
        int helpRecommen = findNumByUserId(userId);
        //推荐团队总数
        Integer recommenTotalUserNum = 0;
        BigDecimal totalSales = BigDecimal.ZERO;
        List<PfUserBrand> pfUserBrands = pfUserBrandMapper.selectByUserId(userId);
        for (PfUserBrand pfUserBrand : pfUserBrands) {
            RecommendBrandStatistic recommendBrandStatistic = recommentBrandStatisticService.selectRecommentBrandStatisticByUserIdAndBrandId(pfUserBrand.getUserId(), pfUserBrand.getBrandId());
            recommenTotalUserNum += recommendBrandStatistic.getUserNum();
            totalSales = totalSales.add(recommendBrandStatistic.getSellAmount());
        }
        myRecommendPo.setMyRecommedPeople(myRecommen);
        myRecommendPo.setHelpMeRecommendCount(helpRecommen);
        myRecommendPo.setRecommenTeamCount(recommenTotalUserNum);
        myRecommendPo.setTotalSales(totalSales);
        myRecommendPo.setIncomeRewards(pfUserStatistics == null ? BigDecimal.ZERO : (pfUserStatistics.getRecommenGetFee() == null ? BigDecimal.ZERO : pfUserStatistics.getRecommenGetFee()));
        myRecommendPo.setSendRewards(pfUserStatistics == null ? BigDecimal.ZERO : (pfUserStatistics.getRecommenSendFee() == null ? BigDecimal.ZERO : pfUserStatistics.getRecommenSendFee()));
        return myRecommendPo;
    }
}
