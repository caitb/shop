package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.platform.order.PfCorderMapper;
import com.masiis.shop.dao.platform.order.PfCorderOperationLogMapper;
import com.masiis.shop.dao.platform.order.PfCorderPaymentMapper;
import com.masiis.shop.dao.platform.product.PfSkuStatisticMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
import com.masiis.shop.web.platform.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
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
    private UserService userService;
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
    /**
     * 确认订单
     *
     * @author hanzengzhi
     * @date 2016/3/8 14:55
     */
    public Map<String, Object> confirmOrder(HttpServletRequest request, Integer skuId, Long userId, Long selectedAddressId) {
        Map<String, Object> pfCorderMap = new HashMap<String, Object>();
        ComUserAddress comUserAddress = userAddressService.getOrderAddress(request, selectedAddressId, userId);
        Product product = getProductDetail(skuId);
        pfCorderMap.put("comUserAddress", comUserAddress);
        pfCorderMap.put("product", product);
        return pfCorderMap;
    }
    /**
     * 获得商品信息
     * @author hanzengzhi
     * @date 2016/3/17 14:40
     */
    public Product getProductDetail(Integer skuId) {
        //获得商品信息
         Product  product = productService.applyTrialToPageService(skuId);
        return product;
    }
    /**
     * 试用支付下订单
     * @author hanzengzhi
     * @date 2016/3/17 16:10
     */
    @Transactional
    public Long trialApplyGenerateOrderService(PfCorder pc ,PfCorderOperationLog pcol ,ComUserAddress cua ,PfCorderConsignee pcc )throws Exception{
        try{
            //插入订单和订单日志
            log.info("试用申请service------插入订单---start");
            Long orderId = trialService.insert(pc,pcol);
            log.info("试用申请service------插入订单---end---订单id----"+orderId);
            pcc.setPfCorderId(orderId);
            //收获地址
            log.info("试用申请service------插入收获地址---start");
            pfCorderConsigneeService.insertPfCC(pcc);
            log.info("试用申请service------插入收获地址---end");
            return orderId;
        }catch (Exception e){
            throw  new BusinessException(e.getMessage());
        }
    }

    /**
     * @author hanzengzhi
     * @date 2016/3/8 15:45
     */
    public PfCorder queryPfCorderById(Long id) {
        return pfCorderMapper.selectById(id);
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
     * 判断是否存在未支付的试用订单
     * @author hanzengzhi
     * @date 2016/3/21 16:21
     */
    public Boolean isExistNotPayTrialOrder(Long userId,Integer skuId){
        existTrilPfCorder = pfCorderService.queryTrialNoPayOrder(userId,skuId);
        if (existTrilPfCorder==null||existTrilPfCorder.size()==0){
            return false;
        }
        return true;
    }
    /**
     * 获得未支付的订单
     * @author hanzengzhi
     * @date 2016/3/21 16:24
     */
    public List<PfCorder> getNoPayTrialOrder(){
        if (existTrilPfCorder==null||existTrilPfCorder.size()==0||existTrilPfCorder.size()>1){
            log.info("用户同一件试用商品的未支付订单超过一个,逻辑出错");
            throw new BusinessException("用户同一件试用商品的未支付订单超过一个,逻辑出错");
        }else {
            log.info("试用申请service---成功获得存在未支付的订单");
            return existTrilPfCorder;
        }
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
     * 合伙人订单支付
     *
     * @author ZhaoLiang
     * @date 2016/3/16 10:04
     * 操作详情 <1>修改订单支付信息<2>修改订单数据<3>添加订单日志<4>修改sku试用人数
     */
    @Transactional
    public void payCOrder(PfCorderPayment pfCorderPayment, String outOrderId) throws Exception {
        log.info("试用申请--微信支付成功--回调---start");
        try{
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
            if (payAmount!=null&&!payAmount.equals(new BigDecimal(0))){
                pfCorder.setReceivableAmount(pfCorder.getReceivableAmount().subtract(payAmount));
                pfCorder.setPayAmount(pfCorder.getPayAmount().add(payAmount));
            }
            pfCorder.setPayTime(new Date());
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
            pfSkuStatistic.setAgentNum(pfSkuStatistic.getAgentNum() + 1);
            pfSkuStatisticMapper.updateById(pfSkuStatistic);
            log.info("修改sku试用人数 pfskustatistic---end--");
        }catch (Exception e){
            log.info("试用申请--微信支付成功--回调失败---"+e.getMessage());
            throw new BusinessException("试用订单微信支付回调失败-----"+e.getMessage());
        }
        log.info("试用申请--微信支付成功--回调---end");
    }

    /**
     *
     *
     * @param payment
     */
    public void addCOrderPayment(PfCorderPayment payment) {
        pfCorderPaymentMapper.insert(payment);
    }

    public PfCorderPayment findOrderPaymentBySerialNum(String paySerialNum) {
        return pfCorderPaymentMapper.selectBySerialNum(paySerialNum);
    }
}
