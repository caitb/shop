package com.masiis.shop.web.api.service;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.user.CountGroup;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.user.PfUserOrganizationMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.PfSkuAgent;
import com.masiis.shop.dao.po.PfUserOrganization;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.service.user.CountGroupService;
import com.masiis.shop.web.platform.service.user.UserOrganizationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * 招商展示
 * Created by cai_tb on 16/8/10.
 */
@Service
public class MerchantsService {

    private final static Log log = LogFactory.getLog(MerchantsService.class);

    @Resource
    private PfUserOrganizationMapper pfUserOrganizationMapper;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private UserOrganizationService userOrganizationService;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private UserService userService;

    /**
     * 招商展示
     *
     * @param brandId
     * @param userPid
     * @return
     * @throws Exception
     */
    public Map<String, Object> show(Integer brandId, Long userPid, Long userId, Integer userSource) throws Exception {

        log.info("招商展示-----------------------start");
        log.info("招商展示---------brandId----" + brandId + "------userId-----" + userPid);


        Map<String, Object> agentBrand = userOrganizationService.loadAgentBrand(userPid, brandId);
        //如果userPid没有家族,向上一级查找
        if(agentBrand == null){
            agentBrand = userOrganizationService.loadPAgentBrand(userPid, brandId);
        }

        agentBrand.put("illustratingPictureName", PropertiesUtils.getStringValue("brand_poster_url") + agentBrand.get("illustratingPictureName"));

        switch (userSource) {
            case 1:
                agentBrand.put("levelId", (Integer) agentBrand.get("levelId") + 1);
                break;
            default:
                agentBrand.put("levelId", 0);
        }



        PfUserOrganization pfUserOrganization = pfUserOrganizationMapper.selectByBrandIdAndUserId(brandId, (Long)agentBrand.get("userId"));
        Map<String, Object> countGroup = userOrganizationService.countGroupByBrandId((Long)agentBrand.get("userId"), brandId);

        //当前用户是否代理了该产品
        PfUserSku us = pfUserSkuMapper.selectByUserIdAndSkuId(userId, (Integer) agentBrand.get("skuId"));

        //家族名称
        String organizationName = pfUserOrganization.getName();
        if(pfUserOrganization.getAgentLevelId().intValue() == 4){
            PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId((Long)agentBrand.get("userId"), (Integer)agentBrand.get("skuId"));
            organizationName = pfUserOrganizationMapper.selectByBrandIdAndUserId(brandId, pfUserSku.getUserPid()).getName();
        }
        if(pfUserOrganization.getAgentLevelId().intValue() == 5){
            PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId((Long)agentBrand.get("userId"), (Integer)agentBrand.get("skuId"));
                      pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(pfUserSku.getUserPid(), (Integer)agentBrand.get("skuId"));
            organizationName = pfUserOrganizationMapper.selectByBrandIdAndUserId(brandId, pfUserSku.getUserPid()).getName();
        }


        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("agentUserId", userPid);
        dataMap.put("pfUserOrganization", pfUserOrganization);
        dataMap.put("agentBrand", agentBrand);
        dataMap.put("countGroup", countGroup);
        dataMap.put("isAgent", us == null ? false : true);
        dataMap.put("isBinding", userService.getUserById(userId).getIsBinding());
        dataMap.put("agentUpper", agentBrand.get("organizationFreemanUpperNum"));
        dataMap.put("imgUrlPrefix", "http://file.qc.masiis.com/static/user/organization/");
        dataMap.put("organizationName", organizationName);

        log.info("招商展示-----------------------end");
        return dataMap;
    }


}
