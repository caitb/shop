package com.masiis.shop.web.mall.controller.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfOrderItemDistribution;
import com.masiis.shop.dao.po.SfUserAccount;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.order.SfOrderItemDistributionService;
import com.masiis.shop.web.mall.service.user.SfUserAccountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
    /**
     * 我的佣金首页
     * @param request
     * @return
     */
    @RequestMapping(value = "commissionHome")
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
            mv.setViewName("");
            return mv;
        }
        SfOrderItemDistribution record = new SfOrderItemDistribution();
        record.setUserId(userId);
        //根据条件查询 小铺订单商品分润 数量
        int count = sfOrderItemDistributionService.findCountByCondition(record);
        //根据userId查询小铺订单商品分润信息
        List<SfOrderItemDistribution> list = sfOrderItemDistributionService.findCommissionRecordByUserId(userId);
        mv.addObject("userAccount",userAccount);
        mv.addObject("count",count);
        mv.addObject("orderItemDistributions",list);
        mv.setViewName("");
        return mv;
    }
}
