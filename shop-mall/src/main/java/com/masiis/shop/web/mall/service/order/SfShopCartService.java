package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.dao.mall.shop.SfShopCartMapper;
import com.masiis.shop.dao.po.SfShopCart;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hzz on 2016/4/9.
 */
@Service
public class SfShopCartService {

    @Resource
    private SfShopCartMapper sfShopCartMapper;

    /**
     * 获得用户购物车中的信息
     * @author hanzengzhi
     * @date 2016/4/9 10:38
     */
    public List<SfShopCart> getShopCartInfoByUserIdAndShopId(Long userId,Long shopId){
        return sfShopCartMapper.getShopCartInfoByUserIdAndShopId(userId,shopId);
    }

}
