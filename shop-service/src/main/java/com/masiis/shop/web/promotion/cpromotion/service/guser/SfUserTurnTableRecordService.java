package com.masiis.shop.web.promotion.cpromotion.service.guser;

import com.masiis.shop.dao.mall.promotion.SfUserTurnTableRecordMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户转盘中奖记录service
 */
@Service
public class SfUserTurnTableRecordService {

    @Resource
    private SfUserTurnTableRecordMapper userTurnTableRecordMapper;
}
