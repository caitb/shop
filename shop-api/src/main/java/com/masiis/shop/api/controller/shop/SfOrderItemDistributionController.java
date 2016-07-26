package com.masiis.shop.api.controller.shop;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.common.CommonReq;
import com.masiis.shop.api.bean.shop.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.web.mall.service.order.SfOrderItemDistributionService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.beans.order.SfDistributionPerson;
import com.masiis.shop.dao.beans.order.SfDistributionRecord;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    private final Integer pageSize = 10;

    /**
     * 分销记录首页
     * @param request
     * @param req
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/home.do",method = RequestMethod.POST)
    @SignValid(paramType = CommonReq.class)
    public ItemDistributionRes itemDistributionHome(HttpServletRequest request, CommonReq req, ComUser user){
        logger.info("分销记录首页");
        ItemDistributionRes res = new ItemDistributionRes();
        try {
            SfDistributionRecord sfCount = sfOrderItemDistributionService.findCountSfDistributionRecord(user.getId());
            SfShop sfShop = sfShopService.getSfShopByUserId(user.getId());
            NumberFormat numberFormat = DecimalFormat.getCurrencyInstance(Locale.CHINA);
            if (sfCount.getCount() == 0){
                res.setDistributionAmount(numberFormat.format(0));
                res.setSumLevel(0);
                res.setSaleAmount(sfShop == null?numberFormat.format(0):numberFormat.format(sfShop.getSaleAmount()));
            }else {
                res.setDistributionAmount(numberFormat.format(sfCount.getDistributionAmount()));
                res.setSaleAmount(sfShop == null?numberFormat.format(0):numberFormat.format(sfShop.getSaleAmount()));
                res.setSumLevel(sfCount.getSumLevel());
            }
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        logger.info("返回参数：" + JSONObject.toJSONString(res));
        return res;
    }

    /**
     * 按月查询分销记录
     * @param request
     * @param req
     * @param user
     * @author:wbj
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRecordByMonth.do",method = RequestMethod.POST)
    @SignValid(paramType = DistributionRecordReq.class)
    public DistributionRecordRes recordByMonth(HttpServletRequest request, DistributionRecordReq req, ComUser user){
        logger.info("分销记录查询");
        logger.info("userId=" + user.getId());
        logger.info("Date=" + req.getDate());
        logger.info("currentPage = " + req.getPageNum());
        DistributionRecordRes res = new DistributionRecordRes();
        if (!StringUtils.isNotBlank(req.getDate()) || req.getPageNum() == null || req.getPageNum() <= 0){
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN_MSG + "【日期或页码有误】");
            logger.info("返回参数：" + JSONObject.toJSONString(res));
            return res;
        }
        Date currentTime = null;
        try{
            currentTime = DateUtil.String2Date(req.getDate(), "yyyy-MM");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("时间格式化错误,原时间字符串为:" + req.getDate());
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN_MSG + "【时间格式化错误,原时间字符串为" + req.getDate() + "】");
            logger.info("返回参数：" + JSONObject.toJSONString(res));
            return res;
        }

        try {
            Date start = DateUtil.getFirstTimeInMonth(currentTime);
            Date end = DateUtil.getLastTimeInMonth(currentTime);
            Integer totalCount = sfOrderItemDistributionService.findCountSfDistributionRecordLimit(user.getId(),start,end);
            if (totalCount == null || totalCount == 0){
                logger.error("当前时间区间内没有数据");
                res.setPageSize(0);
                res.setTotalCount(0);
                res.setTotalPage(0);
                res.setHasQueryData(0);
                res.setLast(true);
                res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
                res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
                logger.info("返回参数：" + JSONObject.toJSONString(res));
                return res;
            }
            Integer totalPage = 0;
            Integer startNum = req.getPageNum();
            Integer qSize = pageSize;
            NumberFormat numberFormat = DecimalFormat.getCurrencyInstance(Locale.CHINA);

            //默认设置每页显示10条
            totalPage = totalCount%pageSize == 0 ? totalCount/pageSize : totalCount/pageSize + 1;
            startNum = (req.getPageNum() - 1) * pageSize;
            if(req.getPageNum() > totalPage){
                logger.error("当前所传页码超过总页码数,总页数:" + totalPage + ",所传页码数:" + req.getPageNum());
                res.setLast(true);
                res.setResCode(SysResCodeCons.RES_CODE_REQ_BUSINESS_ERROR);
                res.setResMsg(SysResCodeCons.RES_CODE_REQ_BUSINESS_ERROR_MSG + "【所传页码超过总页数】");
                logger.info("返回参数：" + JSONObject.toJSONString(res));
                return res;
            }
            if(req.getPageNum() == totalPage){
                qSize = totalCount - startNum;
                res.setLast(true);
            }
            List<SfDistributionRecord> sfDistributionRecords = sfOrderItemDistributionService.findListSfDistributionRecordLimit(user.getId(),start,end,req.getPageNum(),qSize);
            List<SfDistribution> sfDistributions = new ArrayList<>();
            SfDistribution sfDistribution;
            BigDecimal personTotalAmount;
            for(int i = 0; i < sfDistributionRecords.size(); i++){
                sfDistribution = new SfDistribution();
                sfDistribution.setCreateTime(sfDistributionRecords.get(i).getCreateTime());
                sfDistribution.setItemId(sfDistributionRecords.get(i).getItemId());
                sfDistribution.setLevel(sfDistributionRecords.get(i).getLevel());
                sfDistribution.setOrderAmount(numberFormat.format(sfDistributionRecords.get(i).getOrderAmount()));
                sfDistribution.setOrderId(sfDistributionRecords.get(i).getOrderId());
                sfDistribution.setSkuId(sfDistributionRecords.get(i).getSkuId());
                sfDistribution.setSkuName(sfDistributionRecords.get(i).getSkuName());
                sfDistribution.setWxNkName(sfDistributionRecords.get(i).getWxNkName());
                List<SfDistributionPerson> persons = sfOrderItemDistributionService.findListSfDistributionPerson(sfDistributionRecords.get(i).getItemId());
                sfDistribution.setSumLevel(persons.size());
                personTotalAmount = new BigDecimal(0);
                for (SfDistributionPerson sfDistributionPerson : persons){
                    personTotalAmount = personTotalAmount.add(sfDistributionPerson.getAmount());
                }
                sfDistribution.setTotalAmount(numberFormat.format(personTotalAmount));
                sfDistribution.setSfDistributionPersons(persons);
                sfDistributions.add(sfDistribution);
            }
            res.setSfDistributions(sfDistributions);

            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            res.setHasQueryData(1);
            res.setTotalPage(totalPage);
            res.setTotalCount(totalCount);
            res.setCurrentPage(startNum);
            res.setPageSize(qSize);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        logger.info("返回参数：" + JSONObject.toJSONString(res));
        return res;
    }
}
