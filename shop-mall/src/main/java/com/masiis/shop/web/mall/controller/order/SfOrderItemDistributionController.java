package com.masiis.shop.web.mall.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.order.SfDistributionPerson;
import com.masiis.shop.dao.beans.order.SfDistributionRecord;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.order.SfOrderItemDistributionService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangbingjian on 2016/4/13.
 */
@Controller
@RequestMapping(value = "/distribution")
public class SfOrderItemDistributionController extends BaseController {

    private final Logger logger = Logger.getLogger(SfOrderItemDistributionController.class);
    @Autowired
    private SfOrderItemDistributionService sfOrderItemDistributionService;
    @Autowired
    private SfShopService sfShopService;

    /**
     * 分销记录首页
     * @param request
     * @return
     */
    @RequestMapping(value = "/distribution.shtml")
    public ModelAndView SfOrderItemDistributionHome(HttpServletRequest request) throws Exception{
        logger.info("分销记录首页");
        ComUser user = getComUser(request);
        if (user == null){
            throw new BusinessException("用户未登录");
        }
        ModelAndView mv = new ModelAndView();
        Long userId = user.getId();
        SfDistributionRecord sfCount = sfOrderItemDistributionService.findCountSfDistributionRecord(userId);
        Integer count = sfCount.getCount();
        logger.info("count:"+count);
        Integer sumLevel = sfCount.getSumLevel();
        logger.info("sumLevel:"+sumLevel);
        List<SfDistributionRecord> sfDistributionRecords = new ArrayList<>();
        if (count == 0){
            mv.addObject("sumLevel",0);
            mv.addObject("totalCount",0);
        }else {
            List<SfDistributionRecord> sflist = sfOrderItemDistributionService.findListSfDistributionRecord(userId);
            for (SfDistributionRecord sfDistributionRecord : sflist){
                List<SfDistributionPerson> persons = sfOrderItemDistributionService.findListSfDistributionPerson(sfDistributionRecord.getItemId());
                sfDistributionRecord.setSfDistributionPersons(persons);
                sfDistributionRecords.add(sfDistributionRecord);
            }
            mv.addObject("sumLevel",sumLevel);
            mv.addObject("totalCount",count);
        }
        SfShop sfShop = sfShopService.getSfShopByUserId(userId);
        if (sfShop == null){
            throw new BusinessException("未查询到小铺信息");
        }
        mv.addObject("sfShop",sfShop);
        mv.addObject("sfDistributionRecords",sfDistributionRecords);
        mv.setViewName("mall/order/sf_distribution");
        return mv;
    }

}
