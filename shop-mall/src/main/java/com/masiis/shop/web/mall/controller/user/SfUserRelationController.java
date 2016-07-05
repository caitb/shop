package com.masiis.shop.web.mall.controller.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.user.SfSpokenAndFansPageViewPo;
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
     * 每页显示条数
     */
    private static final Integer pageSize = 10;

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
        SfSpokenAndFansPageViewPo pageViewPo = sfUserRelationService.dealWithFansPageView(comUser.getId(), null, null, true, 1, pageSize);
        //用户分页使用，三级粉丝总数量
        Integer threeSum = pageViewPo.getFirstCount() + pageViewPo.getSecondCount() + pageViewPo.getThirdCount();
        logger.info("三级粉丝总数量：" + threeSum);
        //获取总页数
        Integer pageNums = threeSum%pageSize == 0 ? threeSum/pageSize : threeSum/pageSize + 1;
        logger.info("总页数：" + pageNums);
        mv.addObject("threeSum",threeSum);
        mv.addObject("pageNums",pageNums);
        mv.addObject("pageViewPo",pageViewPo);
        mv.setViewName("mall/user/sf_fans");
        return mv;
    }
}
