package com.masiis.shop.admin.service.turn;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.common.enums.promotion.SfTurnTableRuleStatusEnum;
import com.masiis.shop.dao.mall.promotion.*;
import com.masiis.shop.dao.platform.gift.ComGiftMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SfTurnTableRuleService {

    @Resource
    private SfTurnTableRuleMapper sfTurnTableRuleMapper;
    @Resource
    private SfTurnTableMapper sfTurnTableMapper;
    @Resource
    private SfTurnTableGiftMapper turnTableGiftMapper;
    @Resource
    private SfUserTurnTableMapper sfUserTurnTableMapper;
    @Resource
    private SfUserTurnTableRecordMapper sfUserTurnTableRecordMapper;
    @Resource
    private ComGiftMapper comGiftMapper;

    public List<SfTurnTableRule> getRuleByTypeAndStatus(Integer type,Integer status){
        return sfTurnTableRuleMapper.getRuleByTypeAndStatus(type,status);
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
        sfTurnTableRuleMapper.insert(rule);
    }

    public void update(SfTurnTableRule rule, PbUser pbUser) {
        rule.setModifyMan(pbUser.getId());
        rule.setModifyTime(new Date());
        sfTurnTableRuleMapper.update(rule);
    }

    public List<SfTurnTableRule> listByCondition(Integer pageNumber, Integer pageSize, Map<String,Object> conditionMap) {
        PageHelper.startPage(pageNumber, pageSize, "create_time desc");
        return sfTurnTableRuleMapper.listByCondition(conditionMap);
    }

    // 统计

    public List statisticsTurnTable(Integer pageNumber, Integer pageSize, Map<String,Object> conditionMap) {
        List list = new LinkedList();

        List<SfTurnTableRule> rules = listByCondition(pageNumber,  pageSize,  null);
        for(SfTurnTableRule rule : rules) {
            SfTurnTable turnTable = sfTurnTableMapper.selectByPrimaryKey(rule.getTurnTableId());

            conditionMap.put("turnTableId", turnTable.getId());

            int userCount = sfUserTurnTableRecordMapper.countUserByTurnTableId(conditionMap);
            int giftCount = sfUserTurnTableRecordMapper.countByTurnTableId(conditionMap);

            Map<String,Object> map = new HashMap<>();
            map.put("rule", rule);
            map.put("turnTable", turnTable);
            map.put("userCount", userCount);
            map.put("giftCount", giftCount);
            list.add(map);
        }

        return list;
    }

    public List statisticGift(Integer turnTableId, Map<String, Object> conditionMap) {
        List list = new LinkedList();

        List<SfTurnTableGift> tableGifts = turnTableGiftMapper.listByTurnTableId(turnTableId);
        for(SfTurnTableGift tableGift : tableGifts) {
            ComGift comGift = comGiftMapper.selectByPrimaryKey(tableGift.getGiftId());

            conditionMap.put("turnTableId", turnTableId);
            conditionMap.put("comGiftId", tableGift.getGiftId());
            int count = sfUserTurnTableRecordMapper.countByTurnTableIdAndGiftId(conditionMap);

            Map map = new HashMap();
            map.put("comGift", comGift);
            map.put("count", count);

            list.add(map);
        }

        return list;
    }

}
