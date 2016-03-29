package com.masiis.shop.dao.platform.product;


import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.po.ComSkuImage;

import java.util.List;
import java.util.Map;

/**
 * Created by JingHao on 2016/3/2.
 */
public interface ProductMapper {

    Product getSkuDetailsBySkuId(String skuId);

    List<ComSkuImage> getSkuImgById(String skuId);

    Integer maxDiscount();

    List<Product> getProductsByUser(Integer userId);

    void updateStock(Map<String, Object> paramsMap);

    Product getProductStock(Integer id);

}
