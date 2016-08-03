package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.masiis.shop.dao.mall.promotion.SfTurnTableRuleMapper;
import com.masiis.shop.dao.po.SfTurnTableRule;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 转盘规则service
 */
@Service
public class SfTurnTableRuleService {

    @Resource
    private SfTurnTableRuleMapper turnTableRuleMapper;


    public SfTurnTableRule getRuleByTurnTableIdAndType(Integer turnTableId,Integer type){
        return turnTableRuleMapper.getRuleByTurnTableIdAndType(turnTableId,type);
    }
}
