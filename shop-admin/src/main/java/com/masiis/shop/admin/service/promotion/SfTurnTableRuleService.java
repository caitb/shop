package com.masiis.shop.admin.service.promotion;

import com.masiis.shop.common.enums.promotion.SfTurnTableRuleStatusEnum;
import com.masiis.shop.dao.mall.promotion.SfTurnTableRuleMapper;
import com.masiis.shop.dao.po.SfTurnTableRule;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 转盘规则service
 */
@Service
public class SfTurnTableRuleService {

    @Resource
    private SfTurnTableRuleMapper turnTableRuleMapper;


    public SfTurnTableRule getRuleByTurnTableIdAndTypeAndStatus(Integer turnTableId,Integer type,Integer status){
        return turnTableRuleMapper.getRuleByTurnTableIdAndTypeAndStatus(turnTableId,type,status);
    }

    public List<SfTurnTableRule> getRuleByTypeAndStatus(Integer type,Integer status){
        return turnTableRuleMapper.getRuleByTypeAndStatus(type,status);
    }

    /**
     * 判断是否有大转盘抽奖活动
     * @param turnTableRuleType
     * @return
     */
    public Map<String,String> isTurnTableRule(Integer turnTableRuleType){
        List<SfTurnTableRule>  turnTableRules = getRuleByTypeAndStatus(turnTableRuleType, SfTurnTableRuleStatusEnum.EFFECT.getCode());
        Map<String,String> map = new LinkedHashMap<>();
        if (turnTableRules==null||turnTableRules.size()==0){
            map.put("isTurnTableRule","false");
        }else{
            map.put("isTurnTableRule","true");
            map.put("turnTableRuleTimes",turnTableRules.get(0).getTimes().toString());
        }
        return map;
    }
}
