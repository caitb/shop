package com.masiis.shop.api.service.user;

import com.masiis.shop.dao.platform.product.ComSkuExtensionMapper;
import com.masiis.shop.dao.po.ComSkuExtension;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cai_tb on 16/5/23.
 */
@Service
public class SkuExtensionService {

    @Resource
    private ComSkuExtensionMapper comSkuExtensionMapper;

    public ComSkuExtension getBySkuId(Integer skuId){
        return comSkuExtensionMapper.selectBySkuId(skuId);
    }
}
