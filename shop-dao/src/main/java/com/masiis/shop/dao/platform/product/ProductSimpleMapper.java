package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.beans.product.ProductSimple;

/**
 * ProductSimpleMapper
 *
 * @author ZhaoLiang
 * @date 2016/3/7
 */
public interface ProductSimpleMapper {

    ProductSimple selectBySkuId(Integer skuId);

}
