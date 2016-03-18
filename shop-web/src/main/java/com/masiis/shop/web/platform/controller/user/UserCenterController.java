package com.masiis.shop.web.platform.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BorderDetail;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.system.ComDictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */

@Controller
@RequestMapping("/userCenterController")
public class UserCenterController {

    @Resource
    private BOrderService bOrderService;
    @Resource
    private ComDictionaryService comDictionaryService;
    @Resource
    private SkuService skuService;

    @RequestMapping("/closeDeal.do")
    @ResponseBody
    public String toUserCenter(HttpServletRequest request,
                             @RequestParam(required = true)Integer orderStatus,
                             @RequestParam(required = true)Long orderId) {
        JSONObject json = new JSONObject();
        PfBorder pfBorder = bOrderService.getPfBorderById(orderId);
        pfBorder.setOrderStatus(orderStatus);
        try {
            bOrderService.updateBOrder(pfBorder);
            json.put("mesg","交易成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();
    }
    /**
     * 进货订单
     * @author muchaofeng
     * @date 2016/3/16 11:37
     */
    @RequestMapping("/stockBorder")
    public ModelAndView stockBorder(HttpServletRequest request,Integer orderStatus,Integer shipStatus) {
        ComUser comUser =(ComUser)request.getSession().getAttribute("comUser");
        List<PfBorder> pfBorders =bOrderService.findByUserId(comUser.getId(),orderStatus,shipStatus);
        List<PfBorder> pfBorders0 = bOrderService.findByUserId(comUser.getId(),0,shipStatus);//待付款
        List<PfBorder> pfBorders10 = bOrderService.findByUserId(comUser.getId(),1,0);//代发货
        List<PfBorder> pfBorders15 = bOrderService.findByUserId(comUser.getId(),1,5);//待收货
        List<PfBorder> pfBorders3= bOrderService.findByUserId(comUser.getId(),3,shipStatus);//已完成
        List<List<PfBorder>> pfBorderss=new ArrayList<List<PfBorder>>();
        pfBorderss.add(0,pfBorders);
        pfBorderss.add(1,pfBorders0);
        pfBorderss.add(2,pfBorders15);
        pfBorderss.add(3,pfBorders10);
        pfBorderss.add(4,pfBorders3);
        String skuValue = PropertiesUtils.getStringValue("index_product_220_220_url");
        for (List<PfBorder> pfsBorder: pfBorderss) {
            if(pfsBorder!= null && pfsBorder.size()!=0){
                for (PfBorder pfBorder: pfsBorder) {
                    List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(pfBorder.getId());
                    for (PfBorderItem pfBorderItem: pfBorderItems) {
                        ComSkuImage comSkuImage = skuService.findComSkuImage(pfBorderItem.getSkuId());
                        pfBorderItem.setSkuUrl(skuValue+comSkuImage.getImgUrl());
                        pfBorder.setTotalQuantity(pfBorder.getTotalQuantity()+pfBorderItem.getQuantity());//订单商品总量
                    }
                    ComDictionary comDictionary = comDictionaryService.findComDictionary(pfBorder.getOrderStatus());
                    pfBorder.setOrderSkuStatus(comDictionary.getValue());
                    pfBorder.setPfBorderItems(pfBorderItems);
                }
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pfBorders",pfBorderss);
        modelAndView.setViewName("platform/user/jinhuodingdan");
        return modelAndView;
    }
    /**
     * 订单详情
     * @author muchaofeng
     * @date 2016/3/16 15:00
     */
    @RequestMapping("/borderDetils.html")
    public ModelAndView borderDetils(HttpServletRequest request,Long id){
        BorderDetail borderDetail = new BorderDetail();
        String skuValue = PropertiesUtils.getStringValue("index_product_220_220_url");
        PfBorder pfBorder = bOrderService.getPfBorderById(id);
        List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(id);
        for (PfBorderItem pfBorderItem: pfBorderItems) {
            ComSkuImage comSkuImage = skuService.findComSkuImage(pfBorderItem.getSkuId());
            pfBorderItem.setSkuUrl(skuValue+comSkuImage.getImgUrl());
            pfBorder.setTotalQuantity(pfBorder.getTotalQuantity()+pfBorderItem.getQuantity());//订单商品总量
        }
        //快递公司信息
        List<PfBorderFreight> pfBorderFreights = bOrderService.findByPfBorderFreightOrderId(id);
        //收货人
        PfBorderConsignee pfBorderConsignee = bOrderService.findpfBorderConsignee(id);
        borderDetail.setPfBorder(pfBorder);
        borderDetail.setPfBorderItems(pfBorderItems);
        borderDetail.setPfBorderFreights(pfBorderFreights);
        borderDetail.setPfBorderConsignee(pfBorderConsignee);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("borderDetail",borderDetail);
        modelAndView.setViewName("platform/user/jinhuoxiangqing");
        return modelAndView;
    }
}
