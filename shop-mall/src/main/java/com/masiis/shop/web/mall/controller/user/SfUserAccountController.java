package com.masiis.shop.web.mall.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.order.SfOrderItemDistributionService;
import com.masiis.shop.web.mall.service.order.SfOrderService;
import com.masiis.shop.web.mall.service.user.SfUserAccountService;
import com.masiis.shop.web.mall.service.user.SfUserExtractApplyService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import com.masiis.shop.web.mall.service.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangbingjian on 2016/4/8.
 */
@Controller
@RequestMapping(value = "/sfaccount")
public class SfUserAccountController extends BaseController {

    private final Logger log = Logger.getLogger(SfUserAccountController.class);

    @Autowired
    private SfUserAccountService userAccountService;
    @Autowired
    private SfOrderItemDistributionService sfOrderItemDistributionService;
    @Autowired
    private UserService userService;
    @Autowired
    private SfUserRelationService sfUserRelationService;
    @Autowired
    private SfOrderService sfOrderService;
    @Autowired
    private SfUserExtractApplyService sfUserExtractApplyService;
    /**
     * 我的佣金首页
     * @param request
     * @return
     * @author:wbj
     */
    @RequestMapping(value = "/commissionHome.shtml")
    public ModelAndView userCommission(HttpServletRequest request) throws Exception{
        log.info("进入小铺我的佣金首页");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户没有登录");
        }
        ModelAndView mv = new ModelAndView();
        Long userId = comUser.getId();
        //查询分销用户账户表
        SfUserAccount userAccount = userAccountService.findAccountByUserId(userId);
        if (userAccount == null){
            userAccount = new SfUserAccount();
            BigDecimal fee = new BigDecimal(0);
            userAccount.setUserId(Long.valueOf(0));
            userAccount.setCountingFee(fee);
            userAccount.setExtractableFee(fee);
            mv.addObject("userAccount",userAccount);
            mv.addObject("totalCount",0);
            mv.addObject("orderItemDistributions",null);
            mv.setViewName("mall/user/sf_commission");
            return mv;
        }
        SfOrderItemDistribution record = new SfOrderItemDistribution();
        record.setUserId(userId);
        record.setIsCounting(1);
        //根据条件查询 小铺订单商品分润 数量
        int totalCount = sfOrderItemDistributionService.findCountByCondition(record);
        log.info("userId:"+userId+"   小铺订单商品分润数量:"+totalCount);
        List<SfOrderItemDistribution> list = null;
        if (totalCount > 0){
            //根据userId查询小铺订单商品分润信息
            list = sfOrderItemDistributionService.findCommissionRecordByUserIdLimitPage(userId,1,20);
        }
        mv.addObject("currentPage",1);
        mv.addObject("pageSize",20);
        mv.addObject("userAccount",userAccount);
        mv.addObject("totalCount",totalCount);
        mv.addObject("orderItemDistributions",list);
        mv.setViewName("mall/user/sf_commissionHome");
        return mv;
    }

    /**
     * ajax 查询更多用户佣金记录
     * @param currentPage
     * @param count
     * @param userId
     * @param request
     * @return
     * @author:wbj
     */
    @RequestMapping(value = "/moreCommission.do")
    @ResponseBody
    public String queryMoreCommissionRecord(@RequestParam(value = "currentPage",required = true) int currentPage,
                                            @RequestParam(value = "count",required = true) int count,
                                            @RequestParam(value = "userId",required = true) Long userId,
                                            HttpServletRequest request) throws Exception{

        ComUser user = getComUser(request);
        log.info("ajax 查询更多用户佣金记录");
        if (user == null){
            log.info("用户未登录");
            throw new BusinessException("用户未登录");
        }
        if (user.getId().longValue() != userId.longValue()){
            log.info("用户信息错误");
            throw new BusinessException("用户信息错误");
        }
        JSONArray jsonArray = new JSONArray();
        try {
            int pageSize = 20;
            currentPage = currentPage + 1;
            List<SfOrderItemDistribution> list = sfOrderItemDistributionService.findCommissionRecordByUserIdLimitPage(userId,currentPage,pageSize);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            JSONObject jsonObject;
            for(SfOrderItemDistribution itemDistribution : list){
                jsonObject = new JSONObject();
                jsonObject.put("distributionAmount",itemDistribution.getDistributionAmount());
                jsonObject.put("nkName",itemDistribution.getNkName());
                jsonObject.put("skuName",itemDistribution.getSkuName());
                jsonObject.put("skuId",itemDistribution.getSkuId());
                jsonObject.put("orderTime",format.format(itemDistribution.getOrderTime()));
                jsonArray.put(jsonObject);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("ajax 查询更多用户佣金记录 查询异常");
            throw new BusinessException("ajax 查询更多用户佣金记录 查询异常");
        }
        return jsonArray.toString();
    }

    /**
     * 我的奖励首页
     * @param request
     * @return
     */
    @RequestMapping(value = "/rewardHome.shtml")
    public ModelAndView myReward(HttpServletRequest request){
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户没有登录");
        }
        Long userId = comUser.getId();
        ModelAndView mv = new ModelAndView();
        mv.addObject("oneShare",0);
        mv.addObject("twoShare",0);
        mv.addObject("threeShare",0);
        mv.addObject("oneBuy",0);
        mv.addObject("twoBuy",0);
        mv.addObject("threeBuy",0);
        log.info("查询出向下三级分销用户关系列表");
        List<SfUserRelation> sfUserRelations = sfUserRelationService.threeDistributionList(userId);
        //一級分銷
        List<SfUserRelation> oneDistributions = new ArrayList<>();
        //二級分銷
        List<SfUserRelation> twoDistributions = new ArrayList<>();
        //三級分銷
        List<SfUserRelation> threeDistributions = new ArrayList<>();
        log.info("分销用户数量：" + sfUserRelations.size());
        log.info("处理分销begin");
        List<SfOrder> allOrders = new ArrayList<>();
        if (sfUserRelations != null && sfUserRelations.size() > 0){
            List<Long> userPids_two = new ArrayList<>();
            List<Long> userPids_three = new ArrayList<>();
            List<SfUserRelation> userRelations_two = new ArrayList<>();
            List<SfUserRelation> userRelations_three = new ArrayList<>();
            //递归处理分销用户关系，将其分成一级/二级/三级
            for (SfUserRelation oneRelation : sfUserRelations){
                log.info("处理第一级分销");
                if (oneRelation.getUserPid().longValue() == userId.longValue()){
                    oneDistributions.add(oneRelation);
                    userPids_two.add(oneRelation.getUserId());
                }else {
                    userRelations_two.add(oneRelation);
                }
            }
            //处理第二级分销
            if (userRelations_two.size() > 0){
                log.info("处理第二级分销");
                for (Long userPid : userPids_two){
                    for (SfUserRelation twoRelation : userRelations_two){
                        if (userPid.longValue() == twoRelation.getUserPid().longValue()){
                            twoDistributions.add(twoRelation);
                            userPids_three.add(twoRelation.getUserId());
                        }else {
                            userRelations_three.add(twoRelation);
                        }
                    }
                }
                //处理第三级分销
                if (userRelations_three.size() > 0){
                    log.info("处理三级分销");
                    for (Long userPid : userPids_three){
                        for (SfUserRelation threeRelation : userRelations_three){
                            if (userPid.longValue() == threeRelation.getUserId().longValue()){
                                threeDistributions.add(threeRelation);
                            }
                        }
                    }
                }
            }

            log.info("一级分销人数：" + oneDistributions.size());
            log.info("二级分销人数：" + twoDistributions.size());
            log.info("三级分销人数：" + threeDistributions.size());
            //查询在小铺中购买产品的人数
            //处理一级分销
            List<Long> userPids;
            if (oneDistributions.size() > 0){
                mv.addObject("oneShare",oneDistributions.size());
                userPids = new ArrayList<>();
                for (SfUserRelation userRelation : oneDistributions){
                    userPids.add(userRelation.getUserId());
                }
                Map<Long,String> map = new HashMap<>();
                List<SfOrder> oneSfOrders = sfOrderService.findByShopUserIds(userPids);
                for (SfOrder sfOrder : oneSfOrders){
                    map.put(sfOrder.getUserId(),"");
                    allOrders.add(sfOrder);
                }
                mv.addObject("oneBuy",map.size());
            }
            //处理二级级分销
            if (twoDistributions.size() > 0){
                mv.addObject("twoShare",twoDistributions.size());
                userPids = new ArrayList<>();
                for (SfUserRelation userRelation : twoDistributions){
                    userPids.add(userRelation.getUserId());
                }
                Map<Long,String> map = new HashMap<>();
                List<SfOrder> oneSfOrders = sfOrderService.findByShopUserIds(userPids);
                for (SfOrder sfOrder : oneSfOrders){
                    map.put(sfOrder.getUserId(),"");
                    allOrders.add(sfOrder);
                }
                mv.addObject("twoBuy",map.size());
            }
            //处理三级级级分销
            if (threeDistributions.size() > 0){
                userPids = new ArrayList<>();
                mv.addObject("threeShare",threeDistributions.size());
                for (SfUserRelation userRelation : threeDistributions){
                    userPids.add(userRelation.getUserId());
                }
                Map<Long,String> map = new HashMap<>();
                List<SfOrder> oneSfOrders = sfOrderService.findByShopUserIds(userPids);
                for (SfOrder sfOrder : oneSfOrders){
                    map.put(sfOrder.getUserId(),"");
                    allOrders.add(sfOrder);
                }
                mv.addObject("threeBuy",map.size());
            }
        }
        log.info("处理三级分销end");
        log.info("处理可提现的财富、佣金记录begin");
        SfUserAccount userAccount = userAccountService.findAccountByUserId(userId);
        if (userAccount == null){
            userAccount = new SfUserAccount();
            BigDecimal fee = new BigDecimal(0);
            userAccount.setUserId(Long.valueOf(0));
            userAccount.setCountingFee(fee);
            userAccount.setExtractableFee(fee);
            mv.addObject("userAccount",userAccount);
            mv.addObject("totalCount",0);
            mv.addObject("orderItemDistributions",null);
            return mv;
        }
        SfOrderItemDistribution record = new SfOrderItemDistribution();
        record.setUserId(userId);
        record.setIsCounting(1);
        //根据条件查询 小铺订单商品分润 数量
        int totalCount = sfOrderItemDistributionService.findCountByCondition(record);
        log.info("userId:"+userId+"   小铺订单商品分润数量:"+totalCount);
        List<SfOrderItemDistribution> list = null;
        if (totalCount > 0){
            //根据userId查询小铺订单商品分润信息
            list = sfOrderItemDistributionService.findCommissionRecordByUserIdLimitPage(userId,1,10);
        }
        mv.addObject("currentPage",1);
        mv.addObject("pageSize",10);
        mv.addObject("userAccount",userAccount);
        mv.addObject("totalCount",totalCount);
        mv.addObject("orderItemDistributions",list);
        log.info("处理可提现的财富、佣金记录end");

        log.info("处理已付款、未付款订单财富");
        BigDecimal isPayDistribution = new BigDecimal(0);
        BigDecimal isNotPayDistribution = new BigDecimal(0);
        if (allOrders.size() > 0){
            for (SfOrder sfOrder : allOrders){
                log.info("小铺订单id：" + sfOrder.getId());
                if (sfOrder.getPayStatus() == 1 ){
                    if (sfOrder.getOrderStatus() != BOrderStatus.Complete.getCode()){
                        isPayDistribution = isPayDistribution.add(sfOrder.getDistributionAmount());
                        log.info("isPayDistribution = " + isPayDistribution);
                    }else {
                        //处理订单完成七天后的订单
                        //当前时间处理
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        //给定时间处理
                        Calendar setCal = Calendar.getInstance();
                        setCal.setTime(sfOrder.getReceiptTime());
                        setCal.set(Calendar.HOUR_OF_DAY, 0);
                        setCal.set(Calendar.MINUTE, 0);
                        setCal.set(Calendar.SECOND, 0);
                        setCal.set(Calendar.MILLISECOND, 0);
                        long dayDiff =(setCal.getTimeInMillis()-cal.getTimeInMillis())/(1000*60*60*24);
                        if (dayDiff <= 7){
                            isPayDistribution = isPayDistribution.add(sfOrder.getDistributionAmount());
                        }
                    }
                }else {
                    isNotPayDistribution = isNotPayDistribution.add(sfOrder.getDistributionAmount());
                }
            }
        }
        mv.addObject("isPayDistribution",isPayDistribution);
        mv.addObject("isNotPayDistribution",isNotPayDistribution);
        log.info("查询已经提现成功的金额");
        Map<String,Object> map = sfUserExtractApplyService.selectextractFeeByUserId(userId);
        if (map == null){
            mv.addObject("withdraw",0);
        }else {
            mv.addObject("withdraw",(BigDecimal)map.get("extractFee"));
        }
        mv.setViewName("mall/user/sf_reward");
        return mv;
    }
}
