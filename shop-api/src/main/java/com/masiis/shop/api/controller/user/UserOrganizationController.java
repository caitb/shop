package com.masiis.shop.api.controller.user;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.api.bean.base.BaseBusinessReq;
import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.api.bean.user.UserOrganizationReq;
import com.masiis.shop.api.bean.user.UserOrganizationRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.statistic.BrandStatistic;
import com.masiis.shop.dao.platform.user.PfUserBrandMapper;
import com.masiis.shop.dao.po.ComAgentLevel;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserBrand;
import com.masiis.shop.dao.po.PfUserOrganization;
import com.masiis.shop.web.common.service.ComAgentLevelService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.service.statistics.BrandStatisticService;
import com.masiis.shop.web.platform.service.user.PfUserBrandService;
import com.masiis.shop.web.platform.service.user.PfUserOrganizationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 用户组织
 * Created by cai_tb on 16/8/8.
 */
@Controller
@RequestMapping("/userOrganization")
public class UserOrganizationController extends BaseController {

    private final static Log log = LogFactory.getLog(UserOrganizationController.class);

    @Resource
    private PfUserOrganizationService pfUserOrganizationService;
    @Resource
    private UserService userService;
    @Resource
    private ComAgentLevelService comAgentLevelService;
    @Resource
    private PfUserBrandService pfUserBrandService;
    @Resource
    private BrandStatisticService brandStatisticService;
    @Resource
    private PfUserBrandMapper pfUserBrandMapper;

    /**
     * 我创建的家族
     *
     * @param request
     * @param comUser
     * @return
     */
    @RequestMapping("/listAgentBrand")
    @ResponseBody
    @SignValid(paramType = BaseBusinessReq.class)
    public UserOrganizationRes getAgentBrand(HttpServletRequest request, ComUser comUser) {
        UserOrganizationRes userOrganizationRes = new UserOrganizationRes();

        try {
            ComUser user = userService.getUserById(comUser.getId());
            //我代理的品牌数量
            Integer agentBrandNum = pfUserBrandService.getMyBrandNum(comUser.getId());
            Integer totalUserNum = 0;
            BigDecimal totalSaleAmount = BigDecimal.ZERO;
            List<Map<String, Object>> organizations = pfUserOrganizationService.listCreateOrganization(comUser.getId());
            List<PfUserBrand> pfUserBrands = pfUserBrandMapper.selectByUserId(user.getId());
            for (PfUserBrand pfUserBrand : pfUserBrands) {
                BrandStatistic brandStatistic = brandStatisticService.selectBrandStatisticByUserIdAndBrandId(pfUserBrand.getUserId(), pfUserBrand.getBrandId());
                for (Map<String, Object> organization : organizations) {
                    if (pfUserBrand.getUserId().equals(organization.get("userId")) && pfUserBrand.getBrandId().equals(organization.get("brandId"))) {
                        organization.put("brandStatistic", brandStatistic);
                    }
                }
                totalUserNum += brandStatistic.getUserNum();
                totalSaleAmount = totalSaleAmount.add(brandStatistic.getSellAmount());
            }
            List<Map<String, Object>> joinOrganizations = pfUserOrganizationService.listJoinOrganization(comUser.getId());
            for (Map<String, Object> joinOrganization : joinOrganizations) {
                BrandStatistic brandStatistic = brandStatisticService.selectBrandStatisticByUserIdAndBrandId((Long) joinOrganization.get("userId"), (Integer) joinOrganization.get("brandId"));
                joinOrganization.put("brandStatistic", brandStatistic);
            }

            userOrganizationRes.getDataMap().put("isAudit", user.getAuditStatus().intValue() == 2 ? true : false);
            userOrganizationRes.getDataMap().put("organizations", organizations);
            userOrganizationRes.getDataMap().put("joinOrganizations", joinOrganizations);
            userOrganizationRes.getDataMap().put("agentBrandNum", agentBrandNum);
            userOrganizationRes.getDataMap().put("totalUserNum", totalUserNum);
            userOrganizationRes.getDataMap().put("totalSaleAmount", totalSaleAmount);
            userOrganizationRes.getDataMap().put("imgUrlPrefix", PropertiesUtils.getStringValue("organization_url"));
            userOrganizationRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            userOrganizationRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error("获取组织二维码失败!" + e);
            e.printStackTrace();
        }


        return userOrganizationRes;
    }


