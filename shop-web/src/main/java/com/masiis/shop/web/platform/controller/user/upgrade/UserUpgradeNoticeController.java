package com.masiis.shop.web.platform.controller.user.upgrade;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.enums.upgrade.UpGradeStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.user.PfUserUpGradeInfo;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.ComAgentLevelService;
import com.masiis.shop.web.platform.service.user.PfUserSkuService;
import com.masiis.shop.web.platform.service.user.UpgradeNoticeService;
import com.masiis.shop.web.platform.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JingHao on 2016/6/15.
 * 升级管理
 */
@Controller
@RequestMapping("/upgradeInfo")
public class UserUpgradeNoticeController extends BaseController {

    private Logger log = Logger.getLogger(this.getClass());
    @Resource
    private UpgradeNoticeService upgradeNoticeService;
    @Resource
    private SkuService skuService;
    @Resource
    private ComAgentLevelService comAgentLevelService;
    @Resource
    private UserService userService;
    @Resource
    private PfUserSkuService pfUserSkuService;
    /**
     * jjh
     * 我的下级申请记录<默认>
     * @return
     */
    @RequestMapping("/lower")
    public ModelAndView myUpgradeNoticeInfo(HttpServletRequest request,HttpServletResponse response,
                                            @RequestParam(value = "tabId", required = true) Integer tabId){
        ModelAndView mv = new ModelAndView("/platform/user/upgrade/shengjiguanli");
        try {
            ComUser comUser = getComUser(request);
            if(comUser==null){
              throw new BusinessException("用户出现问题");
            }
            //初始化商品查询列表
            List<PfUserSku> pfUserSkuList = pfUserSkuService.getPfUserSkuInfoByUserId(comUser.getId());
            if(pfUserSkuList==null){
                throw new BusinessException("代理商品异常，初始化商品列表失败");
            }else{
                List<ComSku> skuList = new ArrayList();
                for(PfUserSku pfUserSku :pfUserSkuList){
                    ComSku comSku = skuService.getSkuById(pfUserSku.getSkuId());
                    skuList.add(comSku);
                }
                mv.addObject("skuList", skuList);
            }
            //初始化申请状态列表
            mv.addObject("statusPickList", UpGradeStatus.statusPickList.values());
            List<PfUserUpGradeInfo> pfUserUpGradeInfoList = new ArrayList<>();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            List<PfUserUpgradeNotice> pfUserUpgradeNoticeList = upgradeNoticeService.getPfUserUpGradeInfoByUserId(comUser.getId());
            if (pfUserUpgradeNoticeList != null && pfUserUpgradeNoticeList.size() > 0) {
                for (PfUserUpgradeNotice pfUserUpgradeNotice : pfUserUpgradeNoticeList) {
                    PfUserUpGradeInfo pfUserUpGradeInfo = new PfUserUpGradeInfo();
                    pfUserUpGradeInfo.setPfUserUpgradeNotice(pfUserUpgradeNotice);
                    ComSku comSku = skuService.getSkuById(pfUserUpgradeNotice.getSkuId());
                    ComAgentLevel orglevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getOrgAgentLevelId());
                    ComAgentLevel wishLevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getWishAgentLevelId());
                    pfUserUpGradeInfo.setSkuName(comSku.getName());
                    pfUserUpGradeInfo.setWxHeadImg(comUser.getWxHeadImg());
                    pfUserUpGradeInfo.setRealName(comUser.getRealName());
                    pfUserUpGradeInfo.setOrgLevelName(orglevel.getName());
                    pfUserUpGradeInfo.setWishLevelName(wishLevel.getName());
                    String sDate = sdf.format(pfUserUpgradeNotice.getCreateTime());
                    pfUserUpGradeInfo.setCreateDate(sDate);
                    pfUserUpGradeInfo.setStatusValue(upgradeNoticeService.coverCodeByLowerUpgrade(pfUserUpgradeNotice.getStatus()));
                    pfUserUpGradeInfoList.add(pfUserUpGradeInfo);
                }
            }
            mv.addObject("pfUserUpGradeInfoList",pfUserUpGradeInfoList);
            mv.addObject("tabId",tabId);
        }catch (Exception e){
            log.info(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        return mv;
    }
    /**
     * jjh
     * 0：我的申请
     * 1：我的下级申请
     * 2：一次性返利
     * @return
     */
    @RequestMapping(value = "tab")
    @ResponseBody
    public String lowerUpgradeNoticeInfo(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(value = "tabId", required = true) Integer tabId) {
        JSONObject object = new JSONObject();
        try {
            ComUser comUser = getComUser(request);
            if (comUser == null) {
                throw new BusinessException("用户出现问题");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (tabId == 0) {
                List<PfUserUpGradeInfo> pfUserUpGradeInfoList = new ArrayList<>();
                List<PfUserUpgradeNotice> pfUserUpgradeNoticeList = upgradeNoticeService.getPfUserUpGradeInfoByUserId(comUser.getId());
                if (pfUserUpgradeNoticeList != null && pfUserUpgradeNoticeList.size() > 0) {
                    for (PfUserUpgradeNotice pfUserUpgradeNotice : pfUserUpgradeNoticeList) {
                        PfUserUpGradeInfo pfUserUpGradeInfo = new PfUserUpGradeInfo();
                        pfUserUpGradeInfo.setPfUserUpgradeNotice(pfUserUpgradeNotice);
                        ComSku comSku = skuService.getSkuById(pfUserUpgradeNotice.getSkuId());
                        ComAgentLevel orglevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getOrgAgentLevelId());
                        ComAgentLevel wishLevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getWishAgentLevelId());
                        pfUserUpGradeInfo.setSkuName(comSku.getName());
                        pfUserUpGradeInfo.setWxHeadImg(comUser.getWxHeadImg());
                        pfUserUpGradeInfo.setRealName(comUser.getRealName());
                        pfUserUpGradeInfo.setOrgLevelName(orglevel.getName());
                        pfUserUpGradeInfo.setWishLevelName(wishLevel.getName());
                        String sDate = sdf.format(pfUserUpgradeNotice.getCreateTime());
                        pfUserUpGradeInfo.setCreateDate(sDate);
                        pfUserUpGradeInfo.setStatusValue(upgradeNoticeService.coverCodeByLowerUpgrade(pfUserUpgradeNotice.getStatus()));
                        pfUserUpGradeInfoList.add(pfUserUpGradeInfo);
                    }
                }
                object.put("pfUserUpGradeInfoList", pfUserUpGradeInfoList);
            } else if (tabId == 1) {
                List<PfUserUpGradeInfo> pfUserUpGradeInfoList = new ArrayList<>();
                List<PfUserUpgradeNotice> pfUserUpgradeNoticeList = upgradeNoticeService.getPfUserUpGradeInfoByUserPId(comUser.getId());
                if (pfUserUpgradeNoticeList != null && pfUserUpgradeNoticeList.size() > 0) {
                    for (PfUserUpgradeNotice pfUserUpgradeNotice : pfUserUpgradeNoticeList) {
                        PfUserUpGradeInfo pfUserUpGradeInfo = new PfUserUpGradeInfo();
                        pfUserUpGradeInfo.setPfUserUpgradeNotice(pfUserUpgradeNotice);
                        ComSku comSku = skuService.getSkuById(pfUserUpgradeNotice.getSkuId());
                        ComAgentLevel orglevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getOrgAgentLevelId());
                        ComAgentLevel wishLevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getWishAgentLevelId());
                        pfUserUpGradeInfo.setSkuName(comSku.getName());
                        pfUserUpGradeInfo.setWxHeadImg(userService.getUserById(pfUserUpgradeNotice.getUserId()).getWxHeadImg());
                        pfUserUpGradeInfo.setRealName(userService.getUserById(pfUserUpgradeNotice.getUserId()).getRealName());
                        pfUserUpGradeInfo.setOrgLevelName(orglevel.getName());
                        pfUserUpGradeInfo.setWishLevelName(wishLevel.getName());
                        String sDate = sdf.format(pfUserUpgradeNotice.getCreateTime());
                        pfUserUpGradeInfo.setCreateDate(sDate);
                        pfUserUpGradeInfo.setStatusValue(upgradeNoticeService.coverCodeByMyUpgrade(pfUserUpgradeNotice.getStatus()));
                        pfUserUpGradeInfoList.add(pfUserUpGradeInfo);
                    }
                }
                object.put("pfUserUpGradeInfoList", pfUserUpGradeInfoList);
            } else if (tabId == 2) {
                List<PfUserUpGradeInfo> pfUserUpGradeInfoList = new ArrayList<>();
                List<PfUserRebate> pfUserRebateList = upgradeNoticeService.getPfUserRebateByUserId(comUser.getId());//默认获得返利
                if (pfUserRebateList != null && pfUserRebateList.size() > 0) {
                    for (PfUserRebate pfUserRebate : pfUserRebateList) {
                        PfUserUpGradeInfo pfUserUpGradeInfo = new PfUserUpGradeInfo();
                        PfUserUpgradeNotice pfUserUpgradeNotice = upgradeNoticeService.getPfUserUpGradeInfoByPrimaryKey(pfUserRebate.getUserUpgradeNoticeId());
                        pfUserUpGradeInfo.setPfUserUpgradeNotice(pfUserUpgradeNotice);
                        ComSku comSku = skuService.getSkuById(pfUserUpgradeNotice.getSkuId());
                        ComAgentLevel orglevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getOrgAgentLevelId());
                        ComAgentLevel wishLevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getWishAgentLevelId());
                        pfUserUpGradeInfo.setSkuName(comSku.getName());
                        pfUserUpGradeInfo.setWxHeadImg(comUser.getWxHeadImg());
                        pfUserUpGradeInfo.setRealName(comUser.getRealName());
                        pfUserUpGradeInfo.setOrgLevelName(orglevel.getName());
                        pfUserUpGradeInfo.setWishLevelName(wishLevel.getName());
                        String sDate = sdf.format(pfUserRebate.getCreateTime());
                        pfUserUpGradeInfo.setCreateDate(sDate);
                        pfUserUpGradeInfo.setStatusValue("获得返利");
                        pfUserUpGradeInfoList.add(pfUserUpGradeInfo);
                    }
                }
                object.put("pfUserUpGradeInfoList", pfUserUpGradeInfoList);
            } else {
                throw new BusinessException("参数有误");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        return object.toJSONString();
    }

    /**
     * jjh
     * 搜索查询
     * @param request
     * @param response
     * @param skuId 商品
     * @param upStatus 升级处理状态
     * @param rebateType 返利类型
     * @return
     */
    @RequestMapping(value = "search")
    @ResponseBody
    public String searchUpgradeByParam(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "skuId", required = false) Integer skuId,
                                       @RequestParam(value = "upStatus", required = false) Integer upStatus,
                                       @RequestParam(value = "rebateType", required = false) Integer rebateType,
                                       @RequestParam(value = "tabId", required = true) Integer tabId) {
        JSONObject object = new JSONObject();
        try {
            ComUser comUser = getComUser(request);
            if (comUser == null) {
                throw new BusinessException("用户出现问题");
            }
            List<PfUserUpGradeInfo> pfUserUpGradeInfoList = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (tabId ==2) { //一次性返利查询
                List<PfUserUpgradeNotice> pfUserUpgradeNoticeList = null;
                if (rebateType == 0) {//获得返利
                    pfUserUpgradeNoticeList = upgradeNoticeService.getPfUserUpGradeInfoByRebateAndSkuId(skuId, null, comUser.getId());
                } else if (rebateType == 1) { //支付返利
                    pfUserUpgradeNoticeList = upgradeNoticeService.getPfUserUpGradeInfoByRebateAndSkuId(skuId, comUser.getId(), null);
                } else {
                    throw new BusinessException("传入参数有误");
                }
                if (pfUserUpgradeNoticeList != null) {
                    for (PfUserUpgradeNotice pfUserUpgradeNotice : pfUserUpgradeNoticeList) {
                        PfUserUpGradeInfo pfUserUpGradeInfo = new PfUserUpGradeInfo();
                        pfUserUpGradeInfo.setPfUserUpgradeNotice(pfUserUpgradeNotice);
                        ComSku comSku = skuService.getSkuById(pfUserUpgradeNotice.getSkuId());
                        ComAgentLevel orglevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getOrgAgentLevelId());
                        ComAgentLevel wishLevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getWishAgentLevelId());
                        pfUserUpGradeInfo.setSkuName(comSku.getName());
                        if (rebateType == 0) {
                            pfUserUpGradeInfo.setWxHeadImg(comUser.getWxHeadImg());
                            pfUserUpGradeInfo.setRealName(comUser.getRealName());
                            pfUserUpGradeInfo.setStatusValue("获得返利");
                        } else {
                            pfUserUpGradeInfo.setWxHeadImg(userService.getUserById(pfUserUpgradeNotice.getUserId()).getWxHeadImg());
                            pfUserUpGradeInfo.setRealName(userService.getUserById(pfUserUpgradeNotice.getUserId()).getRealName());
                            pfUserUpGradeInfo.setStatusValue("支付返利");
                        }
                        pfUserUpGradeInfo.setOrgLevelName(orglevel.getName());
                        pfUserUpGradeInfo.setWishLevelName(wishLevel.getName());
                        String sDate = sdf.format(pfUserUpgradeNotice.getCreateTime());
                        pfUserUpGradeInfo.setCreateDate(sDate);
                        pfUserUpGradeInfoList.add(pfUserUpGradeInfo);
                    }
                }
                object.put("pfUserUpGradeInfoList", pfUserUpGradeInfoList);
            } else {//申请状态查询
                List<PfUserUpgradeNotice> pfUserUpgradeNoticeList = upgradeNoticeService.getPfUserUpGradeInfoByParam(comUser.getId(), skuId, upStatus);
                if (pfUserUpgradeNoticeList != null && pfUserUpgradeNoticeList.size() > 0) {
                    for (PfUserUpgradeNotice pfUserUpgradeNotice : pfUserUpgradeNoticeList) {
                        PfUserUpGradeInfo pfUserUpGradeInfo = new PfUserUpGradeInfo();
                        pfUserUpGradeInfo.setPfUserUpgradeNotice(pfUserUpgradeNotice);
                        ComSku comSku = skuService.getSkuById(pfUserUpgradeNotice.getSkuId());
                        ComAgentLevel orglevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getOrgAgentLevelId());
                        ComAgentLevel wishLevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getWishAgentLevelId());
                        pfUserUpGradeInfo.setSkuName(comSku.getName());
                        pfUserUpGradeInfo.setWxHeadImg(userService.getUserById(pfUserUpgradeNotice.getUserId()).getWxHeadImg());
                        pfUserUpGradeInfo.setRealName(userService.getUserById(pfUserUpgradeNotice.getUserId()).getRealName());
                        pfUserUpGradeInfo.setOrgLevelName(orglevel.getName());
                        pfUserUpGradeInfo.setWishLevelName(wishLevel.getName());
                        String sDate = sdf.format(pfUserUpgradeNotice.getCreateTime());
                        pfUserUpGradeInfo.setCreateDate(sDate);
                        pfUserUpGradeInfo.setStatusValue(upgradeNoticeService.coverCodeByMyUpgrade(pfUserUpgradeNotice.getStatus()));
                        pfUserUpGradeInfoList.add(pfUserUpGradeInfo);
                    }
                }
                object.put("pfUserUpGradeInfoList", pfUserUpGradeInfoList);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
        return object.toJSONString();
    }
}
