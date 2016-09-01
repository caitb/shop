package com.masiis.shop.web.api.service;

import com.masiis.shop.common.util.CCPRestSmsSDK;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.platform.product.*;
import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by cai_tb on 16/3/16.
 */
@Service
public class TeamService {

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
    private ComUserAccountMapper comUserAccountMapper;


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
            agentSkuMap.put("manage", pus.getAgentLevelId().equals(skuAgentLevels) ? "no" : "yes");//是否可管理

//            Map<String, String> curMap = countChild(pus.getId()); //下级userSkuId和userId数量
//            Integer countChild = StringUtils.isEmpty(curMap.get("childIds").toString())?0:curMap.get("childIds").split(",").length;
//            Double countSales = comUserAccountMapper.sumIncomeFeeByUserIds(curMap.get("userIds"));
//            agentSkuMap.put("countChild", countChild);      //团队人数(不包括自己)
//            agentSkuMap.put("countSales", countSales==null?0:countSales);

            List<Long> teamUserIds = pfUserSkuMapper.selectAllTeamMember(pus.getTreeCode() + "%");
            BigDecimal totalIncomeFee = comUserAccountMapper.totalIncomeFeeByUserIds(teamUserIds);
            agentSkuMap.put("countTeamMember", teamUserIds.size());
            agentSkuMap.put("totalIncomeFee", totalIncomeFee);

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
    public Map<String, Object> teamDetail(Integer userSkuId) {
        PfUserSku pfUserSku = pfUserSkuMapper.selectByPrimaryKey(userSkuId);
        List<Long> userIds = pfUserSkuMapper.selectChildrenByPId(pfUserSku.getId());
        List<ComUser> comUsers = new ArrayList<>();
        if (userIds != null && userIds.size() > 0) {
            comUsers = comUserMapper.selectByIds(userIds);
        }

        /* 下级合伙人id */
        List<Long> teamUserIds = pfUserSkuMapper.selectAllTeamMember(pfUserSku.getTreeCode() + "%");
        /* 团队总销售额 */
        BigDecimal totalIncomeFee = comUserAccountMapper.totalIncomeFeeByUserIds(teamUserIds);
        Map<String, Object> teamMap = new HashMap<>();
        /* 直接下级人数 */
        teamMap.put("countDirectly", userIds.size());
        /* 间接下级人数 */
        teamMap.put("countIndirect", teamUserIds.size() - userIds.size());
        /* 总销售额 */
        teamMap.put("totalIncomeFee", totalIncomeFee);


        List<Map<String, Object>> userAgentMaps = new ArrayList<>();
        for (ComUser comUser : comUsers) {
            PfUserSku userSku = pfUserSkuMapper.selectByUserIdAndSkuId(comUser.getId(), pfUserSku.getSkuId());
            ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(userSku.getAgentLevelId());

            Map<String, Object> userAgentMap = new HashMap<>();
            userAgentMap.put("userId", comUser.getId());
            userAgentMap.put("userName", comUser.getRealName());
            userAgentMap.put("agentLevelName", comAgentLevel.getName());
            userAgentMap.put("code", userSku.getCode());

            userAgentMaps.add(userAgentMap);
        }

        teamMap.put("userAgentMaps", userAgentMaps);


        return teamMap;
    }

    /**
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
        Map<String, String> curMap = countChild(pfUserSku.getId());
        Integer countChild = StringUtils.isEmpty(curMap.get("childIds").toString()) ? 0 : curMap.get("childIds").split(",").length;

        Map<String, Object> memberMap = new HashMap<>();
        memberMap.put("stock", statisticsBuy.get("stock").intValue());
        memberMap.put("totalAmount", statisticsBuy.get("totalAmount").doubleValue());
        memberMap.put("countChild", countChild);
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
}
