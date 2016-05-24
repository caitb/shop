package com.masiis.shop.api.controller.user;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.bean.user.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.order.BOrderService;
import com.masiis.shop.api.service.product.SkuAgentService;
import com.masiis.shop.api.service.product.SkuService;
import com.masiis.shop.api.service.user.ComUserService;
import com.masiis.shop.api.service.user.PfUserRelationService;
import com.masiis.shop.api.service.user.UserSkuService;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PhoneNumUtils;
import com.masiis.shop.dao.po.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Date 2016/5/23
 * @Auther lzh
 */
@Controller
@RequestMapping("/upapply")
public class UserPartnerApplyController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SkuService skuService;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private PfUserRelationService pfUserRelationService;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private ComUserService userService;

    @RequestMapping("/canagent")
    @ResponseBody
    @SignValid(paramType = PartnerPreApplyReq.class)
    public canApplyRes isAgent(HttpServletRequest request, canApplyReq req, ComUser user){
        Integer skuId = req.getSkuId();
        canApplyRes res = new canApplyRes();
        if(skuId == null){
            log.error(SysResCodeCons.RES_CODE_CANAGENT_SKU_NULL_MSG);
            res.setResCode(SysResCodeCons.RES_CODE_CANAGENT_SKU_NULL);
            res.setResMsg(SysResCodeCons.RES_CODE_CANAGENT_SKU_NULL_MSG);
            return res;
        }
        ComSku sku = skuService.getSkuById(skuId);
        if (sku == null) {
            log.error(SysResCodeCons.RES_CODE_CANAGENT_SKU_INVALID_MSG);
            res.setResCode(SysResCodeCons.RES_CODE_CANAGENT_SKU_INVALID);
            res.setResMsg(SysResCodeCons.RES_CODE_CANAGENT_SKU_INVALID_MSG);
            return res;
        }
        PfUserRelation relation = pfUserRelationService.getRelation(user.getId(), skuId);
        PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(user.getId(), skuId);
        if (pfUserSku != null) {
            // 已经代理过该产品
            res.setResCode(SysResCodeCons.RES_CODE_CANAGENT_ALREADY_AGENT);
            res.setResMsg(SysResCodeCons.RES_CODE_CANAGENT_ALREADY_AGENT_MSG);
        } else {
            if(relation == null) {
                // 临时代理关系没有,不能申请
                res.setResCode(SysResCodeCons.RES_CODE_CANAGENT_NOTAGENT);
                res.setResMsg(SysResCodeCons.RES_CODE_CANAGENT_NOTAGENT_MSG);
            } else {
                // 已经绑定过临时代理关系
                res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
                res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            }
        }
        log.error(res.getResMsg());
        return res;
    }

    @RequestMapping("/toapply")
    @ResponseBody
    @SignValid(paramType = PartnerPreApplyReq.class)
    public PartnerPreApplyRes toApply(HttpServletRequest request, PartnerPreApplyReq req, ComUser user){
        PartnerPreApplyRes res = new PartnerPreApplyRes();
        Integer skuId = req.getSkuId();
        if(skuId == null){
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL_MSG);
            return res;
        }
        ComSku sku = skuService.getSkuById(skuId);
        if (sku == null) {
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_SKU_INVALID);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_SKU_INVALID_MSG);
            return res;
        }
        Long temPUserId = pfUserRelationService.getPUserId(user.getId(), skuId);
        res.setIsAgent(0);
        PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(user.getId(), skuId);
        if (pfUserSku != null) {
            // 已经绑定过代理关系
            log.error(SysResCodeCons.RES_CODE_CANAGENT_ALREADY_AGENT);
            res.setResCode(SysResCodeCons.RES_CODE_CANAGENT_ALREADY_AGENT);
            res.setResMsg(SysResCodeCons.RES_CODE_CANAGENT_ALREADY_AGENT_MSG);
            return res;
        }

        try {
            if (temPUserId == 0) {
                //校验上级合伙人数据是否合法,如果合法则建立临时绑定关系
                userSkuService.checkParentData(user, temPUserId, skuId);
                PfUserRelation pfUserRelation = new PfUserRelation();
                pfUserRelation.setUserId(user.getId());
                pfUserRelation.setSkuId(skuId);
                pfUserRelation.setCreateTime(new Date());
                pfUserRelation.setIsEnable(1);
                pfUserRelation.setUserPid(temPUserId);
                pfUserRelationService.insert(pfUserRelation);
            }
        } catch (Exception e) {
            if(e instanceof BusinessException) {
                res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_PUSER_INVALID);
                res.setResMsg(e.getMessage());
            }else{
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
            log.error(res.getResMsg());
            return res;
        }
        Integer isQueuing = 0;
        Integer count = 0;
        int status = skuService.getSkuStockStatus(skuId, 1, temPUserId);
        if (status == 1) {
            isQueuing = 1;
            count = bOrderService.selectQueuingOrderCount(skuId);
        }

        res.setIsBind(user.getIsBinding());
        res.setIsQueuing(isQueuing);
        res.setQueueNum(count);
        res.setIsCreditAudit(user.getAuditStatus());
        return res;
    }


    @RequestMapping("/register")
    @ResponseBody
    @SignValid(paramType = PartnerApplyRegisterReq.class)
    public PartnerApplyRegisterRes partnerApplyRegister(HttpServletRequest request, PartnerApplyRegisterReq req, ComUser user){
        PartnerApplyRegisterRes res = new PartnerApplyRegisterRes();
        Integer skuId = req.getSkuId();
        if (skuId == null || skuId < 0) {
            log.error("skuId不合法,skuId:" + skuId + ",用户id为:" + user.getId());
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL_MSG);
            return res;
        }
        //获取商品信息
        ComSku comSku = skuService.getSkuById(skuId);
        if (comSku == null) {
            log.error("该skuId对应的商品不存在,skuId:" + skuId);
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_SKU_INVALID);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_SKU_INVALID_MSG);
            return res;
        }
        //获取商品代理信息
        List<PfSkuAgent> pfSkuAgents = skuAgentService.getAllBySkuId(skuId);
        //获取代理信息
        List<ComAgentLevel> comAgentLevels = skuAgentService.getComAgentLevel();
        //上级代理等级
        Integer pUserLevelId = 0;
        PfUserRelation relation = pfUserRelationService.getRelation(user.getId(), skuId);
        if(userSkuService.getUserSkuByUserIdAndSkuId(user.getId(), skuId) != null
                    || relation == null){
            log.error(SysResCodeCons.RES_CODE_CANAGENT_NOTAGENT_MSG);
            res.setResCode(SysResCodeCons.RES_CODE_CANAGENT_NOTAGENT);
            res.setResMsg(SysResCodeCons.RES_CODE_CANAGENT_NOTAGENT_MSG);
            return res;
        }
        Long pUserId = relation.getUserPid();
        if (pUserId > 0) {
            PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(pUserId, skuId);
            pUserLevelId = pfUserSku.getAgentLevelId();
        }
        // 创建该sku代理商的代理门槛信息
        List<AgentSkuView> agentSkuViews = new ArrayList<AgentSkuView>();
        for (PfSkuAgent pfSkuAgent : pfSkuAgents) {
            AgentSkuView view = new AgentSkuView();
            view.setQuantity(pfSkuAgent.getQuantity());
            view.setBailFee(pfSkuAgent.getBail());
            for (ComAgentLevel comAgentLevel : comAgentLevels) {
                if (pfSkuAgent.getAgentLevelId() == comAgentLevel.getId()) {
                    view.setLevelId(comAgentLevel.getId());
                    view.setLevelName(comAgentLevel.getName());
                }
            }
            BigDecimal amount = comSku.getPriceRetail().multiply(BigDecimal.valueOf(pfSkuAgent.getQuantity())).multiply(pfSkuAgent.getDiscount());
            // 总金额加上保证金
            amount = amount.add(pfSkuAgent.getBail()).setScale(2, RoundingMode.HALF_DOWN);
            view.setAgentFee(amount);
            view.setSinFee(comSku.getPriceRetail().multiply(pfSkuAgent.getDiscount()).setScale(2, RoundingMode.HALF_DOWN));
            view.setIsShow(pfSkuAgent.getIsShow());
            agentSkuViews.add(view);
        }
        res.setSkuName(comSku.getName());
        res.setpUserLevelId(pUserLevelId);
        res.setAgentSkuViews(agentSkuViews);
        Integer sendType = 0;
        if (user.getSendType() > 0) {
            sendType = user.getSendType();
        }
        if (pUserId != null && pUserId > 0) {
            ComUser pUser = userService.getUserById(pUserId);
            // 上级代理商品关系
            res.setpWxNkName(pUser.getWxNkName());
            if (sendType == 0) {
                sendType = pUser.getSendType();
            }
        }
        res.setSendType(sendType);
        Integer isQueuing = 0;
        Integer count = 0;
        int status = skuService.getSkuStockStatus(skuId, 1, pUserId);
        if (status == 1) {
            isQueuing = 1;
            count = bOrderService.selectQueuingOrderCount(skuId);
        }
        res.setIsQueuing(isQueuing);
        res.setQueueNum(count);
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        return res;
    }


}
