package com.masiis.shop.web.platform.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.user.PfUserUpGradeInfo;
import com.masiis.shop.dao.po.ComAgentLevel;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserUpgradeNotice;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.ComAgentLevelService;
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
    /**
     * jjh
     * 我的下级申请记录<默认>
     * @return
     */
    @RequestMapping("/lower")
    public ModelAndView myUpgradeNoticeInfo(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView("/platform/user/upgrade/shengjiguanli");
        try {
            ComUser comUser = getComUser(request);
            if(comUser==null){
              throw new BusinessException("用户出现问题");
            }
            List<PfUserUpGradeInfo> pfUserUpGradeInfoList = new ArrayList<>();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            List<PfUserUpgradeNotice> pfUserUpgradeNoticeList = upgradeNoticeService.getPfUserUpGradeInfoByUserPId(comUser.getId());
            if(pfUserUpgradeNoticeList!=null && pfUserUpgradeNoticeList.size()>0){
                for (PfUserUpgradeNotice pfUserUpgradeNotice :pfUserUpgradeNoticeList){
                    PfUserUpGradeInfo pfUserUpGradeInfo = new PfUserUpGradeInfo();
                    pfUserUpGradeInfo.setPfUserUpgradeNotice(pfUserUpgradeNotice);
                    ComSku comSku = skuService.getSkuById(pfUserUpgradeNotice.getSkuId());
                    ComAgentLevel orglevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getOrgAgentLevelId());
                    ComAgentLevel wishLevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getWishAgentLevelId());
                    pfUserUpGradeInfo.setSkuName(comSku.getName());
                    pfUserUpGradeInfo.setComUser(userService.getUserById(pfUserUpgradeNotice.getUserId()));
                    pfUserUpGradeInfo.setOrgLevelName(orglevel.getName());
                    pfUserUpGradeInfo.setWishLevelName(wishLevel.getName());
                    String sDate=sdf.format(pfUserUpgradeNotice.getCreateTime());
                    pfUserUpGradeInfo.setCreateDate(sDate);
                    pfUserUpGradeInfoList.add(pfUserUpGradeInfo);
                }
            }
            mv.addObject("pfUserUpGradeInfoList",pfUserUpGradeInfoList);
        }catch (Exception e){
          log.info(e.getMessage());
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
    public String lowerUpgradeNoticeInfo(HttpServletRequest request,HttpServletResponse response,
                                         @RequestParam(value="tabId",required = true) Integer tabId){
        JSONObject object = new JSONObject();
        try {
            ComUser comUser = getComUser(request);
            if(comUser==null){
                throw new BusinessException("用户出现问题");
            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            if(tabId==0){
                List<PfUserUpGradeInfo> pfUserUpGradeInfoList = new ArrayList<>();
                List<PfUserUpgradeNotice> pfUserUpgradeNoticeList = upgradeNoticeService.getPfUserUpGradeInfoByUserId(comUser.getId());
                if(pfUserUpgradeNoticeList!=null && pfUserUpgradeNoticeList.size()>0){
                    for (PfUserUpgradeNotice pfUserUpgradeNotice :pfUserUpgradeNoticeList){
                        PfUserUpGradeInfo pfUserUpGradeInfo = new PfUserUpGradeInfo();
                        pfUserUpGradeInfo.setPfUserUpgradeNotice(pfUserUpgradeNotice);
                        ComSku comSku = skuService.getSkuById(pfUserUpgradeNotice.getSkuId());
                        ComAgentLevel orglevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getOrgAgentLevelId());
                        ComAgentLevel wishLevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getWishAgentLevelId());
                        pfUserUpGradeInfo.setSkuName(comSku.getName());
                        pfUserUpGradeInfo.setComUser(comUser);
                        pfUserUpGradeInfo.setOrgLevelName(orglevel.getName());
                        pfUserUpGradeInfo.setWishLevelName(wishLevel.getName());
                        String sDate=sdf.format(pfUserUpgradeNotice.getCreateTime());
                        pfUserUpGradeInfo.setCreateDate(sDate);
                        pfUserUpGradeInfoList.add(pfUserUpGradeInfo);
                    }
                }
                object.put("pfUserUpGradeInfoList",pfUserUpGradeInfoList);
            }else if (tabId==1){
                List<PfUserUpGradeInfo> pfUserUpGradeInfoList = new ArrayList<>();
                List<PfUserUpgradeNotice> pfUserUpgradeNoticeList = upgradeNoticeService.getPfUserUpGradeInfoByUserPId(comUser.getId());
                if(pfUserUpgradeNoticeList!=null && pfUserUpgradeNoticeList.size()>0){
                    for (PfUserUpgradeNotice pfUserUpgradeNotice :pfUserUpgradeNoticeList){
                        PfUserUpGradeInfo pfUserUpGradeInfo = new PfUserUpGradeInfo();
                        pfUserUpGradeInfo.setPfUserUpgradeNotice(pfUserUpgradeNotice);
                        ComSku comSku = skuService.getSkuById(pfUserUpgradeNotice.getSkuId());
                        ComAgentLevel orglevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getOrgAgentLevelId());
                        ComAgentLevel wishLevel = comAgentLevelService.selectByPrimaryKey(pfUserUpgradeNotice.getWishAgentLevelId());
                        pfUserUpGradeInfo.setSkuName(comSku.getName());
                        pfUserUpGradeInfo.setComUser(userService.getUserById(pfUserUpgradeNotice.getUserId()));
                        pfUserUpGradeInfo.setOrgLevelName(orglevel.getName());
                        pfUserUpGradeInfo.setWishLevelName(wishLevel.getName());
                        String sDate=sdf.format(pfUserUpgradeNotice.getCreateTime());
                        pfUserUpGradeInfo.setCreateDate(sDate);
                        pfUserUpGradeInfoList.add(pfUserUpGradeInfo);
                    }
                }
                object.put("pfUserUpGradeInfoList",pfUserUpGradeInfoList);
            }else if (tabId==2){

            }else {
                throw new BusinessException("参数有误");
            }
        }catch (Exception e){
          log.info(e.getMessage());
        }
        return object.toJSONString();
    }


}
