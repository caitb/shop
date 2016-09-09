package com.masiis.shop.web.platform.controller.system;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.statistic.BrandStatistic;
import com.masiis.shop.dao.beans.statistic.RecommendBrandStatistic;
import com.masiis.shop.dao.platform.user.PfUserBrandMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PbBanner;
import com.masiis.shop.dao.po.PfUserBrand;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.message.PfMessageSrRelationService;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.shop.JSSDKPFService;
import com.masiis.shop.web.platform.service.statistics.BrandStatisticService;
import com.masiis.shop.web.platform.service.statistics.RecommentBrandStatisticService;
import com.masiis.shop.web.platform.service.system.IndexShowService;
import com.masiis.shop.web.platform.service.user.CountGroupService;
import com.masiis.shop.web.platform.service.user.PfUserSkuService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by muchaofeng on 2016/3/2.
 */

@Controller
public class ShopIndexController extends BaseController {

    private final static Log log = LogFactory.getLog(ShopIndexController.class);

    @Resource
    private IndexShowService indexShowService;
    @Resource
    private CountGroupService countGroupService;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private JSSDKPFService jssdkService;
    @Resource
    private PfMessageSrRelationService pfMessageSrRelationService;
    @Resource
    private PfUserBrandMapper pfUserBrandMapper;
    @Resource
    private BrandStatisticService brandStatisticService;
    @Resource
    private RecommentBrandStatisticService recommentBrandStatisticService;
    @Resource
    private PfUserSkuService pfUserSkuService;

    @RequestMapping("/index")
    public ModelAndView shopIndexList(HttpServletRequest req) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        ComUser user = getComUser(req);
        log.info("B端首页");
        log.info("userId = " + user.getId());
        String value = PropertiesUtils.getStringValue("index_banner_url");//获取图片地址常量
        List<PbBanner> pbBanner = indexShowService.findPbBanner();//获取轮播图片
        for (PbBanner banner : pbBanner) {
            banner.setImgUrl(value + banner.getImgUrl());
        }
        int status = 0;
        PfUserSku pfUserSku = pfUserSkuService.getFirstPfUserSku(user.getId());
        if (pfUserSku == null){
            status = 0;
        }else {
            if (user.getAuditStatus().intValue() != 2){
                Date date = new Date();//获取当前时间
                Date date1 = DateUtil.addInteger(pfUserSku.getCreateTime(), 3);
                if (date.getTime() > date1.getTime()) {
                    status = 0;
                } else {
                    status = 1;
                }//验证是否超过三天
            }
        }
        log.info("是否超过三天:" + status);

        modelAndView.addObject("pbBanner", pbBanner);//封装图片地址集合
        modelAndView.addObject("user", user);
        modelAndView.addObject("status",status);
        modelAndView.setViewName("index");
        String curUrl = req.getRequestURL().toString();
        log.info("===========================B-index[curUrl=" + curUrl + "]");
        Map<String, String> shareMap = jssdkService.requestJSSDKData(curUrl);
        modelAndView.addObject("shareMap", shareMap);
        //消息UI提醒
        Integer countMsg = pfMessageSrRelationService.queryNoSeeNumsByToUserAndType(user.getId(), 2);
        modelAndView.addObject("countMsg", countMsg);
        return modelAndView;
    }

    /**
     * 获取首页的业务数据
     * jjh
     *
     * @param req
     * @return
     */
    @RequestMapping("/ajaxIndexData.do")
    @ResponseBody
    public String indexDataAjax(HttpServletRequest req) {
        JSONObject object = new JSONObject();
        try {
            ComUser user = getComUser(req);
            Integer totalUserNum = 0;
            Integer totalOrderNum = 0;
            BigDecimal totalSaleAmount = BigDecimal.ZERO;
            List<PfUserBrand> pfUserBrands = pfUserBrandMapper.selectByUserId(user.getId());
            for (PfUserBrand pfUserBrand : pfUserBrands) {
                BrandStatistic brandStatistic = brandStatisticService.selectBrandStatisticByUserIdAndBrandId(pfUserBrand.getUserId(), pfUserBrand.getBrandId());
                RecommendBrandStatistic recommendBrandStatistic = recommentBrandStatisticService.selectRecommentBrandStatisticByUserIdAndBrandId(pfUserBrand.getUserId(), pfUserBrand.getBrandId());
                totalOrderNum += brandStatistic.getOrderNum() + recommendBrandStatistic.getOrderNum();
                totalUserNum += brandStatistic.getUserNum() + recommendBrandStatistic.getUserNum();
                totalSaleAmount = totalSaleAmount.add(brandStatistic.getSellAmount()).add(recommendBrandStatistic.getSellAmount());
            }


            object.put("count", totalUserNum);
            object.put("groupSum", totalUserNum);
            object.put("orderNum", totalOrderNum);
            object.put("isError", false);
        } catch (Exception e) {
            object.put("isError", true);
            log.info(e.getMessage());
        }
        return object.toJSONString();
    }


    /**
     * 跳转app下载页面
     * wl
     * 16/09/08
     * @param req
     * @return
     */
    @RequestMapping("/download")
    public ModelAndView shopIndexload(HttpServletRequest req) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("common/all");
        return modelAndView;
    }
}
