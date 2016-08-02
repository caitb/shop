package com.masiis.shop.admin.controller.turn;

import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.service.turn.SfTurnTableService;
import com.masiis.shop.admin.utils.DbUtils;
import com.masiis.shop.dao.mall.promotion.SfTurnTableMapper;
import com.masiis.shop.dao.po.PbUser;
import com.masiis.shop.dao.po.SfTurnTable;
import com.masiis.shop.dao.po.SfTurnTableGift;
import com.masiis.shop.dao.po.SfUserPromotion;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/turn")
public class SfTurnController {

    @Resource
    private SfTurnTableService turnTableService;
    @Resource
    private SfTurnTableMapper turnTableMapper;

    @RequestMapping("/add.shtml")
    public String add() {
        return "/turn/addTurn";
    }

    @RequestMapping("/add.do")
    @ResponseBody
    public Object add(HttpServletRequest request, HttpServletResponse response,
                      Integer turnTableId,
                      String name,
                      String describe,
                      String remark,
                      String beginTime,
                      String endTime,
                      @RequestParam("turnTableGiftId") Integer[] turnTableGiftIdArray,
                      @RequestParam("sort") Integer[] sortArray,
                      @RequestParam("giftId") Integer[] giftIdArray,
                      @RequestParam("probability") Integer[] probabilityArray,
                      @RequestParam("totalQuantity") Integer[] totalQuantityArray) {

        Object obj = request.getSession().getAttribute("pbUser");
        PbUser pbUser = (PbUser) obj;

        SfTurnTable turnTable = new SfTurnTable(turnTableId, name, describe, remark, beginTime, endTime);
        List<SfTurnTableGift> gifts = turnTableService.createTurnTableGifts(turnTableGiftIdArray, sortArray, giftIdArray, probabilityArray, totalQuantityArray);

        turnTableService.save(turnTable, gifts, pbUser);

        Map<String, Object> data = new HashMap<>();
        data.put("status", "success");
        data.put("turnTableId", turnTable.getId());
        return data;
    }

    @RequestMapping("/getTurnTable")
    @ResponseBody
    public Object getTurnTable(Integer id) {
        return turnTableService.getTurnTable(id);
    }


    @RequestMapping("/list.shtml")
    public String list() {
        return "turn/listTurnTable";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(Integer pageSize,Integer pageNumber,
                       String beginTime,
                       String endTime) {
        Map<String,Object> conditionMap = DbUtils.createTimeCondition(beginTime, endTime, null);
        List<SfTurnTable> turnTables = turnTableService.listByCondition(pageNumber, pageSize, conditionMap);
        PageInfo<SfTurnTable> promotionPageInfo = new PageInfo<>(turnTables);

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("total", promotionPageInfo.getTotal());
        dataMap.put("rows", turnTables);
        return dataMap;
    }


    @RequestMapping("/all.do")
    @ResponseBody
    public Object all() {
        return turnTableMapper.selectAll();
    }

}
