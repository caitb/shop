package com.masiis.shop.api.controller.order;

import com.masiis.shop.api.bean.base.BaseReq;
import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.bean.order.OManagementIndexReq;
import com.masiis.shop.api.bean.order.OManagementIndexRes;
import com.masiis.shop.api.bean.order.OrderListPagingReq;
import com.masiis.shop.api.bean.order.OrderListPagingRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysConstants;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.order.BOrderService;
import com.masiis.shop.api.service.product.SkuService;
import com.masiis.shop.api.service.user.ComShipManService;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Date 2016/5/5
 * @Auther lzh
 */
@Controller
@RequestMapping("/om")
public class BOrderManagementController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private BOrderService bOrderService;
    @Resource
    private SkuService skuService;
    @Resource
    private ComShipManService comShipManService;

    @RequestMapping("/index")
    @ResponseBody
    @SignValid(paramType = OManagementIndexReq.class)
    public OManagementIndexRes bOrderManagement(HttpServletRequest request, OManagementIndexReq req, ComUser user){
        OManagementIndexRes res = new OManagementIndexRes();

        List<PfBorder> pfBorders = bOrderService.findByUserId(user.getId(), null, null);
        List<PfBorder> pfBorderps = bOrderService.findByUserPid(user.getId(), null, null);
        List<PfBorder> pfBorders0 = new ArrayList<>();
        List<PfBorder> pfBorders7 = new ArrayList<>();//代发货
        List<PfBorder> pfBorders8 = new ArrayList<>();//待收货
        List<PfBorder> pfBorders6 = new ArrayList<>();//排单中
        for (PfBorder pfBord : pfBorders) {
            if (pfBord.getOrderStatus() == 0) {
                pfBorders0.add(pfBord);//待付款
            } else if (pfBord.getOrderStatus() == 7 ) {
                pfBorders7.add(pfBord);//代发货
            } else if (pfBord.getOrderStatus() == 8 ) {
                pfBorders8.add(pfBord);//待收货
            }  else if (pfBord.getOrderStatus() == 6) {
                pfBorders6.add(pfBord);//排单中
            }
        }
        List<PfBorder> pfBorderp0 = new ArrayList<>();
        List<PfBorder> pfBorderp7 = new ArrayList<>();//代发货
        List<PfBorder> pfBorderp8 = new ArrayList<>();//待收货
        List<PfBorder> pfBorderp6 = new ArrayList<>();//排单中
        for (PfBorder pfBord : pfBorderps) {
            if (pfBord.getOrderStatus() == 0) {
                pfBorderp0.add(pfBord);//待付款
            } else if (pfBord.getOrderStatus() == 8 ) {
                pfBorderp8.add(pfBord);//待收货
            }  else if (pfBord.getOrderStatus() == 6) {
                pfBorderp6.add(pfBord);//排单中
            }else if (pfBord.getOrderStatus() == 7 ) {
                pfBorderp7.add(pfBord);//代发货
            }
        }
        res.setInWaitShipNum(pfBorders7.size());
        res.setInMPSNum(pfBorders6.size());
        res.setInPayingNum(pfBorders0.size());
        res.setInReceiveNum(pfBorders8.size());
        res.setOutShipNum(pfBorderp7.size());
        res.setOutMPSNum(pfBorderp6.size());
        res.setOutWaitPayNum(pfBorderp0.size());
        res.setOutWaitReceiveNum(pfBorderp8.size());
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        return res;
    }

    /**
     * 分页查询出货订单
     *
     * @param request
     * @param req
     * @param user
     * @return
     */
    @RequestMapping("/qSaleOrder")
    @ResponseBody
    @SignValid(paramType = OrderListPagingReq.class)
    public OrderListPagingRes querySaleOrder(HttpServletRequest request, OrderListPagingReq req, ComUser user){
        OrderListPagingRes res = new OrderListPagingRes();

        Integer sendType = user.getSendType();
        Integer orderStatus = req.getOstatus();
        List<PfBorder> pfBorders = bOrderService.findByUserPid(user.getId(), orderStatus, sendType);
        String index = null;
        Integer borderNum = 0;
        if(orderStatus == null && sendType == null){
            index = "0";//全部
        } else if (orderStatus == 0) {
            index = "1";//待付款
        } else if (orderStatus == 7) {
            index = "2";//代发货
            borderNum = pfBorders.size();
        } else if (orderStatus == 8) {
            index = "3";//待收货
        } else if (orderStatus == 3) {
            index = "4";//已完成
        } else if(orderStatus == 6) {
            index = "5";//排单中
            Iterator<PfBorder> chk_itw = pfBorders.iterator();
            while (chk_itw.hasNext()) {
                PfBorder pfBorder = chk_itw.next();
                if (pfBorder.getSendType() == 2 && pfBorder.getOrderStatus() == 6 ) {//排单订单
                    chk_itw.remove();
                }
            }
        }
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        if (pfBorders != null && pfBorders.size() != 0) {
            for (PfBorder pfBorder : pfBorders) {
                List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(pfBorder.getId());
                PfBorderConsignee pfBorderConsignee = bOrderService.findpfBorderConsignee(pfBorder.getId());
                for (PfBorderItem pfBorderItem : pfBorderItems) {
                    pfBorderItem.setSkuUrl(skuValue + skuService.findComSkuImage(pfBorderItem.getSkuId()).getImgUrl());
                    pfBorder.setTotalQuantity(pfBorder.getTotalQuantity() + pfBorderItem.getQuantity());//订单商品总量
                }
                pfBorder.setPfBorderItems(pfBorderItems);
                pfBorder.setPfBorderConsignee(pfBorderConsignee);//收货人信息
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pfBorders", pfBorders);
        modelAndView.addObject("borderNum10", borderNum);
        if(request.getSession().getAttribute("comShipMans")==null || request.getSession().getAttribute("comShipMans")==""){
            List<ComShipMan> comShipMans = comShipManService.list();
            request.getSession().setAttribute("comShipMans", comShipMans);
        }
        modelAndView.addObject("index",index);
        modelAndView.setViewName("platform/order/chuhuodingdan");

        return res;
    }

}
