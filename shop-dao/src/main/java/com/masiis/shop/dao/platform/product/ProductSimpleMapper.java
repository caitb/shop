package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.beans.product.ProductSimple;
import org.springframework.stereotype.Repository;

/**
 * ProductSimpleMapper
 *
 * @author ZhaoLiang
 * @date 2016/3/7
 */
@Repository
public interface ProductSimpleMapper {

    ProductSimple selectBySkuId(Integer skuId);

}
