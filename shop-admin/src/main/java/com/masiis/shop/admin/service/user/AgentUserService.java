package com.masiis.shop.admin.service.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.user.AgentUser;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代理商(合伙人)
 * Created by cai_tb on 16/4/11.
 */
@Service
public class AgentUserService {

    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private PfUserSkuStockMapper pfUserSkuStockMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;

    /**
     * 代理商(合伙人列表)
     * @param pageNumber
     * @param pageSize
     * @param conditionMap
     * @return
     */
    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, Map<String, Object> conditionMap){
        PageHelper.startPage(pageNumber, pageSize);
        List<PfUserCertificate> pfUserCertificates = pfUserCertificateMapper.selectByMap(conditionMap);
        PageInfo<PfUserCertificate> pageInfo = new PageInfo<>(pfUserCertificates);

        List<AgentUser> agentUsers = new ArrayList<>();
        for(PfUserCertificate puc : pfUserCertificates){
            ComUser comUser = comUserMapper.selectByPrimaryKey(puc.getUserId());
            ComSku comSku = comSkuMapper.selectById(puc.getSkuId());
            ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(puc.getAgentLevelId());
            PfUserSku pfUserSku = pfUserSkuMapper.selectByPrimaryKey(puc.getPfUserSkuId());
            PfUserSkuStock pfUserSkuStock = pfUserSkuStockMapper.selectByUserIdAndSkuId(puc.getUserId(), puc.getSkuId());
            ComUser parentUser = comUserMapper.selectByPrimaryKey(pfUserSku.getUserPid());

            AgentUser agentUser = new AgentUser();
            agentUser.setComUser(comUser);
            agentUser.setComSku(comSku);
            agentUser.setComAgentLevel(comAgentLevel);
            agentUser.setPfUserSku(pfUserSku);
            agentUser.setPfUserSkuStock(pfUserSkuStock);
            agentUser.setPfUserCertificate(puc);
            agentUser.setParentUser(parentUser);

            agentUsers.add(agentUser);
        }

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", agentUsers);

        return pageMap;
    }
}
