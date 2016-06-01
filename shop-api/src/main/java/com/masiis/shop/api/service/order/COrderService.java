package com.masiis.shop.api.service.order;

import com.masiis.shop.api.service.product.ProductService;
import com.masiis.shop.api.service.user.UserAddressService;
import com.masiis.shop.api.utils.SysSignUtils;
import com.masiis.shop.common.beans.wxpay.WxPaySysParamReq;
import com.masiis.shop.common.constant.wx.WxConsAPP;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.platform.order.PfCorderMapper;
import com.masiis.shop.dao.platform.order.PfCorderOperationLogMapper;
import com.masiis.shop.dao.platform.order.PfCorderPaymentMapper;
import com.masiis.shop.dao.platform.product.PfSkuStatisticMapper;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Service
@Transactional
public class COrderService {

    @Resource
    private UserAddressService userAddressService;
    @Resource
    private PfCorderMapper pfCorderMapper;
    @Resource
    private ProductService productService;
    @Resource
    private PfCorderService pfCorderService;
    @Resource
    private PfCorderPaymentMapper pfCorderPaymentMapper;
    @Resource
    private PfCorderOperationLogMapper pfCorderOperationLogMapper;
    @Resource
    private PfSkuStatisticMapper pfSkuStatisticMapper;
    @Resource
    private PfUserTrialService trialService;
    @Resource
    private PfCorderConsigneeService pfCorderConsigneeService;

    private Logger log = Logger.getLogger(this.getClass());

    List<PfCorder> existTrilPfCorder = null;

    public ComUserAddress getOrderAddress(Long selectedAddressId, Long userId){
       return userAddressService.getOrderAddress( selectedAddressId, userId);
    }
    /**
     * 根据订单id获取订单详情
     * @author hanzengzhi
     * @date 2016/5/25 15:46
     */
    public Map<String, Object> getOrderDetail(Long corderId){
        Map<String, Object> pfCorderMap = new HashMap<String, Object>();
        PfCorder pfCorder = queryPfCorderById(corderId);
        PfCorderConsignee corderConsignee = null;
        Product product = null;
        if (pfCorder != null){
            corderConsignee = pfCorderConsigneeService.getOrdConByOrdId(corderId);
            product = getProductDetail(pfCorder.getSkuId());
            pfCorderMap.put("corderConsignee",corderConsignee);
            pfCorderMap.put("product",product);
        }
        pfCorderMap.put("pfCorder",pfCorder);
        return pfCorderMap;
    }

    /**
     * 确认订单
     * @author hanzengzhi
     * @date 2016/3/8 14:55
     */
    public Map<String, Object> confirmOrder(HttpServletRequest request, Integer skuId, Long userId, Long selectedAddressId) {
        Map<String, Object> pfCorderMap = new HashMap<String, Object>();
        ComUserAddress comUserAddress = userAddressService.getOrderAddress( selectedAddressId, userId);
        Product product = getProductDetail(skuId);
        pfCorderMap.put("comUserAddress", comUserAddress);
        pfCorderMap.put("product", product);
        return pfCorderMap;
    }
    /**
     * 试用订单支付
     * @author  hanzengzhi
     * @date  2016/3/22 23:59
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public WxPaySysParamReq trialApplyPay (HttpServletRequest request,Long userId,ComUser comUser,Integer skuId, Long addressId,String reason) {
        try{
            PfCorder  pfCorder = null;
            WxPaySysParamReq wpspr = null;
            //判断订单是否存在
            if (isExistNotPayTrialOrder(userId, skuId)) {
                log.info("试用申请---存在未支付的订单获得未支付的订单---start");
                //获得未支付的订单
                pfCorder = getNoPayTrialOrder().get(0);
                if (!reason.equals(pfCorder.getUserMassage())){
                    //试用理由有变化更新订单表
                    log.info("试用申请---更新试用理由---start");
                    pfCorder.setUserMassage(reason);
                    int i = updatePfCorderUserMessage(pfCorder);
                    log.info("试用申请---更新试用理由---end---更新条数---i-----"+i);
                }
                log.info("试用申请---成功获得未支付的订单---end");
            } else {
                //生成订单
                log.info("试用申请---不存在未支付的订单创建新的订单---start");
                pfCorder = generateOrder(skuId, userId, reason, comUser, addressId);
                log.info("试用申请---不存在未支付的订单创建新的订单---end");
            }
            //调用微信支付
            log.info("试用申请-----组织调用微信支付的数据--start");
            wpspr = toTrialOrderPay(wpspr, pfCorder, request, addressId);
            log.info("试用申请-----组织调用微信支付的数据--end");
            return wpspr;
        }catch (Exception e){
            throw new BusinessException(e);
        }
    }
    /**
     * 更新用户留言
     * @author hanzengzhi
     * @date 2016/3/24 12:37
     */
    private int updatePfCorderUserMessage(PfCorder pfCorder){
        return pfCorderService.updateUserMessageById(pfCorder);
    }
    /**
     * 判断是否存在未支付的试用订单
     *
     * @author hanzengzhi
     * @date 2016/3/21 16:21
     */
    public Boolean isExistNotPayTrialOrder(Long userId, Integer skuId) {
        existTrilPfCorder = pfCorderService.queryTrialNoPayOrder(userId, skuId);
        if (existTrilPfCorder == null || existTrilPfCorder.size() == 0) {
            return false;
        }
        return true;
    }
    /**
     * 获得未支付的订单
     *
     * @author hanzengzhi
     * @date 2016/3/21 16:24
     */
    public List<PfCorder> getNoPayTrialOrder() {
        if (existTrilPfCorder == null || existTrilPfCorder.size() == 0 || existTrilPfCorder.size() > 1) {
            log.info("用户同一件试用商品的未支付订单超过一个,逻辑出错");
            throw new BusinessException("用户同一件试用商品的未支付订单超过一个,逻辑出错");
        } else {
            log.info("试用申请service---成功获得存在未支付的订单");
            return existTrilPfCorder;
        }
    }

