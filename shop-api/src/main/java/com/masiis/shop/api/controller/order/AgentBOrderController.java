package com.masiis.shop.api.controller.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.api.bean.base.BaseBusinessReq;
import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.api.bean.order.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.common.enums.mall.OrderPayTypeEnum;
import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.enums.platform.BOrderType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BOrderAdd;
import com.masiis.shop.dao.beans.user.AgentSkuViewInfo;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.BrandService;
import com.masiis.shop.web.common.service.ComAgentLevelService;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.service.order.BOrderAddService;
import com.masiis.shop.web.platform.service.order.BOrderPayService;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.user.PfUserOrganizationService;
import com.masiis.shop.web.platform.service.user.PfUserSkuService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Date 2016/5/24
 * @Auther lzh
 */
@Controller
@RequestMapping("/agentapply")
public class AgentBOrderController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private UserService userService;
    @Resource
    private BrandService brandService;
    @Resource
    private SkuService skuService;
    @Resource
    private PfUserSkuService pfUserSkuService;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private PfUserOrganizationService pfUserOrganizationService;
    @Resource
    private BOrderAddService bOrderAddService;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private ComAgentLevelService comAgentLevelService;
    @Resource
    private BOrderPayService payBOrderService;

    /**
     * 合伙人注册选择等级页面
     *
     * @param request
     * @param req
     * @param user
     * @return
     */
    @RequestMapping("/select")
    @ResponseBody
    @SignValid(paramType = BAgentSelectReq.class)
    public BAgentSelectRes addNewAgentBOrder(HttpServletRequest request,
                                             BAgentSelectReq req, ComUser user){
        Long userPid = req.getUserPid();
        Integer skuId = req.getSkuId();
        Integer sourceType = req.getSourceType();
        Integer agentLevelId = req.getAgentLevelId();
        BAgentSelectRes res = new BAgentSelectRes();
        try{
            if(userPid == null || userPid <= 0){
                // 上级用户id不正确
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_NOT_PID);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_NOT_PID_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_NOT_PID_MSG);
            }
            if(sourceType == null
                    || (sourceType.intValue() != 1
                        && sourceType.intValue() != 2
                        && sourceType.intValue() != 3)){
                // 用户来源不正确
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_SOURCE_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_SOURCE_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_SOURCE_ERROR_MSG);
            }
            if(skuId == null || skuId <= 0){
                // 品牌id不对
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR_MSG);
            }
            if(agentLevelId != null && agentLevelId < 0){
                // 等级id不对
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR_MSG);
            }
            if(sourceType.intValue() == 1 && agentLevelId.intValue() == 0){
                // 小白不能同时选品牌和等级
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_SOURCE_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_SOURCE_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_SOURCE_ERROR_MSG);
            }
            ComUser pUser = userService.getUserById(userPid);
            if(pUser == null){
                // 上级用户查不到
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_NOT_PID);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_NOT_PID_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_NOT_PID_MSG);
            }
            // 指定品牌
            res.setIsAssignBrand(1);
            ComSku sku = skuService.getSkuById(skuId);
            if(sku == null){
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR_MSG);
            }
            ComBrand brand = brandService.getBySkuId(skuId);
            List<AgentSkuViewInfo> views = new ArrayList<>();
            // 查找上级的等级
            PfUserSku pUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userPid, skuId);
            if(pUserSku == null){
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_AGENT_SKU_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_AGENT_SKU_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_AGENT_SKU_ERROR_MSG);
            }
            PfUserSku userSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(user.getId(), skuId);
            if(userSku != null){
                // 注册用户已经是该sku的合伙人
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_ALREADY_AGENT_SKU);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_ALREADY_AGENT_SKU_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_ALREADY_AGENT_SKU_MSG);
            }

            log.info("接口参数校验结束");

            // 检测等级
            if(agentLevelId.intValue() == 0){
                // 需要选择等级
                // 查找可以代理的等级
                List<PfSkuAgent> pfSkuAgents = skuAgentService
                        .getLessLevelByLevelIdAndSkuId(pUserSku.getAgentLevelId(), skuId);
                for(PfSkuAgent pfSkuAgent:pfSkuAgents){
                    // 计算
                    AgentSkuViewInfo view = skuAgentService.createAgentSkuView(pfSkuAgent, sku, true);
                    views.add(view);
                }
                res.setIsAssignLevel(0);
            } else {
                // 指定等级
                if(agentLevelId.intValue() < pUserSku.getAgentLevelId().intValue()){
                    // 合伙等级高于上级等级,不正确
                    res.setResCode(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR);
                    res.setResMsg(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR_MSG);
                    throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR_MSG);
                }
                PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, agentLevelId);
                AgentSkuViewInfo view = skuAgentService.createAgentSkuView(pfSkuAgent, sku, true);
                views.add(view);
                res.setIsAssignLevel(1);
            }
            //查找组织信息
            PfUserOrganization userOrganization = pfUserOrganizationService.getByUserIdAndBrandId(pUser.getId(), brand.getId());
            if(userOrganization != null){
                res.setfGeneral(userOrganization.getSlogan());
                res.setfName(userOrganization.getName());
                res.setfUrl(PropertiesUtils.getStringValue("organization_img_url") + userOrganization.getLogo());
            }
            res.setViews(views);
            res.setBrand(brand);
            res.setpName(pUser.getRealName());
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }

        return res;
    }

    /**
     * 合伙注册确认订单页面
     *
     * @param request
     * @param req
     * @param user
     * @return
     */
    @RequestMapping("/confirm")
    @ResponseBody
    @SignValid(paramType = BAgentConfirmReq.class)
    public BAgentConfirmRes confirmNewBOrder(HttpServletRequest request, BAgentConfirmReq req, ComUser user){
        BAgentConfirmRes res = new BAgentConfirmRes();
        Long userPid = req.getUserPid();
        Integer skuId = req.getSkuId();
        Integer sourceType = req.getSourceType();
        Integer agentLevelId = req.getAgentLevelId();
        try{
            if(userPid == null || userPid <= 0){
                // 上级用户id不正确
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_NOT_PID);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_NOT_PID_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_NOT_PID_MSG);
            }
            if(sourceType == null
                    || (sourceType.intValue() != 1
                    && sourceType.intValue() != 2
                    && sourceType.intValue() != 3)){
                // 用户来源不正确
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_SOURCE_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_SOURCE_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_SOURCE_ERROR_MSG);
            }
            if(skuId == null || skuId <= 0){
                // 品牌id不对
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR_MSG);
            }
            if(agentLevelId != null && agentLevelId <= 0){
                // 等级id不对
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR_MSG);
            }
            ComUser pUser = userService.getUserById(userPid);
            if(pUser == null){
                // 上级用户查不到
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_NOT_PID);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_NOT_PID_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_NOT_PID_MSG);
            }
            ComSku sku = skuService.getSkuById(skuId);
            if(sku == null){
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR_MSG);
            }
            ComBrand brand = brandService.getBySkuId(skuId);
            // 查找上级的等级
            PfUserSku pUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userPid, skuId);
            if(pUserSku == null){
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_AGENT_SKU_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_AGENT_SKU_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_AGENT_SKU_ERROR_MSG);
            }
            if(agentLevelId.intValue() < pUserSku.getAgentLevelId().intValue()){
                // 合伙等级高于上级等级,不正确
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR_MSG);
            }

            log.info("接口参数校验结束");

            PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, agentLevelId);
            AgentSkuViewInfo view = skuAgentService.createAgentSkuView(pfSkuAgent, sku, true);

            //查找组织信息
            PfUserOrganization userOrganization = pfUserOrganizationService.getByUserIdAndBrandId(pUser.getId(), brand.getId());
            // 查找品牌下其他商品
            List<PfSkuAgent> otherSkus = pfUserSkuService.getOthersByUserIdAndDefaultSkuId(userPid, skuId);
            List<AgentSkuViewInfo> views = new ArrayList<>();
            for(PfSkuAgent pa:otherSkus){
                if(pa != null) {
                    ComSku cs = skuService.getSkuById(pa.getSkuId());
                    AgentSkuViewInfo info = skuAgentService.createAgentSkuView(pfSkuAgent, cs, true);
                    views.add(info);
                }
            }

            if(userOrganization != null){
                res.setfGeneral(userOrganization.getSlogan());
                res.setfName(userOrganization.getName());
                res.setfUrl(PropertiesUtils.getStringValue("organization_img_url") + userOrganization.getLogo());
            }
            res.setView(view);
            res.setOthers(views);
            res.setBrand(brand);
            res.setpName(pUser.getRealName());
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }
        return res;
    }


    @RequestMapping("/add")
    @ResponseBody
    @SignValid(paramType = AgentBOrderAddReq.class)
    public AgentBOrderAddRes createAgentOrder(HttpServletRequest request, AgentBOrderAddReq req, ComUser user){
        AgentBOrderAddRes res = new AgentBOrderAddRes();
        Long userPid = req.getUserPid();
        Integer skuId = req.getSkuId();
        Integer sourceType = req.getSourceType();
        Integer agentLevelId = req.getAgentLevelId();
        try{
            if(userPid == null || userPid <= 0){
                // 上级用户id不正确
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_NOT_PID);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_NOT_PID_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_NOT_PID_MSG);
            }
            if(sourceType == null
                    || (sourceType.intValue() != 1
                    && sourceType.intValue() != 2
                    && sourceType.intValue() != 3)){
                // 用户来源不正确
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_SOURCE_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_SOURCE_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_SOURCE_ERROR_MSG);
            }
            if(skuId == null || skuId <= 0){
                // 品牌id不对
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR_MSG);
            }
            if(agentLevelId != null && agentLevelId <= 0){
                // 等级id不对
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR_MSG);
            }
            ComUser pUser = userService.getUserById(userPid);
            if(pUser == null){
                // 上级用户查不到
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_NOT_PID);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_NOT_PID_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_NOT_PID_MSG);
            }
            ComSku sku = skuService.getSkuById(skuId);
            if(sku == null){
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_BRANDID_ERROR_MSG);
            }
            ComBrand brand = brandService.getBySkuId(skuId);
            // 查找上级的等级
            PfUserSku pUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userPid, skuId);
            if(pUserSku == null){
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_AGENT_SKU_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_AGENT_SKU_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_AGENT_SKU_ERROR_MSG);
            }
            if(agentLevelId.intValue() < pUserSku.getAgentLevelId().intValue()){
                // 合伙等级高于上级等级,不正确
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_AGENTLEVEL_ERROR_MSG);
            }

            PfBorder pfBorder = bOrderService.getPfBorderBySkuAndUserId(skuId, user.getId());
            if (pfBorder != null) {
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_ALREADY_HAS_AGENTORDER);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_ALREADY_HAS_AGENTORDER_MSG);
                res.setOrderId(pfBorder.getId());
                throw new BusinessException("您已经有了此款产品的合伙订单，订单号编码为:" + pfBorder.getOrderCode()+"，请去合伙人订单中完成合伙人申请。");
            }

            log.info("接口参数校验结束");

            // 创建订单
            BOrderAdd bOrderAdd = new BOrderAdd();

            bOrderAdd.setOrderType(BOrderType.agent.getCode());
            bOrderAdd.setpUserId(userPid);
            bOrderAdd.setUserId(user.getId());
            bOrderAdd.setSendType(1);
            bOrderAdd.setSkuId(skuId);
            bOrderAdd.setAgentLevelId(agentLevelId);
            bOrderAdd.setWeiXinId("");
            bOrderAdd.setUserMessage("");
            bOrderAdd.setUserAddressId(0l);
            bOrderAdd.setUserSource(sourceType);

            Long borderId = bOrderAddService.addBOrder(bOrderAdd);

            log.info("创建代理订单成功,订单id:" + borderId);

            //0元免支付订单，不需要去收银台支付，直接注册成功
            pfBorder = bOrderService.getPfBorderById(borderId);
            if(pfBorder.getReceivableAmount().compareTo(BigDecimal.ZERO) == 0){
                res.setPayType(0);
            }else{
                res.setPayType(1);
            }


            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            res.setOrderId(borderId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }

        return res;
    }

    /**
     * 0元免支付订单回调接口
     * @param request
     * @param req
     * @param user
     * @return
     */
    @RequestMapping("/agentBOrderZeroPay.do")
    @ResponseBody
    @SignValid(paramType = AgentBOrderZeroPayReq.class)
    public AgentBOrderZeroPayRes agentBOrderZeroPay(HttpServletRequest request, AgentBOrderZeroPayReq req, ComUser user){
        AgentBOrderZeroPayRes res = new AgentBOrderZeroPayRes();
        try{
            Long borderId = req.getOrderId();
            if(borderId == null || borderId.longValue() <= 0l){
                // 订单id不正确
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_BORDER_ID_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_BORDER_ID_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_BORDER_ID_ERROR_MSG);
            }
            PfBorder pfBorder = bOrderService.getPfBorderById(borderId);
            if(pfBorder == null){
                // 订单id不正确
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_BORDER_ID_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_BORDER_ID_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_BORDER_ID_ERROR_MSG);
            }
            if(pfBorder.getOrderType().intValue() != 0){
                // 订单非代理订单
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_BORDER_ORDERTYPE_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_BORDER_ORDERTYPE_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_BORDER_ORDERTYPE_ERROR_MSG);
            }
            if(pfBorder.getReceivableAmount().compareTo(BigDecimal.ZERO) != 0){
                //不是0元订单
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_BORDER_ZERO_PAY_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_BORDER_ZERO_PAY_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_BORDER_ZERO_PAY_ERROR_MSG);
            }
            log.info("0元免支付代理订单生成支付信息,订单id:" + borderId);
            PfBorderPayment payment = bOrderService.createPfBorderPaymentByOrderCode(pfBorder.getOrderCode());
            log.info("处理订单开始,类型为B,支付流水号为:" + payment.getPaySerialNum());
            payBOrderService.mainPayBOrder(payment, "ZERO_PAY");

            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        return res;
    }

    @RequestMapping("/applysuccess")
    @ResponseBody
    @SignValid(paramType = AgentApplySuccessReq.class)
    public AgentApplySuccessRes agentApplySuccess(HttpServletRequest request, AgentApplySuccessReq req, ComUser user){
        AgentApplySuccessRes res = new AgentApplySuccessRes();
        try{
            if(req.getOrderId() == null || req.getOrderId().longValue() <= 0l){
                // 订单id不正确
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_BORDER_ID_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_BORDER_ID_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_BORDER_ID_ERROR_MSG);
            }
            PfBorder order = bOrderService.getPfBorderById(req.getOrderId());
            if(order == null){
                // 订单id不正确
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_BORDER_ID_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_BORDER_ID_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_BORDER_ID_ERROR_MSG);
            }
            if(order.getOrderType().intValue() != 0){
                // 订单非代理订单
                res.setResCode(SysResCodeCons.RES_CODE_AGENT_BORDER_ORDERTYPE_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_AGENT_BORDER_ORDERTYPE_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_AGENT_BORDER_ORDERTYPE_ERROR_MSG);
            }
            PfBorderItem item = bOrderService.getPfBorderItemByOrderId(order.getId()).get(0);
            ComUser pUser = userService.getUserById(order.getUserPid());
            ComBrand brand = brandService.getBySkuId(item.getSkuId());
            ComAgentLevel level = comAgentLevelService.selectByPrimaryKey(item.getAgentLevelId());

            res.setUserName(user.getWxNkName());
            res.setpName(pUser.getRealName());
            res.setBrandName(brand.getCname());
            res.setAgentLevelName(level.getName());
            if(user.getAuditStatus().intValue() == 2) {
                res.setIsRealNameAuth(1);
            } else {
                res.setIsRealNameAuth(0);
                res.setNotRealNameAuthDesc("你需要在3天内完成实名认证，逾期店铺将不能访问。");
            }

            if(order.getOrderStatus().intValue() == BOrderStatus.MPS.getCode().intValue()){
                // 排单状态
                Integer count = bOrderService.selectQueuingOrderCount(item.getSkuId());
                res.setIsMPS(1);
                res.setMpsNum(count);

                log.info("该订单处于排单状态，排单人数：" + count);
            } else {
                res.setIsMPS(0);
            }
            res.setOrderId(order.getId());
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }

        return res;
    }

}
