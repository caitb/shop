package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.dao.mall.promotion.SfUserPromotionRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * C端用户活动参与记录表 service
 */
@Service
public class SfUserPromotionRecordService {

    @Resource
    private SfUserPromotionRecordMapper sfUserPromotionRecordMapper;
}
