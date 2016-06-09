package com.masiis.shop.web.mall.service.product;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.platform.product.ComSkuExtensionMapper;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.po.ComSkuExtension;
import com.masiis.shop.dao.po.ComSkuImage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 店铺背景图片
 * @author muchaofeng
 * @date 2016/6/9 11:28
 */

@Service
public class SkuBackGroupImageService {

    @Resource
    private ComSkuExtensionMapper comSkuExtensionMapper;

    /**
     * 获取sku图片
     * @param skuId
     * @return
     */
    public ComSkuExtension backGroupImage(Integer skuId){
        String productImgValue = PropertiesUtils.getStringValue("index_product_background_url");
        ComSkuExtension comSkuExtension1= comSkuExtensionMapper.selectBySkuId(skuId);
        if (comSkuExtension1.getSkuBackgroundImg() != null) {
            comSkuExtension1.setSkuBackgroundImg(productImgValue + comSkuExtension1.getSkuBackgroundImg());
        }
        return comSkuExtension1;
    }
}
