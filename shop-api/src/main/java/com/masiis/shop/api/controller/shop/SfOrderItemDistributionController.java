package com.masiis.shop.api.controller.shop;

import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.shop.SfOrderItemDistributionService;
import com.masiis.shop.common.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 小铺分销记录
 * Created by wangbingjian on 2016/4/22.
 */
@Controller
@RequestMapping(value = "/distribution")
public class SfOrderItemDistributionController extends BaseController {

    private static final Logger logger = Logger.getLogger(SfOrderItemDistributionController.class);
    @Autowired
    private SfOrderItemDistributionService sfOrderItemDistributionService;

    /**
     * 分校记录首页
     * @param userId    用户id
     * @param yDate      日期字符串精确到月份 格式为"yyyyMM"
     * @author:wbj
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{userId}")
    public String itemDistributionHome(@RequestParam(value = "userId",required = true) Long userId,
                                       @RequestParam(value = "yDate",required = true) String yDate,
                                       @RequestParam(value = "currentPage",required = true) String currentPage){

        Date date = DateUtil.String2Date(yDate);
        Date start = DateUtil.getFirstTimeInMonth(date);
        Date end = DateUtil.getLastTimeInMonth(date);
        Integer totalCount = sfOrderItemDistributionService.findCountSfDistributionRecordLimit(userId,start,end);
        return "";
    }
}
