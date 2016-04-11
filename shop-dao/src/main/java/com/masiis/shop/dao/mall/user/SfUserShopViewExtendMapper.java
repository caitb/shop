/**
 * wangbingjian
 */
package com.masiis.shop.dao.mall.user;


import com.masiis.shop.dao.po.SfUserShopView;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SfUserShopViewExtendMapper {
    List<SfUserShopView> selectViewedShopByUserId(Long userId);
}