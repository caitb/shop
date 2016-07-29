package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.masiis.shop.dao.mall.promotion.SfTurnTableRuleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 转盘规则service
 */
@Service
public class SfTurnTableRuleService {

    @Resource
    private SfTurnTableRuleMapper turnTableRuleMapper;
}
