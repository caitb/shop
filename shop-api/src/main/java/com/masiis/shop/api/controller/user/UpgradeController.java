package com.masiis.shop.api.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.base.BaseBusinessReq;
import com.masiis.shop.api.bean.common.CommonReq;
import com.masiis.shop.api.bean.user.upgrade.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.common.constant.platform.SysConstants;
import com.masiis.shop.common.enums.platform.BOrderType;
import com.masiis.shop.common.enums.platform.UpGradeStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.beans.order.BOrderAdd;
import com.masiis.shop.dao.beans.order.BOrderUpgradeDetail;
import com.masiis.shop.dao.beans.system.ComSkuSimple;
import com.masiis.shop.dao.beans.user.upgrade.UpGradeInfoPo;
import com.masiis.shop.dao.beans.user.upgrade.UpgradeManagePo;
import com.masiis.shop.dao.beans.user.upgrade.UpgradePackageInfo;
import com.masiis.shop.dao.beans.user.upgrade.UserSkuAgent;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.ComAgentLevelService;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.service.order.BOrderAddService;
import com.masiis.shop.web.platform.service.order.BOrderPayService;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.order.PfUserUpgradeNoticeService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.user.PfUserRebateService;
import com.masiis.shop.web.platform.service.user.PfUserSkuService;
import com.masiis.shop.web.platform.service.user.UpgradeNoticeService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 升级管理controller
 * Created by wangbingjian on 2016/8/3.
 */
@Controller
@RequestMapping(value = "/api/upgrade")
public class UpgradeController {

    private final Logger logger = Logger.getLogger(UserAccountController.class);
    @Resource
    private UpgradeNoticeService upgradeNoticeService;
    @Resource
    private PfUserSkuService pfUserSkuService;
    @Resource
    private SkuService skuService;
    @Resource
    private UserService userService;
    @Resource
    private PfUserRebateService pfUserRebateService;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private PfUserUpgradeNoticeService userUpgradeNoticeService;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private ComAgentLevelService comAgentLevelService;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private BOrderAddService bOrderAddService;
    @Resource
    private BOrderPayService payBOrderService;