    /**
     * 生成订单
     * @author hanzengzhi
     * @date 2016/3/21 15:41
     */
    private PfCorder generateOrder(Integer skuId,Long userId,String reason,ComUser comUser,Long addressId)throws BusinessException{
        PfCorder pfCorder = null;
        try{
            //获得产品信息
            log.info("试用申请-----获得商品详情信息---start ---商品skuId---"+skuId);
            Product product = getProductDetail(skuId);
            log.info("试用申请-----获得商品详情信息---end ---商品skuId---"+skuId);
            //订单
            pfCorder = initPfCorderParamData(userId, skuId, product, reason);
            //订单日志
            PfCorderOperationLog pfCorderOperationLog = initPfCorderOperationLog(comUser);
            //收获地址
            log.info("试用申请-----获得获得收获地址---start---地址id---"+addressId);
            ComUserAddress comUserAddress = userAddressService.getUserAddressById(addressId);
            log.info("试用申请-----获得获得收获地址---end---地址id---"+addressId);
            //生成订单
            log.info("试用申请------生成新的订单----start");
            PfCorderConsignee pfCorderConsignee = initPfCorderConsigneeParamData(null, comUserAddress);
            Long orderId = trialApplyGenerateOrderService(pfCorder, pfCorderOperationLog, comUserAddress, pfCorderConsignee);
            log.info("试用申请------生成新的订单----end");
        }catch (Exception e){
            throw new BusinessException("试用申请---生成新订单失败---"+e);
        }

        return pfCorder;
    }

