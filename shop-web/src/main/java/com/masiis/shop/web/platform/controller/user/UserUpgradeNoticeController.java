package com.masiis.shop.web.platform.controller.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.user.PfUserUpGradeInfo;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserUpgradeNotice;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.UpgradeNoticeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JingHao on 2016/6/15.
 * 升级管理
 */
@Controller
@RequestMapping(value = "upgradeInfo")
public class UserUpgradeNoticeController extends BaseController {

    private Logger log = Logger.getLogger(this.getClass());
    @Resource
    private UpgradeNoticeService upgradeNoticeService;
    @Resource
    private SkuService skuService;
    /**
     * jjh
     * 我的申请记录
     * @return
     */
    @RequestMapping(value = "myUpgradeInfo")
    public ModelAndView myUpgradeNoticeInfo(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView("");
        try {
            ComUser comUser = getComUser(request);
            if(comUser==null){
              throw new BusinessException("用户出现问题");
            }
            List<PfUserUpGradeInfo> pfUserUpGradeInfoList = new ArrayList<>();
            List<PfUserUpgradeNotice> pfUserUpgradeNoticeList = upgradeNoticeService.getPfUserUpGradeInfoByUserId(comUser.getId());
            if(pfUserUpgradeNoticeList!=null && pfUserUpgradeNoticeList.size()>0){
                for (PfUserUpgradeNotice pfUserUpgradeNotice :pfUserUpgradeNoticeList){
                    PfUserUpGradeInfo pfUserUpGradeInfo = new PfUserUpGradeInfo();
                    pfUserUpGradeInfo.setPfUserUpgradeNotice(pfUserUpgradeNotice);
                    ComSku comSku = skuService.getSkuById(pfUserUpgradeNotice.getSkuId());
                    pfUserUpGradeInfo.setSkuName(comSku.getName());
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
     * 我的下级申请记录
     * @return
     */
    @RequestMapping(value = "lowerUpgradeInfo")
    public String lowerUpgradeNoticeInfo(HttpServletRequest request,HttpServletResponse response){

        return null;
    }


}
