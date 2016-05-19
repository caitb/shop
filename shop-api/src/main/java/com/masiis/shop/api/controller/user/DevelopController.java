package com.masiis.shop.api.controller.user;

import com.masiis.shop.api.bean.base.BaseBusinessReq;
import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.api.bean.user.AgentSkuRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.service.product.BrandService;
import com.masiis.shop.api.service.product.SkuAgentService;
import com.masiis.shop.api.service.product.SkuService;
import com.masiis.shop.api.service.product.SpuService;
import com.masiis.shop.api.service.user.AgentLevelService;
import com.masiis.shop.api.service.user.UserSkuService;
import com.masiis.shop.dao.po.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cai_tb on 16/5/19.
 */
@Controller
@RequestMapping("/develop")
public class DevelopController {

    private final static Log log = LogFactory.getLog(DevelopController.class);

    @Resource
    private UserSkuService userSkuService;
    @Resource
    private SkuService skuService;
    @Resource
    private SpuService spuService;
    @Resource
    private AgentLevelService agentLevelService;
    @Resource
    private BrandService brandService;
    @Resource
    private SkuAgentService skuAgentService;

    @RequestMapping("/listAgentSku")
    @ResponseBody
    @SignValid(paramType = BaseBusinessReq.class)
    public List<AgentSkuRes> listAgentSku(HttpServletRequest request, BaseBusinessRes bbs, ComUser comUser){
        List<AgentSkuRes> agentSkuResList = new ArrayList<>();

        try {
            List<PfUserSku> userSkus = userSkuService.listByUserId(comUser.getId());

            for(PfUserSku userSku : userSkus){
                ComSku comSku = skuService.getSkuById(userSku.getSkuId());
                ComSpu comSpu = spuService.getById(comSku.getSpuId());
                ComBrand comBrand = brandService.getById(comSpu.getBrandId());
                ComAgentLevel comAgentLevel = agentLevelService.getById(userSku.getAgentLevelId());
                List<PfSkuAgent> skuAgents = skuAgentService.getAllBySkuId(comSku.getId());

                AgentSkuRes agentSkuRes = new AgentSkuRes();
                agentSkuRes.setSkuId(comSku.getId());
                agentSkuRes.setSkuName(comSku.geteName());
                agentSkuRes.setLevelId(comAgentLevel.getId());
                agentSkuRes.setLevelName(comAgentLevel.getName());
                agentSkuRes.setBrandLogo(comBrand.getLogoUrl());
                agentSkuRes.setUserSkuId(userSku.getId());
                agentSkuRes.setDevelop(comAgentLevel.getId().intValue()==skuAgents.size() ? false:true);

                agentSkuResList.add(agentSkuRes);
            }

            return agentSkuResList;
        } catch (Exception e) {
            log.error("获取用户代理产品失败![bbs="+bbs+"][comUser="+comUser+"]");
            e.printStackTrace();
        }
        return null;
    }
}
