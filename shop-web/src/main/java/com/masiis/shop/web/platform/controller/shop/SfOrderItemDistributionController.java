package com.masiis.shop.web.platform.controller.shop;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.beans.order.SfDistributionPerson;
import com.masiis.shop.dao.beans.order.SfDistributionRecord;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.shop.SfOrderItemDistributionService;
import com.masiis.shop.web.platform.service.shop.SfShopService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        //默认设置每页显示10条
        Integer totalPage = 0;
        Integer num = 0;
        logger.info("count:"+count);
        Integer sumLevel = sfCount.getSumLevel();
        logger.info("sumLevel:"+sumLevel);
        BigDecimal distributionAmount;
        if (count == 0){
            distributionAmount = new BigDecimal(0);
            mv.addObject("sumLevel",0);
//            mv.addObject("totalCount",0);
            mv.addObject("distributionAmount",distributionAmount);
        }else {
            Date start = DateUtil.getFirstTimeInMonth(new Date());
            Date end = DateUtil.getLastTimeInMonth(new Date());
            num = sfOrderItemDistributionService.findCountSfDistributionRecordLimit(userId,start,end);
            totalPage = num%10 == 0 ? num/10 : num/10 + 1;
            List<SfDistributionRecord> sflist = sfOrderItemDistributionService.findListSfDistributionRecordLimit(userId,start,end,1,10);
            List<SfDistributionRecord> sfDistributionRecords = new ArrayList<>();
            distributionAmount = sfCount.getDistributionAmount();
            StringBuffer buffer;
            for (SfDistributionRecord sfDistributionRecord : sflist){
                List<SfDistributionPerson> persons = sfOrderItemDistributionService.findListSfDistributionPerson(sfDistributionRecord.getItemId());
                buffer = new StringBuffer("");
                for (SfDistributionPerson person : persons){
                    buffer.append("<div><p>" + person.getWxNkName() + ":</p><p>￥" + person.getAmount() + "</p></div>");
                }
                sfDistributionRecord.setSfDistributionPersons(persons);
                sfDistributionRecord.setSfDistributionPersonStr(buffer.toString());
                sfDistributionRecords.add(sfDistributionRecord);

            }
            mv.addObject("sumLevel",sumLevel);
//            mv.addObject("totalCount",count);
            mv.addObject("distributionAmount",distributionAmount);
            mv.addObject("sfDistributionRecords",sfDistributionRecords);
            logger.info("sfDistributionRecords.size()="+sfDistributionRecords.size());
        }
        SfShop sfShop = sfShopService.getSfShopByUserId(userId);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthString = "";
        if (month<10){
            monthString = "0"+month;
        }else {
            monthString += month;
        }
        if (sfShop == null){
            throw new BusinessException("未查询到小铺信息");
        }
        mv.addObject("year",year);
        mv.addObject("month",monthString);
        mv.addObject("totalCount",num);
        mv.addObject("totalPage",totalPage);
        mv.addObject("currentPage",1);
        mv.addObject("sfShop",sfShop);
        mv.setViewName("platform/shop/sf_distribution");
        return mv;
    }

    /**
     * ajax加载更多信息
     * @param currentPage
     * @param count
     * @param year
     * @param month
     * @param request
     * @return
     */
    @RequestMapping(value = "/moreDistribution.do",method = RequestMethod.POST)
    @ResponseBody
    public String ajaxQuerySfOrderItemDistribution(@RequestParam(value = "currentPage",required = true) Integer currentPage,
                                                   @RequestParam(value = "count",required = true) Integer count,
                                                   @RequestParam(value = "year",required = true) String year,
                                                   @RequestParam(value = "month",required = true) String month,
                                                   HttpServletRequest request){
        logger.info("分销记录首页");
        ComUser user = getComUser(request);
        if (user == null){
            throw new BusinessException("用户未登录");
        }
        Long userId = user.getId();
        Date date = DateUtil.String2Date(year+month+"01");
        Date start = DateUtil.getFirstTimeInMonth(date);
        Date end = DateUtil.getLastTimeInMonth(date);
        Integer totalCount = sfOrderItemDistributionService.findCountSfDistributionRecordLimit(userId,start,end);
        Integer totalPage = totalCount%10 == 0 ? totalCount/10 : totalCount/10 + 1;
        JSONObject jsonObject = new JSONObject();
        if (totalCount == 0){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","无查询记录");
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        if (count >= totalCount){
            jsonObject.put("isTrue","false");
            jsonObject.put("message","已经加载全部");
            logger.info(jsonObject.toJSONString());
            return jsonObject.toJSONString();
        }
        List<SfDistributionRecord> sflist = sfOrderItemDistributionService.findListSfDistributionRecordLimit(userId,start,end,currentPage + 1,10);
        StringBuffer str = new StringBuffer();
        StringBuffer buffer;
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        for (SfDistributionRecord sfDistributionRecord : sflist){
            List<SfDistributionPerson> persons = sfOrderItemDistributionService.findListSfDistributionPerson(sfDistributionRecord.getItemId());
            BigDecimal amount =new BigDecimal(0);
            buffer = new StringBuffer("");
            for (SfDistributionPerson sfDistributionPerson : persons){
                buffer.append("<div><p>" + sfDistributionPerson.getWxNkName() + ":</p><p>￥" + sfDistributionPerson.getAmount() + "</p></div>");
                amount = amount.add(sfDistributionPerson.getAmount());
            }
            str.append("<div class=\"record\">");
            str.append("<p><span><b>"+sfDistributionRecord.getLevel()+"</b>人参加</span><span>抗引力-瘦脸精华</span><span onclick=\"viewOrder("+sfDistributionRecord.getOrderId()+")\">查看订单></span></p>");
            str.append("<h1><span>"+sdf.format(sfDistributionRecord.getCreateTime())+"日</span><span>购买人："+sfDistributionRecord.getWxNkName()+"</span><span>￥"+sfDistributionRecord.getOrderAmount()+"}</span></h1>");
            str.append("<h1><span><b>"+persons.size()+"</b>人分佣</span><span>￥"+amount+"</span><span onclick=\"showDetails('"+buffer+"')\">分佣明细></span></h1></div>");
        }
        jsonObject.put("isTrue","true");
        jsonObject.put("totalCount",totalCount);
        jsonObject.put("totalPage",totalPage);
        jsonObject.put("message",str);
        logger.info(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }
}
