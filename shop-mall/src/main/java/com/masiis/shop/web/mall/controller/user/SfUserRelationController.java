package com.masiis.shop.web.mall.controller.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 小铺分销关系Controller
 * Created by wangbingjian on 2016/7/5.
 */
@Controller
@RequestMapping(value = "/distribution")
public class SfUserRelationController extends BaseController {
    private static final Logger logger = Logger.getLogger(SfUserRelationController.class);

    @Autowired
    private SfUserRelationService sfUserRelationService;

    /**
     * 粉丝查询首页
     * @param request   request
     * @return  mv
     */
    @RequestMapping(value = "/fansHome.shtml")
    public ModelAndView fansHome(HttpServletRequest request) throws Exception{
        logger.info("粉丝查询首页");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户未登陆");
        }
        ModelAndView mv = new ModelAndView();

        return mv;
    }
}
