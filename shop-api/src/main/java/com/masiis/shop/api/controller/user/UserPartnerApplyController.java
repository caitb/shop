package com.masiis.shop.api.controller.user;

import com.masiis.shop.api.bean.user.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysConstants;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.service.user.PfUserRelationService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import com.masiis.shop.common.util.PhoneNumUtils;
import com.masiis.shop.common.util.PropertiesUtils;
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
    private UserService userService;

    @RequestMapping("/canagent")
    @ResponseBody
    @SignValid(paramType = PartnerPreApplyReq.class)
    public canApplyRes isAgent(HttpServletRequest request, canApplyReq req, ComUser user) throws Exception {
        Integer skuId = req.getSkuId();
        canApplyRes res = new canApplyRes();
        if (skuId == null) {
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
            if (relation == null) {
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
    public PartnerPreApplyRes toApply(HttpServletRequest request, PartnerPreApplyReq req, ComUser user) throws Exception {
        PartnerPreApplyRes res = new PartnerPreApplyRes();
        Integer skuId = req.getSkuId();
        if (skuId == null) {
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
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        return res;
    }


    @RequestMapping("/register")
    @ResponseBody
    @SignValid(paramType = PartnerApplyRegisterReq.class)
    public PartnerApplyRegisterRes partnerApplyRegister(HttpServletRequest request, PartnerApplyRegisterReq req, ComUser user) throws Exception {
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
        if (userSkuService.getUserSkuByUserIdAndSkuId(user.getId(), skuId) != null
                || relation == null) {
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
            BigDecimal amount = pfSkuAgent.getTotalPrice();
            // 总金额加上保证金
            amount = amount.add(pfSkuAgent.getBail()).setScale(2, RoundingMode.HALF_DOWN);
            view.setAgentFee(amount);
            view.setSinFee(pfSkuAgent.getUnitPrice());
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

    @RequestMapping("/boconfirm")
    @ResponseBody
    @SignValid(paramType = ConfirmBOrderReq.class)
    public ConfirmBOrderRes confirmBorder(HttpServletRequest request, ConfirmBOrderReq req, ComUser user) throws Exception {
        ConfirmBOrderRes res = new ConfirmBOrderRes();
        Integer agentLevelId = req.getAgentLevelId();
        Integer sendType = user.getSendType();
        Integer skuId = req.getSkuId();
        String weiXinId = req.getWxId();

        if (agentLevelId == null || agentLevelId <= 0) {
            log.error(SysResCodeCons.RES_CODE_UPAPPLY_AGENTLEVELID_INVALID_MSG);
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_AGENTLEVELID_INVALID);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_AGENTLEVELID_INVALID_MSG);
            return res;
        }
        if(StringUtils.isBlank(weiXinId)){
            log.error(SysResCodeCons.RES_CODE_UPAPPLY_WXID_INVALID_MSG);
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_WXID_INVALID);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_WXID_INVALID_MSG);
            return res;
        }
        if(skuId == null || skuId <= 0){
            log.error(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL_MSG);
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL_MSG);
            return res;
        }
        //获取sku
        ComSku comSku = skuService.getSkuById(skuId);
        if(comSku == null){
            log.error(SysResCodeCons.RES_CODE_UPAPPLY_SKU_INVALID_MSG);
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_SKU_INVALID);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_SKU_INVALID_MSG);
            return res;
        }

        //拿货方式
        res.setSendType(sendType);
        res.setSkuId(skuId);
        res.setSkuName(comSku.getName());
        //获取sku主图片
        ComSkuImage comSkuImage = skuService.findComSkuImage(skuId);
        String skuImg = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        res.setSkuImg(skuImg + comSkuImage.getImgUrl());
        //获取用户代理等级
        res.setAgentLevelId(agentLevelId);
        PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, agentLevelId);
        res.setQuantity(pfSkuAgent.getQuantity());
        res.setBailFee(pfSkuAgent.getBail().toString());

        BigDecimal unitPrice = pfSkuAgent.getUnitPrice();
        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(res.getQuantity()));
        res.setProductTotalFee(totalPrice.toString());
        BigDecimal highProfit = (comSku.getPriceRetail().subtract(unitPrice))
                .multiply(BigDecimal.valueOf(res.getQuantity()))
                .setScale(2, BigDecimal.ROUND_DOWN);//最高利润
        res.setHighProfit(highProfit.toString());
        Integer lowerAgentLevelId = agentLevelId + 1;
        //获取下级代理信息
        PfSkuAgent lowerSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, lowerAgentLevelId);
        BigDecimal lowProfit = BigDecimal.ZERO;
        if (lowerSkuAgent == null) {
            lowProfit = highProfit;
        } else {
            lowProfit = (lowerSkuAgent.getUnitPrice().subtract(pfSkuAgent.getUnitPrice()))
                    .multiply(BigDecimal.valueOf(res.getQuantity()))
                    .setScale(2, BigDecimal.ROUND_DOWN);//最低利润
        }

        res.setLowProfit(lowProfit.toString());
        res.setTotalFee(totalPrice.add(pfSkuAgent.getBail()).toString());
        Long pUserId = pfUserRelationService.getPUserId(user.getId(), skuId);
        Integer isQueuing = 0;
        Integer count = 0;
        int status = skuService.getSkuStockStatus(skuId, 1, pUserId);
        if (status == 1) {
            isQueuing = 1;
            count = bOrderService.selectQueuingOrderCount(skuId);
        }

        res.setIsQueuing(isQueuing);
        res.setQueueNum(count);
        // 请求成功
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        return res;
    }

    @RequestMapping("/agentbind")
    @ResponseBody
    @SignValid(paramType = AgentBindReq.class)
    public AgentBindRes agentBind(HttpServletRequest request, AgentBindReq req, ComUser user) throws Exception {
        AgentBindRes res = new AgentBindRes();
        Integer skuId = req.getSkuId();
        String phoneNum = req.getPhoneNum();
        if(StringUtils.isBlank(phoneNum)){
            // 手机号为空
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_NULL);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_NULL_MSG);
            log.error(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_NULL_MSG);
            return res;
        }
        if(!PhoneNumUtils.isPhoneNum(phoneNum)){
            // 手机号格式不正确
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_INVALID);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_INVALID_MSG);
            log.error(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_INVALID_MSG);
            return res;
        }
        if(skuId == null || skuId == 0){
            // skuId为空
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL_MSG);
            log.error(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL_MSG);
            return res;
        }
        ComSku sku = skuService.getSkuById(skuId);
        if(sku == null){
            // skuId不存在
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_SKU_INVALID);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_SKU_INVALID_MSG);
            log.info(SysResCodeCons.RES_CODE_UPAPPLY_SKU_INVALID_MSG);
            return res;
        }
        ComUser pUser = userService.getUserByMobile(phoneNum);
        if(pUser == null){
            // 手机号尚未注册
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_NOTKNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_NOTKNOWN_MSG);
            log.info(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_NOTKNOWN_MSG);
            return res;
        }
        PfUserSku pPfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(pUser.getId(), skuId);
        if(pPfUserSku == null){
            // 手机号用户尚未代理该产品
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUMUSER_NOTAGENT);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUMUSER_NOTAGENT_MSG);
            log.info(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUMUSER_NOTAGENT_MSG);
            return res;
        }
        PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, pPfUserSku.getAgentLevelId() + 1);
        if(pfSkuAgent == null){
            // 手机号用户代理该产品最后一级,不能做上级
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUMUSER_LASTAGENT);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUMUSER_LASTAGENT_MSG);
            log.info(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUMUSER_LASTAGENT_MSG);
            return res;
        }
        PfUserSku uPfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(user.getId(), skuId);
        if(uPfUserSku != null){
            // 该用户已经代理过
            res.setResCode(SysResCodeCons.RES_CODE_CANAGENT_ALREADY_AGENT);
            res.setResMsg(SysResCodeCons.RES_CODE_CANAGENT_ALREADY_AGENT_MSG);
            log.info(SysResCodeCons.RES_CODE_CANAGENT_ALREADY_AGENT_MSG);
            return res;
        }
        PfUserRelation relation = pfUserRelationService.getRelation(user.getId(), skuId);
        if(relation != null){
            // 该用户已经建立临时代理关系
            res.setResCode(SysResCodeCons.RES_CODE_CANAGENT_ALREADY_TEMPAGENT);
            res.setResMsg(SysResCodeCons.RES_CODE_CANAGENT_ALREADY_TEMPAGENT_MSG);
            log.info(SysResCodeCons.RES_CODE_CANAGENT_ALREADY_TEMPAGENT_MSG);
            return res;
        }
        try {
            userSkuService.checkParentData(user, pUser.getId(), skuId);
            PfUserRelation pfUserRelation = new PfUserRelation();
            pfUserRelation.setUserId(user.getId());
            pfUserRelation.setSkuId(skuId);
            pfUserRelation.setCreateTime(new Date());
            pfUserRelation.setIsEnable(1);
            pfUserRelation.setUserPid(pUser.getId());
            pfUserRelationService.insert(pfUserRelation);
        } catch (Exception e) {
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_AGENTBIND_FAIL);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_AGENTBIND_FAIL_MSG);
            log.info(SysResCodeCons.RES_CODE_UPAPPLY_AGENTBIND_FAIL_MSG);
            return res;
        }

        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        return res;
    }
}
