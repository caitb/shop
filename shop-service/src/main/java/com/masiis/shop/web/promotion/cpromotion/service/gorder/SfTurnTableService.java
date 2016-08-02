package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.masiis.shop.dao.mall.promotion.SfTurnTableMapper;
import com.masiis.shop.dao.po.SfTurnTable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 转盘service
 */
@Service
public class SfTurnTableService {

    @Resource
    private SfTurnTableMapper turnTableMapper;

    public List<SfTurnTable> getTurnTableByStatus(Integer status){
        return turnTableMapper.getTurnTableByStatus(status);
    }

    public List<SfTurnTable> getTurnTableByRuleTypeAndRuleStatusAndTableStatus(Integer ruleType,Integer ruleStatus,Integer tableStatus){
        return turnTableMapper.getTurnTableByRuleTypeAndRuleStatusAndTableStatus(ruleType,ruleStatus,tableStatus);
    }
}
