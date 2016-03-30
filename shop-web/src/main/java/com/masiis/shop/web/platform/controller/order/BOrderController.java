package com.masiis.shop.web.platform.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.order.BorderDetail;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.order.BorderFreightService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.system.ComDictionaryService;
import com.masiis.shop.web.platform.service.user.ComUserAccountService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import com.masiis.shop.web.platform.utils.WXBeanUtils;
import org.apache.commons.lang.StringUtils;
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
import java.util.*;

/**
 * @autor jipengkun
 */
@Controller
@RequestMapping("/border")
public class BOrderController extends BaseController {
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
    private ComDictionaryService comDictionaryService;
    @Resource
    private BorderFreightService borderFreightService;
    @Resource
    private ComUserAccountService comUserAccountService;

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
            ComUser comUser = getComUser(request);
            comUser.setRealName(realName);
            comUser.setWxId(weixinId);
            setComUser(request, comUser);
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
            if (pUserId != null && pUserId > 0) {
                order.setUserPid(pUserId);
            } else {
                order.setUserPid(0l);
            }
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
            order.setIsCounting(0);
            order.setOrderType(0);
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
            PfUserSku userSku = null;
            PfUserSku checkUserSku = userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(), comSku.getId());
            if (checkUserSku == null) {
                userSku = new PfUserSku();
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
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
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
                                  @RequestParam(value = "bOrderId", required = true) Long bOrderId) throws Exception {
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
        ComUser comUser = getComUser(request);
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
        mv.addObject("orderType", pfBorder.getOrderType());
        mv.setViewName("platform/order/zhifu");
        return mv;
    }

    /**
     * 订单支付处理
     *
     * @author ZhaoLiang
     * @date 2016/3/17 14:32
     */
    @ResponseBody
    @RequestMapping("/payBOrderSubmit.do")
    public String payBOrderSubmit(HttpServletRequest request,
                                  @RequestParam(value = "bOrderId", required = true) Long bOrderId,
                                  @RequestParam(value = "userMessage", required = true) String userMessage,
                                  @RequestParam(value = "userAddressId", required = true) Long userAddressId) {
        JSONObject jsonObject = new JSONObject();
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
            jsonObject.put("isError", false);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping("/payBOrderReady.shtml")
    public String payBOrderReady(HttpServletRequest request,
                                 RedirectAttributes attrs,
                                 @RequestParam(value = "bOrderId", required = true) Long bOrderId) throws Exception {
        WxPaySysParamReq req = null;
        PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
        //切换开发模式和测试模式
        String enviromentkey = PropertiesUtils.getStringValue(SysConstants.SYS_RUN_ENVIROMENT_KEY);
        if (StringUtils.isBlank(enviromentkey)
                || enviromentkey.equals("0")) {
            String uuid = UUID.randomUUID().toString();
            PfBorderPayment payment = new PfBorderPayment();
            payment.setPfBorderId(bOrderId);
            payment.setAmount(pfBorder.getReceivableAmount()); //new BigDecimal(p.getTotal_fee()).divide(new BigDecimal(100)));
            payment.setCreateTime(new Date());
            payment.setIsEnabled(0);
            // 给外部支付使用支付流水号
            payment.setPaySerialNum(uuid);
            payment.setPayTypeId(0);
            payment.setPayTypeName("微信支付");
            bOrderService.addBOrderPayment(payment);

            PfBorderPayment pfBorderPayment = bOrderService.findOrderPaymentBySerialNum(uuid);
            if (pfBorderPayment == null) {
                throw new BusinessException("该支付流水号不存在,pay_serial_num:" + uuid);
            }
            if (pfBorderPayment.getIsEnabled() == 0) {
                // 调用borderService的方法处理
                bOrderService.payBOrder(pfBorderPayment, UUID.randomUUID().toString());
            }
            attrs.addAttribute("bOrderId", bOrderId);
            return "redirect:/border/payBOrdersSuccess.shtml";
        } else if (enviromentkey.equals("1")) {
            req = new WxPaySysParamReq();
            req.setOrderId(pfBorder.getOrderCode());
            req.setSignType("MD5");
            req.setNonceStr(WXBeanUtils.createGenerateStr());
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
            req.setSuccessUrl(basePath + "border/payBOrdersSuccess.shtml?bOrderId=" + pfBorder.getId());
            req.setSign(WXBeanUtils.toSignString(req));
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
        ModelAndView mav = new ModelAndView();
        PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
        //首次代理订单
//            if (pfBorder.getOrderType() == 0) {
        String realName = "";//姓名
        String skuName = "";//合作产品
        String levelName = "";//合伙人等级
        String pRealName = "";//上级合伙人
        ComUser comUser = getComUser(request);
        realName = comUser.getRealName();
        List<PfBorderItem> pfBorderItems = bOrderService.getPfBorderItemByOrderId(bOrderId);
        skuName = pfBorderItems.get(0).getSkuName();
        //获取用户商品信息
        PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(), pfBorderItems.get(0).getSkuId());
        //获取用户代理等级
        ComAgentLevel comAgentLevel = bOrderService.findComAgentLevel(pfUserSku.getAgentLevelId());
        levelName = comAgentLevel.getName();
        //获取上级合伙人
        if (pfBorder.getUserPid() == 0) {
            pRealName = "平台";
        } else {
            ComUser pComuser = userService.getUserById(pfBorder.getUserPid());
            //判断是否已经
            pRealName = pComuser.getRealName();
        }
        mav.addObject("realName", realName);
        mav.addObject("skuName", skuName);
        mav.addObject("levelName", levelName);
        mav.addObject("pRealName", pRealName);
        mav.addObject("userSkuId", pfUserSku.getId());
        mav.setViewName("platform/order/lingquzhengshu");
//            }
//            //补货订单
//            else {
//
//            }
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
                            @RequestParam(required = true) Integer shipStatus,
                            Integer stock) {
        JSONObject json = new JSONObject();
        try {
            ComUser user = getComUser(request);
            if (user == null) {
                throw new BusinessException("用户session丢失");
            }
            PfBorder pfBorder = bOrderService.getPfBorderById(orderId);
            if(pfBorder.getSendType()==1) {//平台代发
                if (pfBorder.getOrderType() == 2) {//拿货
                    pfBorder.setOrderStatus(orderStatus);
                    pfBorder.setShipStatus(shipStatus);
                    bOrderService.updateGetStock(pfBorder,user);
                    bOrderService.updateBOrder(pfBorder);
                    comUserAccountService.countingByOrder(pfBorder);
                }
            }else if(pfBorder.getSendType()==1) {//自己发货
                pfBorder.setOrderStatus(orderStatus);
                pfBorder.setShipStatus(shipStatus);
                bOrderService.updateBOrder(pfBorder);
                comUserAccountService.countingByOrder(pfBorder);
            }
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
        try {
            ComUser user = getComUser(request);
            if (user == null) {
                user = userService.getUserById(1l);
                request.getSession().setAttribute("comUser", user);
            }
            PfBorder pfBorder = bOrderService.getPfBorderById(orderId);
            if(pfBorder.getSendType()==1){//平台代发
                if(pfBorder.getOrderType()==2){//拿货
                    if (freight == null || freight == "") {
                        throw new BusinessException("请重新输入快递单号");
                    } else {
                        pfBorder.setShipStatus(5);
                        PfBorderFreight pfBorderFreight = new PfBorderFreight();
                        pfBorderFreight.setCreateTime(new Date());
                        pfBorderFreight.setPfBorderId(orderId);
                        pfBorderFreight.setFreight(freight);
                        pfBorderFreight.setShipManName(shipManName);
                        bOrderService.updateStock(pfBorder, user);
                        bOrderService.updateBOrder(pfBorder);
                        borderFreightService.addPfBorderFreight(pfBorderFreight);
                    }
                }
            }else if(pfBorder.getSendType()==1){//自己发货
                pfBorder.setShipStatus(5);
                PfBorderFreight pfBorderFreight = new PfBorderFreight();
                pfBorderFreight.setCreateTime(new Date());
                pfBorderFreight.setPfBorderId(orderId);
                pfBorderFreight.setFreight(freight);
                pfBorderFreight.setShipManName(shipManName);
                bOrderService.updateBOrder(pfBorder);
                borderFreightService.addPfBorderFreight(pfBorderFreight);
            }
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
     * 进货订单
     *
     * @author muchaofeng
     * @date 2016/3/16 11:37
     */
    @RequestMapping("/stockBorder")
    public ModelAndView stockBorder(HttpServletRequest request, Integer orderStatus, Integer shipStatus) throws Exception {
        ComUser comUser = getComUser(request);
        List<PfBorder> pfBorders = bOrderService.findByUserId(comUser.getId(), orderStatus, shipStatus);
        List<PfBorder> pfBorders0 =new ArrayList<>();
        List<PfBorder> pfBorders10 = new ArrayList<>();//代发货
        List<PfBorder> pfBorders15 = new ArrayList<>();//待收货
        List<PfBorder> pfBorders3 =  new ArrayList<>();//已完成
        List<PfBorder> pfBorders6 = new ArrayList<>();//排单中
        for (PfBorder pfBord:pfBorders) {
            if(pfBord.getOrderStatus()==0){
                pfBorders0.add(pfBord);//待付款
            }else if (pfBord.getOrderStatus()==1 && pfBord.getShipStatus()==0){
                pfBorders10.add(pfBord);//代发货
            }else if (pfBord.getOrderStatus()==1 && pfBord.getShipStatus()==5){
                pfBorders15.add(pfBord);//待收货
            }else if (pfBord.getOrderStatus()==6) {
                pfBorders6.add(pfBord);//排单中
            }else if (pfBord.getOrderStatus()==3) {
                pfBorders3.add(pfBord);//已完成
            }
        }
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
                if (pfBorder.getUserId().longValue() != comUser.getId().longValue()) {//进货订单
                    chk_itw.remove();
                }
            }
        }
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
//                    ComDictionary  comDictionary = comDictionaryService.findComDictionary(pfBorder.getOrderStatus());
//                    pfBorder.setOrderSkuStatus(comDictionary.getValue());
                    ComUser user = userService.getUserById(pfBorder.getUserPid());
                    pfBorder.setPidUserName(user.getRealName());
                    pfBorder.setPfBorderItems(pfBorderItems);
                }
            }
        }
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("pfBorders", pfBorderss);
//        modelAndView.setViewName("platform/user/jinhuodingdan");
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
    public ModelAndView borderDetils(HttpServletRequest request, Long id) throws Exception {
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
//        modelAndView.addObject("borderDetail", borderDetail);
//        modelAndView.setViewName("platform/user/jinhuoxiangqing");
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
    public ModelAndView deliveryBorderDetils(HttpServletRequest request, Long id) throws Exception {
        BorderDetail borderDetail = new BorderDetail();
        String skuValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        PfBorder pfBorder = bOrderService.getPfBorderById(id);
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
    public ModelAndView deliveryBorder(HttpServletRequest request, Integer orderStatus, Integer shipStatus) throws Exception {
        ComUser comUser = getComUser(request);
        List<PfBorder> pfBorders = bOrderService.findByUserPid(comUser.getId(), orderStatus, shipStatus);
        List<PfBorder> pfBorders0 =new ArrayList<>();
        List<PfBorder> pfBorders10 = new ArrayList<>();//代发货
        List<PfBorder> pfBorders15 = new ArrayList<>();//待收货
        List<PfBorder> pfBorders3 =  new ArrayList<>();//已完成
        List<PfBorder> pfBorders6 = new ArrayList<>();//排单中
        for (PfBorder pfBord:pfBorders) {
            if(pfBord.getOrderStatus()==0){
                pfBorders0.add(pfBord);//待付款
            }else if (pfBord.getOrderStatus()==1 && pfBord.getShipStatus()==0){
                pfBorders10.add(pfBord);//代发货
            }else if (pfBord.getOrderStatus()==1 && pfBord.getShipStatus()==5){
                pfBorders15.add(pfBord);//待收货
            }else if (pfBord.getOrderStatus()==3) {
                pfBorders3.add(pfBord);//已完成
            }else if (pfBord.getOrderStatus()==6) {
                pfBorders6.add(pfBord);//排单中
            }
        }
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
        modelAndView.addObject("pfBorders", pfBorderss);
        modelAndView.setViewName("platform/order/chuhuodingdan");
        return modelAndView;
    }

    @RequestMapping("/payReplenishmentOrder.shtml")
    public ModelAndView payReplenishmentOrder() throws Exception {
        ModelAndView mv = new ModelAndView();
        try {

        } catch (Exception ex) {

        }
        return mv;
    }
}
