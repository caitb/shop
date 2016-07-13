package com.masiis.shop.web.platform.controller.order;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.enums.platform.BOrderType;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BOrder;
import com.masiis.shop.dao.beans.order.BorderDetail;
import com.masiis.shop.dao.platform.order.PfBorderPaymentMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.common.constant.platform.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.*;
import com.masiis.shop.web.platform.service.product.PfUserSkuStockService;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.platform.service.system.ComDictionaryService;
import com.masiis.shop.web.common.service.UserService;
import org.apache.commons.lang.StringUtils;
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
 * 订单管理
 *
 * @author muchaofeng
 * @date 2016/4/5 11:46
 */
@Controller
@RequestMapping("/borderManage")
public class BorderManageController extends BaseController {

    private final static Log log = LogFactory.getLog(BorderManageController.class);

    @Resource
    private SkuService skuService;
    @Resource
    private UserService userService;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private ComDictionaryService comDictionaryService;
    @Resource
    private PfUserSkuStockService pfUserSkuStockService;
    @Resource
    private PfBorderPaymentMapper pfBorderPaymentMapper;
    @Resource
    private ComShipManService comShipManService;
    @Resource
    private PfSupplierBankService pfSupplierBankService;
    @Resource
    private PfBorderConsigneeService pfBorderConsigneeService;
    @Resource
    private BOrderShipService bOrderShipService;

