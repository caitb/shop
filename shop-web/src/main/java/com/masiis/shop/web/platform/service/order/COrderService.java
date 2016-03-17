package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.platform.order.PfCorderMapper;
import com.masiis.shop.dao.platform.order.PfCorderOperationLogMapper;
import com.masiis.shop.dao.platform.order.PfCorderPaymentMapper;
import com.masiis.shop.dao.platform.product.PfSkuStatisticMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
import com.masiis.shop.web.platform.service.user.UserService;
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

    /**
     * 试用申请
     *
     * @author hanzengzhi
     * @date 2016/3/5 15:14
     */
    public void trialApplyService(ComUser comUser, PfUserTrial pfUserTrial) {
        try {
            //插入试用表
            //userService.insertUserTrial(pfUserTrial);
            //更新试用用户信息
            // userService.updateComUser(comUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
    public PfCorder isApplyTrial(Long userId, Integer skuId) {
        try {
            PfCorder pfCorder = new PfCorder();
            pfCorder.setUserId(userId);
            pfCorder.setSkuId(skuId);
            pfCorder.setOrderType(0);
            pfCorder = pfCorderService.trialCorder(pfCorder);
            return pfCorder;
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
     * 合伙人订单支付
     *
     * @author ZhaoLiang
     * @date 2016/3/16 10:04
     * 操作详情 <1>修改订单支付信息<2>修改订单数据<3>添加订单日志<4>修改sku试用人数
     */
    @Transactional
    public void payCOrder(PfCorderPayment pfCorderPayment, String outOrderId) throws Exception {
        //<1>修改订单支付信息
        pfCorderPayment.setOutOrderId(outOrderId);
        pfCorderPayment.setIsEnabled(1);//设置为有效
        pfCorderPaymentMapper.updateById(pfCorderPayment);
        BigDecimal payAmount = pfCorderPayment.getAmount();
        Long cOrderId = pfCorderPayment.getPfCorderId();
        //<2>修改订单数据
        PfCorder pfCorder = pfCorderMapper.selectById(cOrderId);
        pfCorder.setReceivableAmount(pfCorder.getReceivableAmount().subtract(payAmount));
        pfCorder.setPayAmount(pfCorder.getPayAmount().add(payAmount));
        pfCorder.setPayTime(new Date());
        pfCorder.setPayStatus(1);//已付款
        pfCorder.setOrderStatus(1);//已付款
        pfCorderMapper.updateById(pfCorder);
        //<3>添加订单日志
        PfCorderOperationLog pfCorderOperationLog = new PfCorderOperationLog();
        pfCorderOperationLog.setCreateMan(pfCorder.getUserId());
        pfCorderOperationLog.setCreateTime(new Date());
        pfCorderOperationLog.setPfCorderStatus(1);
        pfCorderOperationLog.setPfCorderId(cOrderId);
        pfCorderOperationLog.setRemark("订单已支付");
        pfCorderOperationLogMapper.insert(pfCorderOperationLog);
        //<4>修改sku试用人数
        PfSkuStatistic pfSkuStatistic = pfSkuStatisticMapper.selectBySkuId(pfCorder.getSkuId());
        pfSkuStatistic.setAgentNum(pfSkuStatistic.getAgentNum() + 1);
        pfSkuStatisticMapper.updateById(pfSkuStatistic);
    }

}
