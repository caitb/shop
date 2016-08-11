package com.masiis.shop.admin.controller.turn;

import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.service.turn.SfTurnTableRuleService;
import com.masiis.shop.admin.utils.DbUtils;
import com.masiis.shop.dao.mall.promotion.SfTurnTableRuleMapper;
import com.masiis.shop.dao.po.PbUser;
import com.masiis.shop.dao.po.SfTurnTableRule;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/turnRule")
public class SfTurnTableRuleController {

    @Resource
    private SfTurnTableRuleMapper ruleMapper;
    @Resource
    private SfTurnTableRuleService turnTableRuleService;

    @RequestMapping("/add.shtml")
    public String add() {
        return "turnRule/addTurnRule";
    }

    @RequestMapping("/add.do")
    @ResponseBody
    public Object add(SfTurnTableRule rule, HttpSession session) {
        Object obj = session.getAttribute("pbUser");
        PbUser pbUser = (PbUser) obj;
        turnTableRuleService.save(rule, pbUser);

        Map<String,Object> data = new HashMap<>();
        data.put("status", "success");
        data.put("id", rule.getId());
        return data;
    }

    @RequestMapping("/getTurnRule")
    @ResponseBody
    public Object getTurnRule(Integer id) {
        return ruleMapper.selectByPrimaryKey(id);
    }

    @RequestMapping("/list.shtml")
    public String list() {
        return "turnRule/listTurnRule";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(Integer pageSize,Integer pageNumber,
                       String beginTime,
                       String endTime) {
        Map<String,Object> conditionMap = DbUtils.createTimeCondition(beginTime, endTime, null);
        List<SfTurnTableRule> turnTableRules = turnTableRuleService.listByCondition(pageNumber, pageSize, conditionMap);
        PageInfo<SfTurnTableRule> rulesPageInfo = new PageInfo<>(turnTableRules);

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("total", rulesPageInfo.getTotal());
        dataMap.put("rows", turnTableRules);
        return dataMap;
    }

    @RequestMapping("/changeStatus")
    @ResponseBody
    public Object changeStatus(Integer id, Integer status) {
        SfTurnTableRule rule = new SfTurnTableRule();
        rule.setId(id);
        rule.setStatus(status);
        ruleMapper.update(rule);
        return "success";
    }

    /**
     * 统计
     * @return
     */
    @RequestMapping("/listStatistics.shtml")
    public String statistic() {
        return "turnRule/listStatistics";
    }

    @RequestMapping("/listStatistics.do")
    @ResponseBody
    public Object listStatistics(Integer pageSize,Integer pageNumber,
                                 String beginTime,
                                 String endTime) {
        Map<String,Object> conditionMap = DbUtils.createTimeCondition(beginTime, endTime, null);
        List turnTableRules = turnTableRuleService.statisticsTurnTable(pageNumber, pageSize, conditionMap);
        PageInfo rulesPageInfo = new PageInfo<>(turnTableRules);

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("total", rulesPageInfo.getTotal());
        dataMap.put("rows", turnTableRules);
        return dataMap;
    }

    @RequestMapping("/detailStatistics.do")
    @ResponseBody
    public Object detailStatistics(Integer turnTableId,
                                   String beginTime,
                                   String endTime) {
        Map<String,Object> conditionMap = DbUtils.createTimeCondition(beginTime, endTime, null);

        return turnTableRuleService.statisticGift(turnTableId, conditionMap);
    }

}
