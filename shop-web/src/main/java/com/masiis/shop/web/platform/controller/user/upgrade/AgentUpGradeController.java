package com.masiis.shop.web.platform.controller.user.upgrade;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfSkuAgent;
import com.masiis.shop.dao.po.PfUserRelation;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.dao.po.extendPo.UserSkuAgent;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.user.PfUserRelationService;
import com.masiis.shop.web.platform.service.user.PfUserSkuService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * 代理升级Controller
 * Created by wangbingjian on 2016/6/14.
 */
@Controller
@RequestMapping(value = "/upgrade")
public class AgentUpGradeController extends BaseController {

    private static final Logger logger = Logger.getLogger(AgentUpGradeController.class);
    @Autowired
    private PfUserSkuService pfUserSkuService;
    @Autowired
    private PfUserRelationService pfUserRelationService;

    /**
     * 初始化我要升级首页
     * @param request
     * @return
     */
    @RequestMapping(value = "/init.shtml")
    public ModelAndView initUpGrade(HttpServletRequest request){
        logger.info("初始化我要升级首页");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户未登录");
        }
        ModelAndView mv = new ModelAndView();
        logger.info("查询当前商品代理等级，用户id = " + comUser.getId());
        List<UserSkuAgent> skuAgents = pfUserSkuService.getCurrentAgentLevel(comUser.getId());
        if (skuAgents == null || skuAgents.size() == 0){
            throw new BusinessException("该用户当前没有代理商品");
        }
        mv.addObject("userSkuAgents",skuAgents);
        mv.setViewName("platform/user/upgrade/upGrade");
        return mv;
    }

    /**
     * 获取代理用户可以升级的等级
     * @param skuId         商品skuid
     * @param agentLevelId  当前代理等级id
     * @param userPid       上级userid
     * @param skuName       商品名称
     * @param agentName     当前代理名称
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUpGradePackage.do")
    @ResponseBody
    public String getUpGradePackage(@RequestParam(value = "skuId", required = true) Integer skuId,
                                    @RequestParam(value = "agentLevelId", required = true) Integer agentLevelId,
                                    @RequestParam(value = "userPid", required = true) Long userPid,
                                    @RequestParam(value = "skuName", required = true) String skuName,
                                    @RequestParam(value = "agentName", required = true) String agentName,
                                    HttpServletRequest request){
        logger.info("获取代理用户可以升级的等级");
        ComUser comUser = getComUser(request);
        JSONObject jsonObject = new JSONObject();
        if (comUser == null){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","用户未登录");
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        if (userPid.longValue() == 0){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","直接上级为平台，无上级代理");
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        logger.info("查询上级用户代理等级begin");
        PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userPid,skuId);
        if (pfUserSku == null){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","上级代理信息为空");
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        Integer pLevelId = pfUserSku.getAgentLevelId();
        logger.info("上级用户：" + pfUserSku.getUserId() + "：：：skuId = " + skuId + "：：：代理级别：" + pLevelId);
        logger.info("查询上级用户代理等级end");

        logger.info("获取当前用户商品代理等级信息begin");
        PfSkuAgent currentSkuAgent = pfUserSkuService.getCurrentSkuAgent(skuId, agentLevelId);
        if (currentSkuAgent == null){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","用户代理等级信息有误");
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        logger.info("获取当前用户商品代理等级信息end");

        logger.info("获取用户可以升级的代理级别信息begin");
        List<PfSkuAgent> pfSkuAgents = pfUserSkuService.getUpgradeAgents(skuId, agentLevelId, pLevelId);
        if (pfSkuAgents == null || pfSkuAgents.size() == 0){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","暂时无法升级");
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        StringBuffer sb = new StringBuffer();
        NumberFormat rmbFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
        StringBuffer data;
        for (PfSkuAgent pfSkuAgent : pfSkuAgents){
//           n>(包含商品" + pfSkuAgent.getQuantity() + "件　保证金差额：" + rmbFormat.format(pfSkuAgent.getBail().subtract(currentSkuAgent.getBail())) + ")</span>");
        }
        logger.info("获取用户可以升级的代理级别信息end");
        jsonObject.put("isTrue","true");
        jsonObject.put("message",sb.toString());
        logger.info(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }
}
