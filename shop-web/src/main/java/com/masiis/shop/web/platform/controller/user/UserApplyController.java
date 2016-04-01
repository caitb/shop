package com.masiis.shop.web.platform.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import org.apache.commons.lang.StringUtils;
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
    public String partnersApply(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam(value = "skuId", required = true) Integer skuId,
                                @RequestParam(value = "pUserId", required = false) Long pUserId,
                                Model model) throws Exception {

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
            checkParentData(pUser, skuId);
            model.addAttribute("", pUserId);
        }
        return "platform/order/shenqing";
    }

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
    }

    @ResponseBody
    @RequestMapping("/checkPMobile.do")
    public String checkPMobile(HttpServletRequest request,
                               @RequestParam(value = "skuId", required = true) Integer skuId,
                               @RequestParam(value = "pMobile", required = true) String pMobile) {
        JSONObject jsonObject = new JSONObject();
        try {
            ComUser pUser = null;
            PfUserSku pfUserSku = null;
            if (StringUtils.isNotBlank(pMobile)) {
                pUser = userService.getUserByMobile(pMobile);
                checkParentData(pUser, skuId);
            } else {
                throw new BusinessException("手机号为空");
            }
            jsonObject.put("isError", false);
            jsonObject.put("pUserId", pUser.getId());
            jsonObject.put("levelId", pfUserSku.getAgentLevelId());
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
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

    /**
     * @param pUser 上级合伙人
     * @param skuId 代理的商品
     * @author ZhaoLiang
     * @date 2016/4/1 12:11
     */
    public void checkParentData(ComUser pUser, Integer skuId) throws Exception {
        if (pUser == null) {
            throw new BusinessException(" 您的推荐人还未注册，请联系您的推荐人先注册!");
        } else {
            PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(pUser.getId(), skuId);
            if (null == pfUserSku || pfUserSku.getIsPay() == 0) {
                throw new BusinessException("您的推荐人还未代理此款商品");
            }
        }
    }

    /**
     * @param pUser        上级合伙人
     * @param skuId        代理的商品
     * @param agentLevelId 自己的代理等级
     * @author ZhaoLiang
     * @date 2016/4/1 12:11
     */
    public PfUserSku checkParentData(ComUser pUser, Integer skuId, Integer agentLevelId) throws Exception {
        PfUserSku pfUserSku = null;
        if (pUser == null) {
            throw new BusinessException(" 您的推荐人还未注册，请联系您的推荐人先注册!");
        } else {
            pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(pUser.getId(), skuId);
            if (null == pfUserSku || pfUserSku.getIsPay() == 0) {
                throw new BusinessException("您的推荐人还未代理此款商品");
            }
            if (pfUserSku.getAgentLevelId() > agentLevelId) {
                throw new BusinessException("您的代理等级不能高于您的推荐人代理等级");
            }
        }
        return pfUserSku;
    }
}
