package com.masiis.shop.api.controller.shop;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.base.ShopApiConstant;
import com.masiis.shop.api.bean.base.ShopApiResponseModel;
import com.masiis.shop.api.bean.shop.DistributionRecord;
import com.masiis.shop.api.bean.shop.SfDistribution;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.shop.SfOrderItemDistributionService;
import com.masiis.shop.api.service.shop.SfShopService;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.beans.order.SfDistributionPerson;
import com.masiis.shop.dao.beans.order.SfDistributionRecord;
import com.masiis.shop.dao.po.SfShop;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private SfShopService sfShopService;

    /**
     * 分校记录首页
     * @param userId    用户id
     * @param yDate      日期字符串精确到月份 格式为"yyyyMM"
     * @author:wbj
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/home.do",method = RequestMethod.GET)
    public ShopApiResponseModel itemDistributionHome(@RequestParam(value = "userCode",required = true) Long userId,
                                                     @RequestParam(value = "yDate",required = true) String yDate,
                                                     @RequestParam(value = "currentPage",required = true) Integer currentPage,
                                                     @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        logger.info("分销记录查询");
        logger.info("userCode="+userId);
        logger.info("yDate="+yDate);
        logger.info("currentPage="+currentPage);
        ShopApiResponseModel responseModel = new ShopApiResponseModel();
        Date date = DateUtil.String2Date(yDate);
        Date start = DateUtil.getFirstTimeInMonth(date);
        Date end = DateUtil.getLastTimeInMonth(date);
        Integer totalCount = sfOrderItemDistributionService.findCountSfDistributionRecordLimit(userId,start,end);
        if (totalCount == 0){
            responseModel.setCode(ShopApiConstant.DATA_NOT_EXIST);
            responseModel.setMsg("noData");
            return responseModel;
        }
        try {
            SfDistributionRecord sfCount = sfOrderItemDistributionService.findCountSfDistributionRecord(userId);
            SfShop sfShop = sfShopService.getSfShopByUserId(userId);
            DistributionRecord distributionRecord = new DistributionRecord();
            Integer totalPage = 0;
            if (sfCount.getCount() == 0){
                distributionRecord.setDistributionAmount(new BigDecimal(0));
                distributionRecord.setSumLevel(0);
                distributionRecord.setSaleAmount(sfShop == null?new BigDecimal(0):sfShop.getSaleAmount());
            }else {
                //默认设置每页显示10条
                totalPage = totalCount%pageSize == 0 ? pageSize/10 : pageSize/10 + 1;
                List<SfDistributionRecord> sfDistributionRecords = sfOrderItemDistributionService.findListSfDistributionRecordLimit(userId,start,end,currentPage,pageSize);
                List<SfDistribution> sfDistributions = new ArrayList<>();
                distributionRecord.setDistributionAmount(sfCount.getDistributionAmount());
                distributionRecord.setSaleAmount(sfShop == null?new BigDecimal(0):sfShop.getSaleAmount());
                distributionRecord.setSumLevel(sfCount.getSumLevel());
                SfDistribution sfDistribution;
                BigDecimal personTotalAmount;
                for(int i = 0; i < sfDistributionRecords.size(); i++){
                    sfDistribution = new SfDistribution();
                    sfDistribution.setSumLevel(sfDistributionRecords.get(i).getSumLevel());
                    sfDistribution.setCount(sfDistributionRecords.get(i).getCount());
                    sfDistribution.setCreateTime(sfDistributionRecords.get(i).getCreateTime());
                    sfDistribution.setItemId(sfDistributionRecords.get(i).getItemId());
                    sfDistribution.setLevel(sfDistributionRecords.get(i).getLevel());
                    sfDistribution.setOrderAmount(sfDistributionRecords.get(i).getOrderAmount());
                    sfDistribution.setOrderId(sfDistributionRecords.get(i).getOrderId());
                    sfDistribution.setSkuId(sfDistributionRecords.get(i).getSkuId());
                    sfDistribution.setSkuName(sfDistributionRecords.get(i).getSkuName());
                    sfDistribution.setWxNkName(sfDistributionRecords.get(i).getWxNkName());
                    List<SfDistributionPerson> persons = sfOrderItemDistributionService.findListSfDistributionPerson(sfDistributionRecords.get(i).getItemId());
                    personTotalAmount = new BigDecimal(0);
                    for (SfDistributionPerson sfDistributionPerson : persons){
                        personTotalAmount.add(sfDistributionPerson.getAmount());
                    }
                    sfDistribution.setTotalAmount(personTotalAmount);
                    sfDistribution.setSfDistributionPersons(persons);
                    sfDistributions.add(sfDistribution);
                }
                distributionRecord.setSfDistributions(sfDistributions);
            }
            responseModel.setData(distributionRecord);
            responseModel.setCode(ShopApiConstant.SUCCESS);
            responseModel.setMsg(SUCCESS);
            responseModel.setCurrentPage(currentPage);
            responseModel.setTotalCount(totalCount);
            responseModel.setTotalPage(totalPage);
            responseModel.setData(distributionRecord);
        }catch (Exception e){
            e.printStackTrace();
            responseModel.setCode(ShopApiConstant.ERROR);
            responseModel.setMsg(ERROR);
            return responseModel;
        }
        return responseModel;
    }
}