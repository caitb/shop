package com.masiis.shop.dao.platform.statistic;

import com.masiis.shop.dao.beans.statistic.BrandStatistic;
import com.masiis.shop.dao.beans.statistic.RecommendBrandStatistic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * BrandStatisticMapper
 *
 * @author ZhaoLiang
 * @date 2016/8/25
 */
@Repository
public interface RecommentBrandStatisticMapper {

    /**
     * 查询用户推荐团队统计数据
     * @param userId
     * @param brandId
     * @return
     */
    RecommendBrandStatistic selectRecommentBrandStatisticByUserIdAndBrandId(@Param("userId") Long userId, @Param("brandId") Integer brandId);

}
