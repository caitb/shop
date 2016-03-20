package com.masiis.shop.web.platform.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BorderDetail;
import com.masiis.shop.dao.beans.order.OrderUserSku;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.order.BorderFreightService;
import com.masiis.shop.web.platform.service.order.PfBorderConsigneeService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.system.ComDictionaryService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import com.masiis.shop.web.platform.utils.WXBeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.or.ThreadGroupRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @autor jipengkun
 */
@Controller
@RequestMapping("/border")
public class BOrderController extends BaseController {

    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private SkuService skuService;
    @Resource
    private UserService userService;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private PfBorderConsigneeService pfBorderConsigneeService;
    @Resource
    private ComDictionaryService comDictionaryService;
    @Resource
    private BorderFreightService borderFreightService;

    /**
     * 用户确认生成订单
     *
     * @author ZhaoLiang
     * @date 2016/3/8 12:50
     */
    @ResponseBody
    @RequestMapping("/addBOrder.do")
    public String addBOrder(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam(value = "realName", required = true) String realName,
                            @RequestParam(value = "weixinId", required = true) String weixinId,
                            @RequestParam(value = "skuId", required = true) Integer skuId,
                            @RequestParam(value = "levelId", required = true) Integer levelId,
                            @RequestParam(value = "pUserId", required = true) Long pUserId) {
        JSONObject obj = new JSONObject();
        try {
            if (StringUtils.isBlank(realName)) {
                throw new BusinessException("名称不能为空");
            }
            if (StringUtils.isBlank(weixinId)) {
                throw new BusinessException("微信号不能为空");
            }
            if (levelId <= 0) {
                throw new BusinessException("代理等级有误");
            }
            PfUserSku pfUserSku = null;
            if (pUserId != null && pUserId > 0) {
                ComUser pUser = userService.getUserById(pUserId);
                if (pUser == null) {
                    throw new BusinessException("您的推荐人还未注册，请联系您的推荐人先注册!");
                } else {
                    pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(pUser.getId(), skuId);
                    if (pfUserSku == null) {
                        throw new BusinessException("您的推荐人还未代理此款商品");
                    } else {
                        if (pfUserSku.getAgentLevelId() >= levelId) {
                            throw new BusinessException("您的代理等级只能低于您的推荐人代理等级");
                        }
                    }
                }
            }
            //处理用户数据
            ComUser comUser = (ComUser) request.getSession().getAttribute(SysConstants.SESSION_LOGIN_USER_NAME);
            comUser.setRealName(realName);
            comUser.setWxId(weixinId);
            request.getSession().setAttribute(SysConstants.SESSION_LOGIN_USER_NAME, comUser);
            PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, levelId);
            ComSku comSku = skuService.getSkuById(skuId);
            //折扣后单价
            BigDecimal unitPrice = comSku.getPriceRetail().multiply(pfSkuAgent.getDiscount());
            //折扣后总价
            BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(pfSkuAgent.getQuantity()));
            //处理订单数据
            PfBorder order = new PfBorder();
            order.setCreateTime(new Date());
            order.setCreateMan(comUser.getId());
            String orderCode = OrderMakeUtils.makeOrder("B");
            order.setOrderCode(orderCode);
            order.setUserMessage("");
            order.setUserId(comUser.getId());
            order.setUserPid(0l);
            order.setSupplierId(0);
            order.setReceivableAmount(totalPrice);
            order.setOrderAmount(totalPrice);//运费到付，商品总价即订单总金额
            order.setProductAmount(totalPrice);
            order.setShipAmount(BigDecimal.ZERO);
            order.setPayAmount(BigDecimal.ZERO);
            order.setShipType(0);
            order.setOrderStatus(0);
            order.setShipStatus(0);
            order.setPayStatus(0);
            order.setIsShip(0);
            order.setIsReplace(0);
            order.setIsReceipt(0);
            //处理订单商品数据
            List<PfBorderItem> orderItems = new ArrayList<>();
            PfBorderItem pfBorderItem = new PfBorderItem();
            pfBorderItem.setCreateTime(new Date());
            pfBorderItem.setSpuId(comSku.getSpuId());
            pfBorderItem.setSkuId(comSku.getId());
            pfBorderItem.setSkuName(comSku.getName());
            pfBorderItem.setQuantity(pfSkuAgent.getQuantity());
            pfBorderItem.setOriginalPrice(comSku.getPriceRetail());
            pfBorderItem.setUnitPrice(unitPrice);
            pfBorderItem.setTotalPrice(totalPrice);
            pfBorderItem.setIsComment(0);
            pfBorderItem.setIsReturn(0);
            orderItems.add(pfBorderItem);
            //处理用户sku关系数据
            PfUserSku userSku = new PfUserSku();
            PfUserSku checkUserSku = userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(), comSku.getId());
            if (checkUserSku == null) {
                userSku.setCreateTime(new Date());
                if (pfUserSku == null) {
                    userSku.setPid(0);
                } else {
                    userSku.setPid(pfUserSku.getId());
                }
                userSku.setCode("");
                userSku.setUserId(comUser.getId());
                userSku.setSkuId(comSku.getId());
                userSku.setAgentLevelId(levelId);
                userSku.setIsPay(0);
                userSku.setIsCertificate(0);
            }
            Long bOrderId = bOrderService.AddBOrder(order, orderItems, userSku, comUser);
            obj.put("isError", false);
            obj.put("bOrderId", bOrderId);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            obj.put("isError", true);
            obj.put("message", ex.getMessage());
        }
        return obj.toJSONString();
    }

    /**
     * 合伙人支付页面
     *
     * @author ZhaoLiang
     * @date 2016/3/5 16:32
     */
    @RequestMapping("/payBOrder.shtml")
    public ModelAndView payBOrder(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestParam(value = "userAddressId", required = false) Long userAddressId,
                                  @RequestParam(value = "userMessage", required = false) String userMessage,
                                  @RequestParam(value = "bOrderId", required = false) Long bOrderId
    ) {
        ModelAndView mv = new ModelAndView();
        String skuImg = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
        List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(bOrderId);
        StringBuffer stringBuffer = new StringBuffer();
        int sumQuantity = 0;
        for (PfBorderItem pfBorderItem : pfBorderItems) {
            ComSkuImage comSkuImage = skuService.findComSkuImage(pfBorderItem.getSkuId());
            stringBuffer.append("<section class=\"sec2\" >");
            stringBuffer.append("<p class=\"photo\" >");
            stringBuffer.append("<img src = '" + skuImg + comSkuImage.getImgUrl() + "' alt = \"\" >");
            stringBuffer.append("</p>");
            stringBuffer.append("<div>");
            stringBuffer.append("<h2> " + pfBorderItem.getSkuName() + "'</h2>");
            stringBuffer.append("<h3 ></h3>");
            stringBuffer.append("<p ><span> ￥" + pfBorderItem.getUnitPrice() + " </span ><b style = \"float:right; margin-right:10px;font-size:12px;\" > x" + pfBorderItem.getQuantity() + " </b ></p >");
            stringBuffer.append("</div>");
            stringBuffer.append("</section>");
            sumQuantity += pfBorderItem.getQuantity();
        }

        //获得地址
        ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
        Long userId = null;
        if (comUser != null) {
            userId = comUser.getId();
        } else {
            userId = 1L;
        }
        ComUserAddress comUserAddress = userAddressService.getOrderAddress(request, userAddressId, userId);
        if (comUserAddress != null) {
            request.getSession().setAttribute(SysConstants.SESSION_ORDER_SELECTED_ADDRESS, comUserAddress.getId());
        }
        mv.addObject("comUserAddress", comUserAddress);

        mv.addObject("bOrderId", bOrderId);
        mv.addObject("receivableAmount", pfBorder.getReceivableAmount());
        mv.addObject("orderAmount", pfBorder.getOrderAmount());
        mv.addObject("productInfo", stringBuffer.toString());
        mv.addObject("quantity", sumQuantity);
        mv.setViewName("platform/order/zhifu");

        return mv;
    }

    /**
     * 订单支付处理
     *
     * @author ZhaoLiang
     * @date 2016/3/17 14:32
     */
    @RequestMapping("/payBOrderSubmit.do")
    public String payBOrderSubmit(HttpServletRequest request,
                                  @RequestParam(value = "bOrderId", required = true) Long bOrderId,
                                  @RequestParam(value = "userMessage", required = true) String userMessage,
                                  @RequestParam(value = "userAddressId", required = true) Long userAddressId,
                                  RedirectAttributes attrs) {
        WxPaySysParamReq req = null;
        try {
            if (userAddressId <= 0) {
                throw new BusinessException("收货地址不能为空");
            }
            if (bOrderId <= 0) {
                throw new BusinessException("订单号错误");
            }
            PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
            if (!bOrderService.checkBOrderStock(pfBorder)) {
                throw new BusinessException("订单商品库存不足");
            }
            pfBorder.setUserMessage(userMessage);
            ComUserAddress comUserAddress = userAddressService.getUserAddressById(userAddressId);
            PfBorderConsignee pfBorderConsignee = new PfBorderConsignee();
            pfBorderConsignee.setCreateTime(new Date());
            pfBorderConsignee.setPfBorderId(pfBorder.getId());
            pfBorderConsignee.setUserId(comUserAddress.getUserId());
            pfBorderConsignee.setConsignee(comUserAddress.getName());
            pfBorderConsignee.setMobile(comUserAddress.getMobile());
            pfBorderConsignee.setProvinceId(comUserAddress.getProvinceId());
            pfBorderConsignee.setProvinceName(comUserAddress.getProvinceName());
            pfBorderConsignee.setCityId(comUserAddress.getCityId());
            pfBorderConsignee.setCityName(comUserAddress.getCityName());
            pfBorderConsignee.setRegionId(comUserAddress.getRegionId());
            pfBorderConsignee.setRegionName(comUserAddress.getRegionName());
            pfBorderConsignee.setAddress(comUserAddress.getAddress());
            pfBorderConsignee.setZip(comUserAddress.getZip());
            bOrderService.toPayBOrder(pfBorder, pfBorderConsignee);
            req = new WxPaySysParamReq();
            req.setOrderId(pfBorder.getOrderCode());
            req.setSignType("MD5");
            req.setNonceStr(WXBeanUtils.createGenerateStr());
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
            req.setSuccessUrl(basePath + "border/payBOrdersSuccess.shtml?bOrderId=" + pfBorder.getId());
            req.setSign(WXBeanUtils.toSignString(req));
        } catch (Exception ex) {
            //DO ERROR
        }
        attrs.addAttribute("param", JSONObject.toJSONString(req));
        return "redirect:/wxpay/wtpay";
    }

    /**
     * 成功支付订单
     *
     * @author muchaofeng
     * @date 2016/3/9 15:06
     */
    @RequestMapping("/payBOrdersSuccess.shtml")
    public ModelAndView payBOrdersSuccess(HttpServletRequest request,
                                          @RequestParam(value = "bOrderId", required = true) Long bOrderId) throws Exception {
        ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
        OrderUserSku orderUserSku = new OrderUserSku();
        PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
        List<PfBorderItem> pfBorderItem = bOrderService.getPfBorderItemByOrderId(bOrderId);
        List<String> skuNames = new ArrayList<>();
        Integer skuId = 0;
        for (PfBorderItem pforderItem : pfBorderItem) {
            skuNames.add(pforderItem.getSkuName());
            skuId = pforderItem.getSkuId();
        }
        ComUser userpId = userService.getUserById(pfBorder.getUserPid());
        ;
        if (userpId == null) {
            //上级姓名
            orderUserSku.setSuperiorName("");
        } else {
            //上级姓名
            orderUserSku.setSuperiorName(userpId.getRealName());
        }
        orderUserSku.setUserName(comUser.getRealName());
        //商品名字集合
        orderUserSku.setSkuName(skuNames);
        //获取用户商品信息
        PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(), skuId);
        //获取用户代理等级
        ComAgentLevel comAgentLevel = bOrderService.findComAgentLevel(pfUserSku.getAgentLevelId());
        orderUserSku.setAgentLevel(comAgentLevel.getName());
        ModelAndView mav = new ModelAndView();
        mav.addObject("orderUserSku", orderUserSku);
        mav.addObject("userSkuId", pfUserSku.getId());
        mav.setViewName("platform/order/lingquzhengshu");
        return mav;
    }

    /**
     * 确认收货（异步）
     *
     * @author muchaofeng
     * @date 2016/3/20 13:40
     */

    @RequestMapping("/closeDeal.do")
    @ResponseBody
    public String closeDeal(HttpServletRequest request,
                            @RequestParam(required = true) Integer orderStatus,
                            @RequestParam(required = true) Long orderId,
                            @RequestParam(required = true) Integer shipStatus) {
        JSONObject json = new JSONObject();
        PfBorder pfBorder = bOrderService.getPfBorderById(orderId);
        pfBorder.setOrderStatus(orderStatus);
        pfBorder.setShipStatus(shipStatus);
        try {
            bOrderService.updateBOrder(pfBorder);
            json.put("mesg", "交易成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();
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
                          @RequestParam(required = true) String freight) {
        JSONObject json = new JSONObject();
        if (freight == null || freight == "") {
            json.put("msgs", false);
            json.put("msg", "请重新输入快递单号");
        } else {
            PfBorder pfBorder = bOrderService.getPfBorderById(orderId);
            pfBorder.setShipStatus(5);
            PfBorderFreight pfBorderFreight = new PfBorderFreight();
            pfBorderFreight.setCreateTime(new Date());
            pfBorderFreight.setPfBorderId(orderId);
            pfBorderFreight.setFreight(freight);
            pfBorderFreight.setShipManName(shipManName);
            try {
                bOrderService.updateBOrder(pfBorder);
                borderFreightService.addPfBorderFreight(pfBorderFreight);
                json.put("msgs", true);
                json.put("msg", "已发货，待收货");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return json.toString();
    }

    /**
     * 进货订单
     *
     * @author muchaofeng
     * @date 2016/3/16 11:37
     */
    @RequestMapping("/stockBorder")
    public ModelAndView stockBorder(HttpServletRequest request, Integer orderStatus, Integer shipStatus) {
        ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
        List<PfBorder> pfBorders = bOrderService.findByUserId(comUser.getId(), orderStatus, shipStatus);
        List<PfBorder> pfBorders0 = bOrderService.findByUserId(comUser.getId(), 0, shipStatus);//待付款
        List<PfBorder> pfBorders10 = bOrderService.findByUserId(comUser.getId(), 1, 0);//代发货
        List<PfBorder> pfBorders15 = bOrderService.findByUserId(comUser.getId(), 1, 5);//待收货
        List<PfBorder> pfBorders3 = bOrderService.findByUserId(comUser.getId(), 3, shipStatus);//已完成
        List<List<PfBorder>> pfBorderss = new ArrayList<>();
        pfBorderss.add(0, pfBorders);
        pfBorderss.add(1, pfBorders0);
        pfBorderss.add(2, pfBorders10);
        pfBorderss.add(3, pfBorders15);
        pfBorderss.add(4, pfBorders3);
        for (List<PfBorder> pfBorderw : pfBorderss) {
            Iterator<PfBorder> chk_itw = pfBorderw.iterator();
            while (chk_itw.hasNext()) {
                PfBorder pfBorder = chk_itw.next();
                if (pfBorder.getUserId().longValue() != comUser.getId().longValue()) {//进货订单
                    chk_itw.remove();
                }
            }
        }
//        Iterator<List<PfBorder>> chk_it = pfBorderss.iterator();
//        while(chk_it.hasNext()){
//            List<PfBorder> checkWork = chk_it.next();
//            Iterator<PfBorder> chk_itw = checkWork.iterator();
//            while(chk_itw.hasNext()) {
//                PfBorder pfBorder =chk_itw.next();
//                if (pfBorder.getUserId()!=comUser.getId()){//进货订单
//                    chk_itw.remove();
//                }
//            }
//        }
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        for (List<PfBorder> pfsBorder : pfBorderss) {
            if (pfsBorder != null && pfsBorder.size() != 0) {
                for (PfBorder pfBorder : pfsBorder) {
                    List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(pfBorder.getId());
                    for (PfBorderItem pfBorderItem : pfBorderItems) {
                        ComSkuImage comSkuImage = skuService.findComSkuImage(pfBorderItem.getSkuId());
                        pfBorderItem.setSkuUrl(skuValue + comSkuImage.getImgUrl());
                        pfBorder.setTotalQuantity(pfBorder.getTotalQuantity() + pfBorderItem.getQuantity());//订单商品总量
                    }
                    ComDictionary comDictionary = comDictionaryService.findComDictionary(pfBorder.getOrderStatus());
                    pfBorder.setOrderSkuStatus(comDictionary.getValue());
                    pfBorder.setPfBorderItems(pfBorderItems);
                }
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pfBorders", pfBorderss);
        modelAndView.setViewName("platform/user/jinhuodingdan");
        modelAndView.addObject("pfBorders", pfBorderss);
        modelAndView.setViewName("platform/order/jinhuodingdan");
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
    public ModelAndView borderDetils(HttpServletRequest request, Long id) {
        BorderDetail borderDetail = new BorderDetail();
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        PfBorder pfBorder = bOrderService.getPfBorderById(id);
        List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(id);
        for (PfBorderItem pfBorderItem : pfBorderItems) {
            ComSkuImage comSkuImage = skuService.findComSkuImage(pfBorderItem.getSkuId());
            pfBorderItem.setSkuUrl(skuValue + comSkuImage.getImgUrl());
            pfBorder.setTotalQuantity(pfBorder.getTotalQuantity() + pfBorderItem.getQuantity());//订单商品总量
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
        modelAndView.addObject("borderDetail", borderDetail);
        modelAndView.setViewName("platform/user/jinhuoxiangqing");
        modelAndView.addObject("borderDetail", borderDetail);
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
    public ModelAndView deliveryBorderDetils(HttpServletRequest request, Long id) {
        BorderDetail borderDetail = new BorderDetail();
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        PfBorder pfBorder = bOrderService.getPfBorderById(id);
        ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
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
        borderDetail.setBuyerName(comUser.getRealName());
        borderDetail.setPfBorder(pfBorder);
        borderDetail.setPfBorderItems(pfBorderItems);
        borderDetail.setPfBorderFreights(pfBorderFreights);
        borderDetail.setPfBorderConsignee(pfBorderConsignee);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("borderDetail", borderDetail);
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
    public ModelAndView deliveryBorder(HttpServletRequest request, Integer orderStatus, Integer shipStatus) {
        ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
        List<PfBorder> pfBorders = bOrderService.findByUserId(comUser.getId(), orderStatus, shipStatus);
        List<PfBorder> pfBorders0 = bOrderService.findByUserId(comUser.getId(), 0, shipStatus);//待付款
        List<PfBorder> pfBorders10 = bOrderService.findByUserId(comUser.getId(), 1, 0);//代发货
        List<PfBorder> pfBorders15 = bOrderService.findByUserId(comUser.getId(), 1, 5);//待收货
        List<PfBorder> pfBorders3 = bOrderService.findByUserId(comUser.getId(), 3, shipStatus);//已完成
        List<List<PfBorder>> pfBorderss = new ArrayList<>();
        pfBorderss.add(0, pfBorders);
        pfBorderss.add(1, pfBorders0);
        pfBorderss.add(2, pfBorders10);
        pfBorderss.add(3, pfBorders15);
        pfBorderss.add(4, pfBorders3);
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
        modelAndView.addObject("pfBorders", pfBorderss);
        modelAndView.setViewName("platform/order/chuhuodingdan");
        return modelAndView;
    }
}
