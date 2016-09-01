package com.masiis.shop.web.platform.service.statistics;

import com.masiis.shop.dao.beans.statistic.BrandStatistic;
import com.masiis.shop.dao.platform.statistic.BrandStatisticMapper;
import com.masiis.shop.web.platform.service.shop.SfShopManQrCodeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * BrandStatisticService
 *
 * @author ZhaoLiang
 * @date 2016/8/25
 */
@Service
public class BrandStatisticService {
    private final static Log log = LogFactory.getLog(SfShopManQrCodeService.class);

    @Resource
    private BrandStatisticMapper brandStatisticMapper;

    /**
     * 查询品牌统计数据根据用户ID
     *
     * @param userId
     * @return
     */
//    public BrandStatistic selectBrandStatisticByUserId(Long userId) {
//        return brandStatisticMapper.selectBrandStatisticByUserId(userId);
//    }

    /**
     * 查询品牌统计数据根据用户ID和品牌ID
     *
     * @param userId
     * @param brandId
     * @return
     */
    public BrandStatistic selectBrandStatisticByUserIdAndBrandId(Long userId, Integer brandId) {
        return brandStatisticMapper.selectBrandStatisticByUserIdAndBrandId(userId, brandId);
    }

    /**
     * 查询直接下级人数
     *
     * @param userId
     * @param brandId
     * @return
     */
    public Integer selectDownUserNumByUserIdAndBrandId(Long userId, Integer brandId) {
        return brandStatisticMapper.selectDownUserNumByUserIdAndBrandId(userId, brandId);
    }
}
