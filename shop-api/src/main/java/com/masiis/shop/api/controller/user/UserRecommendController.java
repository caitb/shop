package com.masiis.shop.api.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.common.CommonReq;
import com.masiis.shop.api.bean.user.recommend.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.common.enums.platform.AuditStatusEnum;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.common.util.RootPathUtils;
import com.masiis.shop.dao.beans.recommend.MyRecommendPo;
import com.masiis.shop.dao.beans.user.CountGroup;
import com.masiis.shop.dao.beans.user.HelpRecommended;
import com.masiis.shop.dao.beans.user.RecommendedMen;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.ComAgentLevelService;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.service.order.PfBorderRecommenRewardService;
import com.masiis.shop.web.platform.service.user.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

/**
 * Created by wangbingjian on 2016/8/1.
 */
@Controller
@RequestMapping(value = "/api/recommend")
public class UserRecommendController {

    private static final Logger logger = Logger.getLogger(UserRecommendController.class);

    @Resource
    private PfBorderRecommenRewardService pfBorderRecommenRewardService;
    @Resource
    private PfUserRecommendRelationService pfUserRecommendRelationService;
    @Resource
    private UserCertificateService userCertificateService;
    @Resource
    private SkuService skuService;
    @Resource
    private UserService userService;
    @Resource
    private ComAgentLevelService comAgentLevelService;
    @Resource
    private PfUserStatisticsService pfUserStatisticsService;
    @Resource
    private PfUserSkuService pfUserSkuService;
    @Resource
    private CountGroupService countGroupService;

