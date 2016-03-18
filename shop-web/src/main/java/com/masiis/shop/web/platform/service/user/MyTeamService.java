package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.ComBrandMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    /**
     * 获取用户代理的所有产品
     * @param userId
     * @return
     */
    public List<Map<String, Object>> listAgentSku(Long userId){
        PfUserSku pfUserSku = new PfUserSku();
        pfUserSku.setUserId(userId);

        List<PfUserSku> pfUserSkus = pfUserSkuMapper.selectByCondition(pfUserSku);

        List<Map<String, Object>> agentSkuMaps = new ArrayList<>();
        for(PfUserSku pus : pfUserSkus){
            ComSku comSku = comSkuMapper.selectById(pus.getSkuId());
            ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
            ComBrand comBrand = comBrandMapper.selectById(comSpu.getBrandId());

            Map<String, Object> agentSkuMap = new HashMap<>();
            agentSkuMap.put("userSkuId", pus.getId());
            agentSkuMap.put("skuId", comSku.getId());
            agentSkuMap.put("skuName", comSku.getName());
            agentSkuMap.put("brandLogo", comBrand.getLogoUrl());

            agentSkuMaps.add(agentSkuMap);
        }

        return agentSkuMaps;
    }

    /**
     * 获取团队列表
     * @param userSkuId
     * @param skuId
     * @param isCertificate
     * @return
     */
    public List<Map<String, Object>> findTeam(Integer userSkuId, Integer skuId, Integer isCertificate){
        PfUserSku pfUserSku = new PfUserSku();
        pfUserSku.setPid(userSkuId);
        pfUserSku.setSkuId(skuId);
        pfUserSku.setIsCertificate(isCertificate);

        List<PfUserSku> pfUserSkus = pfUserSkuMapper.selectByCondition(pfUserSku);

        List<Map<String, Object>> teamMaps = new ArrayList<>();
        for(PfUserSku pus : pfUserSkus){
            ComUser comUser = comUserMapper.selectByPrimaryKey(pus.getUserId());
            ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(pus.getAgentLevelId());

            Map<String, Object> teamMap = new HashMap<>();
            teamMap.put("comUserId", comUser.getId());
            teamMap.put("comUserName", comUser.getRealName());
            teamMap.put("skuId", pus.getSkuId());
            teamMap.put("agentLevelId", comAgentLevel.getId());
            teamMap.put("agentLevelName", comAgentLevel.getName());
            teamMap.put("code", pus.getCode());
        }

        return teamMaps;
    }

    public Map<String, Object> viewMember(Long comUserId, Integer skuId, Integer agentLevelId){
        ComUser comUser = comUserMapper.selectByPrimaryKey(comUserId);
        ComSku comSku = comSkuMapper.selectById(skuId);
        ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(agentLevelId);
        PfUserCertificate pfUserCertificate = pfUserCertificateMapper.selectByUserId(comUserId);

        Map<String, Object> memberMap = new HashMap<>();
        memberMap.put("comUserId", comUser.getId());
        memberMap.put("comUserName", comUser.getRealName());
        memberMap.put("mobile", comUser.getMobile());
        memberMap.put("weixin", comUser.getWxId());
        memberMap.put("idCard", comUser.getIdCard());
        memberMap.put("frontImg", comUser.getIdCardFrontUrl());
        memberMap.put("backImg", comUser.getIdCardBackUrl());
        memberMap.put("skuName", comSku.getName());
        memberMap.put("agentLevelName", comAgentLevel.getName());
        memberMap.put("certificateImg", pfUserCertificate.getImgUrl());

        return memberMap;
    }
}
