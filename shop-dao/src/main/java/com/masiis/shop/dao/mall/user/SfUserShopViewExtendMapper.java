/**
 * wangbingjian
 */
package com.masiis.shop.dao.mall.user;


import com.masiis.shop.dao.po.SfUserShopView;

import java.util.List;

public interface SfUserShopViewExtendMapper {
    List<SfUserShopView> selectViewedShopByUserId(Long userId);
}