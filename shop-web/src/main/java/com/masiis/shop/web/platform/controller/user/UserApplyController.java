package com.masiis.shop.web.platform.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.product.ProductSimple;
import com.masiis.shop.dao.po.*;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * UserApplyController
 *
 * @author ZhaoLiang
 * @date 2016/3/15
 */
@Controller
@RequestMapping("/userApply")
public class UserApplyController {
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
                                      @RequestParam(value = "pUserId", required = false) Long pUserId) {
        ModelAndView mv = new ModelAndView();
        try {
            String skuImg = PropertiesUtils.getStringValue("index_product_220_220_url");
            ProductSimple productSimple = productService.getSkuSimple(skuId);
            mv.addObject("skuId", skuId);
            mv.addObject("skuName", productSimple.getSkuName());
            mv.addObject("skuImg", skuImg + productSimple.getSkuDefaultImgURL());
            mv.addObject("slogan", productSimple.getSlogan());
            mv.addObject("pUserId", pUserId);
            mv.setViewName("platform/order/shenqing");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return mv;
    }

    /**
     * 合伙人注册一
     *
     * @author ZhaoLiang
     * @date 2016/3/5 14:27
     */
    @RequestMapping("/register.shtml")
    public ModelAndView partnersRegister(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @RequestParam(value = "skuId", required = true) Integer skuId,
                                         @RequestParam(value = "pUserId", required = false) Long pUserId
    ) {
        ModelAndView mv = new ModelAndView();
        try {
            ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
            //获取商品信息
            ComSku comSku = skuService.getSkuById(skuId);
            //获取商品代理信息
            List<PfSkuAgent> pfSkuAgents = null;
            if (pUserId == null || pUserId == 0) {
                pfSkuAgents = skuAgentService.getAllBySkuId(skuId);
            } else {
                //do
                pfSkuAgents = skuAgentService.getAllBySkuId(skuId);
            }
            //获取代理信息
            List<ComAgentLevel> comAgentLevels = skuAgentService.getComAgentLevel();
            StringBuffer sb = new StringBuffer();
            for (PfSkuAgent pfSkuAgent : pfSkuAgents) {
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
                sb.append("<b style='padding-left: 10px;'>商品数量:</b> <span name='quantity'>" + pfSkuAgent.getQuantity() + "</span>");
                sb.append("<b style='padding-left: 10px;'>金额:</b> <span name='amount'>" + comSku.getPriceRetail().multiply(BigDecimal.valueOf(pfSkuAgent.getQuantity())) + "</span>");
                sb.append("</p>");
            }
            mv.addObject("agentInfo", sb.toString());
            mv.addObject("skuId", comSku.getId());
            mv.addObject("skuName", comSku.getName());
            mv.addObject("name", comUser.getRealName());
            mv.addObject("weixinId", comUser.getWxId());
            mv.addObject("mobile", comUser.getMobile());
            mv.addObject("pUserId", pUserId);
            mv.setViewName("platform/order/zhuce");
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return mv;
    }

    /**
     * 合伙人注册数据验证
     *
     * @author ZhaoLiang
     * @date 2016/3/5 14:27
     */
    @ResponseBody
    @RequestMapping("/registerConfirm/check.do")
    public String partnersRegisterConfirmCheck(HttpServletRequest request,
                                               HttpServletResponse response,
                                               @RequestParam(value = "name", required = true) String name,
                                               @RequestParam(value = "weixinId", required = true) String weixinId,
                                               @RequestParam(value = "skuId", required = true) Integer skuId,
                                               @RequestParam(value = "levelId", required = true) Long levelId,
                                               @RequestParam(value = "pMobile", required = true) String pMobile) {

        JSONObject object = new JSONObject();
        try {
            if (StringUtils.isBlank(name)) {
                throw new BusinessException("名称不能为空");
            }
            if (StringUtils.isBlank(weixinId)) {
                throw new BusinessException("微信号不能为空");
            }
            if (levelId <= 0) {
                throw new BusinessException("代理等级有误");
            }
            if (StringUtils.isNotBlank(pMobile)) {
                ComUser pUser = userService.getUserByMobile(pMobile);
                if (pUser == null) {
                    throw new BusinessException(" 您的推荐人还未注册，请联系您的推荐人先注册!");
                } else {
                    PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(pUser.getId(), skuId);
                    if (null == pfUserSku) {
                        throw new BusinessException("您的推荐人还未代理此款商品");
                    } else {
                        if (pfUserSku.getAgentLevelId() >= levelId) {
                            throw new BusinessException(" 您的代理等级只能低于您的推荐人代理等级");
                        }
                    }
                }
            }
            object.put("isError", false);
        } catch (Exception ex) {
            object.put("isError", true);
            object.put("message", ex.getMessage());
        }
        return object.toJSONString();
    }

    /**
     * 合伙人注册确认
     *
     * @author ZhaoLiang
     * @date 2016/3/5 14:27
     */
    @RequestMapping("/registerConfirm.shtml")
    public ModelAndView partnersRegisterConfirm(HttpServletRequest request,
                                                HttpServletResponse response,
                                                @RequestParam(value = "name", required = true) String name,
                                                @RequestParam(value = "weixinId", required = true) String weixinId,
                                                @RequestParam(value = "skuId", required = true) Long skuId,
                                                @RequestParam(value = "skuName", required = true) String skuName,
                                                @RequestParam(value = "levelId", required = true) Long levelId,
                                                @RequestParam(value = "levelName", required = true) String levelName,
                                                @RequestParam(value = "amount", required = true) BigDecimal amount,
                                                @RequestParam(value = "pUserId", required = true) Long pUserId,
                                                @RequestParam(value = "pMobile", required = true) String pMobile) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("platform/order/zhuce2");
        modelAndView.addObject("name", name);
        modelAndView.addObject("weixinId", weixinId);
        modelAndView.addObject("pUserId", pUserId);
        modelAndView.addObject("pMobile", pMobile);
        modelAndView.addObject("skuId", skuId);
        modelAndView.addObject("skuName", skuName);
        modelAndView.addObject("levelId", levelId);
        modelAndView.addObject("levelName", levelName);
        modelAndView.addObject("amount", amount);

        return modelAndView;
    }

    /**
     * 申请完成
     *
     * @author ZhaoLiang
     * @date 2016/3/15 17:03
     */
    @RequestMapping("/applyOK.shtml")
    public ModelAndView applyOK() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("platform/order/shenqingok");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/checkPMobile.do")
    public String checkPMobile(HttpServletRequest request,
                               @RequestParam(value = "skuId", required = true) Integer skuId,
                               @RequestParam(value = "pMobile", required = true) String pMobile) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (StringUtils.isBlank(pMobile)) {
                throw new BusinessException("推荐人电话不能为空");
            }
            ComUser comUser = userService.getUserByMobile(pMobile);
            if (comUser == null) {
                throw new BusinessException("推荐人电话有误");
            }
            PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(), skuId);
            if (pfUserSku == null) {
                throw new BusinessException("推荐人还没有代理过此产品");
            }
            if (pfUserSku.getAgentLevelId() == 3) {
                throw new BusinessException("推荐人等级原因无法发展下级代理");
            }
            jsonObject.put("isError", false);
            jsonObject.put("pUserId", comUser.getId());
            jsonObject.put("levelId", pfUserSku.getAgentLevelId());
        } catch (Exception ex) {
            jsonObject.put("isError", true);
            jsonObject.put("message", ex.getMessage());
        }
        return jsonObject.toJSONString();
    }
}
