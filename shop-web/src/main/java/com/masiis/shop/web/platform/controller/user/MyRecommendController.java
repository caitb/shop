package com.masiis.shop.web.platform.controller.user;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.user.UserRecommend;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserStatistics;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.PfBorderRecommenRewardService;
import com.masiis.shop.web.platform.service.user.PfUserRecommendRelationService;
import com.masiis.shop.web.platform.service.user.PfUserStatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 我的推荐
 * @author muchaofeng
 * @date 2016/6/15 11:05
 */
@Controller
@RequestMapping("/myRecommend")
public class MyRecommendController extends BaseController{
    @Resource
    private PfUserStatisticsService pfUserStatisticsService;
    @Resource
    private PfUserRecommendRelationService pfUserRecommendRelationService;
    @Resource
    private PfBorderRecommenRewardService pfBorderRecommenRewardService;

    private final Log log = LogFactory.getLog(MyRecommendController.class);

    /**
     * 我的推荐
     * @author muchaofeng
     * @date 2016/6/15 17:44
     */
    @RequestMapping("/feeList")
    public ModelAndView feeList(HttpServletRequest request){
        try{
            ModelAndView modelAndView = new ModelAndView();
            ComUser comUser = getComUser(request);
            PfUserStatistics pfUserStatistics = pfUserStatisticsService.selectFee(comUser.getId());
            int numByUserId = pfUserRecommendRelationService.findNumByUserId(comUser.getId());//推荐我的人数
            int numByUserPid = pfUserRecommendRelationService.findNumByUserPid(comUser.getId());//我推荐的人数
            Integer borders = pfBorderRecommenRewardService.findBorders(comUser.getId());//获得奖励订单数
            Integer pBorders = pfBorderRecommenRewardService.findPBorders(comUser.getId());//发出奖励订单数
            modelAndView.addObject("pfUserStatistics",pfUserStatistics);
            modelAndView.addObject("numByUserId",numByUserId);
            modelAndView.addObject("numByUserPid",numByUserPid);
            modelAndView.addObject("borders",borders);
            modelAndView.addObject("pBorders",pBorders);
            modelAndView.setViewName("platform/user/wodetuijian");
            return modelAndView;
        }catch (Exception e){
            log.error("获取代理产品列表失败!",e);
        }
        return null;
    }

    /**
     * 推荐给我的详情列表
     * @author muchaofeng
     * @date 2016/6/15 17:44
     */
    @RequestMapping("/RecommendGiveList")
    public ModelAndView RecommendGiveList(HttpServletRequest request){
        try{
            ModelAndView modelAndView = new ModelAndView();
            ComUser comUser = getComUser(request);

            List<UserRecommend> sumByUserPid = pfUserRecommendRelationService.findSumByUserPid(comUser.getId());//推荐给我的
            modelAndView.addObject("sumByUserPid",sumByUserPid);
            modelAndView.setViewName("platform/user/wotuijianderen");
            return modelAndView;
        }catch (Exception e){
            log.error("获取代理产品列表失败!",e);
        }
        return null;
    }
}
