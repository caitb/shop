package com.masiis.shop.web.mall.controller.shop;

import com.masiis.shop.dao.mall.order.SfOrderMapper;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.web.mall.controller.base.BaseController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cai_tb on 16/4/13.
 */
@Controller
@RequestMapping("shop/manage")
public class SfShopManageController extends BaseController {

    private final static Log log = LogFactory.getLog(SfShopManageController.class);

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private SfShopMapper sfShopMapper;
    @Resource
    private SfOrderMapper sfOrderMapper;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("mall/shop/manage/index");
        ComUser comUser = null;
        SfShop sfShop = null;

        try {
            comUser = getComUser(request);
            comUser = comUserMapper.selectByPrimaryKey(comUser.getId());
            sfShop = sfShopMapper.selectByUserId(comUser.getId());
            Integer orderCount = sfOrderMapper.countByShopId(sfShop.getId());

            mav.addObject("comUser", comUser);
            mav.addObject("sfShop", sfShop);
            mav.addObject("orderCount", orderCount);

            return mav;
        } catch (Exception e) {
            log.error("加载店铺首页数据失败![comUser="+comUser+"][sfShop="+sfShop+"]");
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping("/setupShop")
    public ModelAndView setupShop(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("mall/shop/manage/setupShop");
        ComUser comUser = null;
        SfShop sfShop = null;
        try {
            comUser = getComUser(request);
            comUser = comUserMapper.selectByPrimaryKey(comUser.getId());
            sfShop = sfShopMapper.selectByUserId(comUser.getId());

            mav.addObject("comUser", comUser);
            mav.addObject("sfShop", sfShop);

            return mav;
        } catch (Exception e) {
            log.error("店铺设置页面失败![comUser="+comUser+"][sfShop="+sfShop+"]");
            e.printStackTrace();
        }

        return mav;
    }

    @RequestMapping("/updateShop")
    public String updateShop(HttpServletRequest request, HttpServletResponse response, SfShop sfShop){

        try {
            sfShopMapper.updateByPrimaryKey(sfShop);
        } catch (Exception e) {
            log.error("设置店铺失败![sfShop="+sfShop+"]");
        }

        return "redirect:/shop/manage/index";
    }

    @RequestMapping("/setupFreight")
    public ModelAndView setupFreight(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("mall/shop/manage/setupFreight");

        SfShop sfShop = null;
        try {
            ComUser comUser = getComUser(request);
            sfShop = sfShopMapper.selectByUserId(comUser.getId());

            mav.addObject("sfShop", sfShop);

            return mav;
        } catch (Exception e) {
            log.error("去运费设置页面失败![sfShop="+sfShop+"]");
            e.printStackTrace();
        }

        return mav;
    }
}
