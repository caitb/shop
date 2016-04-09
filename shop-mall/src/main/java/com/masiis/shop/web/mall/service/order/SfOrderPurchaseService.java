package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.dao.po.ComUserAddress;
import com.masiis.shop.web.mall.service.user.UserAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by hzz on 2016/4/8.
 */
@Service
public class SfOrderPurchaseService {

    @Resource
    private UserAddressService userAddressService;

    /**
     * 获得确认订单界面，地址信息和商品信息
     * @author hanzengzhi
     * @date 2016/4/8 20:55
     */
    public Map<String,Object> getConfirmOrderInfoService(Long userId,Long selectedAddressId){
        //获得用户地址地址信息
        ComUserAddress comUserAddress = getUserAddressByUserId(userId,selectedAddressId);
        //获得购物车中的商品信息

        return null;
    }

    /**
     * 获得
     * @author hanzengzhi
     * @date 2016/4/8 21:00
     */
    private ComUserAddress getUserAddressByUserId(Long userId,Long selectedAddressId){
        return userAddressService.getOrderAddress(selectedAddressId, userId);
    }

}
