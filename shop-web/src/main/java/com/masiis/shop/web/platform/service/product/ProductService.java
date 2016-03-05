package com.masiis.shop.web.platform.service.product;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.platform.product.ProductMapper;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.po.ComAgentLevel;
import com.masiis.shop.dao.po.ComSkuImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
      * @Author 贾晶豪
      * @Date 2016/3/5 0005 下午 2:30
      * 根据商品ID展示商品属性详情
      */
    public Product getSkuDetails(String skuId) throws Exception{
        Product product = productMapper.getSkuDetailsBySkuId(skuId);
        List<ComSkuImage> skuImgList = productMapper.getSkuImgById(skuId);
        String productImgValue = PropertiesUtils.getStringValue("index_product_200_200_url");
        if(skuImgList!=null && skuImgList.size()>0){
            for(ComSkuImage comSkuImage:skuImgList){
                comSkuImage.setFullImgUrl(productImgValue + comSkuImage.getImgUrl());
            }
            product.setComSkuImages(skuImgList);
        }
        return product;
    }
    /**
      * @Author 贾晶豪
      * @Date 2016/3/5 0005 下午 2:30
      * 代理商折扣，基础数据
      */
    public String getDiscountByAgentLevel() throws Exception {
        String discountLevel = null;
        List<ComAgentLevel> comAgentLevels = productMapper.agentLevelDiscount();
        if (comAgentLevels != null && comAgentLevels.size() > 0) {
            discountLevel = comAgentLevels.get(0).getDiscount() + "-" + comAgentLevels.get(comAgentLevels.size()-1).getDiscount();
        }
        return discountLevel;
    }
}
