package com.masiis.shop.admin.service.turn;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.common.enums.promotion.SfTurnTableRuleStatusEnum;
import com.masiis.shop.dao.mall.promotion.SfTurnTableRuleMapper;
import com.masiis.shop.dao.po.PbUser;
import com.masiis.shop.dao.po.SfTurnTable;
import com.masiis.shop.dao.po.SfTurnTableRule;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SfTurnTableRuleService {

    @Resource
    private SfTurnTableRuleMapper ruleMapper;
    @Resource
    private SfTurnTableRuleMapper turnTableRuleMapper;

    public List<SfTurnTableRule> getRuleByTypeAndStatus(Integer type,Integer status){
        return turnTableRuleMapper.getRuleByTypeAndStatus(type,status);
    }

    /**
     * 判断是否有大转盘抽奖活动
     * @param turnTableRuleType
     * @return
     */
    public Boolean isTurnTableRule(Integer turnTableRuleType){
        List<SfTurnTableRule>  turnTableRules = getRuleByTypeAndStatus(turnTableRuleType, SfTurnTableRuleStatusEnum.EFFECT.getCode());
        if (turnTableRules==null||turnTableRules.size()==0){
            return false;
        }
        return true;
    }

    public void save(SfTurnTableRule rule, PbUser pbUser) {
        if(rule.getId() == null) {
            insert(rule, pbUser);
        } else {
            update(rule, pbUser);
        }
    }

    public void insert(SfTurnTableRule rule, PbUser pbUser) {
        rule.setCreateTime(new Date());
        rule.setCreateMan(pbUser.getId());
        rule.setStatus(0);
        ruleMapper.insert(rule);
    }

    public void update(SfTurnTableRule rule, PbUser pbUser) {
        rule.setModifyMan(pbUser.getId());
        rule.setModifyTime(new Date());
        ruleMapper.update(rule);
    }

    public List<SfTurnTableRule> listByCondition(Integer pageNumber, Integer pageSize, Map<String,Object> conditionMap) {
        PageHelper.startPage(pageNumber, pageSize, "create_time desc");
        return turnTableRuleMapper.listByCondition(conditionMap);
    }

}
