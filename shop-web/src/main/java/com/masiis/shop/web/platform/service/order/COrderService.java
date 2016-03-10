package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.platform.order.PfCorderMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAddress;
import com.masiis.shop.dao.po.PfCorder;
import com.masiis.shop.dao.po.PfUserTrial;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
import com.masiis.shop.web.platform.service.user.UserService;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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

    /**
     * 试用申请
     * @author  hanzengzhi
     * @date  2016/3/5 15:14
     */
    public void trialApplyService(ComUser comUser,PfUserTrial pfUserTrial){
        try {
            //插入试用表
            userService.insertUserTrial(pfUserTrial);
            //更新试用用户信息
            userService.updateComUser(comUser);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 确认订单
     * @author  hanzengzhi
     * @date  2016/3/8 14:55
     */
    public Map<String,Object> confirmOrder(Long orderId, Long userId,Integer selectedAddressId){
        //获得用户的默认地址
        ComUserAddress comUserAddress = new ComUserAddress();
        comUserAddress.setUserId(userId);
        if (StringUtils.isEmpty(selectedAddressId)){
            //如果没有选中地址选择默认地址
            comUserAddress.setIsDefault(1);
        }else{
            //选中的地址
            comUserAddress.setId(selectedAddressId);
        }
        List<ComUserAddress> comuserAddressList =  userAddressService.queryComUserAddressesByParam(comUserAddress);
        Map<String,Object> pfCorderMap = new HashMap<String, Object>();
        pfCorderMap.put("address", comuserAddressList);
/*        //获得订单信息
        List<PfCorder> pfCorders = queryPfCorderById(orderId);
        //获得商品信息
        Product product = null;
        if (pfCorders!=null&&pfCorders.size()>0){
            product = productService.applyTrialToPageService(pfCorders.get(0).getSkuId());
        }else {

        }
        pfCorderMap.put("pfCorder",pfCorders);
        pfCorderMap.put("product",product);*/
        return pfCorderMap;
    }
    /**
     *
     * @author  hanzengzhi
     * @date  2016/3/8 15:45 
     */
    public List<PfCorder> queryPfCorderById(Long id){
        PfCorder pfCorder = new PfCorder();
        pfCorder.setId(id);
        return  pfCorderMapper.queryPfCorderByParam(pfCorder);
    }
    /**
     * 判断用户是否使用过商品
     * @author  hanzengzhi
     * @date  2016/3/9 11:39
     */
    public Boolean isApplyTrial(Long userId,Integer skuId){
        PfUserTrial pfUserTrial = new PfUserTrial();
        pfUserTrial.setUserId(userId);
        pfUserTrial.setSkuId(skuId);
        List<PfUserTrial> pfUserTrials = userService.isApplyTrial(pfUserTrial);
        if (pfUserTrials!=null&&pfUserTrials.size()>0){
            return true;
        }else {
            return false;
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
}