    /**
     * 升级管理我的申请单（列表）
     * @param request   request
     * @param req       req
     * @param comUser   comUser
     * @return
     */
    @RequestMapping("/myUpgrade.do")
    @ResponseBody
    @SignValid(paramType = UpgradeManageMyReq.class)
    public UpgradeManageMyRes myUpgradeNoticeInfo(HttpServletRequest request, UpgradeManageMyReq req, ComUser comUser) {
        logger.info("升级管理我的申请");
        logger.info("userId = " + comUser.getId());
        logger.info("pageNum = " + req.getPageNum());
        UpgradeManageMyRes res = new UpgradeManageMyRes();
        if (req.getPageNum().intValue() <= 0){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("页码有误");
            return res;
        }
        try {
            UpgradeManagePo upgradeManagePo = upgradeNoticeService.getPfUserUpGradeInfoPaging(comUser, req.getPageNum(), 1, null, null, null);
            res.setUpgradeManagePo(upgradeManagePo);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            if (e.getMessage().equals("1")){
                res.setResCode(SysResCodeCons.RES_CODE_PAGE_LAST);
                res.setResMsg(SysResCodeCons.RES_CODE_PAGE_LAST_MSG);
            }else {
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }
        res.setAuditStatus(comUser.getAuditStatus());
        logger.info(JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 升级管理下级申请单（列表）
     * @param request   request
     * @param req       req
     * @param comUser   comUser
     * @return
     */
    @RequestMapping("/lowerupgrade.do")
    @ResponseBody
    @SignValid(paramType = UpgradeManageLowerReq.class)
    public UpgradeManageLowerRes lowerUpgradeNotice(HttpServletRequest request, UpgradeManageLowerReq req, ComUser comUser) {
        logger.info("升级管理下级申请单（列表）");
        logger.info("userId = " + comUser.getId());
        logger.info("pageNum = " + req.getPageNum());
        logger.info("skuId = " + req.getSkuId());
        logger.info("status = " + req.getStatus());
        UpgradeManageLowerRes res = new UpgradeManageLowerRes();
        if (req.getPageNum().intValue() <= 0){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("页码有误");
            return res;
        }
        try {
            //初始化商品查询列表
//            List<PfUserSku> pfUserSkus = pfUserSkuService.getPfUserSkuInfoByUserId(comUser.getId());
            List<PfUserSku> pfUserSkus = pfUserSkuMapper.selectPrimarySkuByUserId(comUser.getId());//显示代理的主打商品
            if(pfUserSkus==null){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg("代理商品异常，初始化商品列表失败");
                return res;
            }else{
                List<ComSkuSimple> skuList = new ArrayList();
                for(PfUserSku pfUserSku :pfUserSkus){
                    ComSku comSku = skuService.getSkuById(pfUserSku.getSkuId());
                    ComSkuSimple simple = new ComSkuSimple();
                    simple.setSkuId(comSku.getId());
                    simple.setSkuName(comSku.getName());
                    skuList.add(simple);
                }
                ComSkuSimple simple = new ComSkuSimple();
                simple.setSkuName("全部");
                simple.setSkuId(0);
                skuList.add(simple);
                res.setComSkuSimples(skuList);
            }
            //初始化升级单状态
            Map<Integer, String> mapStatus = UpGradeStatus.statusPickList;
            mapStatus.put(-1,"全部");
            res.setStatus(mapStatus);
            Integer skuId = req.getSkuId() == null?null:req.getSkuId().intValue() == 0?null:req.getSkuId();
            Integer status = req.getStatus() == null?null:req.getStatus().intValue() == -1?null:req.getStatus();
            UpgradeManagePo upgradeManagePo = upgradeNoticeService.getPfUserUpGradeInfoPaging(comUser, req.getPageNum(), 2, skuId, status, null);
            res.setUpgradeManagePo(upgradeManagePo);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            if (e.getMessage().equals("1")){
                res.setResCode(SysResCodeCons.RES_CODE_PAGE_LAST);
                res.setResMsg(SysResCodeCons.RES_CODE_PAGE_LAST_MSG);
            }else {
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }
        res.setAuditStatus(comUser.getAuditStatus());
        logger.info(JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 一次性返利（列表）
     * @param request   request
     * @param req       req
     * @param comUser   comUser
     * @return
     */
    @RequestMapping("/oneRebate.do")
    @ResponseBody
    @SignValid(paramType = UpgradeManageRebateReq.class)
    public UpgradeManageRebateRes oneTimeRebate(HttpServletRequest request, UpgradeManageRebateReq req, ComUser comUser){
        logger.info("升级管理一次性返利");
        logger.info("userId = " + comUser.getId());
        logger.info("pageNum = " + req.getPageNum());
        logger.info("skuId = " + req.getSkuId());
        logger.info("status = " + req.getStatus());
        UpgradeManageRebateRes res = new UpgradeManageRebateRes();
        if (req.getPageNum().intValue() <= 0){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("页码有误");
            return res;
        }
        try {
            //初始化商品查询列表
            List<PfUserSku> pfUserSkus = pfUserSkuService.getPfUserSkuInfoByUserId(comUser.getId());
            if(pfUserSkus==null){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg("代理商品异常，初始化商品列表失败");
                return res;
            }else{
                List<ComSkuSimple> skuList = new ArrayList();
                for(PfUserSku pfUserSku :pfUserSkus){
                    ComSku comSku = skuService.getSkuById(pfUserSku.getSkuId());
                    ComSkuSimple simple = new ComSkuSimple();
                    simple.setSkuId(comSku.getId());
                    simple.setSkuName(comSku.getName());
                    skuList.add(simple);
                }
                ComSkuSimple simple = new ComSkuSimple();
                simple.setSkuName("全部");
                simple.setSkuId(0);
                skuList.add(simple);
                res.setComSkuSimples(skuList);
            }
            //初始化升级单状态
            Map<Integer, String> mapStatus = new HashMap<>();
            mapStatus.put(0,"支付返利");
            mapStatus.put(1,"获取返利");
            mapStatus.put(-1,"全部");
            res.setStatus(mapStatus);
            Integer skuId = req.getSkuId() == null?null:req.getSkuId().intValue() == 0?null:req.getSkuId();
            Integer status = req.getStatus() == null?null:req.getStatus().intValue() == -1?null:req.getStatus();
            UpgradeManagePo upgradeManagePo = upgradeNoticeService.getPfUserUpGradeInfoPaging(comUser, req.getPageNum(), 3, skuId, null, status);
            res.setUpgradeManagePo(upgradeManagePo);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            if (e.getMessage().equals("1")){
                res.setResCode(SysResCodeCons.RES_CODE_PAGE_LAST);
                res.setResMsg(SysResCodeCons.RES_CODE_PAGE_LAST_MSG);
            }else {
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }
        res.setAuditStatus(comUser.getAuditStatus());
        logger.info(JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 下级申请详情页展示
     * @param request   request
     * @param req       req
     * @param comUser   comUser
     */
    @RequestMapping("/upgradeInfo.do")
    @ResponseBody
    @SignValid(paramType = UpgradeDetailReq.class)
    public LowerUpgradeDetailRes upgradeInformation(HttpServletRequest request, UpgradeDetailReq req, ComUser comUser) throws Exception{
        LowerUpgradeDetailRes res = new LowerUpgradeDetailRes();
        Long upgradeId = req.getUpgradeId();
        logger.info("升级信息页面 upgradeId="+upgradeId);
        PfUserUpgradeNotice upgradeNotice = upgradeNoticeService.getUpgradeNoticeById(upgradeId);
        if (upgradeNotice == null){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("无升级申请信息");
            logger.info(JSONObject.toJSONString(res));
            return res;
        }
        logger.info("登录人id="+comUser.getId());
        logger.info("申请人上级id="+upgradeNotice.getUserPid());
        if (comUser.getId().longValue() != upgradeNotice.getUserPid().longValue()){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("申请人不是您下级");
            logger.info(JSONObject.toJSONString(res));
            return res;
        }
        logger.info("查询升级信息页面数据begin");
        UpGradeInfoPo upGradeInfoPo = upgradeNoticeService.getUpGradeInfo(upgradeId);
        res.setUpGradeInfoPo(upGradeInfoPo);
        logger.info("根据处理获取升级后的上级信息 end");
        Calendar cal = Calendar.getInstance();
        cal.setTime(upGradeInfoPo.getCreateTime());
        cal.add(Calendar.DATE, 2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        res.setApplyTime(simpleDateFormat.format(upGradeInfoPo.getCreateTime()));
        res.setOverdueDate(simpleDateFormat.format(cal.getTime()));
        res.setApplyStatusView(UpGradeStatus.statusPickList.get(upGradeInfoPo.getApplyStatus()));
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        logger.info(JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 我的申请单升级信息页面展示
     * @param request   request
     * @param req       req
     * @param comUser   comUser
     */
    @RequestMapping("/myApplyUpgrade.do")
    @ResponseBody
    @SignValid(paramType = UpgradeDetailReq.class)
    public MyUpgradeDetailRes myApplyUpgradeNotice(HttpServletRequest request, UpgradeDetailReq req, ComUser comUser) throws Exception{
        MyUpgradeDetailRes res = new MyUpgradeDetailRes();
        Long upgradeId = req.getUpgradeId();
        logger.info("我的申请单升级信息页面展示-----upgradeId="+upgradeId);
        logger.info("upgradeId=" + upgradeId);
        //获取页面展示po
        UpGradeInfoPo upGradeInfoPo = upgradeNoticeService.getUpGradeInfo(upgradeId);
        if (upGradeInfoPo == null){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("查无升级申请单数据");
            logger.info(JSONObject.toJSONString(res));
            return res;
        }
        if (upGradeInfoPo.getApplyId().longValue() != comUser.getId().longValue()){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("升级申请单id有误（不是当前用户申请）申请人id："+upGradeInfoPo.getApplyId()+" 当前用户id："+comUser.getId());
            logger.info(JSONObject.toJSONString(res));
            return res;
        }
        if (upGradeInfoPo.getApplyStatus().intValue() == UpGradeStatus.STATUS_NoPayment.getCode().intValue()
                || upGradeInfoPo.getApplyStatus().intValue() == UpGradeStatus.STATUS_Complete.getCode().intValue()
                || upGradeInfoPo.getApplyStatus().intValue() == UpGradeStatus.STATUS_Revocation.getCode().intValue()){
            res.setNuwUpName(upgradeNoticeService.getNewUpAgent(upGradeInfoPo));
        }
        logger.info("查询当前上级用户信息 pid="+upGradeInfoPo.getApplyPid());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        res.setApplyTime(sdf.format(upGradeInfoPo.getCreateTime()));
        res.setUpGradeInfoPo(upGradeInfoPo);
        res.setApplyStatusView(UpGradeStatus.statusPickList.get(upGradeInfoPo.getApplyStatus()));
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        logger.info(JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 升级信息页面   (一次性返利跳转)
     * @param request   request
     * @param req       req
     * @param comUser   comUser
     * @throws Exception
     */
    @RequestMapping(value = "/upgradeInfoNewUp.do")
    @ResponseBody
    @SignValid(paramType = UpgradeDetailReq.class)
    public OneRebateUpgradeDetailRes upgradeInformationNewUp(HttpServletRequest request, UpgradeDetailReq req, ComUser comUser) throws Exception{
        OneRebateUpgradeDetailRes res = new OneRebateUpgradeDetailRes();
        Long upgradeId = req.getUpgradeId();
        logger.info("升级信息页面(一次性返利跳转)----upgradeId="+upgradeId);
        PfUserUpgradeNotice upgradeNotice = upgradeNoticeService.getUpgradeNoticeById(upgradeId);
        if (upgradeNotice == null){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("无升级申请信息");
            logger.info(JSONObject.toJSONString(res));
            return res;
        }
        logger.info("登录人id="+comUser.getId());
        logger.info("申请人id="+upgradeNotice.getUserId());
        logger.info("申请人原上级id="+upgradeNotice.getUserPid());
        PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(upgradeNotice.getUserId(),upgradeNotice.getSkuId());
        if (pfUserSku == null){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("代理等级信息有误");
            logger.info(JSONObject.toJSONString(res));
            return res;
        }

        logger.info("查询升级信息页面数据begin");
        UpGradeInfoPo upGradeInfoPo = upgradeNoticeService.getUpGradeInfo(upgradeId);
        PfUserRebate pfUserRebate = pfUserRebateService.selectByupgradeId(upgradeId);
        if (pfUserRebate == null){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("一次性返利有误");
            return res;
        }
        if (comUser.getId().longValue() == pfUserRebate.getUserId().longValue()){
            logger.info("登录人将获得一次性奖励");
            res.setIncome(1);
        }
        if (comUser.getId().longValue() == pfUserRebate.getUserPid().longValue()){
            logger.info("登录人将发出一次性奖励");
            res.setIncome(2);
        }
        //新上级
        ComUser user = userService.getUserById(pfUserSku.getUserPid());
        res.setNuwUpName(user.getRealName());
        res.setUpGradeInfoPo(upGradeInfoPo);
        res.setApplyStatusView(UpGradeStatus.statusPickList.get(upGradeInfoPo.getApplyStatus()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        res.setApplyTime(sdf.format(upGradeInfoPo.getCreateTime()));
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        logger.info(JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 初始化我要升级页面
     * @param request   request
     * @param req       req
     * @param comUser   comUser
     */
    @RequestMapping(value = "/initUpgrade.do")
    @ResponseBody
    @SignValid(paramType = BaseBusinessReq.class)
    public InitUpgradeRes initUpGrade(HttpServletRequest request, BaseBusinessReq req, ComUser comUser){
        InitUpgradeRes res = new InitUpgradeRes();
        logger.info("初始化我要升级首页");
        logger.info("查询当前商品代理等级，用户id = " + comUser.getId());
        List<UserSkuAgent> skuAgents = pfUserSkuService.getCurrentAgentLevel(comUser.getId());
        if (skuAgents == null || skuAgents.size() == 0){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("该用户当前没有代理商品");
            logger.info(JSONObject.toJSONString(res));
            return res;
        }
        res.setSkuAgents(skuAgents);
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        logger.info(JSONObject.toJSONString(res));
        return res;
    }


    /**
     * 获取代理用户可以升级的等级
     * @param request   request
     * @param req       req
     * @param comUser   comUser
     * @return          String
     */
    @RequestMapping(value = "/getUpGradePackage.do")
    @ResponseBody
    @SignValid(paramType = UpGradePackageReq.class)
    public UpGradePackageRes getUpGradePackage(HttpServletRequest request, UpGradePackageReq req, ComUser comUser) throws Exception{
        logger.info("获取代理用户可以升级的等级");
        Long userPid = req.getUserPid();
        logger.info("userPid = " + userPid);
        Integer skuId = req.getSkuId();
        logger.info("skuId = " + skuId);
        Integer agentLevelId = req.getAgentLevelId();
        logger.info("agentLevelId = " + agentLevelId);
        UpGradePackageRes res = new UpGradePackageRes();
        if (req.getUserPid().longValue() == 0){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("直接上级为平台，无上级代理");
            logger.info(JSONObject.toJSONString(res));
            return res;
        }
        logger.info("查询上级用户代理等级begin");
        PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userPid,skuId);
        if (pfUserSku == null){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("上级代理信息为空");
            logger.info(JSONObject.toJSONString(res));
            return res;
        }
        Integer pLevelId = pfUserSku.getAgentLevelId();logger.info("查询上级用户代理等级end");
        logger.info("上级用户：" + pfUserSku.getUserId() + "：：：skuId = " + skuId + "：：：代理级别：" + pLevelId);
        logger.info("获取当前用户商品代理等级信息begin");
        PfSkuAgent currentSkuAgent = pfUserSkuService.getCurrentSkuAgent(skuId, agentLevelId);
        if (currentSkuAgent == null){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("用户代理等级信息有误");
            logger.info(JSONObject.toJSONString(res));
            return res;
        }
        logger.info("获取当前用户商品代理等级信息end");
        logger.info("获取用户可以升级的代理级别信息begin");
        List<UpgradePackageInfo> upgradePackageInfos = pfUserSkuService.getUpgradePackageInfo15(skuId, agentLevelId, pLevelId);
        if (upgradePackageInfos == null || upgradePackageInfos.size() == 0){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("暂时无法升级");
            logger.info(JSONObject.toJSONString(res));
            return res;
        }
        res.setUpgradePackageInfo(upgradePackageInfos);
        logger.info("获取用户可以升级的代理级别信息end");
        res.setUpAgentLevel(pLevelId);
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        logger.info(JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 用户确认升级
     * @param request   request
     * @param req       req
     * @param comUser   comUser
     * @return
     */
    @RequestMapping(value = "/upGradeConfirm.do")
    @ResponseBody
    @SignValid(paramType = UpgradeConfirmReq.class)
    public UpgradeConfirmRes upGradeConfirm(HttpServletRequest request, UpgradeConfirmReq req, ComUser comUser){
        logger.info("代理确认升级申请Controller");
        UpgradeConfirmRes res = new UpgradeConfirmRes();
        Long userPid = req.getUserPid();
        Integer skuId = req.getSkuId();
        Integer curAgentLevel = req.getCurAgentLevel();
        Integer upgradeLevel = req.getUpgradeLevel();
        logger.info("userPid = " + userPid);
        logger.info("skuId = " + skuId);
        logger.info("查询用户上级代理等级id begin");
        PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userPid,skuId);
        if (pfUserSku == null){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("上级代理数据有误");
            logger.info(JSONObject.toJSONString(res));
            return res;
        }
        PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, upgradeLevel);
        if (pfSkuAgent.getIsUpgrade().intValue() == 0){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("该等级不支持升级");
            logger.info(JSONObject.toJSONString(res));
            return res;
        }
        Integer pAgentLevel = pfUserSku.getAgentLevelId();
        logger.info("查询用户上级代理等级id end");
        PfUserUpgradeNotice upgradeNotice;
        try {
            upgradeNotice = upgradeNoticeService.dealAgentUpGradeApi(comUser.getId(), userPid, curAgentLevel, upgradeLevel, pAgentLevel, skuId);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(e.getMessage());
            logger.error(JSONObject.toJSONString(res),e);
            return res;
        }
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        res.setKeyProperty(upgradeNotice.getId());
        logger.info("判断申请代理等级与上级代理等级（申请代理等级："+upgradeLevel+"，上级代理等级："+pAgentLevel+"）");
        //发送消息
        try {
            upgradeNoticeService.upgradeApplySubmitNotice(upgradeLevel, pAgentLevel, upgradeNotice.getId(), comUser);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        if (upgradeLevel.intValue() == pAgentLevel.intValue()){
            logger.info("判断是否可以升级，生成申请单id="+upgradeNotice.getId());
            if (upgradeNotice.getStatus().intValue() == 2){
                //跳转到订单确认页面
                res.setStatus(1);
            }else {
                //跳转到完成页面
                res.setStatus(2);
            }
        }else {
            res.setStatus(1);
        }
        logger.info(JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 通知界面跳转到订单界面
     * @param request   request
     * @param req       req
     * @param comUser   comUser
     */
    @RequestMapping(value = "/skipOrderPageGetNoticeInfo.do")
    @ResponseBody
    @SignValid(paramType = CommonReq.class)
    public UpgradeOrderPageRes skipOrderPageGetNoticeInfo(HttpServletRequest request, CommonReq req, ComUser comUser){
        UpgradeOrderPageRes res = new UpgradeOrderPageRes();
        BOrderUpgradeDetail upgradeDetail = null;
        Long upgradeNoticeId = req.getId();
        if (req.getId() != null){
            upgradeDetail = upgradeNoticeService.getUpgradeNoticeInfo(upgradeNoticeId);
            upgradeDetail.setName(comUser.getRealName());
        }
        res.setUpgradeDetail(upgradeDetail);
        res.setPayDate(DateUtil.delayDays(SysConstants.UPGRADE_LATEST_TIME));
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        logger.info(JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 代理暂不升级处理
     * @param request   request
     * @param req       req
     * @param comUser   comUser
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/temporarilyUpgrade.do")
    @ResponseBody
    @SignValid(paramType = CommonReq.class)
    public TemporarilyRes temporarilyUpgrade(HttpServletRequest request, CommonReq req, ComUser comUser)throws Exception{
        Long upgradeId = req.getId();
        TemporarilyRes res = new TemporarilyRes();
        logger.info("代理暂不升级处理，申请id="+upgradeId);
        PfUserUpgradeNotice pfUserUpgradeNotice = upgradeNoticeService.getPfUserUpGradeInfoByPrimaryKey(upgradeId);
        if (pfUserUpgradeNotice == null){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("没有用户升级申请数据");
            return res;
        }
        //处理下级申请单据
        try {
            upgradeNoticeService.dealLowerUpgradeNotice(pfUserUpgradeNotice);
            logger.info("更新新上级和处理状态页面展示");
            pfUserUpgradeNotice = upgradeNoticeService.getPfUserUpGradeInfoByPrimaryKey(upgradeId);
            logger.info("设置申请人申请状态 status="+ UpGradeStatus.statusPickList.get(pfUserUpgradeNotice.getStatus()));
            res.setStatus(pfUserUpgradeNotice.getStatus());
            res.setStatusView(UpGradeStatus.statusPickList.get(pfUserUpgradeNotice.getStatus()));
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(e.getMessage());
        }
        //发送消息
        try {
            upgradeNoticeService.notUpgradeMessage(upgradeId);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        logger.info(JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 升级成功后获得界面信息
     * @param request
     * @param req
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getUpgradeSuccessPageInfo.do")
    @ResponseBody
    @SignValid(paramType = BOrderUpgradeDetailReq.class)
    public BOrderUpgradeDetailRes getUpgradeSuccessPageInfo(HttpServletRequest request, BOrderUpgradeDetailReq req)throws Exception{
        BOrderUpgradeDetailRes res = new BOrderUpgradeDetailRes();
        try{
            if (req==null||req.getPfBorderId()==null){
                res.setResCode(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID);
                res.setResMsg(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID_MSG);
                return res;
            }
            PfUserUpgradeNotice pfUserUpgradeNotice = userUpgradeNoticeService.selectByPfBorderId(req.getPfBorderId());
            BOrderUpgradeDetail upgradeDetail = upgradeNoticeService.getUpgradeNoticeInfo(pfUserUpgradeNotice.getId());
            res.setbOrderUpgradeDetail(upgradeDetail);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.getMessage();
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }
        return res;
    }

    /**
     * 创建升级订单
     * @param request
     * @param req
     * @param comUser
     * @return
     */
    @RequestMapping(value = "/upgradeAddOrder.do")
    @ResponseBody
    @SignValid(paramType = CommonReq.class)
    public UpgradeAddOrderRes addUpgradeOrder(HttpServletRequest request, CommonReq req, ComUser comUser){
        logger.info("创建升级订单");
        UpgradeAddOrderRes res = new UpgradeAddOrderRes();
        Long upgradeId = req.getId();
        logger.info("upgradeId = " + upgradeId);
        res.setUpgradeType(1);
        if (upgradeId == null){
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN_MSG);
            return res;
        }
        PfUserUpgradeNotice upgradeNotice = upgradeNoticeService.getUpgradeNoticeById(upgradeId);
        if (upgradeNotice == null){
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg("申请单id无效");
            return res;
        }
        if (upgradeNotice.getPfBorderId() != null){
            PfBorder pfBorder = bOrderService.getPfBorderById(upgradeNotice.getPfBorderId());
            if(pfBorder.getReceivableAmount().compareTo(BigDecimal.ZERO) == 0){
                res.setUpgradeType(0);
            }
            res.setOrderId(upgradeNotice.getPfBorderId());
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            return res;
        }
        if (comUser.getId().longValue() != upgradeNotice.getUserId().longValue()){
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg("升级单申请人与登录人不一致");
            return res;
        }
        //插入订单表
        BOrderUpgradeDetail upgradeDetail = upgradeNoticeService.getUpgradeNoticeInfo(upgradeId);
        PfSkuAgent newSkuAgent = skuAgentService.getBySkuIdAndLevelId(upgradeDetail.getSkuId(), upgradeDetail.getApplyAgentLevel());

        //不创建0元订单
        if(newSkuAgent.getTotalPrice().compareTo(BigDecimal.ZERO) == 0){
            res.setUpgradeType(0);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }else{
            res.setUpgradeType(1);

            BOrderAdd orderAdd = new BOrderAdd();
            orderAdd.setUpgradeNoticeId(upgradeNotice.getId());
            logger.info("升级订单对应的通知单id--------" + upgradeNotice.getId());
            orderAdd.setOrderType(3);
            orderAdd.setUserId(comUser.getId());
            orderAdd.setOldPUserId(upgradeDetail.getOldPUserId());
            orderAdd.setpUserId(upgradeDetail.getNewPUserId() == null?0:upgradeDetail.getNewPUserId());//设置新的上级
            logger.info("新上级id----------" + upgradeDetail.getNewPUserId());
            orderAdd.setSendType(1);//拿货方式
            orderAdd.setSkuId(upgradeDetail.getSkuId());
            orderAdd.setQuantity(newSkuAgent.getQuantity());
            logger.info("订单数量---------" + newSkuAgent.getQuantity());
            orderAdd.setCurrentAgentLevel(upgradeDetail.getCurrentAgentLevel());
            orderAdd.setAgentLevelId(upgradeDetail.getApplyAgentLevel());
            logger.info("原始等级--------" + upgradeDetail.getCurrentAgentLevel());
            logger.info("期望等级--------" + upgradeDetail.getApplyAgentLevel());
            orderAdd.setUserSource(0);

            try {
                Long orderId = bOrderAddService.addBOrder(orderAdd);
                logger.info("添加的升级订单id = " + orderId);
                //升级申请表添加orderId
                upgradeNotice.setPfBorderId(orderId);
                upgradeNoticeService.updateUpgradeNotice(upgradeNotice);

                // 0元升级，不需要支付，直接升级成功（不支持0元升级）
                /*PfBorder pfBorder = bOrderService.getPfBorderById(orderId);
                if(pfBorder.getReceivableAmount().compareTo(BigDecimal.ZERO) == 0){
                    logger.info("0元升级订单处理,订单id:" + orderId);
                    PfBorderPayment payment = bOrderService.createPfBorderPaymentByOrderCode(pfBorder.getOrderCode());
                    logger.info("处理0元升级订单,支付流水号为:" + payment.getPaySerialNum());
                    payBOrderService.mainPayBOrder(payment, "ZERO_UPGRADE");
                    res.setUpgradeType(0);
                }*/

                res.setOrderId(orderId);
                res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
                res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            }catch (Exception e){
                e.printStackTrace();
                logger.error(e.getMessage(),e);
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg("创建升级订单失败");
            }
        }
        return res;
    }

    @RequestMapping("/paySuccessInfo.do")
    @ResponseBody
    @SignValid(paramType = UpgradePaySuccessInfoReq.class)
    public UpgradePaySuccessInfoRes upgradePaySuccessInfo(HttpServletRequest request,
                                 UpgradePaySuccessInfoReq req, ComUser user){
        UpgradePaySuccessInfoRes res = new UpgradePaySuccessInfoRes();
        Long orderId = req.getOrderId();
        try{
            // 校验订单号
            if(orderId == null || orderId.longValue() <= 0l){
                res.setResCode(SysResCodeCons.RES_CODE_UPGRADE_ORDER_ID_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_UPGRADE_ORDER_ID_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_UPGRADE_ORDER_ID_ERROR_MSG);
            }
            PfBorder pfBorder = bOrderService.getPfBorderById(orderId);
            if(pfBorder == null){
                res.setResCode(SysResCodeCons.RES_CODE_UPGRADE_ORDER_NOT_EXISTS);
                res.setResMsg(SysResCodeCons.RES_CODE_UPGRADE_ORDER_NOT_EXISTS_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_UPGRADE_ORDER_NOT_EXISTS_MSG);
            }
            if(pfBorder.getUserId().longValue() != user.getId().longValue()){
                res.setResCode(SysResCodeCons.RES_CODE_UPGRADE_ORDER_USER_NOTMATCH);
                res.setResMsg(SysResCodeCons.RES_CODE_UPGRADE_ORDER_USER_NOTMATCH_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_UPGRADE_ORDER_USER_NOTMATCH_MSG);
            }
            if(pfBorder.getOrderType().intValue() != BOrderType.UPGRADE.getCode().intValue()){
                res.setResCode(SysResCodeCons.RES_CODE_UPGRADE_ORDER_TYPE_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_UPGRADE_ORDER_TYPE_ERROR_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_UPGRADE_ORDER_TYPE_ERROR_MSG);
            }
            PfUserUpgradeNotice notice = userUpgradeNoticeService.findByPfBorderId(orderId);
            if(notice == null){
                throw new BusinessException("怎么可能");
            }
            ComUser oldPUser = userService.getUserById(notice.getUserPid());
            ComUser nowPUser = userService.getUserById(pfBorder.getUserPid());
            ComAgentLevel oldLevel = comAgentLevelService.selectByPrimaryKey(notice.getOrgAgentLevelId());
            ComAgentLevel nowLevel = comAgentLevelService.selectByPrimaryKey(notice.getWishAgentLevelId());
            ComSku sku = skuService.getSkuById(notice.getSkuId());
            List<PfBorderItem> items = bOrderService.getPfBorderItemDetail(orderId);
            Integer quantity = 0;
            for(PfBorderItem item:items){
                if(item != null && item.getSkuId().intValue() == notice.getSkuId().intValue()){
                    quantity += item.getQuantity();
                }
            }

            // 组织返回数据
            res.setUserName(user.getRealName());
            res.setSkuName(sku.getName());
            res.setNowLevelName(nowLevel.getName());
            res.setOldLevelName(oldLevel.getName());
            res.setOldParentName(oldPUser.getRealName());
            res.setNowParentName(nowPUser.getRealName());
            res.setStockChangeView("+" + quantity);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }

        return res;
    }



}