    /**
     * 确认收货
     *
     * @author muchaofeng
     * @date 2016/3/20 13:40
     */
    @RequestMapping("/closeDeal.do")
    @ResponseBody
    public String closeDeal(@RequestParam(required = true) Long orderId) {
        JSONObject json = new JSONObject();
        try {
            PfBorder border = bOrderService.getPfBorderById(orderId);
            bOrderShipService.receiptBOrder(border);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return json.toString();
    }

    /**
     * 异步查询进货订单
     *
     * @author muchaofeng
     * @date 2016/4/6 14:31
     */

    @RequestMapping("/clickType.do")
    @ResponseBody
    public List<PfBorder> clickType(HttpServletRequest request, @RequestParam(required = true) Integer index) {
        List<PfBorder> pfBorder = null;
        try {
            ComUser user = getComUser(request);
            if (user == null) {
                throw new BusinessException("user不能为空");
            }
            if (request.getSession().getAttribute("defaultBank") == null || request.getSession().getAttribute("defaultBank") == "") {
                PfSupplierBank defaultBank = pfSupplierBankService.getDefaultBank();
                request.getSession().setAttribute("defaultBank", defaultBank);
            }

            if (index == 0) {
                pfBorder = bOrderService.findPfBorder(user.getId(), null, null);
            } else if (index == 1) {
                pfBorder = bOrderService.findPfBorder(user.getId(), 0, null);
                List<PfBorder> pfBorder1 = bOrderService.findPfBorder(user.getId(), 9, null);
                for (PfBorder pfBorder11 : pfBorder1) {
                    pfBorder.add(pfBorder11);
                }
            } else if (index == 2) {
                pfBorder = bOrderService.findPfBorder(user.getId(), 7, null);
            } else if (index == 6) {
                pfBorder = bOrderService.findPfBorder(user.getId(), 2, null);
            } else if (index == 3) {
                pfBorder = bOrderService.findPfBorder(user.getId(), 8, null);
            } else if (index == 4) {
                pfBorder = bOrderService.findPfBorder(user.getId(), 3, null);
            } else if (index == 5) {
                pfBorder = bOrderService.findPfBorder(user.getId(), 6, null);
                Iterator<PfBorder> chk_itw = pfBorder.iterator();
                while (chk_itw.hasNext()) {
                    PfBorder pfBorders = chk_itw.next();
                    if (pfBorders.getSendType() == 2 && pfBorders.getOrderStatus() == 6) {//排单订单
                        chk_itw.remove();
                    }
                }
            }
//            for (PfBorder pfBorders : pfBorder) {
//                pfBorders.setPidUserName("平台");
//                String insertDay = DateUtil.insertDay(pfBorders.getCreateTime());
//                pfBorders.setPayTimes(insertDay);
//            }
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return pfBorder;
    }

    /**
     * 异步查询出货订单
     *
     * @author muchaofeng
     * @date 2016/4/7 16:18
     */

    @RequestMapping("/clickdeliverType.do")
    @ResponseBody
    public List<PfBorder> clickdeliverType(HttpServletRequest request, @RequestParam(required = true) Integer index) {
        List<PfBorder> pfBorder = null;
        try {
            ComUser user = getComUser(request);
            if (user == null) {
                throw new BusinessException("user不能为空");
            }

            if (index == 0) {
                pfBorder = bOrderService.findPfpBorder(user.getId(), null, null);
            } else if (index == 2) {
                pfBorder = bOrderService.findPfpBorder(user.getId(), 0, null);
                List<PfBorder> pfBorder1 = bOrderService.findPfpBorder(user.getId(), 9, null);
                for (PfBorder pfBorder11 : pfBorder1) {
                    pfBorder.add(pfBorder11);
                }
//            } else if (index == 2) {
//                pfBorder = bOrderService.findPfpBorder(user.getId(), 7, null);
            } else if (index == 4) {
                pfBorder = bOrderService.findPfpBorder(user.getId(), 2, null);
            } else if (index == 3) {
                pfBorder = bOrderService.findPfpBorder(user.getId(), 3, null);
            } else if (index == 1) {
                pfBorder = bOrderService.findPfpBorder(user.getId(), 6, null);
                Iterator<PfBorder> chk_itw = pfBorder.iterator();
                while (chk_itw.hasNext()) {
                    PfBorder pfBorders = chk_itw.next();
                    if (pfBorders.getSendType() == 2 && pfBorders.getOrderStatus() == 6) {//排单订单
                        chk_itw.remove();
                    }
                }
            }
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return pfBorder;
    }

    /**
     * 确认发货
     *
     * @author muchaofeng
     * @date 2016/3/20 13:40
     */
    @RequestMapping("/deliver.do")
    @ResponseBody
    public String deliver(HttpServletRequest request,
                          @RequestParam(required = true) String shipManName,
                          @RequestParam(required = true) Long orderId,
                          @RequestParam(required = true) String freight,
                          @RequestParam(required = true) String shipManId) {
        JSONObject json = new JSONObject();
        try {
            ComUser user = getComUser(request);
            if (user == null) {
                throw new BusinessException("user不能为空");
            }
            bOrderService.deliver(shipManName, orderId, freight, shipManId, user);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return json.toString();
    }

    /**
     * 订单管理
     *
     * @author muchaofeng
     * @date 2016/4/2 14:09
     */
    @RequestMapping("/borderManagement.html")
    public ModelAndView borderManagement(HttpServletRequest request, Integer orderStatus, Integer shipStatus) throws Exception {
        ComUser comUser = getComUser(request);
        if (comUser == null) {
            throw new BusinessException("user不能为空");
        }
        List<PfBorder> pfBorders = bOrderService.findByUserId(comUser.getId(), orderStatus, shipStatus);
        List<PfBorder> pfBorderps = bOrderService.findByUserPid(comUser.getId(), orderStatus, shipStatus);
        List<PfBorder> pfBorders0 = new ArrayList<>();
        List<PfBorder> pfBorders7 = new ArrayList<>();//代发货
        List<PfBorder> pfBorders8 = new ArrayList<>();//待收货
        List<PfBorder> pfBorders6 = new ArrayList<>();//排单中
        List<PfBorder> pfBorders3 = new ArrayList<>();//已完成
        for (PfBorder pfBord : pfBorders) {
            if (pfBord.getOrderStatus() == 0 || pfBord.getOrderStatus() == 9) {
                pfBorders0.add(pfBord);//待付款
            } else if (pfBord.getOrderStatus() == 7) {
                pfBorders7.add(pfBord);//代发货
            } else if (pfBord.getOrderStatus() == 8) {
                pfBorders8.add(pfBord);//待收货
            } else if (pfBord.getOrderStatus() == 6) {
                pfBorders6.add(pfBord);//排单中
            } else if (pfBord.getOrderStatus() == 3) {
                pfBorders3.add(pfBord);//已完成
            }
        }
        List<PfBorder> pfBorderp0 = new ArrayList<>();
        List<PfBorder> pfBorderp7 = new ArrayList<>();//代发货
        List<PfBorder> pfBorderp8 = new ArrayList<>();//待收货
        List<PfBorder> pfBorderp6 = new ArrayList<>();//排单中
        List<PfBorder> pfBorderp3 = new ArrayList<>();//已完成
        for (PfBorder pfBord : pfBorderps) {
            if (pfBord.getOrderStatus() == 0 || pfBord.getOrderStatus() == 9) {
                pfBorderp0.add(pfBord);//待付款
            } else if (pfBord.getOrderStatus() == 6) {
                pfBorderp6.add(pfBord);//排单中
            } else if (pfBord.getOrderStatus() == 3) {
                pfBorderp3.add(pfBord);//代发货
            } else if (pfBord.getOrderStatus() == 8) {
                pfBorderp8.add(pfBord);//待收货
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pfBorders7", pfBorders7.size());
        modelAndView.addObject("pfBorders6", pfBorders6.size());
        modelAndView.addObject("pfBorders0", pfBorders0.size());
        modelAndView.addObject("pfBorders8", pfBorders8.size());
        modelAndView.addObject("pfBorders3", pfBorders3.size());
        modelAndView.addObject("pfBorderps3", pfBorderp3.size());
        modelAndView.addObject("pfBorderps6", pfBorderp6.size());
        modelAndView.addObject("pfBorderps0", pfBorderp0.size());
//        modelAndView.addObject("pfBorderps8", pfBorderp8.size());
        modelAndView.setViewName("platform/order/dingdanguanli");
        return modelAndView;
    }

    /**
     * 合伙订单列表
     *
     * @param request
     * @param isShipment  进出货(0进货;1出货)
     * @param orderStatus 订单状态
     * @return
     */
    @RequestMapping("/orderList")
    public ModelAndView orderList(
            HttpServletRequest request,
            @RequestParam(value = "isShipment", required = false, defaultValue = "0") Integer isShipment,
            @RequestParam(value = "orderStatus", required = false) Integer orderStatus
    ) {

        ModelAndView mav = new ModelAndView("platform/order/orderList");

        Long userId = null;
        Long userPid = null;
        if (isShipment == 0) userId = getComUser(request).getId();
        if (isShipment == 1) userPid = getComUser(request).getId();
        try {
            if (request.getSession().getAttribute("defaultBank") == null || request.getSession().getAttribute("defaultBank") == "") {
                PfSupplierBank defaultBank = pfSupplierBankService.getDefaultBank();
                request.getSession().setAttribute("defaultBank", defaultBank);
            }

            List<BOrder> orderMaps = bOrderService.orderList(userId, userPid, orderStatus);
            mav.addObject("imgUrlPrefix", PropertiesUtils.getStringValue("index_product_220_220_url"));
            mav.addObject("orderStatus", orderStatus);
            mav.addObject("isShipment", isShipment);
            mav.addObject("orderMaps", orderMaps);
            mav.addObject("orderStatuses", BOrderStatus.values());
            mav.addObject("orderTypes", BOrderType.values());
        } catch (Exception e) {
            log.error("查询订单列表失败![userPid=" + userPid + "][orderStatus=" + orderStatus + "][userId=" + userId + "]" + e);
            e.printStackTrace();
            throw new BusinessException("网络错误", e);
        }

        return mav;
    }

    /**
     * 获取收货人信息
     *
     * @param bOrderId 代理订单ID
     * @return
     */
    @RequestMapping("/getConsignee")
    @ResponseBody
    public Object getConsignee(Long bOrderId) {
        PfBorderConsignee pfBorderConsignee = null;
        try {
            pfBorderConsignee = pfBorderConsigneeService.getByBOrderId(bOrderId);
        } catch (Exception e) {
            log.error("获取收货人信息失败![bOrderId=" + bOrderId + "]" + e);
            e.printStackTrace();
        }

        return pfBorderConsignee;
    }

    /**
     * 分段查询进货订单
     *
     * @author muchaofeng
     * @date 2016/4/6 14:11
     */
    @RequestMapping("/stockDouckBorder")
    public ModelAndView stockDouckBorder(HttpServletRequest request, Integer orderStatus, Integer sendType) throws Exception {
        ComUser comUser = getComUser(request);
        if (comUser == null) {
            throw new BusinessException("user不能为空");
        }
        if (request.getSession().getAttribute("defaultBank") == null || request.getSession().getAttribute("defaultBank") == "") {
            PfSupplierBank defaultBank = pfSupplierBankService.getDefaultBank();
            request.getSession().setAttribute("defaultBank", defaultBank);
        }
        List<PfBorder> pfBorders = bOrderService.findByUserId(comUser.getId(), orderStatus, sendType);
        if (orderStatus != null) {
            if (orderStatus == 0) {
                List<PfBorder> byUserId = bOrderService.findByUserId(comUser.getId(), 9, sendType);
                for (PfBorder pfBorder : byUserId) {
                    pfBorders.add(pfBorder);
                }
            }
        }
        String index = null;
        if (orderStatus == null && sendType == null) {
            index = "0";//全部
        } else if (orderStatus == 0 || orderStatus == 9) {
            index = "1";//待付款//线下支付待付款
        } else if (orderStatus == 8) {
            index = "3";//待收货
        } else if (orderStatus == 7) {
            index = "2";//代发货
        } else if (orderStatus == 6) {
            index = "5";//排单中
            Iterator<PfBorder> chk_itw = pfBorders.iterator();
            while (chk_itw.hasNext()) {
                PfBorder pfBorder = chk_itw.next();
                if (pfBorder.getSendType() == 2 && pfBorder.getOrderStatus() == 6) {//排单订单
                    chk_itw.remove();
                }
            }
        } else if (orderStatus == 3) {
            index = "4";//已完成
        }
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        if (pfBorders != null && pfBorders.size() != 0) {
            for (PfBorder pfBorder : pfBorders) {
                List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(pfBorder.getId());
                for (PfBorderItem pfBorderItem : pfBorderItems) {
                    pfBorderItem.setSkuUrl(skuValue + skuService.findComSkuImage(pfBorderItem.getSkuId()).getImgUrl());
                    pfBorder.setTotalQuantity(pfBorder.getTotalQuantity() + pfBorderItem.getQuantity());//订单商品总量
                }
                pfBorder.setOrderMoney(pfBorder.getOrderAmount().toString());
//                if(pfBorder.getUserPid()==0){
//                    pfBorder.setPidUserName("平台");
//                }else{
//                    ComUser user = userService.getUserById(pfBorder.getUserPid());
//                    pfBorder.setPidUserName(user.getRealName());
//                }
                pfBorder.setPidUserName("平台");
                pfBorder.setPfBorderItems(pfBorderItems);
                String insertDay = DateUtil.insertDay(pfBorder.getCreateTime());
                pfBorder.setPayTimes(insertDay);
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("index", index);
        modelAndView.addObject("pfBorders", pfBorders);
        modelAndView.addObject("orderStatuses", BOrderStatus.values());
        modelAndView.addObject("bOrderTypes", BOrderType.values());
        modelAndView.setViewName("platform/order/jinhuodingdan");
        return modelAndView;
    }

    /**
     * 分步查询出货订单
     *
     * @author muchaofeng
     * @date 2016/4/7 14:46
     */
    @RequestMapping("/deliveryDouckBorder")
    public ModelAndView deliveryDouckBorder(HttpServletRequest request, Integer orderStatus, Integer sendType) throws Exception {
        ComUser comUser = getComUser(request);
        if (comUser == null) {
            throw new BusinessException("user不能为空");
        }
        List<PfBorder> pfBorders = bOrderService.findByUserPid(comUser.getId(), orderStatus, sendType);
        if (orderStatus != null) {
            if (orderStatus == 0) {
                List<PfBorder> byUserId = bOrderService.findByUserPid(comUser.getId(), 9, sendType);
                for (PfBorder pfBorder : byUserId) {
                    pfBorders.add(pfBorder);
                }
            }
        }
        String index = null;
        Integer borderNum = 0;
        if (orderStatus == null && sendType == null) {
            index = "0";//全部
        } else if (orderStatus == 0 || orderStatus == 9) {
            index = "2";//待付款//线下支付待付款
//        } else if (orderStatus == 7) {
//            index = "2";//代发货
//            borderNum = pfBorders.size();
//        } else if (orderStatus == 8) {
//            index = "3";//待收货
        } else if (orderStatus == 3) {
            index = "3";//已完成
        } else if (orderStatus == 6) {
            index = "1";//排单中
            Iterator<PfBorder> chk_itw = pfBorders.iterator();
            while (chk_itw.hasNext()) {
                PfBorder pfBorder = chk_itw.next();
                if (pfBorder.getSendType() == 2 && pfBorder.getOrderStatus() == 6) {//排单订单
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
        modelAndView.addObject("orderStatuses", BOrderStatus.values());
        modelAndView.addObject("bOrderTypes", BOrderType.values());
        if (request.getSession().getAttribute("comShipMans") == null || request.getSession().getAttribute("comShipMans") == "") {
            List<ComShipMan> comShipMans = comShipManService.list();
            request.getSession().setAttribute("comShipMans", comShipMans);
        }
        modelAndView.addObject("index", index);
        modelAndView.setViewName("platform/order/chuhuodingdan");
        return modelAndView;
    }

    /**
     * 订单详情
     * <p/>
     * 进货订单详情
     *
     * @author muchaofeng
     * @date 2016/3/16 15:00
     */
    @RequestMapping("/borderDetils.html")
    public ModelAndView borderDetils(HttpServletRequest request, Long id) throws Exception {
        BorderDetail borderDetail = new BorderDetail();
        ComUser user = getComUser(request);
        if (user == null) {
            throw new BusinessException("user不能为空");
        }
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        PfBorder pfBorder = bOrderService.getPfBorderById(id);
        List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(id);
        PfUserSkuStock pfUserSkuStock = null;
        Integer stockNum = 0;
        StringBuffer stringBuffer = new StringBuffer();
        for (PfBorderItem pfBorderItem : pfBorderItems) {
            ComSkuImage comSkuImage = skuService.findComSkuImage(pfBorderItem.getSkuId());
            pfBorderItem.setSkuUrl(skuValue + comSkuImage.getImgUrl());
            pfBorder.setTotalQuantity(pfBorder.getTotalQuantity() + pfBorderItem.getQuantity());//订单商品总量
            pfUserSkuStock = pfUserSkuStockService.selectByUserIdAndSkuId(user.getId(), pfBorderItem.getSkuId());
            if (pfUserSkuStock != null) {
                stockNum = stockNum + pfUserSkuStock.getStock();
            } else {
//                pfUserSkuStock.setStock(0);
                stockNum = stockNum + 0;
            }
        }
        //快递公司信息
        List<PfBorderFreight> pfBorderFreights = bOrderService.findByPfBorderFreightOrderId(id);
        if (pfBorderFreights.size() != 0 && pfBorderFreights != null) {
            for (PfBorderFreight pfBorderFreight : pfBorderFreights) {
                stringBuffer.append("<p>承运公司：<span>" + pfBorderFreight.getShipManName() + "</span></p>");
                stringBuffer.append("<p>运单编号：<span>" + pfBorderFreight.getFreight() + "</span></p>");
            }
        } else {
            stringBuffer.append("<p>承运公司：<span></span></p>");
            stringBuffer.append("<p>运单编号：<span></span></p>");
        }

        //收货人
        PfBorderConsignee pfBorderConsignee = bOrderService.findpfBorderConsignee(id);
        borderDetail.setPfBorder(pfBorder);
        borderDetail.setPfBorderItems(pfBorderItems);
        borderDetail.setPfBorderFreights(pfBorderFreights);
        borderDetail.setPfBorderConsignee(pfBorderConsignee);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("stockNum", stockNum);
        modelAndView.addObject("stringBuffer", stringBuffer.toString());
        modelAndView.addObject("borderDetail", borderDetail);
        modelAndView.addObject("bOrderTypes", BOrderType.values());
        modelAndView.setViewName("platform/order/jinhuoxiangqing");
        return modelAndView;
    }

    /**
     * 出货订单详情
     *
     * @author muchaofeng
     * @date 2016/3/16 15:00
     */
    @RequestMapping("/deliveryBorderDetils.html")
    public ModelAndView deliveryBorderDetils(HttpServletRequest request, Long id) throws Exception {
        BorderDetail borderDetail = new BorderDetail();
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        PfBorder pfBorder = bOrderService.getPfBorderById(id);
        ComUser Buser = userService.getUserById(pfBorder.getUserId());
        ComUser comUser = getComUser(request);
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
        borderDetail.setBuyerName(Buser.getWxNkName());
        borderDetail.setPfBorderPayments(pfBorderPayments);
        borderDetail.setPfBorder(pfBorder);
        borderDetail.setPfBorderItems(pfBorderItems);
        borderDetail.setPfBorderFreights(pfBorderFreights);
        borderDetail.setPfBorderConsignee(pfBorderConsignee);
        ModelAndView modelAndView = new ModelAndView();
        List<ComShipMan> comShipMans = comShipManService.list();
        modelAndView.addObject("comShipMans", comShipMans);
        modelAndView.addObject("borderDetail", borderDetail);
        modelAndView.addObject("bOrderTypes", BOrderType.values());
        modelAndView.addObject("bOrderStatuses", BOrderStatus.values());
        modelAndView.setViewName("platform/order/chuhuoxiangqing");
        return modelAndView;
    }

    /**
     * 出货订单
     *
     * @author muchaofeng
     * @date 2016/3/16 11:37
     */
    @RequestMapping("/deliveryBorder")
    public ModelAndView deliveryBorder(HttpServletRequest request, Integer orderStatus, Integer shipStatus) throws Exception {
        ComUser comUser = getComUser(request);
        if (comUser == null) {
            throw new BusinessException("user不能为空");
        }
        List<PfBorder> pfBorders = bOrderService.findByUserPid(comUser.getId(), orderStatus, shipStatus);
        List<PfBorder> pfBorders0 = new ArrayList<>();
        List<PfBorder> pfBorders10 = new ArrayList<>();//代发货
        List<PfBorder> pfBorders15 = new ArrayList<>();//待收货
        List<PfBorder> pfBorders3 = new ArrayList<>();//已完成
        List<PfBorder> pfBorders6 = new ArrayList<>();//排单中
        for (PfBorder pfBord : pfBorders) {
            if (pfBord.getOrderStatus() == 0) {
                pfBorders0.add(pfBord);//待付款
            } else if (pfBord.getOrderStatus() == 7) {
                pfBorders10.add(pfBord);//代发货
            } else if (pfBord.getOrderStatus() == 8) {
                pfBorders15.add(pfBord);//待收货
            } else if (pfBord.getOrderStatus() == 3) {
                pfBorders3.add(pfBord);//已完成
            } else if (pfBord.getOrderStatus() == 6) {
                pfBorders6.add(pfBord);//排单中
            }
        }
        Integer borderNum10 = pfBorders10.size();
        List<List<PfBorder>> pfBorderss = new ArrayList<>();
        pfBorderss.add(0, pfBorders);
        pfBorderss.add(1, pfBorders0);
        pfBorderss.add(2, pfBorders10);
        pfBorderss.add(3, pfBorders15);
        pfBorderss.add(4, pfBorders3);
        pfBorderss.add(5, pfBorders6);
        for (List<PfBorder> pfBorderw : pfBorderss) {
            Iterator<PfBorder> chk_itw = pfBorderw.iterator();
            while (chk_itw.hasNext()) {
                PfBorder pfBorder = chk_itw.next();
                if (pfBorder.getUserPid().longValue() == comUser.getId().longValue()) {//进货订单
                } else {
                    chk_itw.remove();
                }
            }
        }
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        for (List<PfBorder> pfsBorder : pfBorderss) {
            if (pfsBorder != null && pfsBorder.size() != 0) {
                for (PfBorder pfBorder : pfsBorder) {
                    List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(pfBorder.getId());
                    PfBorderConsignee pfBorderConsignee = bOrderService.findpfBorderConsignee(pfBorder.getId());
                    for (PfBorderItem pfBorderItem : pfBorderItems) {
                        ComSkuImage comSkuImage = skuService.findComSkuImage(pfBorderItem.getSkuId());
                        pfBorderItem.setSkuUrl(skuValue + comSkuImage.getImgUrl());
                        pfBorder.setTotalQuantity(pfBorder.getTotalQuantity() + pfBorderItem.getQuantity());//订单商品总量
                    }
                    pfBorder.setPfBorderItems(pfBorderItems);
                    pfBorder.setPfBorderConsignee(pfBorderConsignee);//收货人信息
                }
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        List<ComShipMan> comShipMans = comShipManService.list();
        modelAndView.addObject("comShipMans", comShipMans);
        modelAndView.addObject("pfBorders", pfBorderss);
        modelAndView.addObject("borderNum10", borderNum10);
        modelAndView.setViewName("platform/order/chuhuodingdan");
        return modelAndView;
    }
}
