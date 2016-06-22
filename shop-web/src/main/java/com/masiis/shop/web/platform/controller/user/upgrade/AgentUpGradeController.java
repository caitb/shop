package com.masiis.shop.web.platform.controller.user.upgrade;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.enums.upgrade.UpGradeStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.beans.order.BOrderUpgradeDetail;
import com.masiis.shop.dao.beans.user.upgrade.UpGradeInfoPo;
import com.masiis.shop.dao.beans.user.upgrade.UserSkuAgent;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.user.*;
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
    @Autowired
    private UserService userService;
    @Autowired
    private UpgradeWechatNewsService upgradeWechatNewsService;
    @Autowired
    private PfUserRebateService pfUserRebateService;

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
     * @return              String
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
        jsonObject.put("upAgentLevel",pLevelId);
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
     * @return return
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
        logger.info("查询用户上级代理等级id begin");
        PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userPid,skuId);
        if (pfUserSku == null){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","上级代理数据有误");
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        Integer pAgentLevel = pfUserSku.getAgentLevelId();
        logger.info("查询用户上级代理等级id end");
        Long keyProperty;
        try {
            keyProperty = upgradeNoticeService.dealAgentUpGrade(comUser.getId(), userPid, curAgentLevel, upgradeLevel, pAgentLevel, skuId);
        }catch (Exception e){
            logger.info(e.getMessage());
            jsonObject.put("isTrue","false");
            jsonObject.put("message",e.getMessage());
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
     * 我的申请单升级信息页面展示
     * @param upgradeId     升级申请信息id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/myApplyUpgrade.shtml")
    public ModelAndView myApplyUpgradeNotice(@RequestParam(value = "upgradeId") Long upgradeId,
                                             HttpServletRequest request) throws Exception{
        logger.info("我的申请单升级信息页面展示");
        logger.info("upgradeId=" + upgradeId);
        ComUser user = getComUser(request);
        if (user == null){
            throw new BusinessException("用户未登录");
        }
        ModelAndView mv = new ModelAndView();
        //获取页面展示po
        UpGradeInfoPo upGradeInfoPo = upgradeNoticeService.getUpGradeInfo(upgradeId);
        if (upGradeInfoPo == null){
            throw new BusinessException("查无升级申请单数据");
        }
        if (upGradeInfoPo.getApplyId().longValue() != user.getId().longValue()){
            throw new BusinessException("升级申请单id有误（不是当前用户申请）申请人id："+upGradeInfoPo.getApplyId()+" 当前用户id："+user.getId());
        }
        if (upGradeInfoPo.getApplyStatus().intValue() == UpGradeStatus.STATUS_NoPayment.getCode().intValue()
                 || upGradeInfoPo.getApplyStatus().intValue() == UpGradeStatus.STATUS_Complete.getCode().intValue()){
            mv.addObject("newUp",this.getNewUpAgent(upGradeInfoPo));
        }
        logger.info("查询当前上级用户信息 pid="+upGradeInfoPo.getApplyPid());
        ComUser pUser = userService.getUserById(upGradeInfoPo.getApplyPid());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        mv.addObject("createTime",sdf.format(upGradeInfoPo.getCreateTime()));
        mv.addObject("applyPName",pUser.getRealName());
        mv.addObject("upGradeInfoPo",upGradeInfoPo);
        mv.addObject("status",UpGradeStatus.statusPickList.get(upGradeInfoPo.getApplyStatus()));
        mv.setViewName("platform/user/upgrade/myApplyUpgradeNotice");
        return mv;
    }

    /**
     * 升级信息页面   (处理未完成)
     * @param upgradeId       升级申请表id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upgradeInfo.shtml")
    public ModelAndView upgradeInformation(@RequestParam(value = "upgradeId",required = true) Long upgradeId,
                                           HttpServletRequest request)throws Exception{
        logger.info("升级信息页面");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户未登录");
        }
        ModelAndView mv = new ModelAndView();
        PfUserUpgradeNotice upgradeNotice = upgradeNoticeService.getUpgradeNoticeById(upgradeId);
        if (upgradeNotice == null){
            throw new BusinessException("无升级申请信息");
        }
        logger.info("登录人id="+comUser.getId());
        logger.info("申请人上级id="+upgradeNotice.getUserPid());
        if (comUser.getId().longValue() != upgradeNotice.getUserPid().longValue()){
            throw new BusinessException("申请人不是您下级");
        }
        logger.info("查询升级信息页面数据begin");
        UpGradeInfoPo upGradeInfoPo = upgradeNoticeService.getUpGradeInfo(upgradeId);
//        mv.addObject("newUp",this.getNewUpAgent(upGradeInfoPo));
        logger.info("根据处理获取升级后的上级信息 end");
        mv.addObject("upGradeInfoPo",upGradeInfoPo);
        Calendar cal = Calendar.getInstance();
        cal.setTime(upGradeInfoPo.getCreateTime());
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        mv.addObject("createTime",simpleDateFormat.format(upGradeInfoPo.getCreateTime()));
        mv.addObject("overdueDate", simpleDateFormat.format(cal.getTime()) );
        mv.addObject("status",UpGradeStatus.statusPickList.get(upGradeInfoPo.getApplyStatus()));
        mv.setViewName("platform/user/upgrade/upGradeInformation");
        return mv;
    }

    /**
     * 查找升级后的新上级
     * @param upGradeInfoPo  升级信息页面po
     * @return
     * @throws Exception
     */
    private String getNewUpAgent(UpGradeInfoPo upGradeInfoPo) throws Exception{
        logger.info("根据处理获取升级后的上级信息 begin");
        logger.info("---------------------status="+upGradeInfoPo.getApplyStatus()+"------------------------");
        String returnMsg = "";
        if (upGradeInfoPo.getApplyStatus() == UpGradeStatus.STATUS_Untreated.getCode().intValue()
                || upGradeInfoPo.getApplyStatus().intValue() == UpGradeStatus.STATUS_Processing.getCode().intValue()
                || upGradeInfoPo.getApplyStatus().intValue() == UpGradeStatus.STATUS_Revocation.getCode().intValue()){
            returnMsg = upGradeInfoPo.getApplyPName();
        }
        if (upGradeInfoPo.getApplyStatus().intValue() == UpGradeStatus.STATUS_NoPayment.getCode().intValue()
                || upGradeInfoPo.getApplyStatus().intValue() == UpGradeStatus.STATUS_Complete.getCode().intValue()){
            logger.info("查询原上级代理商品信息-------pid="+upGradeInfoPo.getApplyPid());
            PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(upGradeInfoPo.getApplyPid(), upGradeInfoPo.getSkuId());
            if (upGradeInfoPo.getWishAgentId().intValue() == pfUserSku.getAgentLevelId().intValue()){
                if (pfUserSku.getUserPid().longValue() == 0){
                    returnMsg = "平台";
                }else {
                    ComUser comUser = userService.getUserById(pfUserSku.getUserPid());
                    returnMsg = comUser.getRealName();
                }
            }else {
                returnMsg = upGradeInfoPo.getApplyPName();
            }
        }
        logger.info("根据处理获取升级后的上级信息 end");
        return returnMsg;
    }
    /**
     * 升级信息页面   (一次性返利跳转)
     * @param upgradeId       升级申请表id
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upgradeInfoNewUp.shtml")
    public ModelAndView upgradeInformationNewUp(@RequestParam(value = "upgradeId",required = true) Long upgradeId,
                                           HttpServletRequest request)throws Exception{
        logger.info("升级信息页面(一次性返利跳转)");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户未登录");
        }
        ModelAndView mv = new ModelAndView();
        PfUserUpgradeNotice upgradeNotice = upgradeNoticeService.getUpgradeNoticeById(upgradeId);
        if (upgradeNotice == null){
            throw new BusinessException("无升级申请信息");
        }
        logger.info("登录人id="+comUser.getId());
        logger.info("申请人id="+upgradeNotice.getUserId());
        logger.info("申请人原上级id="+upgradeNotice.getUserPid());
        PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(upgradeNotice.getUserId(),upgradeNotice.getSkuId());
        if (pfUserSku == null){
            throw new BusinessException("代理等级信息有误");
        }

        logger.info("查询升级信息页面数据begin");
        UpGradeInfoPo upGradeInfoPo = upgradeNoticeService.getUpGradeInfo(upgradeId);
        //原上级
        ComUser former = userService.getUserById(upgradeNotice.getUserPid());
        PfUserRebate pfUserRebate = pfUserRebateService.selectByupgradeId(upgradeId);
        if (pfUserRebate == null){
            throw new BusinessException("一次性返利有误");
        }
        if (comUser.getId().longValue() == pfUserRebate.getUserId().longValue()){
            logger.info("登录人将获得一次性奖励");
            mv.addObject("income","1");
        }
        if (comUser.getId().longValue() == pfUserRebate.getUserPid().longValue()){
            logger.info("登录人将发出一次性奖励");
            mv.addObject("income","2");
        }
        mv.addObject("former",former.getRealName());
        //新上级
        ComUser user = userService.getUserById(pfUserSku.getUserPid());
        mv.addObject("newUp",user.getRealName());
        mv.addObject("upGradeInfoPo",upGradeInfoPo);
        mv.addObject("status",UpGradeStatus.statusPickList.get(upGradeInfoPo.getApplyStatus()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        mv.addObject("createTime",sdf.format(upGradeInfoPo.getCreateTime()));
        mv.setViewName("platform/user/upgrade/upGradeInformationNewUp");
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
        //处理下级申请单据
        try {
            upgradeNoticeService.dealLowerUpgradeNotice(pfUserUpgradeNotice);
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

    /**
     * 用户撤销升级申请单
     * @param upgradeId     升级申请单id
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/cannelUpgrade.do")
    @ResponseBody
    public String cannelUpgradeNotice(@RequestParam(value = "upgradeId", required = true) Long upgradeId,
                               HttpServletRequest request) throws Exception{
        logger.info("用户撤销升级申请单");
        ComUser comUser = getComUser(request);
        JSONObject jsonObject = new JSONObject();
        if (comUser == null){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","用户未登录");
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        try {
            upgradeNoticeService.cannelUpgradeNotice(upgradeId);
        }catch (Exception e){
            jsonObject.put("isTrue","false");
            jsonObject.put("message",e.getMessage());
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        jsonObject.put("isTrue","true");
        jsonObject.put("message","撤销成功");
        logger.info(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }

    /**
     * 通知界面跳转到订单界面
     * @param upgradeNoticeId
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/skipOrderPageGetNoticeInfo.html")
    @ResponseBody
    public ModelAndView skipOrderPageGetNoticeInfo(@RequestParam(value = "upgradeNoticeId", required = true) Long upgradeNoticeId,
                                      HttpServletRequest request) throws Exception{
        BOrderUpgradeDetail upgradeDetail = null;
        ModelAndView mv = new ModelAndView();
        ComUser comUser =getComUser(request);
        if (upgradeNoticeId!=null){
            upgradeDetail = upgradeNoticeService.getUpgradeNoticeInfo(upgradeNoticeId);
            upgradeDetail.setName(comUser.getRealName());
        }
        mv.addObject("upgradeDetail",upgradeDetail);
        mv.addObject("payDate", DateUtil.addDays(SysConstants.UPGRADE_LATEST_TIME));
        mv.setViewName("platform/user/upgrade/shengjishenhe");
        return mv;
    }

    /**
     * 申請升級成功之後發送微信消息
     * @param upgradeLevel  申請代理等級
     * @param upAgentLevel  上級代理等級
     * @param upgradeId     通知单id
     * @param request
     */
    @RequestMapping(value = "/upgradeApplySubmitNotice.do")
    @ResponseBody
    public String upgradeApplySubmitNotice(@RequestParam(value = "upgradeLevel") Integer upgradeLevel,
                                         @RequestParam(value = "upAgentLevel") Integer upAgentLevel,
                                         @RequestParam(value = "upgradeId") Long upgradeId,
                                         HttpServletRequest request){
        logger.info("升级申请成功发送微信消息");
        ComUser comUser = getComUser(request);
        JSONObject jsonObject = new JSONObject();
        if (comUser == null){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","用户未登录");
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        if (upAgentLevel.intValue() == upgradeLevel.intValue()){
            UpGradeInfoPo upGradeInfoPo = upgradeNoticeService.getUpGradeInfo(upgradeId);
            PfSkuAgent pfSkuAgent = pfUserSkuService.getCurrentSkuAgent(upGradeInfoPo.getSkuId(),upGradeInfoPo.getWishAgentId());
            if (pfSkuAgent.getIsUpgrade().intValue() == 1){
                logger.info("查询上级用户信息");
                ComUser pUser = userService.getUserById(upGradeInfoPo.getApplyPid());
                boolean upBoolean = upgradeWechatNewsService.subLineUpgradeApplyNotice(pUser, upGradeInfoPo, "/upgradeInfo/lower?tabId=0");
                jsonObject.put("upBoolean",upBoolean);
                boolean applyBoolean = upgradeWechatNewsService.upgradeApplySubmitNotice(comUser, upGradeInfoPo, "/upgrade/myApplyUpgrade.shtml?upgradeId="+upgradeId);
                jsonObject.put("applyBoolean",applyBoolean);
            }else {
                boolean applyBoolean = upgradeWechatNewsService.upgradeApplyAuditPassNotice(comUser, upGradeInfoPo, "/upgrade/myApplyUpgrade.shtml?upgradeId="+upgradeId);
                jsonObject.put("applyBoolean",applyBoolean);
            }
        }
        jsonObject.put("isTrue","true");
        logger.info(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }

    /**
     * 代理暂不升级发送微信消息
     * @param upgradeId
     * @param request
     * @return
     */
    @RequestMapping(value = "/notUpgradeMessage.do")
    @ResponseBody
    public String notUpgradeMessage(@RequestParam(value = "upgradeId") Long upgradeId, HttpServletRequest request) throws Exception {
        ComUser comUser = getComUser(request);
        JSONObject jsonObject = new JSONObject();
        if (comUser == null){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","用户未登录");
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        UpGradeInfoPo upGradeInfoPo = upgradeNoticeService.getUpGradeInfo(upgradeId);
        PfUserUpgradeNotice upgradeNotice = upgradeNoticeService.getUpgradeNoticeById(upgradeId);
        ComUser applyUser = userService.getUserById(upgradeNotice.getUserId());
        ComUser oldUpUser = userService.getUserById(upgradeNotice.getUserPid());
        logger.info("代理暂不升级发送消息");
        boolean oldBoolean = upgradeWechatNewsService.upgradeApplyResultNotice(oldUpUser, upGradeInfoPo, "/upgrade/upgradeInfoNewUp.shtml?upgradeId="+upgradeId);
        logger.info("代理暂不升级给下级发送微信消息");
        boolean applyBoolean = upgradeWechatNewsService.upgradeApplyAuditPassNotice(applyUser, upGradeInfoPo, "/upgrade/myApplyUpgrade.shtml?upgradeId="+upgradeId);
        jsonObject.put("isTrue","true");
        jsonObject.put("oldBoolean",oldBoolean);
        jsonObject.put("applyBoolean",applyBoolean);
        logger.info(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }
}
