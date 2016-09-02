package com.masiis.shop.web.platform.service.user;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.family.MyTeamListHomePo;
import com.masiis.shop.dao.beans.family.TeamListPo;
import com.masiis.shop.dao.beans.family.TeamListPoPaging;
import com.masiis.shop.dao.beans.family.TeamMemberInfo;
import com.masiis.shop.dao.beans.statistic.BrandStatistic;
import com.masiis.shop.dao.beans.user.CountGroup;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.product.*;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.platform.user.PfUserUpgradeNoticeMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.statistics.BrandStatisticService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by cai_tb on 16/3/16.
 */
@Service
public class MyTeamService {

    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private ComSpuMapper comSpuMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;
    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;
    @Resource
    private ComBrandMapper comBrandMapper;
    @Resource
    private PfBorderMapper pfBorderMapper;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private CountGroupService countGroupService;
    @Resource
    private PfUserUpgradeNoticeMapper pfUserUpgradeNoticeMapper;
    @Resource
    private BrandStatisticService brandStatisticService;


    private static final Logger logger = Logger.getLogger(MyTeamService.class);

    private static final Integer pageSize = 10;

    /**
     * 获取用户代理的所有产品
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> listAgentSku(Long userId) {
        PfUserSku pfUserSku = new PfUserSku();
        pfUserSku.setUserId(userId);

        List<PfUserSku> pfUserSkus = pfUserSkuMapper.selectByCondition(pfUserSku);

        List<Map<String, Object>> agentSkuMaps = new ArrayList<>();
        for (PfUserSku pus : pfUserSkus) {
            ComSku comSku = comSkuMapper.selectById(pus.getSkuId());
            ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
            ComBrand comBrand = comBrandMapper.selectById(comSpu.getBrandId());
            /* 商品代理等级数 */
            Integer skuAgentLevels = pfSkuAgentMapper.countSkuAgentLevel(pus.getSkuId());

            Map<String, Object> agentSkuMap = new HashMap<>();
            agentSkuMap.put("userSkuId", pus.getId());
            agentSkuMap.put("skuId", comSku.getId());
            agentSkuMap.put("skuName", comSku.getName());
            agentSkuMap.put("brandLogo", comBrand.getLogoUrl());
            agentSkuMap.put("isLastLevel", pus.getAgentLevelId() == skuAgentLevels ? "yes" : "no");//是否代理最后一级

            //Map<String, String> curMap = countChild(pus.getId()); //下级userSkuId和userId数量
//            Integer countChild = StringUtils.isEmpty(curMap.get("childIds").toString())?0:curMap.get("childIds").split(",").length;
//            Double countSales = comUserAccountMapper.sumIncomeFeeByUserIds(curMap.get("userIds"));

            CountGroup countGroup = countGroupService.countGroupInfo(pus.getTreeCode());
            agentSkuMap.put("countChild", countGroup.getCount());      //团队人数(不包括自己)
            agentSkuMap.put("countSales", countGroup.getGroupMoney());

