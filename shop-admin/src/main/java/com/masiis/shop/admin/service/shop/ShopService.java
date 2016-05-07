package com.masiis.shop.admin.service.shop;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.shop.Shop;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/4/18.
 */
@Service
public class ShopService {

    @Resource
    private SfShopMapper sfShopMapper;
    @Resource
    private ComUserMapper comUserMapper;

    /**
     * 条件查询小铺
     * @param pageNumber
     * @param pageSize
     * @param conditionMap  查询条件
     * @return
     */
    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, Map<String, Object> conditionMap){
        PageHelper.startPage(pageNumber, pageSize, "create_time desc");
        List<SfShop> sfShops = sfShopMapper.selectByMap(conditionMap);
        PageInfo<SfShop> pageInfo = new PageInfo<>(sfShops);

        List<Shop> shops = new ArrayList<>();
        for(SfShop sfShop : sfShops){
            ComUser comUser = comUserMapper.selectByPrimaryKey(sfShop.getUserId());

            Shop shop = new Shop();
            shop.setComUser(comUser);
            shop.setSfShop(sfShop);

            shops.add(shop);
        }

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", shops);

        return pageMap;
    }

    /**
     * 店铺详情
     * @param shopId
     * @return
     */
    public Shop shopDetail(Long shopId){
        SfShop sfShop = sfShopMapper.selectByPrimaryKey(shopId);
        ComUser comUser = comUserMapper.selectByPrimaryKey(sfShop.getUserId());

        Shop shop = new Shop();
        shop.setSfShop(sfShop);
        shop.setComUser(comUser);

        return shop;
    }
}
