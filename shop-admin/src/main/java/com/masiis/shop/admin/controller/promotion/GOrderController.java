package com.masiis.shop.admin.controller.promotion;

import com.masiis.shop.admin.service.promotion.SfGorderService;
import com.masiis.shop.admin.utils.DbUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 奖品订单 Controller
 */
@Controller
@RequestMapping("/gorder")
public class GOrderController {

    @Resource
    private SfGorderService sfGorderService;

    @RequestMapping("list.shtml")
    public String list() {
        return "gorder/listGOrder";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(Integer pageSize,Integer pageNumber,
                       String beginTime,
                       String endTime,
                       Integer isShip) {
        Map<String,Object> conditionMap = DbUtils.createTimeCondition(beginTime, endTime, null);
        conditionMap.put("isShip", isShip);

        return sfGorderService.listByCondition(pageNumber, pageSize, conditionMap);
    }

}
