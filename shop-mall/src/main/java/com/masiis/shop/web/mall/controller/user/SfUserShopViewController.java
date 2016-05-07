package com.masiis.shop.web.mall.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShopSku;
import com.masiis.shop.dao.po.SfUserShopView;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.shop.SfShopSkuService;
import com.masiis.shop.web.mall.service.user.SfUserShopViewService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangbingjian on 2016/4/10.
 */
@Controller
@RequestMapping(value = "/shopview")
public class SfUserShopViewController extends BaseController {

    private final Logger logger = Logger.getLogger(SfUserShopViewController.class);

    @Autowired
    private SfUserShopViewService sfUserShopViewService;
    @Autowired
    private SfShopSkuService sfShopSkuService;
    /**
     * 用户查看浏览过的店铺
     * @param request
     * @return
     * @author:wbj
     */
    @RequestMapping(value = "/home.shtml")
    public ModelAndView viewedShop(HttpServletRequest request) throws Exception{

        logger.info("用户查看浏览过的店铺");
        ComUser user = getComUser(request);
        if (user == null){
            throw new BusinessException("用户未登录");
        }
        Long userId = user.getId();
        Integer count = sfUserShopViewService.findCountByUserId(userId);
        //默认设置每页显示20条
        Integer totalPage = count%20 == 0 ? count/20 : count/20 + 1;
        List<SfUserShopView> sfUserShopViews = new ArrayList<>();
        String url = PropertiesUtils.getStringValue("agent_level_product_icon_url");
        if (count > 0){
            logger.info("用户查看浏览过的店铺userId="+userId);
            List<SfUserShopView> shopViews = sfUserShopViewService.findShopViewByUserIdByLimit(userId,1,20);
            List<SfShopSku> sfShopSkus;
            for (SfUserShopView sfUserShopView : shopViews){
                sfShopSkus = sfShopSkuService.findShopSkuByShopId(sfUserShopView.getShopId());
                for (int i = 0; i < sfShopSkus.size(); i++){
                    SfShopSku sfShopSku = sfShopSkus.get(i);
                    sfShopSku.setIcon(url + sfShopSku.getIcon());
                    sfShopSkus.set(i,sfShopSku);
                }
                sfUserShopView.setShopSkus(sfShopSkus);
                sfUserShopViews.add(sfUserShopView);
            }
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("count",count);
        mv.addObject("totalPage",totalPage);
        mv.addObject("currentPage",1);
        mv.addObject("sfUserShopViews",sfUserShopViews);
        mv.setViewName("mall/shop/viewedShop");
        return mv;
    }

    /**
     * ajax获取更多浏览的店铺
     * @param currentPage
     * @param count
     * @param request
     * @return
     * @author:wbj
     */
    @RequestMapping(value = "/showMoreViewed.do")
    @ResponseBody
    public String showMoreViewedShop(@RequestParam(value = "currentPage",required = true) Integer currentPage,
                                     @RequestParam(value = "count",required = true) Integer count,
                                     HttpServletRequest request){
        logger.info("ajax获取更多浏览的店铺");
        ComUser user = getComUser(request);
        if (user == null){
            throw new BusinessException("用户未登录");
        }
        Long userId = user.getId();
        Integer totalCount = sfUserShopViewService.findCountByUserId(userId);
//        //默认设置每页显示20条
//        Integer totalPage = count%20 == 0 ? count/20 : count/20 + 1;
        JSONObject jsonObject = new JSONObject();
        if (totalCount == 0){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","无浏览记录");
            return jsonObject.toJSONString();
        }
        if (count >= totalCount){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","已经加载全部");
            return jsonObject.toJSONString();
        }
        logger.info("用户查看浏览过的店铺userId="+userId);
        List<SfUserShopView> shopViews = sfUserShopViewService.findShopViewByUserIdByLimit(userId, currentPage + 1, 20);
        List<SfShopSku> sfShopSkus;
        StringBuffer str = new StringBuffer();
        String path = request.getContextPath();
        String url = PropertiesUtils.getStringValue("agent_level_product_icon_url");
        for (SfUserShopView sfUserShopView : shopViews){
            sfShopSkus = sfShopSkuService.findShopSkuByShopId(sfUserShopView.getShopId());
            sfUserShopView.setShopSkus(sfShopSkus);
            str.append("<section class=\"sec1\">");
            str.append("<p class=\"photo\">");
            str.append("<img src=\""+path + sfUserShopView.getLogo()+"\" alt=\"\">");
            str.append("</p>");
            str.append("<div class=\"shop\"><h2>"+sfUserShopView.getShopName()+"</h2><h1>");
            for (SfShopSku sku : sfShopSkus){
                str.append("<img src=\""+ url + sku.getIcon() +"\" alt=\"\">");
            }
            str.append(sfUserShopView.getBailFee()+"保证金</h1><h3>"+sfUserShopView.getExplanation()+"</h3>");
            if (sfUserShopView.getDays() == 0){
                str.append("<h2><span>今天浏览过</span><b onclick=\"showShop(\"+sfUserShopView.getShopId()+\",\"+sfUserShopView.getShopUserId()+\")\">点击查看></b></h2>");
            }else {
                str.append("<h2><span>"+sfUserShopView.getDays()+"天前浏览过</span><b onclick=\"showShop("+sfUserShopView.getShopId()+","+sfUserShopView.getShopUserId()+")\">点击查看></b></h2>");
            }
            str.append("</div></section>");
        }
        jsonObject.put("isTrue","true");
        jsonObject.put("message",str);
        logger.info(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }
}