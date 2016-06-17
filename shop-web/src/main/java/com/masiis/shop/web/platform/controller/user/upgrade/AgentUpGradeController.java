package com.masiis.shop.web.platform.controller.user.upgrade;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.enums.upgrade.UpGradeUpStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfSkuAgent;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.dao.po.PfUserUpgradeNotice;
import com.masiis.shop.dao.beans.extendPo.UpGradeInfoPo;
import com.masiis.shop.dao.beans.extendPo.UserSkuAgent;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.user.PfUserSkuService;
import com.masiis.shop.web.platform.service.user.UpgradeNoticeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private UpgradeNoticeService upgradeNoticeService;

    /**
     * 初始化我要升级首页
     * @param request   servlet
     * @return          mv
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
     * @param request       servlet
     * @return
     */
    @RequestMapping(value = "/getUpGradePackage.do", method = RequestMethod.POST)
    @ResponseBody
    public String getUpGradePackage(@RequestParam(value = "skuId", required = true) Integer skuId,
                                    @RequestParam(value = "agentLevelId", required = true) Integer agentLevelId,
                                    @RequestParam(value = "userPid", required = true) Long userPid,
                                    @RequestParam(value = "skuName", required = true) String skuName,
                                    @RequestParam(value = "agentName", required = true) String agentName,
                                    HttpServletRequest request) throws Exception{
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
            data = new StringBuffer(skuName+","+agentLevelId+","+agentName+","+pfSkuAgent.getAgentLevelId()+","+pfSkuAgent.getRemark());
            sb.append("<p onclick=\"choiceAgent('" + data + "')\">");
            sb.append(pfSkuAgent.getRemark());
            sb.append("<span>(包含商品" + pfSkuAgent.getQuantity() + "件　保证金差额：" + rmbFormat.format(pfSkuAgent.getBail().subtract(currentSkuAgent.getBail())) + ")</span>");
        }
        logger.info("获取用户可以升级的代理级别信息end");
        jsonObject.put("isTrue","true");
        jsonObject.put("message",sb.toString());
        logger.info(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }

    /**
     * 代理确认升级申请
     * @param curAgentLevel 当前代理等级
     * @param upgradeLevel  申请代理等级
     * @param skuId         代理skuId
     * @param userPid       代理上级用户id
     * @param request
     * @return
     */
    @RequestMapping(value = "/upGradeConfirm.do")
    @ResponseBody
    public String upGradeConfirm(@RequestParam(value = "curAgentLevel") Integer curAgentLevel,
                                       @RequestParam(value = "upgradeLevel") Integer upgradeLevel,
                                       @RequestParam(value = "skuId") Integer skuId,
                                       @RequestParam(value = "userPid") Long userPid,
                                       HttpServletRequest request) throws Exception{
        logger.info("代理确认升级申请Controller");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户未登录");
        }
        ModelAndView mv = new ModelAndView();
        JSONObject jsonObject = new JSONObject();
        logger.info("查询用户上级代理用户id begin");

        PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userPid,skuId);
        if (pfUserSku == null){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","上级代理数据有误");
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        if (userPid.longValue() != pfUserSku.getUserPid().longValue()){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","用户代理上级有误");
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        Integer pAgentLevel = pfUserSku.getAgentLevelId();
        logger.info("查询用户上级代理用户id end");
        Long keyProperty;
        try {
            keyProperty = upgradeNoticeService.dealAgentUpGrade(comUser.getId(), userPid, curAgentLevel, upgradeLevel, pAgentLevel, skuId);
        }catch (Exception e){
            logger.info(e.getMessage());
            jsonObject.put("isTrue","false");
            jsonObject.put("message","创建升级申请数据失败");
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        jsonObject.put("isTrue","true");
        jsonObject.put("keyProperty",keyProperty);
        logger.info("判断申请代理等级与上级代理等级（申请代理等级："+upgradeLevel+"，上级代理等级："+pAgentLevel+"）");
        if (upgradeLevel.intValue() == pAgentLevel.intValue()){
            jsonObject.put("isEquals","true");
        }else {
            jsonObject.put("isEquals","false");
        }
        logger.info(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }

    /**
     * 跳转申请成功页面（申请等级等于上级代理等级）
     * @return
     */
    @RequestMapping(value = "/applicationComplete.shtml")
    public ModelAndView turnApplilcation(){
        ModelAndView mv = new ModelAndView();
        logger.info("跳转申请成功页面（申请等级等于上级代理等级）");
        mv.setViewName("platform/user/upgrade/applicationComplete");
        return mv;
    }

    /**
     * 升级信息页面
     * @param applyId       升级申请表id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upgradeInfo.shtml")
    public ModelAndView upgradeInformation(@RequestParam(value = "applyId",required = true) Long applyId,
                                           HttpServletRequest request)throws Exception{
        logger.info("升级信息页面");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户未登录");
        }
        ModelAndView mv = new ModelAndView();
        PfUserUpgradeNotice upgradeNotice = upgradeNoticeService.getUpgradeNoticeById(applyId);
        if (upgradeNotice == null){
            throw new BusinessException("无升级申请信息");
        }
        logger.info("登录人id="+comUser.getId());
        logger.info("申请人上级id="+upgradeNotice.getUserPid());
        if (comUser.getId().longValue() != upgradeNotice.getUserPid().longValue()){
            throw new BusinessException("申请人不是您下级");
        }
        logger.info("查询升级信息页面数据begin");
        UpGradeInfoPo upGradeInfoPo = upgradeNoticeService.getUpGradeInfo(applyId);
        mv.addObject("upGradeInfoPo",upGradeInfoPo);
        Calendar cal = Calendar.getInstance();
        cal.setTime(upGradeInfoPo.getCreateTime());
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        mv.addObject("overdueDate", simpleDateFormat.format(cal.getTime()) );
        mv.setViewName("platform/user/upgrade/upGradeInformation");
        return mv;
    }

    /**
     * 代理暂不升级处理
     * @param upgradeId   升级申请表id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/temporarilyUpgrade.do")
    @ResponseBody
    public String temporarilyUpgrade(@RequestParam("upgradeId") Long upgradeId,
                                     HttpServletRequest request)throws Exception{
        logger.info("代理暂不升级处理，申请id="+upgradeId);
        ComUser user = getComUser(request);
        JSONObject jsonObject = new JSONObject();
        if (user == null){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","用户未登录");
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        PfUserUpgradeNotice pfUserUpgradeNotice = upgradeNoticeService.getPfUserUpGradeInfoByPrimaryKey(upgradeId);
        if (pfUserUpgradeNotice == null){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","没有用户升级申请数据");
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        pfUserUpgradeNotice.setUpStatus(UpGradeUpStatus.UP_STATUS_NotUpgrade.getCode());
        try {
            upgradeNoticeService.updateUpgradeNotice(pfUserUpgradeNotice);
        }catch (Exception e){
            jsonObject.put("isTrue","false");
            jsonObject.put("message",e.getMessage());
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        jsonObject.put("isTrue","true");
        jsonObject.put("message","处理完成");
        logger.info(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }
}
