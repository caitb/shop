/**
 * wangbingjian
 */
package com.masiis.shop.dao.mall.user;


import com.masiis.shop.dao.po.SfUserShopView;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SfUserShopViewExtendMapper {
    List<SfUserShopView> selectViewedShopByUserId(@Param(value = "userId") Long userId);

    Integer selectCountByUserId(@Param(value = "userId") Long userId);
}