    /**
     * 家族信息设置页面
     *
     * @param request
     * @param comUser
     * @return
     */
    @RequestMapping("/toSetup")
    @ResponseBody
    @SignValid(paramType = UserOrganizationReq.class)
    public UserOrganizationRes toSetup(HttpServletRequest request, UserOrganizationReq userOrganizationReq, ComUser comUser) {
        UserOrganizationRes userOrganizationRes = new UserOrganizationRes();

        try {
            PfUserOrganization pfUserOrganization = pfUserOrganizationService.loadByBrandIdAndUserId(userOrganizationReq.getBrandId(), comUser.getId());
            ComAgentLevel comAgentLevel = comAgentLevelService.selectByPrimaryKey(userOrganizationReq.getAgentLevelId());

            if(pfUserOrganization != null){
                String name = StringUtils.isBlank(comAgentLevel.getOrganizationSuffix()) ? pfUserOrganization.getName() : pfUserOrganizationService.handlerName(pfUserOrganization.getName(), comAgentLevel.getOrganizationSuffix());
                pfUserOrganization.setName(name);
            }

            userOrganizationRes.getDataMap().put("pfUserOrganization", pfUserOrganization);
            userOrganizationRes.getDataMap().put("organizationSuffix", comAgentLevel.getOrganizationSuffix());
            userOrganizationRes.getDataMap().put("agentLevelId", userOrganizationReq.getAgentLevelId());
            userOrganizationRes.getDataMap().put("brandId", userOrganizationReq.getBrandId());
            userOrganizationRes.getDataMap().put("imgUrlPrefix", PropertiesUtils.getStringValue("organization_url"));
            userOrganizationRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            userOrganizationRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            userOrganizationRes.setResCode(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
            userOrganizationRes.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR_MSG);

            log.error("获取家族设置参数失败!" + e);
            e.printStackTrace();
        }

        return userOrganizationRes;
    }

    /**
     * 保存家族设置
     *
     * @param userOrganizationReq
     * @param comUser
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    @SignValid(paramType = UserOrganizationReq.class)
    public BaseBusinessRes save(HttpServletRequest request, UserOrganizationReq userOrganizationReq, ComUser comUser) {
        UserOrganizationRes userOrganizationRes = new UserOrganizationRes();

        try {
            ComAgentLevel comAgentLevel = comAgentLevelService.selectByPrimaryKey(userOrganizationReq.getAgentLevelId());
            String name = StringUtils.isBlank(comAgentLevel.getOrganizationSuffix()) ? userOrganizationReq.getName() : pfUserOrganizationService.handlerName(userOrganizationReq.getName(), comAgentLevel.getOrganizationSuffix());
            name += comAgentLevel.getOrganizationSuffix();

            PfUserOrganization pfUserOrganization = new PfUserOrganization();
            pfUserOrganization.setId(userOrganizationReq.getOrganizationId());
            pfUserOrganization.setCreateTime(new Date());
            pfUserOrganization.setStatus(1);
            pfUserOrganization.setAgentLevelId(userOrganizationReq.getAgentLevelId());
            pfUserOrganization.setUserId(comUser.getId());
            pfUserOrganization.setName(name);
            pfUserOrganization.setAddDescription(userOrganizationReq.getAddDescription());
            pfUserOrganization.setBackImg(userOrganizationReq.getBackImg());
            pfUserOrganization.setBrandId(userOrganizationReq.getBrandId());
            pfUserOrganization.setIntroduction(userOrganizationReq.getIntroduction());
            pfUserOrganization.setLogo(userOrganizationReq.getLogo());
            pfUserOrganization.setSlogan(userOrganizationReq.getSlogan());
            pfUserOrganization.setWxId(userOrganizationReq.getWxId());
            pfUserOrganization.setWxQrCode(userOrganizationReq.getWxQrCode());
            pfUserOrganization.setFreemanNum(0);
            if (pfUserOrganization.getId() == null) {
                pfUserOrganizationService.save(pfUserOrganization);
            } else {
                pfUserOrganizationService.update(pfUserOrganization);
            }
            userOrganizationRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            userOrganizationRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            userOrganizationRes.setResCode(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
            userOrganizationRes.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR_MSG);
            log.error("保存家族设置失败![userOrganizationReq="+userOrganizationReq+"]" + e);
            e.printStackTrace();
        }

        return userOrganizationRes;
    }



    /**
     * 验证是否有家族(包括加入的)
     * @param request
     * @param comUser
     * @return
     */
    @RequestMapping("/hasOrganization")
    @ResponseBody
    @SignValid(paramType = UserOrganizationReq.class)
    public BaseBusinessRes hasOrganization(HttpServletRequest request, ComUser comUser){
        UserOrganizationRes userOrganizationRes = new UserOrganizationRes();

        try {
            Boolean hasOrganization = pfUserOrganizationService.hasOrganization(comUser.getId());
            userOrganizationRes.getDataMap().put("hasOrganization", hasOrganization);
            userOrganizationRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            userOrganizationRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            userOrganizationRes.setResCode(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
            userOrganizationRes.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR_MSG);
            log.error("验证是否有家族(包括加入的)失败!" + e);
            e.printStackTrace();
        }

        return userOrganizationRes;
    }
}