            agentSkuMaps.add(agentSkuMap);
        }

        return agentSkuMaps;
    }

    /**
     * 统计团队人数
     *
     * @param userSkuId
     * @return
     */
    public Map<String, String> countChild(Integer userSkuId) {
        String curPIds = userSkuId.toString();
//        String curPIds = ""+userSkuId+"";
        String curUserIds = "";
        StringBuilder childIds = new StringBuilder(4000);
        StringBuilder userIds = new StringBuilder(4000);


        while (curPIds != null) {
            Map<String, String> curMap = pfUserSkuMapper.countChild(curPIds);
            if (curMap == null) curMap = new HashMap<>();
            curPIds = curMap.get("sPIds");
            curUserIds = curMap.get("sUserIds");

            if (curPIds != null) childIds.append("," + curPIds);
            if (curUserIds != null) userIds.append("," + curUserIds);
        }

        if (childIds.length() > 0) childIds.deleteCharAt(0);
        if (userIds.length() > 0) userIds.deleteCharAt(0);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("childIds", childIds.toString());
        resultMap.put("userIds", userIds.toString());

        return resultMap;
    }

    /**
     * 获取团队列表
     *
     * @param userSkuId
     * @return
     */
    public Map<String, Object> findTeam(Integer userSkuId) {
        PfUserSku pfUserSku = pfUserSkuMapper.selectByPrimaryKey(userSkuId);
        List<Long> userIds = pfUserSkuMapper.selectChildrenByPId(pfUserSku.getId());
        List<ComUser> comUsers = new ArrayList<>();
        if (userIds != null && userIds.size() > 0) {
            comUsers = comUserMapper.selectByIds(userIds);
        }
        ComSku comSku = comSkuMapper.selectById(pfUserSku.getSkuId());

        //下级userSkuId和userId数量
        Map<String, String> curMap = countChild(pfUserSku.getId());
//        //下级人数
//        Integer countChild = StringUtils.isEmpty(curMap.get("childIds").toString())?0:curMap.get("childIds").split(",").length;
//        //销售额
//        Double countSales = comUserAccountMapper.sumIncomeFeeByUserIds(curMap.get("userIds"));
//        ComUserAccount comUserAccount = comUserAccountMapper.findByUserId(pfUserSku.getUserId());

        Map<String, Object> teamMap = new HashMap<>();
        CountGroup countGroup = countGroupService.countGroupInfo(pfUserSku.getTreeCode());
        teamMap.put("skuName", comSku.getName());//商品名称
        teamMap.put("totalChildren", userIds.size());//直接下级人数
        teamMap.put("countChild", countGroup.getCount() - 1 - userIds.size());//间接下级人数
        teamMap.put("countSales", countGroup.getGroupMoney());//总销售额

        List<Map<String, Object>> userAgentMaps = new ArrayList<>();
        for (ComUser comUser : comUsers) {
            PfUserSku userSku = pfUserSkuMapper.selectByUserIdAndSkuId(comUser.getId(), pfUserSku.getSkuId());
            ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(userSku.getAgentLevelId());

            Map<String, Object> userAgentMap = new HashMap<>();
            userAgentMap.put("userId", comUser.getId());
            userAgentMap.put("userName", comUser.getRealName());
            userAgentMap.put("agentLevelName", comAgentLevel.getName());
            userAgentMap.put("code", userSku.getCode());
            userAgentMap.put("levelImgUrl", PropertiesUtils.getStringValue("agent_level_product_icon_url") + comAgentLevel.getImgUrl());
            userAgentMap.put("imgUrl", comUser.getWxHeadImg());
            userAgentMaps.add(userAgentMap);
        }

        teamMap.put("userAgentMaps", userAgentMaps);


        return teamMap;
    }

    /**
     * 团队列表首页
     *
     * @param pfUserSkuId id
     * @return
     */
    public MyTeamListHomePo teamListHome(Integer pfUserSkuId) throws Exception {
        logger.info("团队成员列表首页");
        logger.info("id = " + pfUserSkuId);
        PfUserSku pfUserSku = pfUserSkuMapper.selectByPrimaryKey(pfUserSkuId);
        if (pfUserSku == null) {
            throw new BusinessException("代理信息不存在");
        }
        ComSku comSku = comSkuMapper.selectById(pfUserSku.getSkuId());
        if (comSku == null) {
            throw new BusinessException("商品信息不存在");
        }
        ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
        //查询不包含自己的代理商品团队人数
        BrandStatistic brandStatistic = brandStatisticService.selectBrandStatisticByUserIdAndBrandId(pfUserSku.getUserId(), comSpu.getBrandId());
        //直接下级人数
        Integer downUserNum = brandStatisticService.selectDownUserNumByUserIdAndBrandId(pfUserSku.getUserId(), comSpu.getBrandId());
        //获得间接下级人数
        Integer indirectUserNum = brandStatistic.getUserNum() - downUserNum - 1;
        MyTeamListHomePo myTeamListHomePo = new MyTeamListHomePo();
        myTeamListHomePo.setSkuName(comSku.getName());
        myTeamListHomePo.setSkuId(comSku.getId());
        myTeamListHomePo.setDirectNum(downUserNum);
        myTeamListHomePo.setIndirectNum(indirectUserNum);
        myTeamListHomePo.setBrandStatistic(brandStatistic);
        return myTeamListHomePo;
    }

    /**
     * 团队列表查询
     *
     * @param isPaging    是否分页 true：分页  false：不分页
     * @param pfUserSkuId id
     * @param pageNum     查询页码
     * @return
     */
    public TeamListPoPaging teamList(boolean isPaging, Integer pfUserSkuId, Integer pageNum) throws Exception {
        logger.info("团队列表查询");
        logger.info("id = " + pfUserSkuId);
        logger.info("是否分页：" + isPaging);
        logger.info("pageNum = " + pageNum);
        TeamListPoPaging teamListPoPaging = new TeamListPoPaging();
        PfUserSku pfUserSku = pfUserSkuMapper.selectByPrimaryKey(pfUserSkuId);
        if (pfUserSku == null) {
            throw new BusinessException("代理信息不存在");
        }
        List<Map<String, Object>> maps;
        if (isPaging) {
            if (pageNum.intValue() < 1 || pageNum == null) {
                throw new BusinessException("页码不正确");
            }
            Page page = PageHelper.startPage(pageNum, pageSize, false);
            maps = pfUserSkuMapper.selectDirectListByuserId(pfUserSku.getId());
            teamListPoPaging.setPageNum(page.getPageNum());
            teamListPoPaging.setPageSize(page.getPageSize());
            teamListPoPaging.setTotalNum(page.getTotal());
            teamListPoPaging.setTotalPages(page.getPages());
        } else {
            maps = pfUserSkuMapper.selectDirectListByuserId(pfUserSku.getId());
        }
        List<TeamListPo> teamListPos = new LinkedList<>();
        TeamListPo teamListPo;
        String url = PropertiesUtils.getStringValue("agent_level_product_icon_url");
        for (Map<String, Object> map : maps) {
            teamListPo = new TeamListPo();
            teamListPo.setUserId(Long.valueOf(map.get("userId").toString()));
            teamListPo.setUserName(map.get("userName").toString());
            teamListPo.setWxId(map.get("wxId") == null ? "" : map.get("wxId").toString());
            teamListPo.setCode(map.get("code") == null ? "" : map.get("code").toString());
            teamListPo.setAgentLevel(Integer.valueOf(map.get("agentLevel").toString()));
            teamListPo.setAgentLevelName(map.get("agentLevelName").toString());
            teamListPo.setAgentLevelImg(url + map.get("agentLevelImg").toString());
            teamListPo.setWxHeadImg(map.get("wxHeadImg") == null ? "" : map.get("wxHeadImg").toString());
            teamListPo.setSkuId(Integer.valueOf(map.get("skuId").toString()));
            teamListPo.setUserSkuId(Integer.valueOf(map.get("userSkuId").toString()));
            teamListPos.add(teamListPo);
        }
        teamListPoPaging.setTeamListPos(teamListPos);
        return teamListPoPaging;
    }

    /**
     * 查看队员信息
     *
     * @param userSkuId
     * @return
     */
    public TeamMemberInfo getMemberInfo(Integer userSkuId) {
        logger.info("pfUserSkuId = " + userSkuId);
        TeamMemberInfo teamMemberInfo = pfUserSkuMapper.selectMemberInfo(userSkuId);
        Map<String, Number> statisticsBuy = pfBorderMapper.statisticsBuy(teamMemberInfo.getUserId(), teamMemberInfo.getUserPid(), teamMemberInfo.getSkuId());
        teamMemberInfo.setPurchaseTimes(statisticsBuy.get("stock").intValue());
        teamMemberInfo.setPurchaseAmount(BigDecimal.valueOf(statisticsBuy.get("totalAmount").doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
        teamMemberInfo.setCertImg(PropertiesUtils.getStringValue("index_user_certificate_url") + teamMemberInfo.getCertImg());
        //查询不包含自己的代理商品团队人数
        Integer totalNum = pfUserSkuMapper.selectTeamCountById(userSkuId, teamMemberInfo.getUserId());
        teamMemberInfo.setLowPartner(totalNum);
        return teamMemberInfo;
    }

    /**
     * 查看队员信息
     *
     * @param code
     * @return
     */
    public Map<String, Object> viewMember(String code) {
        PfUserCertificate pfUserCertificate = pfUserCertificateMapper.selectByCode(code);
        if (pfUserCertificate == null) {//合伙人未填写证书申请
            return null;
        }

        ComUser comUser = comUserMapper.selectByPrimaryKey(pfUserCertificate.getUserId());
        ComSku comSku = comSkuMapper.selectById(pfUserCertificate.getSkuId());
        PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(comUser.getId(), comSku.getId());
        Map<String, Number> statisticsBuy = pfBorderMapper.statisticsBuy(pfUserCertificate.getUserId(), pfUserSku.getUserPid(), pfUserSku.getSkuId());
        ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(pfUserCertificate.getAgentLevelId());
        //Map<String, String> curMap = countChild(pfUserSku.getId());
        //Integer countChild = StringUtils.isEmpty(curMap.get("childIds").toString())?0:curMap.get("childIds").split(",").length;
        CountGroup countGroup = countGroupService.countGroupInfo(pfUserSku.getTreeCode());

        Map<String, Object> memberMap = new HashMap<>();
        memberMap.put("userId", comUser.getId());
        memberMap.put("stock", statisticsBuy.get("stock").intValue());
        memberMap.put("totalAmount", statisticsBuy.get("totalAmount").doubleValue());
        memberMap.put("countChild", countGroup.getCount() - 1);
        memberMap.put("comUserId", comUser.getId());
        memberMap.put("comUserName", comUser.getRealName());
        memberMap.put("mobile", comUser.getMobile());
        memberMap.put("auditStatus", comUser.getAuditStatus());
        memberMap.put("idCardImg", comUser.getIdCardFrontUrl());
        //memberMap.put("idCardFrontImg", PropertiesUtils.getStringValue("index_user_idCard_url") + comUser.getIdCardFrontUrl());
        //memberMap.put("idCardBackImg", PropertiesUtils.getStringValue("index_user_idCard_url") + comUser.getIdCardBackUrl());
        memberMap.put("weixin", pfUserCertificate.getWxId());
        memberMap.put("idCard", pfUserCertificate.getIdCard());
        memberMap.put("frontImg", comUser.getIdCardFrontUrl());
        memberMap.put("backImg", comUser.getIdCardBackUrl());
        memberMap.put("skuId", comSku.getId());
        memberMap.put("skuName", comSku.getName());
        memberMap.put("agentLevelId", comAgentLevel.getId());
        memberMap.put("agentLevelName", comAgentLevel.getName());
        memberMap.put("status", pfUserCertificate.getStatus());
        memberMap.put("certificateImg", PropertiesUtils.getStringValue("index_user_certificate_url") + pfUserCertificate.getImgUrl());
        memberMap.put("applyTime", pfUserCertificate.getBeginTime());
        memberMap.put("pid", pfUserSku.getPid());

        return memberMap;
    }

    /**
     * 查看队员升级记录
     *
     * @param userId
     * @param skuId
     * @return
     */
    public List<Map<String, Object>> upgradeRecord(Long userId, Integer skuId) {
        List<Map<String, Object>> upgradeRecords = pfUserUpgradeNoticeMapper.selectUpgradeRecordByUserIdAndSkuId(userId, skuId);
        return upgradeRecords;
    }
}
