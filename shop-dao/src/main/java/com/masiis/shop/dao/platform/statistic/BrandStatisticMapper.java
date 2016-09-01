package com.masiis.shop.dao.platform.statistic;

import com.masiis.shop.dao.beans.statistic.BrandStatistic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * BrandStatisticMapper
 *
 * @author ZhaoLiang
 * @date 2016/8/25
 */
@Repository
public interface BrandStatisticMapper {

    /**
     * 查询品牌统计数据根据用户ID
     *
     * @param userId
     * @return
     */
//    BrandStatistic selectBrandStatisticByUserId(@Param("userId") Long userId);


    /**
     * 查询品牌统计数据根据用户ID和品牌ID
     * @param userId
     * @param brandId
     * @return
     */
    BrandStatistic selectBrandStatisticByUserIdAndBrandId(@Param("userId") Long userId, @Param("brandId") Integer brandId);


    /**
     * 查询直接下级人数
     * @param userId
     * @param brandId
     * @return
     */
    Integer selectDownUserNumByUserIdAndBrandId(@Param("userId") Long userId, @Param("brandId") Integer brandId);
}
