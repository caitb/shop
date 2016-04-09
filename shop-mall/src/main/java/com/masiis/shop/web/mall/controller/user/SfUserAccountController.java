package com.masiis.shop.web.mall.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.order.SfOrderItemDistributionService;
import com.masiis.shop.web.mall.service.user.SfUserAccountService;
import com.masiis.shop.web.mall.service.user.UserService;
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
import java.util.List;

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
    /**
     * 我的佣金首页
     * @param request
     * @auto:wbj
     * @return
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
        mv.setViewName("mall/user/sf_commission");
        return mv;
    }

    /**
     * ajax 查询更多用户佣金记录
     * @param currentPage
     * @param count
     * @param userId
     * @param request
     * @auto:wbj
     * @return
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
        if (user.getId() != userId){
            log.info("用户信息错误");
            throw new BusinessException("用户信息错误");
        }
        JSONArray jsonArray = new JSONArray();
        try {
            int pageSize = count/currentPage;
            currentPage = currentPage + 1;
            List<SfOrderItemDistribution> list = sfOrderItemDistributionService.findCommissionRecordByUserIdLimitPage(userId,currentPage,pageSize);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:");
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
     * 用户提现申请
     * @param request
     * @return
     */
    @RequestMapping(value = "/withdrawRequest.shtml")
    public ModelAndView withdrawRequest(HttpServletRequest request) throws Exception{
        log.info("进入用户提现申请");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户没有登录");
        }
        ModelAndView mv = new ModelAndView();
        Long userId = comUser.getId();
        log.info("userId="+userId);
        SfUserAccount userAccount = userAccountService.findAccountByUserId(userId);
        ComWxUser comWxUser = userService.findComWxUserByUserId(userId);
        mv.addObject("userAccount",userAccount);
        mv.addObject("comWxUser",comWxUser);
        mv.setViewName("mall/user/sf_withdrawRequest");
        return mv;
    }
}
