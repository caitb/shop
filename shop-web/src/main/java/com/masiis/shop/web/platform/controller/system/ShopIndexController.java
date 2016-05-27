package com.masiis.shop.web.platform.controller.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.shop.JSSDKService;
import com.masiis.shop.web.platform.service.system.IndexShowService;
import com.masiis.shop.web.platform.service.user.ComUserAccountService;
import com.masiis.shop.web.platform.service.user.MyTeamService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import com.masiis.shop.web.platform.utils.wx.WxUserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private MyTeamService myTeamService;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private JSSDKService jssdkService;

    @RequestMapping("/index")
    public ModelAndView shopIndexList(HttpServletRequest req) throws Exception {
        ComUser user = getComUser(req);
//        ComUser user = userService.getUserById(1l);
        if (user == null) {
            throw new  BusinessException("user不能为空");
//            user = userService.getUserById(217l);
//            req.getSession().setAttribute("comUser", user);
        }
        ModelAndView modelAndView = new ModelAndView();
        List<String> urls = new ArrayList<>();
        String value = PropertiesUtils.getStringValue("index_banner_url");//获取图片地址常量
        List<PbBanner> pbBanner = indexShowService.findPbBanner();//获取轮播图片
        for (PbBanner banner : pbBanner) {
//            String url = value + banner.getImgUrl();//图片地址
            banner.setImgUrl(value + banner.getImgUrl());
//            urls.add(url);
        }

        ComUserAccount comUserAccount = comUserAccountService.findAccountByUserid(user.getId());
        if (comUserAccount == null) {
            throw new BusinessException("comUserAccount 统计为空");
        }
        int num = 0;
        List<PfUserSku> agentNum = userSkuService.getAgentNumByUserId(user.getId());
        if (agentNum != null) {
            for (PfUserSku pfUserSku : agentNum) {
//                if (pfUserSku != null && pfUserSku.getAgentNum() != null) {
//                    num = num + pfUserSku.getAgentNum();
//                }
                Map<String, String> stringStringMap = myTeamService.countChild(pfUserSku.getId());
                int a=StringUtils.isEmpty(stringStringMap.get("childIds").toString())?0:stringStringMap.get("childIds").split(",").length;
//                int a= Integer.parseInt(stringStringMap.get("childIds"));
                num += a;
            }
        }
        List<PfBorder> pfBorders = bOrderService.findByUserPid(user.getId(), null, null);
        List<PfBorder> pfBorders10 = new ArrayList<>();//代发货
        List<PfBorder> pfBorders6 = new ArrayList<>();//排单中
        for (PfBorder pfBord : pfBorders) {
            if (pfBord.getOrderStatus() == 7) {
                pfBorders10.add(pfBord);//代发货
            } else if (pfBord.getOrderStatus() == 6) {
                pfBorders6.add(pfBord);//排单中
            }
        }
//        Integer borderNum = pfBorders10.size() + pfBorders6.size();
//        Boolean forcusPF = WxUserUtils.getInstance().isUserForcusPF(user);
        modelAndView.addObject("borderNum", pfBorders10.size() + pfBorders6.size());//订单数量
//        modelAndView.addObject("forcusPF",forcusPF);
        modelAndView.addObject("num", num);//订单数量
        modelAndView.addObject("comUserAccount", comUserAccount);//封装用户统计信息
        modelAndView.addObject("pbBanner", pbBanner);//封装图片地址集合
        modelAndView.setViewName("index");
        modelAndView.addObject("user", user);


        String curUrl = req.getRequestURL().toString();
        Map<String, String> shareMap = jssdkService.requestJSSDKData(curUrl);
        modelAndView.addObject("shareMap", shareMap);

        return modelAndView;
    }
}
