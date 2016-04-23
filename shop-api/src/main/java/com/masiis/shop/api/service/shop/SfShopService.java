package com.masiis.shop.api.service.shop;

import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.po.SfShop;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cai_tb on 16/4/8.
 */
@Service
public class SfShopService {

    @Resource
    private SfShopMapper sfShopMapper;

    /**
     * 添加个人小铺
     * @author ZhaoLiang
     * @date 2016/4/11 11:25
     */
    public void AddSfShop(SfShop sfShop) {
        sfShopMapper.insert(sfShop);
    }

    /**
     * 根据小铺归属人查询小铺信息
     * @param userId
     * @return
     */
    public SfShop getSfShopByUserId(Long userId){
        return sfShopMapper.selectByUserId(userId);
    }
}