    /**
     * 我的推荐首页
     * @param request
     * @return
     */
    @RequestMapping(value = "/myRecommenHome.do")
    @ResponseBody
    @SignValid(paramType = CommonReq.class)
    public MyRecommendRes myRecommen(HttpServletRequest request, CommonReq req, ComUser comUser){
        logger.info("我的推荐首页");
        MyRecommendRes res = new MyRecommendRes();
        try {
            MyRecommendPo myRecommendPo = pfUserRecommendRelationService.myRecommen(comUser.getId(),1);
            res.setMyRecommendPo(myRecommendPo);
        }catch (Exception e){
            e.printStackTrace();
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        logger.info("返回参数：" + JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 查询推荐奖励和获得奖励订单列表
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/myRecommen.do")
    @ResponseBody
    @SignValid(paramType = MyRecommendReq.class)
    public MyRecommendRes ajaxMyRecommen(HttpServletRequest request, MyRecommendReq req, ComUser comUser) throws Exception{
        logger.info("查询推荐订单列表");
        MyRecommendRes res = new MyRecommendRes();
        try {
            MyRecommendPo myRecommendPo = pfBorderRecommenRewardService.getRecommenRewardOrder(comUser.getId(), req.getPageNum(), req.getTab());
            res.setMyRecommendPo(myRecommendPo);
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
        logger.info("返回参数：" + JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 我推荐的详情列表
     * @date 2016/6/16 17:27
     */
    @RequestMapping("/myRecommendList.do")
    @ResponseBody
    @SignValid(paramType = MyRecomReq.class)
    public MyRecomRes myRecommendList(HttpServletRequest request, MyRecomReq req, ComUser comUser){
        logger.info("我推荐的详情列表");
        MyRecomRes res = new MyRecomRes();
        try{
            Integer skuId = req.getSkuId() == null?null:req.getSkuId() == -1?null:req.getSkuId();
            Integer level = req.getLevel() == null?null:req.getLevel() == -1?null:req.getLevel();
            logger.info("skuId = " + skuId);
            logger.info("level = " + level);
            logger.info("pageNum = " + req.getPageNum());
            RecommendedMen recommendedMen = pfUserRecommendRelationService.recommendedMenPage(comUser.getId(), skuId, level, req.getPageNum(), true);
            res.setRecommendedMens(recommendedMen);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            logger.error("获取代理产品列表失败!",e);
        }
        logger.info(JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 帮我推荐的详情列表
     * @author muchaofeng
     * @date 2016/6/15 17:44
     */
    @RequestMapping("/recommendGiveList.do")
    @ResponseBody
    @SignValid(paramType = MyRecomReq.class)
    public MyRecomRes helpRecommendList(HttpServletRequest request, MyRecomReq req, ComUser comUser){
        logger.info("帮我推荐的人");
        MyRecomRes res = new MyRecomRes();
        if (req.getPageNum().intValue() <= 0){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg("页码错误");
            logger.info(JSONObject.toJSONString(res));
            return res;
        }
        try{
            Integer skuId = req.getSkuId() == null?null:req.getSkuId() == -1?null:req.getSkuId();
            Integer level = req.getLevel() == null?null:req.getLevel() == -1?null:req.getLevel();
            logger.info("skuId = " + skuId);
            logger.info("level = " + level);
            logger.info("pageNum = " + req.getPageNum());
            RecommendedMen recommendedMen = pfUserRecommendRelationService.recommendedMenPage(comUser.getId(), skuId, null, req.getPageNum(), false);
            res.setRecommendedMens(recommendedMen);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            logger.error("获取代理产品列表失败!",e);
        }
        logger.info(JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 我推荐的人个人信息
     * @param request
     * @param req
     * @param comUser
     * @return
     */
    @RequestMapping("/myRecommend.do")
    @ResponseBody
    @SignValid(paramType = MyRecomDetailReq.class)
    public MyRecomDetailRes myRecommendDetail(HttpServletRequest request, MyRecomDetailReq req, ComUser comUser){
        logger.info("我推荐的人个人信息");
        Long userId = req.getUserId();
        Integer skuId = req.getSkuId();
        MyRecomDetailRes res = new MyRecomDetailRes();
        try {
            PfUserCertificate certificate = userCertificateService.getCertificateByuserskuId(req.getUserId(),req.getSkuId());
            String ctValue = PropertiesUtils.getStringValue("index_user_certificate_url");

            ComSku skuName = skuService.getSkuName(skuId);
            ComUser user = userService.getUserById(userId);
            PfUserSku pfUserSku = pfUserSkuService.getPfUserSkuByUserIdAndSkuId(userId, skuId);
            ComAgentLevel agentLevel = comAgentLevelService.selectByPrimaryKey(pfUserSku.getAgentLevelId());
            //进货信息
            PfUserStatistics pfUserStatistics = pfUserStatisticsService.selectByUserIdAndSkuId(userId, skuId);
            CountGroup countGroup = countGroupService.countRecommendGroup(pfUserSku.getTreeCode());
            res.setAuditStatusCode(user.getAuditStatus());
            res.setAuditStatus(AuditStatusEnum.getName(user.getAuditStatus()));
            if(certificate!=null || user.getAuditStatus()==2){
                res.setCertificateIcon(ctValue+ certificate.getImgUrl());
                res.setWxId(user.getWxId());
            }
            res.setPurchaseNum(pfUserStatistics.getUpOrderCount());
            res.setPurchaseAmount(pfUserStatistics.getCostFee());
            res.setRecommendTeam(countGroup.getR_count());
            res.setUserName(user.getRealName());
            res.setSkuName(skuName.getName());
            res.setAgentLevelName(agentLevel.getName());
            res.setPhone(user.getMobile());
            res.setWxNickName(user.getWxNkName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            res.setCreateTime(sdf.format(pfUserSku.getCreateTime()));
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        logger.info(JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 帮我推荐的人详情
     * @param request
     * @param req
     * @param comUser
     * @return
     */
    @RequestMapping("/giveRecommend.do")
    @ResponseBody
    @SignValid(paramType = HelpRecomDetailReq.class)
    public HelpRecomDetailRes giveRecommend(HttpServletRequest request, HelpRecomDetailReq req, ComUser comUser){
        logger.info("帮我推荐的人详情");
        HelpRecomDetailRes res = new HelpRecomDetailRes();
        try{
            Integer skuId = req.getSkuId();
            Long userId = req.getUserId();
            ComSku skuName = skuService.getSkuName(skuId);
            ComUser userName = userService.getUserById(userId);
            HelpRecommended recommended = pfUserRecommendRelationService.findGiveListPaging(userId, skuId, req.getPageNum());
            res.setRecommended(recommended);
            res.setRecommedName(userName.getRealName());
            res.setSkuName(skuName.getName());
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
        logger.info(JSONObject.toJSONString(res));
        return res;
    }
}
