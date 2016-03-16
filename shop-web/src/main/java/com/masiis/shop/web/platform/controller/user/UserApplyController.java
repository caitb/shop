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
import com.masiis.shop.web.platform.utils.MobileMessageUtil;
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
                                         @RequestParam(value = "name", required = false) String name,
                                         @RequestParam(value = "weixinId", required = false) String weixinId,
                                         @RequestParam(value = "pUserId", required = false) Long pUserId,
                                         @RequestParam(value = "pMobile", required = false) String pMobile,
                                         @RequestParam(value = "levelId", required = false) Integer levelId
    ) {
        ModelAndView mv = new ModelAndView();
        try {
            //获取商品信息
            ComSku comSku = skuService.getSkuById(skuId);
            //获取商品代理信息
            List<PfSkuAgent> pfSkuAgents = null;
            if (pUserId == null) {
                pfSkuAgents = skuAgentService.getAllBySkuId(skuId);
            } else {
                //do
                pfSkuAgents = skuAgentService.getAllBySkuId(skuId);
            }
            //获取代理信息
            List<ComAgentLevel> comAgentLevels = skuAgentService.getComAgentLevel();
            StringBuffer sb = new StringBuffer();
            for (PfSkuAgent pfSkuAgent : pfSkuAgents) {
                String path = request.getContextPath();
                if (pfSkuAgent.getAgentLevelId() == levelId) {
                    sb.append("<input type='radio' id='level" + pfSkuAgent.getAgentLevelId() + "' name='radio'background='" + path + "/static/images/icon_11.png' value='" + pfSkuAgent.getAgentLevelId() + "'/>");
                } else {
                    sb.append("<input type='radio' id='level" + pfSkuAgent.getAgentLevelId() + "' name='radio' name='radio' value='" + pfSkuAgent.getAgentLevelId() + "'/>");
                }
                sb.append("<label for='level" + pfSkuAgent.getAgentLevelId() + "' class='level" + pfSkuAgent.getAgentLevelId() + "'>");
                for (ComAgentLevel comAgentLevel : comAgentLevels) {
                    if (pfSkuAgent.getAgentLevelId() == comAgentLevel.getId()) {
                        sb.append("<span name='levelName'>" + comAgentLevel.getName() + "</span>");
                    }
                }
                sb.append("<b>&nbsp;&nbsp;商品数量：</b> <span name='quantity'>" + pfSkuAgent.getQuantity() + "</span>");
                sb.append("<b>&nbsp;&nbsp;金额：</b> <span name='amount'>" + comSku.getPriceRetail().multiply(BigDecimal.valueOf(pfSkuAgent.getQuantity())) + "</span>");
                sb.append("</label>");
            }
            mv.addObject("agentInfo", sb.toString());
            mv.addObject("skuId", comSku.getId());
            mv.addObject("skuName", comSku.getName());
            //返回修改默认数据
            mv.addObject("name", name);
            mv.addObject("weixinId", weixinId);
            mv.addObject("pMobile", pMobile);
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
                                               @RequestParam(value = "amount", required = true) BigDecimal amount,
                                               @RequestParam(value = "parentMobile", required = true) String parentMobile) {

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
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("代理金额有误");
            }
            if (StringUtils.isNotBlank(parentMobile)) {
                ComUser pUser = userService.getUserByMobile(parentMobile);
                if (pUser == null) {
                    throw new BusinessException("您的推荐人还未注册，请联系您的推荐人先注册!");
                } else {
                    PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(pUser.getId(), skuId);
                    if (pfUserSku == null) {
                        throw new BusinessException("您的推荐人还未代理此款商品");
                    } else {
                        if (pfUserSku.getAgentLevelId() >= levelId) {
                            throw new BusinessException("您的代理等级只能低于您的推荐人代理等级");
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
                                                @RequestParam(value = "parentUserId", required = true) Long parentUserId,
                                                @RequestParam(value = "parentMobile", required = true) String parentMobile) {
//        ComUser comUser = userService.getUserByMobile(parentMobile);
//        if (comUser == null) {
//            throw new BusinessException("");
//        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("platform/order/zhuce2");
        modelAndView.addObject("name", name);
        modelAndView.addObject("weixinId", weixinId);
        modelAndView.addObject("parentUserId", parentUserId);
        modelAndView.addObject("parentMobile", parentMobile);
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
}
