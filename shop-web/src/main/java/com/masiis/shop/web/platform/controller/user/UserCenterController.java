package com.masiis.shop.web.platform.controller.user;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BorderDetail;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.system.ComDictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping("/userCenter.html")
    public String toUserCenter(HttpServletRequest request) {
        ComUser comUser = (ComUser)request.getSession().getAttribute("comUser");
        try {
//            List<PfBorder> pfBorders = bOrderService.findByUserId(comUser.getId());
//            if (pfBorders !=null && pfBorders.size()!=0){
//
//            }else{
//
//            }
        }catch (Exception e){

        }
        return "";
    }
    /**
     * 进货订单
     * @author muchaofeng
     * @date 2016/3/16 11:37
     */
    @RequestMapping("/stockBorder")
    public ModelAndView stockBorder(HttpServletRequest request,Integer orderStatus,Integer shipStatus) {
        ComUser comUser =(ComUser)request.getSession().getAttribute("comUser");
//        List<PfBorder> pfBorders = bOrderService.findByUserId(comUser.getId());
        List<PfBorder> pfBorders =bOrderService.findByUserId(comUser.getId(),orderStatus,shipStatus);
//        if(orderStatus==0){
//            pfBorders = bOrderService.findByUserId(comUser.getId(),orderStatus,shipStatus);//待付款
//        }
//        if(orderStatus==1 && shipStatus==0){
//            pfBorders = bOrderService.findByUserId(comUser.getId(),orderStatus,shipStatus);//代发货
//        }
//        if(orderStatus==1 && shipStatus==5){
//            pfBorders = bOrderService.findByUserId(comUser.getId(),orderStatus,shipStatus);//待收货
//        }
//        if(orderStatus==3){
//            pfBorders = bOrderService.findByUserId(comUser.getId(),orderStatus,shipStatus);//已完成
//        }
        String skuValue = PropertiesUtils.getStringValue("index_product_220_220_url");
        if(pfBorders!= null && pfBorders.size()!=0){
            for (PfBorder pfBorder: pfBorders) {
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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pfBorders",pfBorders);
        modelAndView.setViewName("platform/user/jinhuodingdan");
        return modelAndView;
    }
    /**
     * 订单详情
     * @author muchaofeng
     * @date 2016/3/16 15:00
     */
    @RequestMapping("/borderDetils")
    public ModelAndView borderDetils(HttpServletRequest request,Long id){
        BorderDetail borderDetail = new BorderDetail();
        List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(id);
        //快递公司信息
        List<PfBorderFreight> pfBorderFreights = bOrderService.findByPfBorderFreightOrderId(id);
        //收货人
        PfBorderConsignee pfBorderConsignee = bOrderService.findpfBorderConsignee(id);
        borderDetail.setPfBorderItems(pfBorderItems);
        borderDetail.setPfBorderFreights(pfBorderFreights);
        borderDetail.setPfBorderConsignee(pfBorderConsignee);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("borderDetail",borderDetail);
        modelAndView.setViewName("");
        return modelAndView;
    }
}
