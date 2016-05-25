package com.masiis.shop.api.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.base.BaseReq;
import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.bean.order.BAgentOrderAddReq;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.common.enums.BOrder.BOrderType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.order.BOrderAdd;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfSkuAgent;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Date 2016/5/24
 * @Auther lzh
 */
@Controller
@RequestMapping("/agentborder")
public class AgentBOrderController extends BaseController {

    @RequestMapping("/add")
    @ResponseBody
    @SignValid(paramType = BAgentOrderAddReq.class)
    public BaseRes addNewAgentBOrder(HttpServletRequest request, BAgentOrderAddReq req, ComUser user){
        JSONObject jsonObject = new JSONObject();
        /*try {
            if (skuId == null || skuId <= 0) {
                throw new BusinessException("参数校验失败：skuId:" + skuId);
            }
            if (agentLevelId == null || agentLevelId <= 0) {
                throw new BusinessException("参数校验失败：agentLevelId:" + agentLevelId);
            }
            if (StringUtils.isBlank(weiXinId)) {
                throw new BusinessException("参数校验失败：weiXinId:" + weiXinId);
            }
            ComUser comUser = getComUser(request);
            PfBorder pfBorder = bOrderService.getPfBorderBySkuAndUserId(skuId, comUser.getId());
            if (pfBorder != null) {
                throw new BusinessException("您已经有了此款产品的代理订单，订单号编码为:" + pfBorder.getOrderCode());
            }
            Long pUserId = pfUserRelationService.getPUserId(comUser.getId(), skuId);
            if (comUser.getSendType() > 0) {
                sendType = comUser.getSendType();
            } else if (pUserId > 0) {
                sendType = userService.getUserById(pUserId).getSendType();
            }
            if (sendType == null && sendType <= 0) {
                throw new BusinessException("参数校验失败：sendType:" + sendType);
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
            bOrderAdd.setUserId(comUser.getId());
            bOrderAdd.setSendType(sendType);
            bOrderAdd.setSkuId(skuId);
            bOrderAdd.setAgentLevelId(agentLevelId);
            bOrderAdd.setWeiXinId(weiXinId);
            bOrderAdd.setUserMessage(userMessage);
            bOrderAdd.setUserAddressId(userAddressId);
            Long bOrderId = bOrderAddService.addBOrder(bOrderAdd);
            jsonObject.put("isError", false);
            jsonObject.put("bOrderId", bOrderId);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }*/
        return null;
    }
}
