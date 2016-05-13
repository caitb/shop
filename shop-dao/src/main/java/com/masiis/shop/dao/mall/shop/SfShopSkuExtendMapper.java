package com.masiis.shop.dao.mall.shop;

import com.masiis.shop.dao.po.SfShopSku;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangbingjian on 2016/4/10.
 */
@Repository
public interface SfShopSkuExtendMapper {

    List<SfShopSku> selectShopviewByShopId(Long shopId);

    SfShopSku selectShopviewByShopIdAndSkuId(@Param("shopId") Long shopId,@Param("skuId") Integer skuId);

}
