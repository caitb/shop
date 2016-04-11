package com.masiis.shop.web.platform.service.shop;

import com.masiis.shop.dao.mall.shop.SfShopSkuExtendMapper;
import com.masiis.shop.dao.mall.shop.SfShopSkuMapper;
import com.masiis.shop.dao.po.SfShopSku;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangbingjian on 2016/4/10.
 */
@Service
public class SfShopSkuService {
    private final Logger logger = Logger.getLogger(SfShopSkuService.class);

    @Autowired
    private SfShopSkuMapper sfShopSkuMapper;

    /**
     * 添加小铺商品信息
     * @author ZhaoLiang
     * @date 2016/4/11 11:26
     */
    public void AddSfShopSku(SfShopSku sfShopSku) {
        sfShopSkuMapper.insert(sfShopSku);
    }
}
