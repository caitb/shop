package com.masiis.shop.api.controller.familyExtra;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.bean.family.AgentSecondarySkuReq;
import com.masiis.shop.api.bean.product.ProDetailReq;
import com.masiis.shop.api.bean.system.CommonActivityReq;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.dao.platform.product.PbBannerMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PbBanner;
import com.masiis.shop.web.platform.service.order.DirectAgentService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by jiajinghao on 2016/8/3.
 */
@Controller
@RequestMapping("/extra")
public class FamilyExtraController extends BaseController {

    private Logger log = Logger.getLogger(this.getClass());
    @Resource
    private DirectAgentService directAgentService;
    @Resource
    private PbBannerMapper pbBannerMapper;

    /**
     * 非主打添加到仓库
     */
    @RequestMapping("/agentSku")
    @ResponseBody
    @SignValid(paramType = AgentSecondarySkuReq.class)
    public BaseRes worldInfo(HttpServletRequest request, AgentSecondarySkuReq req, ComUser user) {
        log.info("非主打商品添加到代理中");
        BaseRes res = new BaseRes();
        try {
            directAgentService.directAgent(user, req.getSkuId());
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
        }
        return res;
    }


    /**
     * 活动通用界面
     * html 页面
     */
    @RequestMapping("/activityView")
    @SignValid(paramType = CommonActivityReq.class,isPageReturn = true)
    public ModelAndView getActivityHtml(HttpServletRequest request, CommonActivityReq req){
        ModelAndView mav = null;
        try {
            switch (req.getPbbannerLink()){
                case "activity.html" :
                    mav = new ModelAndView("activity/common_activity"); break;
                default:
                    mav = new ModelAndView("activity/404"); break;
            }
            mav.addObject("activityData", "hello");
        } catch (Exception e){
            mav.addObject("message", e.getMessage());
        }
        return mav;
    }
}
