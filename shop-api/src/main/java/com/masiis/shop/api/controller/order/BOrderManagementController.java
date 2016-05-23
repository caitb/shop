package com.masiis.shop.api.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.base.BaseReq;
import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.bean.order.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysConstants;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.order.BOrderService;
import com.masiis.shop.api.service.product.SkuService;
import com.masiis.shop.api.service.system.ComDictionaryService;
import com.masiis.shop.api.service.user.ComShipManService;
import com.masiis.shop.api.service.user.ComUserService;
import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BorderDetail;
import com.masiis.shop.dao.platform.order.PfBorderPaymentMapper;
import com.masiis.shop.dao.po.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Resource
    private ComUserService comUserService;
    @Resource
    private ComDictionaryService comDictionaryService;
    @Resource
    private PfBorderPaymentMapper pfBorderPaymentMapper;

    @RequestMapping("/index")
    @ResponseBody
    @SignValid(paramType = OManagementIndexReq.class)
    public OManagementIndexRes bOrderManagement(HttpServletRequest request, OManagementIndexReq req, ComUser user){
        OManagementIndexRes res = new OManagementIndexRes();
        try{
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
        }catch (Exception e){
            e.printStackTrace();
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(e.getMessage());
        }
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
        try{
            Integer sendType = req.getSendType();
            Integer orderStatus = req.getOrderStatus();
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
//        Integer waitShipNum = bOrderService.queryOrderNumsByUpidAndStatus(user.getId(), BOrderStatus.WaitShip.getCode());

            String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
            if (pfBorders != null && pfBorders.size() != 0) {
                for (PfBorder pfBorder : pfBorders) {
                    List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(pfBorder.getId());
                    PfBorderConsignee pfBorderConsignee = bOrderService.findpfBorderConsignee(pfBorder.getId());
                    for (PfBorderItem pfBorderItem : pfBorderItems) {
                        pfBorderItem.setSkuUrl(skuValue + skuService.findComSkuImage(pfBorderItem.getSkuId()).getImgUrl());
                        pfBorder.setTotalQuantity(pfBorder.getTotalQuantity() + pfBorderItem.getQuantity());//订单商品总量
                    }
                    pfBorder.setPfBorderConsignee(pfBorderConsignee);//收货人信息
                    pfBorder.setPfBorderItems(pfBorderItems);
                }
            }
            if(request.getSession().getAttribute("comShipMans")==null || request.getSession().getAttribute("comShipMans")==""){
                List<ComShipMan> comShipMans = comShipManService.list();
                request.getSession().setAttribute("comShipMans", comShipMans);
            }
            res.setPfBorders(pfBorders);
            res.setWaitShipNum(borderNum);
            res.setIndex(index);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch(Exception e){
            e.printStackTrace();
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(e.getMessage());
        }
        return res;
    }

    /**
     * 异步查询出货订单
     *
     * @author muchaofeng
     * @date 2016/4/7 16:18
     */

    @RequestMapping("/clickDeliverType.do")
    @ResponseBody
    @SignValid(paramType = OrderListPagingReq.class)
    public OrderListPagingRes clickDeliverType(HttpServletRequest request, OrderListPagingReq req, ComUser user) {
        OrderListPagingRes res = new OrderListPagingRes();
        List<PfBorder> pfBorder = null;
        try {
            Integer index= req.getIndex();
            if (index== 0) {
                pfBorder = bOrderService.findPfpBorder(user.getId(), null, null);
            } else if (index == 1) {
                pfBorder = bOrderService.findPfpBorder(user.getId(), 0, null);
                List<PfBorder> pfBorder1 = bOrderService.findPfpBorder(user.getId(), 9, null);
                for (PfBorder pfBorder11 : pfBorder1) {
                    pfBorder.add(pfBorder11);
                }
            } else if (index == 2) {
                pfBorder = bOrderService.findPfpBorder(user.getId(), 7, null);
            } else if (index == 3) {
                pfBorder = bOrderService.findPfpBorder(user.getId(), 8, null);
            } else if (index == 4) {
                pfBorder = bOrderService.findPfpBorder(user.getId(), 3, null);
            } else if (index == 5) {
                pfBorder = bOrderService.findPfpBorder(user.getId(), 6, null);
                Iterator<PfBorder> chk_itw = pfBorder.iterator();
                while (chk_itw.hasNext()) {
                    PfBorder pfBorders = chk_itw.next();
                    if (pfBorders.getSendType() == 2 && pfBorders.getOrderStatus() == 6) {//排单订单
                        chk_itw.remove();
                    }
                }
            }
            res.setPfBorders(pfBorder);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                res.setResMsg(ex.getMessage());
            } else {
                res.setResMsg("网络错误");
            }
        }
        return res;
    }

    /**
     * 确认发货
     *
     * @author muchaofeng
     * @date 2016/3/20 13:40
     */
    @RequestMapping("/appDeliver.do")
    @ResponseBody
    @SignValid(paramType = BorderDeliverReq.class)
    public OrderListPagingRes appDeliver(HttpServletRequest request, BorderDeliverReq req, ComUser user) {
        OrderListPagingRes res = new OrderListPagingRes();
        try {
            String shipManName=req.getShipManName();
            Long orderId=req.getOrderId();
            String freight=req.getFreight();
            String shipManId= req.getShipManId();
            bOrderService.deliver(shipManName, orderId, freight, shipManId, user);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                res.setResMsg(ex.getMessage());
            } else {
                res.setResMsg("网络错误");
            }
        }
        return res;
    }
    /**
     * 出货订单详情
     *
     * @author muchaofeng
     * @date 2016/3/16 15:00
     */
    @RequestMapping("/deliverBorderDetail.html")
    @ResponseBody
    @SignValid(paramType = BorderDetailReq.class)
    public BorderDetailRes deliveryBorderDetils(HttpServletRequest request, BorderDetailReq req) throws Exception {
        BorderDetail borderDetail = new BorderDetail();
        BorderDetailRes res = new BorderDetailRes();
        Long id = req.getId();
        try{
            String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
            PfBorder pfBorder = bOrderService.getPfBorderById(id);
            ComUser user = comUserService.getUserById(pfBorder.getUserId());
            List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(id);
            for (PfBorderItem pfBorderItem : pfBorderItems) {
                ComSkuImage comSkuImage = skuService.findComSkuImage(pfBorderItem.getSkuId());
                pfBorderItem.setSkuUrl(skuValue + comSkuImage.getImgUrl());
                pfBorder.setTotalQuantity(pfBorder.getTotalQuantity() + pfBorderItem.getQuantity());//订单商品总量
            }
            ComDictionary comDictionary = comDictionaryService.findComDictionary(pfBorder.getOrderStatus());
            pfBorder.setOrderSkuStatus(comDictionary.getValue());
            //快递公司信息
            List<PfBorderFreight> pfBorderFreights = bOrderService.findByPfBorderFreightOrderId(id);
            //收货人
            PfBorderConsignee pfBorderConsignee = bOrderService.findpfBorderConsignee(id);
            //支付方式
            List<PfBorderPayment> pfBorderPayments = pfBorderPaymentMapper.selectByBorderId(id);
            borderDetail.setBuyerName(user.getWxNkName());
            borderDetail.setPfBorderPayments(pfBorderPayments);
            borderDetail.setPfBorder(pfBorder);
            borderDetail.setPfBorderItems(pfBorderItems);
            borderDetail.setPfBorderFreights(pfBorderFreights);
            borderDetail.setPfBorderConsignee(pfBorderConsignee);
            List<ComShipMan> comShipMans = comShipManService.list();

            res.setComShipMans(comShipMans);
            res.setBorderDetail(borderDetail);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception ex){
            if (StringUtils.isNotBlank(ex.getMessage())) {
                res.setResMsg(ex.getMessage());
            } else {
                res.setResMsg("网络错误");
            }
        }
        return res;
    }
}
