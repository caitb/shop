package com.masiis.shop.web.platform.service.product;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.platform.product.ProductMapper;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.po.ComSkuImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 根据商品ID展示商品属性详情
     * @param skuId
     */
    public Product getSkuDetails(String skuId) throws Exception{
        Product product = productMapper.getSkuDetailsBySkuId(skuId);
        List<ComSkuImage> skuImgList = productMapper.getSkuImgById(skuId);
        String productImgValue = PropertiesUtils.getStringValue("index_banner_url");
        if(skuImgList!=null && skuImgList.size()>0){
            for(ComSkuImage comSkuImage:skuImgList){
                comSkuImage.setFullImgUrl(productImgValue + comSkuImage.getImgUrl());
            }
            product.setComSkuImages(skuImgList);
        }
        return product;
    }
}
