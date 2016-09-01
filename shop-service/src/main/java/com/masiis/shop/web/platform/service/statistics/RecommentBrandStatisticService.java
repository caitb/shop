package com.masiis.shop.web.platform.service.statistics;

import com.masiis.shop.dao.beans.statistic.BrandStatistic;
import com.masiis.shop.dao.beans.statistic.RecommendBrandStatistic;
import com.masiis.shop.dao.platform.statistic.BrandStatisticMapper;
import com.masiis.shop.dao.platform.statistic.RecommentBrandStatisticMapper;
import com.masiis.shop.web.platform.service.shop.SfShopManQrCodeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * BrandStatisticService
 *
 * @author ZhaoLiang
 * @date 2016/8/25
 */
@Service
public class RecommentBrandStatisticService {
    private final static Log log = LogFactory.getLog(SfShopManQrCodeService.class);

    @Resource
    private RecommentBrandStatisticMapper recommentBrandStatisticMapper;

    /**
     * 查询品牌统计数据根据用户ID和品牌ID
     * @param userId
     * @param brandId
     * @return
     */
    public RecommendBrandStatistic selectRecommentBrandStatisticByUserIdAndBrandId(Long userId, Integer brandId) {
        return recommentBrandStatisticMapper.selectRecommentBrandStatisticByUserIdAndBrandId(userId, brandId);
    }

}
