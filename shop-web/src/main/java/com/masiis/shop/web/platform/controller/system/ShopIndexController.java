package com.masiis.shop.web.platform.controller.system;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.system.IndexShowService;
import com.masiis.shop.web.platform.service.system.SpuService;
import com.masiis.shop.web.platform.service.user.ComUserAccountService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muchaofeng on 2016/3/2.
 */

@Controller
//@RequestMapping("/index")
public class ShopIndexController extends BaseController {

    @Resource
    private IndexShowService indexShowService;
    @Resource
    private ComUserAccountService comUserAccountService;
    @Resource
    private UserService userService;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private BOrderService bOrderService;

    @RequestMapping("/index")
    public ModelAndView shopIndexList(HttpServletRequest req)throws Exception{
        ComUser user = getComUser(req);
//        ComUser user = userService.getUserById(1l);
        if (user == null) {
            user = userService.getUserById(1l);
            req.getSession().setAttribute("comUser", user);
        }
        ModelAndView modelAndView = new ModelAndView();
        List<String> urls = new ArrayList<>();
        String value = PropertiesUtils.getStringValue("index_banner_url");//获取图片地址常量
        List<PbBanner> pbBanner = indexShowService.findPbBanner();//获取轮播图片
        for (PbBanner banner:pbBanner) {
            String url = value + banner.getImgUrl();//图片地址
            urls.add(url);
        }

        ComUserAccount comUserAccount = comUserAccountService.findAccountByUserid(user.getId());
        Long num =0l;
//        List<PfUserSku> agentNum = userSkuService.getAgentNumByUserId(user.getId());
//        if(agentNum!= null){
//            for (PfUserSku pfUserSku :agentNum) {
//                num= num + pfUserSku.getAgentNum();
//            }
//        }
        List<PfBorder> pfBorders = bOrderService.findByUserPid(user.getId(), null, null);
        List<PfBorder> pfBorders10 = new ArrayList<>();//代发货
        List<PfBorder> pfBorders6 = new ArrayList<>();//排单中
        for (PfBorder pfBord : pfBorders) {
            if (pfBord.getOrderStatus() == 7 ) {
                pfBorders10.add(pfBord);//代发货
            } else if (pfBord.getOrderStatus() == 6) {
                pfBorders6.add(pfBord);//排单中
            }
        }
        Integer borderNum = pfBorders10.size()+pfBorders6.size();
        modelAndView.addObject("borderNum",borderNum);//订单数量
        modelAndView.addObject("num",num);//订单数量
        modelAndView.addObject("comUserAccount",comUserAccount);//封装用户统计信息
        modelAndView.addObject("urls",urls);//封装图片地址集合
        modelAndView.setViewName("index");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
