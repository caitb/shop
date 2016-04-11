package com.masiis.shop.web.platform.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.beans.order.AgentSkuView;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
    private ProductService productService;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private SkuService skuService;
    @Resource
    private UserService userService;
    @Resource
    private UserSkuService userSkuService;

    /**
     * 合伙人申请
     *
     * @author ZhaoLiang
     * @date 2016/3/5 13:51
     */
    @RequestMapping("/apply.shtml")
    public ModelAndView partnersApply(HttpServletRequest request,
                                      HttpServletResponse response,
                                      @RequestParam(value = "skuId", required = true) Integer skuId,
                                      @RequestParam(value = "pUserId", required = false) Long pUserId) throws Exception {
        ModelAndView res = new ModelAndView();
        ComUser user = getComUser(request);
        if (user == null) {
            throw new BusinessException("用户未登录!");
        }
        ComSku sku = skuService.getSkuById(skuId);
        if (sku == null) {
            throw new BusinessException("sku不合法,系统不存在该sku");
        }
        if (pUserId != null && pUserId > 0) {
            ComUser pUser = userService.getUserById(pUserId);
            userSkuService.checkParentData(pUser, skuId);
            res.addObject("pUserId", pUserId);
        }

        // 判断排单标志位,如果处于排单状态下,显示排单人数

        res.addObject("user", userService.getUserById(user.getId()));
        res.addObject("skuId", skuId);
        res.setViewName("platform/order/shenqing");
        return res;
    }

    /**
     * 合伙人注册
     *
     * @author ZhaoLiang再次
     * @date 2016/3/5 14:27
     *//*
    @RequestMapping("/register.shtml")
    public ModelAndView partnersRegister(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @RequestParam(value = "skuId", required = true) Integer skuId,
                                         @RequestParam(value = "pUserId", required = false) Long pUserId) throws Exception {
        ModelAndView mv = new ModelAndView();
        ComUser comUser = getComUser(request);
        //获取商品信息
        ComSku comSku = skuService.getSkuById(skuId);
        //获取商品代理信息
        List<PfSkuAgent> pfSkuAgents = skuAgentService.getAllBySkuId(skuId);
        int levelID = 0;
        if (pUserId != null && pUserId > 0) {
            ComUser pComUser = userService.getUserById(pUserId);
            PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(pUserId, skuId);
            checkParentData(pComUser, skuId);
            levelID = pfUserSku.getAgentLevelId();
        }
        //获取代理信息
        List<ComAgentLevel> comAgentLevels = skuAgentService.getComAgentLevel();
        StringBuffer sb = new StringBuffer();
        for (PfSkuAgent pfSkuAgent : pfSkuAgents) {
            if (pfSkuAgent.getAgentLevelId() > levelID) {
                if (pfSkuAgent.getAgentLevelId() == 1) {
                    sb.append("<p class='on' levelId='" + pfSkuAgent.getAgentLevelId() + "'>");
                } else {
                    sb.append("<p levelId='" + pfSkuAgent.getAgentLevelId() + "'>");
                }
                for (ComAgentLevel comAgentLevel : comAgentLevels) {
                    if (pfSkuAgent.getAgentLevelId() == comAgentLevel.getId()) {
                        sb.append("<label name='levelName' style='font-size: 12px;'>" + comAgentLevel.getName() + "</label>");
                    }
                }
                BigDecimal amount = comSku.getPriceRetail().multiply(BigDecimal.valueOf(pfSkuAgent.getQuantity())).multiply(pfSkuAgent.getDiscount());
                amount = amount.setScale(2, RoundingMode.HALF_DOWN);
                sb.append("<b style='padding-left: 10px;'>商品数量:</b> <span name='quantity'>" + pfSkuAgent.getQuantity() + "</span>");
                sb.append("<b style='padding-left: 10px;'>金额:</b> <span name='amount'>" + amount + "</span>");
                sb.append("</p>");
            }
        }
        if (StringUtils.isBlank(sb.toString())) {
            throw new BusinessException("您的推荐人还不能发展下级代理");
        }
        mv.addObject("agentInfo", sb.toString());
        mv.addObject("skuId", comSku.getId());
        mv.addObject("skuName", comSku.getName());
        mv.addObject("pUserId", pUserId);
        if (pUserId != null && pUserId > 0) {
            mv.addObject("pWxNkName", userService.getUserById(pUserId).getWxNkName());
        } else {
            mv.addObject("pWxNkName", "");
        }
        mv.setViewName("platform/order/zhuce");
        return mv;
    }*/

    /**
     * 合伙人注册
     *
     * @author ZhaoLiang再次
     * @date 2016/3/5 14:27
     */
    @RequestMapping("/register.shtml")
    public ModelAndView partnersRegister(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @RequestParam(value = "skuId", required = true) Integer skuId,
                                         @RequestParam(value = "pUserId", required = false) Long pUserId) throws Exception {
        ModelAndView mv = new ModelAndView();
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
        // 上级代理等级id(0表示没有上级推荐)
        Integer pUserLevelId = 0;
        if (pUserId != null && pUserId > 0) {
            ComUser pComUser = userService.getUserById(pUserId);
            if (pComUser == null) {
                log.error("上级代理id不合法,pUserId:" + pUserId);
                throw new BusinessException("上级代理id不合法!");
            }
            userSkuService.checkParentData(pComUser, skuId);
            PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(pUserId, skuId);
            if (pfUserSku.getAgentLevelId() >= 3) {
                throw new BusinessException("您的推荐人还不能发展下级代理");
            }
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
            amount.add(pfSkuAgent.getBail());
            amount = amount.setScale(2, RoundingMode.HALF_DOWN);
            view.setAgentFee(amount);
            view.setSinFee(comSku.getPriceRetail().multiply(pfSkuAgent.getDiscount()).setScale(2, RoundingMode.HALF_DOWN));
            agentSkuViews.add(view);
        }
        mv.addObject("skuId", comSku.getId());
        mv.addObject("skuName", comSku.getName());
        mv.addObject("pUserLevelId", pUserLevelId);
        mv.addObject("pUserId", pUserId);
        mv.addObject("agentSkuViews", agentSkuViews);
        if (pUserId != null && pUserId > 0) {
            // 上级代理商品关系
            mv.addObject("pWxNkName", userService.getUserById(pUserId).getWxNkName());
        } else {
            mv.addObject("pWxNkName", "");
        }
        mv.setViewName("platform/order/zhuce");
        return mv;
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
            ComUser pUser = null;
            PfUserSku pfUserSku = null;
            if (StringUtils.isNotBlank(pMobile)) {
                pUser = userService.getUserByMobile(pMobile);
                if (agentLevel == null || agentLevel <= 0) {
                    userSkuService.checkParentData(pUser, skuId);
                } else {
                    userSkuService.checkParentData(pUser, skuId, agentLevel);
                }
            } else {
                throw new BusinessException("手机号为空");
            }
            pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(pUser.getId(), skuId);

            jsonObject.put("isError", false);
            jsonObject.put("pUserId", pUser.getId());
            jsonObject.put("levelId", pfUserSku.getAgentLevelId());
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

    /**
     * 申请完成
     *
     * @author ZhaoLiang
     * @date 2016/3/15 17:03
     */
    @RequestMapping("/applyOK.shtml")
    public ModelAndView applyOK() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("platform/order/shenqingok");
        return modelAndView;
    }


}
