package com.masiis.shop.web.platform.controller.system;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.user.CountGroup;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.shop.JSSDKService;
import com.masiis.shop.web.platform.service.system.IndexShowService;
import com.masiis.shop.web.platform.service.user.*;
import com.masiis.shop.web.platform.utils.wx.WxUserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by muchaofeng on 2016/3/2.
 */

@Controller
//@RequestMapping("/index")
public class ShopIndexController extends BaseController {

    private final static Log log = LogFactory.getLog(ShopIndexController.class);

    @Resource
    private IndexShowService indexShowService;
    @Resource
    private CountGroupService countGroupService;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private JSSDKService jssdkService;

    @RequestMapping("/index")
    public ModelAndView shopIndexList(HttpServletRequest req) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        ComUser user = getComUser(req);
        String value = PropertiesUtils.getStringValue("index_banner_url");//获取图片地址常量
        List<PbBanner> pbBanner = indexShowService.findPbBanner();//获取轮播图片
        for (PbBanner banner : pbBanner) {
            banner.setImgUrl(value + banner.getImgUrl());
        }

        int orderNum = 0;
        int numb=0;
        BigDecimal countNum =new BigDecimal("0");
        List<PfUserSku> agentNum = userSkuService.getAgentNumByUserId(user.getId());
        if (agentNum != null) {
            for (PfUserSku pfUserSku : agentNum) {
                CountGroup countGroup = countGroupService.countGroupInfo(user.getId(),pfUserSku.getTreeCode());
                CountGroup countGroup1 = countGroupService.infoOrderNum(user.getId(), pfUserSku.getTreeCode());
                numb+=countGroup.getCount();
                countNum=countGroup.getGroupMoney().add(countNum);
                orderNum+=countGroup1.getOrderNum();
            }
        }
        CountGroup countGroup = new CountGroup();
        countGroup.setCount(numb);
        NumberFormat rmbFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
        countGroup.setGroupSum(rmbFormat.format(countNum));
        countGroup.setOrderNum(orderNum);

        List<PfBorder> pfBorders = bOrderService.findByUserPid(user.getId(), null, null);
        List<PfBorder> pfBorders10 = new ArrayList<>();//代发货
        List<PfBorder> pfBorders6 = new ArrayList<>();//排单中
        for (PfBorder pfBord : pfBorders) {
            if (pfBord.getOrderStatus() == 6){
                pfBorders6.add(pfBord);//排单中
            }else if (pfBord.getOrderStatus() == 7){
                pfBorders10.add(pfBord);//代发货
            }
        }
        modelAndView.addObject("borderNum", pfBorders10.size() + pfBorders6.size());//订单数量
//        modelAndView.addObject("num", orderNum);//团队订单数量
//        modelAndView.addObject("numb", numb);//团队人数
//        modelAndView.addObject("countNum", countNum);// 团队总销售额
        modelAndView.addObject("countGroup", countGroup);// 团队统计
        modelAndView.addObject("pbBanner", pbBanner);//封装图片地址集合
        modelAndView.setViewName("index");
        modelAndView.addObject("user", user);

        String curUrl = req.getRequestURL().toString();
        log.info("===========================B-index[curUrl="+curUrl+"]");
        Map<String, String> shareMap = jssdkService.requestJSSDKData(curUrl);
        modelAndView.addObject("shareMap", shareMap);

        return modelAndView;
    }
}
