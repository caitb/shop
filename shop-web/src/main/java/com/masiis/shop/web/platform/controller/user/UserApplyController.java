package com.masiis.shop.web.platform.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.beans.order.AgentSkuView;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.PfUserRelationService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import com.masiis.shop.web.platform.utils.wx.WxUserUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * UserApplyController
 *
 * @author ZhaoLiang
 * @date 2016/3/15
 */
@Controller
@RequestMapping("/userApply")
public class UserApplyController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private SkuService skuService;
    @Resource
    private UserService userService;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private PfUserRelationService pfUserRelationService;

    /**
     * 合伙人申请
     *
     * @author ZhaoLiang
     * @date 2016/3/5 13:51
     */
    @RequestMapping("/apply.shtml")
    public ModelAndView apply(HttpServletRequest request,
                              @RequestParam(value = "skuId", required = true) Integer skuId,
                              @RequestParam(value = "pUserId", required = false) Long pUserId) throws Exception {
        ModelAndView res = new ModelAndView("platform/order/agent/apply");
        ComUser user = getComUser(request);
        if (user == null) {
            throw new BusinessException("用户未登录!");
        }
        ComSku sku = skuService.getSkuById(skuId);
        if (sku == null) {
            throw new BusinessException("sku不合法,系统不存在该sku");
        }
        if (pUserId != null && pUserId > 0) {
            PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(user.getId(), skuId);
            if (pfUserSku != null && pfUserSku.getIsCertificate() == 1) {
                res = new ModelAndView("platform/order/agent/applied");
                return res;
            }
        }
        Long temPUserId = pfUserRelationService.getPUserId(user.getId(), skuId);
        if (temPUserId == 0) {
            if (pUserId != null && pUserId > 0) {
                //校验上级合伙人数据是否合法,如果合法则建立临时绑定关系
                userSkuService.checkParentData(user, pUserId, skuId);
                PfUserRelation pfUserRelation = new PfUserRelation();
                pfUserRelation.setUserId(user.getId());
                pfUserRelation.setSkuId(skuId);
                pfUserRelation.setCreateTime(new Date());
                pfUserRelation.setIsEnable(1);
                pfUserRelation.setUserPid(pUserId);
                pfUserRelationService.insert(pfUserRelation);
            } else {
                pUserId = 0l;
            }
        } else {
            pUserId = temPUserId;
        }
        boolean isQueuing = false;
        Integer count = 0;
        int status = skuService.getSkuStockStatus(skuId, 1, pUserId);
        if (status == 1) {
            isQueuing = true;
            count = bOrderService.selectQueuingOrderCount(skuId);
        }
//        boolean isUserForcus = WxUserUtils.getInstance().isUserForcusPF(user);
        res.addObject("user", userService.getUserById(user.getId()));
        res.addObject("skuId", skuId);
        res.addObject("isQueuing", isQueuing);
        res.addObject("count", count);
//        res.addObject("isUserForcus", isUserForcus);
        return res;
    }

    /**
     * 合伙人注册
     *
     * @author ZhaoLiang
     * @date 2016/3/5 14:27
     */
    @RequestMapping("/register.shtml")
    public ModelAndView register(HttpServletRequest request,
                                 @RequestParam(value = "skuId", required = true) Integer skuId) throws Exception {
        ModelAndView modelAndView = new ModelAndView("platform/order/agent/register");
        ComUser comUser = getComUser(request);
        if (comUser == null) {
            throw new BusinessException("用户未登录!");
        }
        if (skuId == null || skuId < 0) {
            log.error("skuId不合法,skuId:" + skuId + ",用户id为:" + comUser.getId());
            throw new BusinessException("skuId不合法!");
        }
        //获取商品信息
        ComSku comSku = skuService.getSkuById(skuId);
        if (comSku == null) {
            log.error("该skuId对应的商品不存在,skuId:" + skuId);
            throw new BusinessException("该skuId对应的商品不存在");
        }
        //获取商品代理信息
        List<PfSkuAgent> pfSkuAgents = skuAgentService.getAllBySkuId(skuId);
        //获取代理信息
        List<ComAgentLevel> comAgentLevels = skuAgentService.getComAgentLevel();
        //上级代理等级
        Integer pUserLevelId = 0;
        Long pUserId = pfUserRelationService.getPUserId(comUser.getId(), skuId);
        if (pUserId > 0) {
            PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(pUserId, skuId);
            pUserLevelId = pfUserSku.getAgentLevelId();
        }
        // 创建该sku代理商的代理门槛信息
        List<AgentSkuView> agentSkuViews = new ArrayList<AgentSkuView>();
        for (PfSkuAgent pfSkuAgent : pfSkuAgents) {
            AgentSkuView view = new AgentSkuView();
            view.setAgent(pfSkuAgent);
            for (ComAgentLevel comAgentLevel : comAgentLevels) {
                if (pfSkuAgent.getAgentLevelId() == comAgentLevel.getId()) {
                    view.setLevel(comAgentLevel);
                }
            }
            BigDecimal amount = comSku.getPriceRetail().multiply(BigDecimal.valueOf(pfSkuAgent.getQuantity())).multiply(pfSkuAgent.getDiscount());
            // 总金额加上保证金
            amount = amount.add(pfSkuAgent.getBail()).setScale(2, RoundingMode.HALF_DOWN);
            view.setAgentFee(amount);
            view.setSinFee(comSku.getPriceRetail().multiply(pfSkuAgent.getDiscount()).setScale(2, RoundingMode.HALF_DOWN));
            agentSkuViews.add(view);
        }
        modelAndView.addObject("skuId", comSku.getId());
        modelAndView.addObject("skuName", comSku.getName());
        modelAndView.addObject("pUserLevelId", pUserLevelId);
        modelAndView.addObject("pUserId", pUserId);
        modelAndView.addObject("agentSkuViews", agentSkuViews);
        Integer sendType = 0;
        if (comUser.getSendType() > 0) {
            sendType = comUser.getSendType();
        }
        if (pUserId != null && pUserId > 0) {
            ComUser pUser = userService.getUserById(pUserId);
            // 上级代理商品关系
            modelAndView.addObject("pWxNkName", pUser.getWxNkName());
            if (sendType == 0) {
                sendType = pUser.getSendType();
            }
        } else {
            modelAndView.addObject("pWxNkName", "");
        }
        modelAndView.addObject("sendType", sendType);
        boolean isQueuing = false;
        Integer count = 0;
        int status = skuService.getSkuStockStatus(skuId, 1, pUserId);
        if (status == 1) {
            isQueuing = true;
            count = bOrderService.selectQueuingOrderCount(skuId);
        }
        modelAndView.addObject("isQueuing", isQueuing);
        modelAndView.addObject("count", count);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/register/save.do")
    public String registerSave(HttpServletRequest request,
                               @RequestParam(value = "skuId", required = true) Integer skuId,
                               @RequestParam(value = "pUserId", required = true) Long pUserId) throws Exception {
        JSONObject jsonObject = new JSONObject();
        try {
            if (pUserId != null && pUserId > 0) {
                ComUser comUser = getComUser(request);
                Long temPUserId = pfUserRelationService.getPUserId(comUser.getId(), skuId);
                if (temPUserId == 0) {
                    //校验上级合伙人数据是否合法,如果合法则建立临时绑定关系
                    userSkuService.checkParentData(comUser, pUserId, skuId);
                    PfUserRelation pfUserRelation = new PfUserRelation();
                    pfUserRelation.setCreateTime(new Date());
                    pfUserRelation.setUserId(comUser.getId());
                    pfUserRelation.setSkuId(skuId);
                    pfUserRelation.setIsEnable(1);
                    pfUserRelation.setUserPid(pUserId);
                    pfUserRelationService.insert(pfUserRelation);
                }
            }
            jsonObject.put("isError", false);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                jsonObject.put("isError", true);
                jsonObject.put("message", ex.getMessage());
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return jsonObject.toJSONString();
    }

    @ResponseBody
    @RequestMapping("/checkPMobile.do")
    public String checkPMobile(HttpServletRequest request,
                               @RequestParam(value = "skuId", required = true) Integer skuId,
                               @RequestParam(value = "pMobile", required = true) String pMobile,
                               @RequestParam(value = "agentLevel", required = false) Integer agentLevel) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (skuId == null || skuId <= 0) {
                throw new BusinessException("商品skuId有误");
            }
            if (StringUtils.isBlank(pMobile)) {
                throw new BusinessException("手机号有误");
            }
            ComUser user = getComUser(request);
            ComUser pUser = null;
            if (StringUtils.isNotBlank(pMobile)) {
                pUser = userService.getUserByMobile(pMobile);
                if (pUser == null) {
                    throw new BusinessException("此手机号还没有注册");
                }
                if (agentLevel == null || agentLevel <= 0) {
                    //校验上级合伙人数据是否合法
                    userSkuService.checkParentData(user, pUser.getId(), skuId);
                } else {
                    //校验上级合伙人数据是否合法
                    userSkuService.checkParentData(user, pUser.getId(), skuId, agentLevel);
                }
            } else {
                throw new BusinessException("手机号为空");
            }
            jsonObject.put("isError", false);
            jsonObject.put("pUserId", pUser.getId());
            jsonObject.put("sendType", pUser.getSendType());
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                jsonObject.put("isError", true);
                jsonObject.put("message", ex.getMessage());
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return jsonObject.toJSONString();
    }
}
