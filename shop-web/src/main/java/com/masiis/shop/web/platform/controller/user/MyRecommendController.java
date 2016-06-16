package com.masiis.shop.web.platform.controller.user;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.user.UserRecommend;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.order.PfBorderRecommenRewardService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

/**
 * 我的推荐
 * @author muchaofeng
 * @date 2016/6/15 11:05
 */
@Controller
@RequestMapping("/myRecommend")
public class MyRecommendController extends BaseController{

    private final Log log = LogFactory.getLog(MyRecommendController.class);

    @Resource
    private PfUserStatisticsService pfUserStatisticsService;
    @Resource
    private PfUserRecommendRelationService pfUserRecommendRelationService;
    @Resource
    private PfBorderRecommenRewardService pfBorderRecommenRewardService;
    @Resource
    private SkuService skuService;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private UserService userService;
    @Resource
    private UserCertificateService userCertificateService;
    @Resource
    private ComAgentLevelService comAgentLevelService;

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
            int numByUserId = pfUserRecommendRelationService.findNumByUserId(comUser.getId());//推荐给我的人数
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
    @RequestMapping("/recommendGiveList")
    public ModelAndView recommendGiveList(HttpServletRequest request){
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


    /**
     * 我推荐的详情列表
     * @author muchaofeng
     * @date 2016/6/16 17:27
     */
    @RequestMapping("/myRecommendList")
    public ModelAndView myRecommendList(HttpServletRequest request){
        try{
            ModelAndView modelAndView = new ModelAndView();
            ComUser comUser = getComUser(request);

            List<UserRecommend> sumByUserPid = pfUserRecommendRelationService.findSumByUserPid(comUser.getId());//我推荐的详情列表
            modelAndView.addObject("sumByUserPid",sumByUserPid);
            modelAndView.setViewName("platform/user/wotuijianderen");
            return modelAndView;
        }catch (Exception e){
            log.error("获取代理产品列表失败!",e);
        }
        return null;
    }

    /**
     * 我推荐的人详情
     * @author muchaofeng
     * @date 2016/6/16 17:56
     */

    @RequestMapping("/myRecommend")
    public ModelAndView myRecommend(Long userId,Integer skuId){
        try{
            ModelAndView modelAndView = new ModelAndView();

            PfUserCertificate certificate = userCertificateService.getCertificateByuserskuId(userId,skuId);
            String ctValue = PropertiesUtils.getStringValue("index_user_certificate_url");
            certificate.setImgUrl(ctValue + certificate.getImgUrl());
            modelAndView.addObject("certificate",certificate);

            ComSku skuName = skuService.getSkuName(skuId);
            ComUser user = userService.getUserById(userId);
            ComAgentLevel agentLevel = comAgentLevelService.selectByPrimaryKey(certificate.getAgentLevelId());
            modelAndView.addObject("skuName",skuName.getName());
            modelAndView.addObject("userName",user.getRealName());
            modelAndView.addObject("agentLevelName",agentLevel.getName());
            modelAndView.setViewName("platform/user/wotuijianderenxinxi");
            return modelAndView;
        }catch (Exception e){
            log.error("获取代理产品列表失败!",e);
        }
        return null;
    }
    
    /**
     * 获得奖励订单
     * @author muchaofeng
     * @date 2016/6/16 10:47
     */
    @RequestMapping("/getRewardBorder")
    public ModelAndView getRewardBorder(HttpServletRequest request) throws Exception {
        ComUser comUser = getComUser(request);
        try{
            List<PfBorder> pfBorders = bOrderService.getRecommendPfBorder(comUser.getId());
            String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
            PfUserCertificate certificateByuserskuId =null;
            PfUserCertificate certificateByuserskuId1 = null;
            if (pfBorders != null && pfBorders.size() != 0) {
                for (PfBorder pfBorder : pfBorders) {
                    List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(pfBorder.getId());
                    for (PfBorderItem pfBorderItem : pfBorderItems) {
                        pfBorderItem.setSkuUrl(skuValue + skuService.findComSkuImage(pfBorderItem.getSkuId()).getImgUrl());
                        pfBorder.setTotalQuantity(pfBorder.getTotalQuantity() + pfBorderItem.getQuantity());//订单商品总量
                        certificateByuserskuId = userCertificateService.getCertificateByuserskuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
                        certificateByuserskuId1 = userCertificateService.getCertificateByuserskuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
                    }
                    ComUser user = userService.getUserById(pfBorder.getUserId());
                    ComUser userName = userService.getUserById(pfBorder.getUserPid());
                    user.setWxId(certificateByuserskuId.getWxId());
                    userName.setWxId(certificateByuserskuId1.getWxId());
                    pfBorder.setUserName(user);
                    pfBorder.setUserPname(userName);
                    pfBorder.setOrderMoney(pfBorder.getOrderAmount().toString());
                    pfBorder.setPfBorderItems(pfBorderItems);
                }
            }
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("pfBorders", pfBorders);
            modelAndView.setViewName("platform/user/huoqujianglidingdan");
            return modelAndView;
        }catch (Exception e){
            log.error("获取获得奖励订单列表失败!",e);
        }
        return null;
    }

    
    /**
     * 发出奖励订单
     * @author muchaofeng
     * @date 2016/6/16 16:50
     */
    @RequestMapping("/sendRewardBorder")
    public ModelAndView sendRewardBorder(HttpServletRequest request) throws Exception {
        ComUser comUser = getComUser(request);
        try{
            List<PfBorder> pfBorders = bOrderService.SendRecommendPfBorder(comUser.getId());
            String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
            PfUserCertificate certificateByuserskuId =null;
            PfUserCertificate certificateByuserskuId1 = null;
            if (pfBorders != null && pfBorders.size() != 0) {
                for (PfBorder pfBorder : pfBorders) {
                    List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(pfBorder.getId());
                    for (PfBorderItem pfBorderItem : pfBorderItems) {
                        pfBorderItem.setSkuUrl(skuValue + skuService.findComSkuImage(pfBorderItem.getSkuId()).getImgUrl());
                        pfBorder.setTotalQuantity(pfBorder.getTotalQuantity() + pfBorderItem.getQuantity());//订单商品总量
                        certificateByuserskuId1 = userCertificateService.getCertificateByuserskuId(pfBorder.getUserPid(), pfBorderItem.getSkuId());
                        certificateByuserskuId = userCertificateService.getCertificateByuserskuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
                    }
                    ComUser user = userService.getUserById(pfBorder.getUserId());
                    ComUser userName = userService.getUserById(pfBorder.getUserPid());
                    user.setWxId(certificateByuserskuId.getWxId());
                    userName.setWxId(certificateByuserskuId1.getWxId());
                    pfBorder.setUserName(user);
                    pfBorder.setUserPname(userName);
                    pfBorder.setOrderMoney(pfBorder.getOrderAmount().toString());
                    pfBorder.setPfBorderItems(pfBorderItems);
                }
            }
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("pfBorders", pfBorders);
            modelAndView.setViewName("platform/user/fachujianglidingdan");
            return modelAndView;
        }catch (Exception e){
            log.error("获取获得奖励订单列表失败!",e);
        }
        return null;
    }
}
