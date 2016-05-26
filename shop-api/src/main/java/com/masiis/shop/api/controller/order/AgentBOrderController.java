package com.masiis.shop.api.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.base.BaseReq;
import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.bean.order.BAgentOrderAddReq;
import com.masiis.shop.api.bean.order.BAgentOrderAddRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.order.BOrderAddService;
import com.masiis.shop.api.service.order.BOrderService;
import com.masiis.shop.api.service.product.SkuAgentService;
import com.masiis.shop.api.service.product.SkuService;
import com.masiis.shop.api.service.user.ComUserService;
import com.masiis.shop.api.service.user.PfUserRelationService;
import com.masiis.shop.api.service.user.UserAddressService;
import com.masiis.shop.api.service.user.UserSkuService;
import com.masiis.shop.common.enums.BOrder.BOrderType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.order.BOrderAdd;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfSkuAgent;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Date 2016/5/24
 * @Auther lzh
 */
@Controller
@RequestMapping("/agentborder")
public class AgentBOrderController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private SkuService skuService;
    @Resource
    private ComUserService userService;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private BOrderAddService bOrderAddService;
    @Resource
    private PfUserRelationService pfUserRelationService;

    @RequestMapping("/add")
    @ResponseBody
    @SignValid(paramType = BAgentOrderAddReq.class)
    public BAgentOrderAddRes addNewAgentBOrder(HttpServletRequest request, BAgentOrderAddReq req, ComUser user){
        BAgentOrderAddRes res = new BAgentOrderAddRes();
        Integer skuId = req.getSkuId();
        Integer agentLevelId = req.getAgentLevelId();
        String weiXinId = req.getWxId();
        Integer sendType = user.getSendType();
        Long userAddressId = req.getUserAddressId();
        String userMessage = req.getUserMessage();
        try {
            if (skuId == null || skuId <= 0) {
                res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL);
                res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL_MSG);
                log.error(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL_MSG);
                return res;
            }
            if (agentLevelId == null || agentLevelId <= 0) {
                res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_AGENTLEVELID_INVALID);
                res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_AGENTLEVELID_INVALID_MSG);
                log.error(SysResCodeCons.RES_CODE_UPAPPLY_AGENTLEVELID_INVALID_MSG);
                return res;
            }
            if (StringUtils.isBlank(weiXinId)) {
                res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_WXID_INVALID);
                res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_WXID_INVALID_MSG);
                log.error(SysResCodeCons.RES_CODE_UPAPPLY_WXID_INVALID_MSG);
                return res;
            }
            PfBorder pfBorder = bOrderService.getPfBorderBySkuAndUserId(skuId, user.getId());
            if (pfBorder != null) {
                throw new BusinessException("您已经有了此款产品的代理订单，订单号编码为:" + pfBorder.getOrderCode());
            }
            Long pUserId = pfUserRelationService.getPUserId(user.getId(), skuId);
            if (user.getSendType() > 0) {
                sendType = user.getSendType();
            } else if (pUserId > 0) {
                sendType = userService.getUserById(pUserId).getSendType();
            }
            if (StringUtils.isBlank(userMessage)) {
                userMessage = "";
            }
            if (sendType == 2 && (userAddressId == null || userAddressId <= 0)) {
                throw new BusinessException("参数校验失败：userAddressId:" + userAddressId);
            }
            PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, agentLevelId);
            int status = skuService.getSkuStockStatus(skuId, pfSkuAgent.getQuantity(), pUserId);
            if (status == 2) {
                throw new BusinessException("您的上级合伙人库存不足，请联系补货");
            }
            BOrderAdd bOrderAdd = new BOrderAdd();
            bOrderAdd.setOrderType(BOrderType.agent.getCode());
            bOrderAdd.setpUserId(pUserId);
            bOrderAdd.setUserId(user.getId());
            bOrderAdd.setSendType(sendType);
            bOrderAdd.setSkuId(skuId);
            bOrderAdd.setAgentLevelId(agentLevelId);
            bOrderAdd.setWeiXinId(weiXinId);
            bOrderAdd.setUserMessage(userMessage);
            bOrderAdd.setUserAddressId(userAddressId);
            Long bOrderId = bOrderAddService.addBOrder(bOrderAdd);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return null;
    }
}
