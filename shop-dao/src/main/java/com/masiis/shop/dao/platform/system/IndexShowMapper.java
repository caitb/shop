package com.masiis.shop.dao.platform.system;

import com.masiis.shop.dao.beans.system.IndexComSku;
import com.masiis.shop.dao.po.PbBanner;

import java.util.List;

/**
 * Created by muchaofeng on 2016/3/3.
 */
public interface IndexShowMapper {
    /**
     * 查首页所有轮播图片
     * @return
     */
    List<PbBanner> selectAllPbBanner();

    /**
     * 查首页所有展示商品信息
     * @return
     */
    List<IndexComSku> selectAllIndexComSku(Long userId);

    /**
     * 查询该人能展示的测试商品
     *
     * @param userId
     * @return
     */
    List<IndexComSku> selectTestListComSku(Long userId);
}
