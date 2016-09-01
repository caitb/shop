package com.masiis.shop.api.controller.family;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.common.CommonReq;
import com.masiis.shop.api.bean.family.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.dao.beans.family.FamilyHomeListPo;
import com.masiis.shop.dao.beans.family.MyTeamListHomePo;
import com.masiis.shop.dao.beans.family.TeamListPoPaging;
import com.masiis.shop.dao.beans.family.TeamMemberInfo;
import com.masiis.shop.dao.beans.user.CountGroup;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.service.user.MyTeamService;
import com.masiis.shop.web.platform.service.user.PfUserBrandService;
import com.masiis.shop.web.platform.service.user.PfUserOrganizationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/api/myteam")
public class MyTeamController {

    private final Log log = LogFactory.getLog(MyTeamController.class);

    @Resource
    private MyTeamService myTeamService;
    @Resource
    private PfUserBrandService pfUserBrandService;
    @Resource
    private PfUserOrganizationService pfUserOrganizationService;

    /**
     * 我的团队列表首页
     */
    @RequestMapping("/teamListHome.do")
    @ResponseBody
    @SignValid(paramType = MyTeamReq.class)
    public MyTeamRes teamListHome(HttpServletRequest request, MyTeamReq req, ComUser comUser){
        log.info("我的团队列表首页");
        MyTeamRes res = new MyTeamRes();
        try {
            MyTeamListHomePo team = myTeamService.teamListHome(req.getUserSkuId());
            res.setMyTeamListHomePo(team);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(e.getMessage());
        }
        log.info("【返回参数】" + JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 我的团队列表
     */
    @RequestMapping("/teamList.do")
    @ResponseBody
    @SignValid(paramType = TeamListReq.class)
    public TeamListRes teamList(HttpServletRequest request, TeamListReq req, ComUser comUser){
        log.info("我的团队列表");
        TeamListRes res = new TeamListRes();
        TeamListPoPaging teamListPoPaging;
        try {
            teamListPoPaging = myTeamService.teamList(true,req.getUserSkuId(),req.getPageNum());
            res.setTeamListPoPaging(teamListPoPaging);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(e.getMessage());
        }
        log.info("【返回参数】" + JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 团队成员详细信息
     */
    @RequestMapping("/memberinfo.do")
    @ResponseBody
    @SignValid(paramType = MyTeamReq.class)
    public TeamMemberInfoRes memberInfo(HttpServletRequest request, MyTeamReq req, ComUser comUser){
        log.info("团队成员详细信息");
        TeamMemberInfoRes res = new TeamMemberInfoRes();
        try {
            TeamMemberInfo teamMemberInfo = myTeamService.getMemberInfo(req.getUserSkuId());
            res.setTeamMemberInfo(teamMemberInfo);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error("获取团队成员信息失败!");
            e.printStackTrace();
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        log.info("【返回参数】" + JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 家族首页
     * @param request   request
     * @param req       req
     * @param comUser   user
     * @return          res
     */
    @RequestMapping("/familyHome.do")
    @ResponseBody
    @SignValid(paramType = CommonReq.class)
    public FamilyHomeRes familyHome(HttpServletRequest request, CommonReq req, ComUser comUser){
        FamilyHomeRes res = new FamilyHomeRes();
        try {
            Integer brandNum = pfUserBrandService.getMyBrandNum(comUser.getId());
            CountGroup countGroup = pfUserBrandService.countFamilyOrTeamNumber(comUser.getId());
            res.setBrandNum(brandNum);
            res.setCountGroup(countGroup);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        log.info("【返回参数】"+JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 家族首页列表展示
     * @param request   request
     * @param req       req
     * @param comUser   user
     * @return          res
     */
//    @RequestMapping("/familyHomeList.do")
//    @ResponseBody
//    @SignValid(paramType = FamilyHomeListReq.class)
//    public FamilyHomeListRes familyHomeList(HttpServletRequest request, FamilyHomeListReq req, ComUser comUser){
//
//        log.info("家族首页列表展示");
//        FamilyHomeListRes res = new FamilyHomeListRes();
//        try {
//            FamilyHomeListPo familyHomeListPo = null;
//            if (req.getFlag().intValue() == 1){
//                familyHomeListPo = pfUserOrganizationService.getMyFamilyHomeList(comUser.getId(), req.getPageNum());
//            }else {
//                familyHomeListPo = pfUserOrganizationService.getJoinFamilyHomeList(comUser.getId(), req.getPageNum());
//            }
//            res.setFamilyHomeListPo(familyHomeListPo);
//            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
//            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
//        }catch (Exception e){
//            e.printStackTrace();
//            if (e.getMessage().equals("1")){
//                res.setResCode(SysResCodeCons.RES_CODE_PAGE_LAST);
//                res.setResMsg(SysResCodeCons.RES_CODE_PAGE_LAST_MSG);
//            }else {
//                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
//                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
//            }
//        }
//        log.info("【返回参数】"+JSONObject.toJSONString(res));
//        return res;
//    }
}
