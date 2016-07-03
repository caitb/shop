package com.masiis.shop.web.mall.service.shop;

import com.masiis.shop.dao.mall.shop.SfShopSkuExtendMapper;
import com.masiis.shop.dao.mall.shop.SfShopSkuMapper;
import com.masiis.shop.dao.po.SfShopSku;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangbingjian on 2016/4/10.
 */
@Service
public class SfShopSkuService {
    private final Logger logger = Logger.getLogger(SfShopSkuService.class);

    @Autowired
    private SfShopSkuExtendMapper sfShopSkuExtendMapper;
    @Resource
    private SfShopSkuMapper shopSkuMapper;

    /**
     * 根据shopId查询小铺商品及等级信息
     * @param shopId
     * @return
     */
    public List<SfShopSku> findShopSkuByShopId(Long shopId){
        return sfShopSkuExtendMapper.selectShopviewByShopId(shopId);
    }
    public SfShopSku findShopSkuByShopIdAndSkuId(Long shopId,Integer skuId){
        return sfShopSkuExtendMapper.selectShopviewByShopIdAndSkuId(shopId,skuId);
    }

    public SfShopSku selectByShopIdAndSkuId(Long shopId,Integer skuId){
        return shopSkuMapper.selectByShopIdAndSkuId(shopId,skuId);
    }
    public int update(SfShopSku shopSku){
        return shopSkuMapper.updateByPrimaryKey(shopSku);
    }
}
