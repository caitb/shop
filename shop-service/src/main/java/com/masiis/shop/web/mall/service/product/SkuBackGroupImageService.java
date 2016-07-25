package com.masiis.shop.web.mall.service.product;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.shop.SfShopSkuMapper;
import com.masiis.shop.dao.platform.product.ComSkuExtensionMapper;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.po.ComSkuExtension;
import com.masiis.shop.dao.po.ComSkuImage;
import com.masiis.shop.dao.po.SfShopSku;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 店铺背景图片
 * @author muchaofeng
 * @date 2016/6/9 11:28
 */

@Service
public class SkuBackGroupImageService {

    @Resource
    private ComSkuExtensionMapper comSkuExtensionMapper;
    @Resource
    private SfShopSkuMapper sfShopSkuMapper;

    /**
     * 获取sku图片
     * @param skuId
     * @return
     */
    public ComSkuExtension backGroupImage(Integer skuId){
        String productImgValue = PropertiesUtils.getStringValue("index_product_prototype_url");
        ComSkuExtension comSkuExtension1= comSkuExtensionMapper.selectBySkuId(skuId);
        if (comSkuExtension1.getSkuBackgroundImg() != null) {
            comSkuExtension1.setSkuBackgroundImg(productImgValue + comSkuExtension1.getSkuBackgroundImg());
        }
        return comSkuExtension1;
    }


    /**
     * 获取小铺中商品的轮播图(sku)
     * @author jjh
     * @date 2016/4/10 14:37
     */
    public List<String> getSfShopSkuImgByShopId(Long shopId) throws Exception {
        List<SfShopSku> skuIds = sfShopSkuMapper.selectImgByShopId(shopId);
        List<String> imgString = new ArrayList<>();
        for (SfShopSku sfShopSku :skuIds){
            String str = backGroupImage(sfShopSku.getSkuId()).getSkuBackgroundImg();
            imgString.add(str);
        }
        return imgString;
    }

}
