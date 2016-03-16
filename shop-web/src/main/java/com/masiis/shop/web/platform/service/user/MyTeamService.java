package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.ComAgentLevel;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserSku;
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
    private ComUserMapper comUserMapper;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;


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

            Map<String, Object> agentSkuMap = new HashMap<>();
            agentSkuMap.put("userSkuId", pus.getId());
            agentSkuMap.put("skuId", comSku.getId());
            agentSkuMap.put("skuName", comSku.getName());

            agentSkuMaps.add(agentSkuMap);
        }

        return agentSkuMaps;
    }

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
            teamMap.put("agentLevelId", comAgentLevel.getId());
            teamMap.put("agentLevelName", comAgentLevel.getName());
        }

        return teamMaps;
    }
}
