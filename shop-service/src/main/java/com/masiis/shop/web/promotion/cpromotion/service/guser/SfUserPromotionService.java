package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.dao.mall.promotion.SfUserPromotionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商城用户活动主表service
 */
@Service
public class SfUserPromotionService {
    @Resource
    private SfUserPromotionMapper sfUserPromotionMapper;
}
