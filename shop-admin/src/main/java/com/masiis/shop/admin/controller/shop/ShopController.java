package com.masiis.shop.admin.controller.shop;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.beans.shop.Shop;
import com.masiis.shop.admin.service.shop.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cai_tb on 16/4/18.
 */
@Controller
@RequestMapping("/shop")
public class ShopController {

    private final static Log log = LogFactory.getLog(ShopController.class);

    @Resource
    private ShopService shopService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "shop/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortOrder){

        Map<String, Object> conditionMap = new HashMap<>();
        try {
            Map<String, Object> pageMap = shopService.listByCondition(pageNumber, pageSize, conditionMap);

            return pageMap;
        } catch (Exception e) {
            log.error("获取店铺列表失败!");
            e.printStackTrace();
        }

        return "error";
    }

    @RequestMapping("/detail.shtml")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, Long shopId){

        ModelAndView mav = new ModelAndView("shop/detail");
        try {
            Shop shop = shopService.shopDetail(shopId);
            mav.addObject("shop", shop);

            return mav;
        } catch (Exception e) {
            log.error("获取店铺详细信息失败![shopId="+shopId+"]");
            e.printStackTrace();
        }

        return null;
    }
}
