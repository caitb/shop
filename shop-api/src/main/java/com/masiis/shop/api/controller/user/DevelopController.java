package com.masiis.shop.api.controller.user;

import com.masiis.shop.api.bean.base.BaseBusinessReq;
import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.api.bean.user.AgentSku;
import com.masiis.shop.api.bean.user.AgentSkuRes;
import com.masiis.shop.api.bean.user.PopularizeReq;
import com.masiis.shop.api.bean.user.PopularizeRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.service.product.BrandService;
import com.masiis.shop.api.service.product.SkuAgentService;
import com.masiis.shop.api.service.product.SkuService;
import com.masiis.shop.api.service.product.SpuService;
import com.masiis.shop.api.service.user.AgentLevelService;
import com.masiis.shop.api.service.user.CertificateService;
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
    @Resource
    private CertificateService certificateService;

    @RequestMapping("/listAgentSku")
    @ResponseBody
    @SignValid(paramType = BaseBusinessReq.class)
    public AgentSkuRes listAgentSku(HttpServletRequest request, BaseBusinessReq bbq, ComUser comUser){
        AgentSkuRes agentSkuRes = new AgentSkuRes();

        try {
            List<PfUserSku> userSkus = userSkuService.listByUserId(comUser.getId());

            for(PfUserSku userSku : userSkus){
                ComSku comSku = skuService.getSkuById(userSku.getSkuId());
                ComSpu comSpu = spuService.getById(comSku.getSpuId());
                ComBrand comBrand = brandService.getById(comSpu.getBrandId());
                ComAgentLevel comAgentLevel = agentLevelService.getById(userSku.getAgentLevelId());
                List<PfSkuAgent> skuAgents = skuAgentService.getAllBySkuId(comSku.getId());

                AgentSku agentSku = new AgentSku();
                agentSku.setSkuId(comSku.getId());
                agentSku.setSkuName(comSku.geteName());
                agentSku.setLevelId(comAgentLevel.getId());
                agentSku.setLevelName(comAgentLevel.getName());
                agentSku.setBrandLogo(comBrand.getLogoUrl());
                agentSku.setUserSkuId(userSku.getId());
                agentSku.setDevelop(comAgentLevel.getId().intValue()==skuAgents.size() ? false:true);

                agentSkuRes.getAgentSkus().add(agentSku);
            }

        } catch (Exception e) {
            log.error("获取用户代理产品失败![bbs="+bbq+"][comUser="+comUser+"]");
            e.printStackTrace();

            agentSkuRes.setResCode(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID);
            agentSkuRes.setResMsg("获取用户代理产品失败!");
        }

        return agentSkuRes;
    }

    @RequestMapping("/popularize")
    @ResponseBody
    @SignValid(paramType = PopularizeReq.class)
    public PopularizeRes popularize(HttpServletRequest request, PopularizeReq popularizeReq, ComUser comUser){
        PopularizeRes popularizeRes = new PopularizeRes();

        try {
            PfUserSku userSku = userSkuService.getUserSkuById(popularizeReq.getUserSkuId());
            ComSku comSku = skuService.getSkuById(userSku.getSkuId());
            ComAgentLevel comAgentLevel = agentLevelService.getById(userSku.getAgentLevelId());
            PfUserCertificate certificate = certificateService.getByCode(userSku.getCode());
        } catch (Exception e) {
            log.error("获取海报失败![popularizeReq="+popularizeReq+"][comUser="+comUser+"]");
        }

        return popularizeRes;
    }
}
