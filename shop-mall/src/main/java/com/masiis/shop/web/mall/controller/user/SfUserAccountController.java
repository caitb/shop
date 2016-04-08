package com.masiis.shop.web.mall.controller.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.user.SfUserAccountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wangbingjian on 2016/4/8.
 */
@Controller
@RequestMapping(value = "/sfaccount")
public class SfUserAccountController extends BaseController {

    private final Logger log = Logger.getLogger(SfUserAccountController.class);

    @Autowired
    private SfUserAccountService userAccountService;
    /**
     * 我的佣金首页
     * @param request
     * @return
     */
    @RequestMapping(value = "commissionHome")
    public ModelAndView userCommission(HttpServletRequest request){
        log.info("进入小铺我的佣金首页");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户没有登录");
        }
        ModelAndView mv = new ModelAndView();
        Long userId = comUser.getId();
        //查询分销用户账户表
        userAccountService.findAccountByUserId(userId);

        return null;
    }
}