    /**
     * 试用支付下订单
     * @author hanzengzhi
     * @date 2016/3/17 16:10
     */
    @Transactional(propagation =Propagation.REQUIRED,readOnly = false)
    public Long trialApplyGenerateOrderService(PfCorder pc, PfCorderOperationLog pcol, ComUserAddress cua, PfCorderConsignee pcc) throws BusinessException {
        try {
            //插入订单和订单日志
            log.info("试用申请service------插入订单---start");
            Long orderId = trialService.insert(pc, pcol);
            log.info("试用申请service------插入订单---end---订单id----" + orderId);
            pcc.setPfCorderId(orderId);
            //收获地址
            log.info("试用申请service------插入收获地址---start");
            pfCorderConsigneeService.insertPfCC(pcc);
            log.info("试用申请service------插入收获地址---end");
            return orderId;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    /**
     * 获得商品信息
     * @author hanzengzhi
     * @date 2016/3/17 14:40
     */
    public Product getProductDetail(Integer skuId) {
        //获得商品信息
        Product product = productService.applyTrialToPageService(skuId);
        return product;
    }

    /**
     * 初始化订单参数数据
     *
     * @author hanzengzhi
     * @date 2016/3/15 10:47
     */
    private PfCorder initPfCorderParamData(Long userId, Integer skuId, Product product, String reason) {
        //生成试用订单
        PfCorder pfCorder = new PfCorder();
        pfCorder.setCreateTime(new Date());
        pfCorder.setOrderCode(OrderMakeUtils.makeOrder("C"));
        pfCorder.setOrderType(0);
        pfCorder.setOrderStatus(0);//未付款
        pfCorder.setShipStatus(0);//未发货
        pfCorder.setSkuId(skuId);
        pfCorder.setUserId(userId);
        pfCorder.setProductAmount(new BigDecimal(0));
        pfCorder.setShipAmount(product.getShipAmount());
        pfCorder.setOrderAmount(pfCorder.getProductAmount().add(pfCorder.getShipAmount()));
        pfCorder.setReceivableAmount(pfCorder.getOrderAmount());
        pfCorder.setPayAmount(new BigDecimal(0));
        pfCorder.setPayStatus(0);//未支付
        pfCorder.setUserMassage(reason);
        pfCorder.setSupplierId(0);
        pfCorder.setCreateMan(userId);
        return pfCorder;
    }

    /**
     * 初始化pfcorderOperation日志表
     *
     * @author hanzengzhi
     * @date 2016/3/15 10:38
     */
    private PfCorderOperationLog initPfCorderOperationLog(ComUser comUser) {
        //生成试用日志
        PfCorderOperationLog pcol = new PfCorderOperationLog();
        pcol.setCreateTime(new Date());
        pcol.setCreateMan(comUser.getId());
        pcol.setPfCorderStatus(0);
        return pcol;
    }

    /**
     * 订单收获人信息
     *
     * @author hanzengzhi
     * @date 2016/3/17 13:45
     */
    private PfCorderConsignee initPfCorderConsigneeParamData(Long cOrderId, ComUserAddress comUserAddress) throws BusinessException {
        PfCorderConsignee pfCorderConsignee = new PfCorderConsignee();
        try {
            if (comUserAddress != null) {
                pfCorderConsignee.setPfCorderId(cOrderId);
                pfCorderConsignee.setCreateTime(new Date());
                pfCorderConsignee.setUserId(comUserAddress.getUserId());
                pfCorderConsignee.setConsignee(comUserAddress.getName());
                pfCorderConsignee.setMobile(comUserAddress.getMobile());
                pfCorderConsignee.setProvinceId(comUserAddress.getProvinceId());
                pfCorderConsignee.setProvinceName(comUserAddress.getProvinceName());
                pfCorderConsignee.setCityId(comUserAddress.getCityId());
                pfCorderConsignee.setCityName(comUserAddress.getCityName());
                pfCorderConsignee.setRegionId(comUserAddress.getRegionId());
                pfCorderConsignee.setRegionName(comUserAddress.getRegionName());
                pfCorderConsignee.setAddress(comUserAddress.getAddress());
                pfCorderConsignee.setZip(comUserAddress.getZip());
            }
        } catch (Exception e) {
            throw new BusinessException("生成订单获取用户地址错误----"+e.getMessage());
        }
        return pfCorderConsignee;
    }

    /**
     * 调用微信支付
     *
     * @author hanzengzhi
     * @date 2016/3/17 16:34
     */
    private WxPaySysParamReq toTrialOrderPay(WxPaySysParamReq wpspr, PfCorder pfCorder, HttpServletRequest request, Long addressId) {
        wpspr = new WxPaySysParamReq();
        wpspr.setOrderId(pfCorder.getOrderCode());
        wpspr.setSignType("MD5");
        wpspr.setNonceStr(SysSignUtils.createGenerateStr());
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        wpspr.setSuccessUrl(basePath + "corder/weChatCallBackSuccess.shtml?pfCorderId="+pfCorder.getId());
        wpspr.setErrorUrl(basePath + "corder/weChatCallBackFail.shtml?pfCorderId="+pfCorder.getId());
        wpspr.setSign(SysSignUtils.toSignString(wpspr, WxConsAPP.WX_PAY_SIGNKEY));
        return wpspr;
    }

    /**
     * 合伙人订单支付
     * @author ZhaoLiang
     * @date 2016/3/16 10:04
     * 操作详情
     * <1>修改订单支付信息
     * <2>修改订单数据
     * <3>添加订单日志
     * <4>修改sku试用人数
     */
    @Transactional
    public void payCOrder(PfCorderPayment pfCorderPayment, String outOrderId) throws BusinessException {
        log.info("试用申请--微信支付成功--回调---start");
        try {
            //<1>修改订单支付信息
            log.info("修改订单的支付状态pfcorderPayment---start-");
            pfCorderPayment.setOutOrderId(outOrderId);
            pfCorderPayment.setIsEnabled(1);//设置为有效
            pfCorderPaymentMapper.updateById(pfCorderPayment);
            log.info("修改订单的支付状态pfcorderPayment---end-");
            BigDecimal payAmount = pfCorderPayment.getAmount();
            Long cOrderId = pfCorderPayment.getPfCorderId();
            //<2>修改订单数据
            log.info("修改订单表的金额和状态pfcorder---start-");
            PfCorder pfCorder = pfCorderMapper.selectById(cOrderId);
            if (payAmount != null && !payAmount.equals(new BigDecimal(0))) {
                pfCorder.setReceivableAmount(pfCorder.getReceivableAmount().subtract(payAmount));
                pfCorder.setPayAmount(pfCorder.getPayAmount().add(payAmount));
            }
            pfCorder.setPayTime(pfCorderPayment.getCreateTime());
            pfCorder.setPayStatus(1);//已付款
            pfCorder.setOrderStatus(1);//已付款
            pfCorderMapper.updateById(pfCorder);
            log.info("修改订单表的金额和状态pfcorder---end--");
            //<3>添加订单日志
            log.info("添加订单表的log信息 pfcorderOperationLog---start--");
            PfCorderOperationLog pfCorderOperationLog = new PfCorderOperationLog();
            pfCorderOperationLog.setCreateMan(pfCorder.getUserId());
            pfCorderOperationLog.setCreateTime(new Date());
            pfCorderOperationLog.setPfCorderStatus(1);
            pfCorderOperationLog.setPfCorderId(cOrderId);
            pfCorderOperationLog.setRemark("订单已支付");
            pfCorderOperationLogMapper.insert(pfCorderOperationLog);
            log.info("添加订单表的log信息 pfcorderOperationLog---end--");
            //<4>修改sku试用人数
            log.info("修改sku试用人数 pfskustatistic---start--");
            PfSkuStatistic pfSkuStatistic = pfSkuStatisticMapper.selectBySkuId(pfCorder.getSkuId());
            pfSkuStatistic.setTrialNum(pfSkuStatistic.getTrialNum()+1);
            pfSkuStatisticMapper.updateById(pfSkuStatistic);
            log.info("修改sku试用人数 pfskustatistic---end--");
        } catch (Exception e) {
            log.info("试用申请--微信支付成功--回调失败---" + e.getMessage());
            throw new BusinessException("试用订单微信支付回调失败-----" + e.getMessage());
        }
        log.info("试用申请--微信支付成功--回调---end");
    }
    /**
     * 判断用户是否使用过商品
     *
     * @author hanzengzhi
     * @date 2016/3/9 11:39
     */
    public List<PfCorder> isApplyTrial(Long userId, Integer skuId) {
        List<PfCorder> pfCorders = null;
        try {
            PfCorder pfCorder = new PfCorder();
            pfCorder.setUserId(userId);
            pfCorder.setSkuId(skuId);
            pfCorder.setOrderType(0);
            pfCorders = pfCorderService.trialCorder(pfCorder);
            return pfCorders;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
    /**
     * 根据订单编号查询试用订单
     *
     * @param orderCode
     * @return
     */
    public PfCorder findByOrderCode(String orderCode) {
        return pfCorderMapper.selectByOrderCode(orderCode);
    }
    /**
     * @param payment
     */
    public void addCOrderPayment(PfCorderPayment payment) {
        pfCorderPaymentMapper.insert(payment);
    }

    public PfCorderPayment findOrderPaymentBySerialNum(String paySerialNum) {
        return pfCorderPaymentMapper.selectBySerialNum(paySerialNum);
    }
    /**
     * 通过订单id查询订单信息
     * @author hanzengzhi
     * @date 2016/3/23 15:09
     */
    public PfCorder queryPfCorderById(Long id){
        log.info("------订单pfcorder----id-----"+id);
        PfCorder pfCorder = pfCorderService.getPfCorderById(id);
        return pfCorder;
    }
}
