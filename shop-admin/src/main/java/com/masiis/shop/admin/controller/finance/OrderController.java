package com.masiis.shop.admin.controller.finance;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.order.OrderService;
import com.masiis.shop.admin.service.system.DictionaryService;
import com.masiis.shop.dao.po.ComDictionary;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/8/25.
 */
@Controller
@RequestMapping("/finance/order")
public class OrderController {

    private final static Log log = LogFactory.getLog(OrderController.class);

    @Resource
    private OrderService orderService;
    @Resource
    private DictionaryService dictionaryService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "finance/order/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortName,
                       String beginTime,
                       String endTime,
                       String sortOrder,
                       String orderCode,
                       Integer orderStatus,
                       Integer payStatus,
                       Integer  shipStatus,
                       Integer isCounting,
                       Integer sendType
    ){

        Map<String, Object> conditionMap = new HashMap<>();
        try {
            if (shipStatus!=null){
                conditionMap.put("shipStatus",shipStatus);
            }
            if (orderStatus!=null){
                conditionMap.put("orderStatus",orderStatus);
            }
            if (payStatus!=null){
                conditionMap.put("payStatus",payStatus);
            }
            if (!StringUtils.isEmpty(beginTime)){
                conditionMap.put("beginTime",beginTime);
            }
            if (!StringUtils.isEmpty(endTime)){
                conditionMap.put("endTime",endTime);
            }
            if (isCounting!=null){
                conditionMap.put("isCounting",isCounting);
            }
            if(sendType != null) {
                conditionMap.put("sendType", sendType);
            }
            conditionMap.put("orderCode", orderCode);

            Map<String, Object> pageMap = orderService.listByCondition(pageNumber, pageSize, sortName, sortOrder, conditionMap,shipStatus);
            if (shipStatus == null) {
                List<ComDictionary> wuliuList = dictionaryService.pickListOfBaseData("PF_BORDER_SHIP_STATUS");//物流状态
                pageMap.put("wuliuList", wuliuList);
            }
            if(orderStatus == null){
                List<ComDictionary> orderStatusList = dictionaryService.pickListOfBaseData("SF_ORDER_STATUS");//订单状态
                pageMap.put("orderStatusList", orderStatusList);
            }
            return pageMap;
        } catch (Exception e) {
            log.error("获取店铺订单列表失败![conditionMap="+conditionMap+"]");
            e.printStackTrace();
        }

        return "error";
    }
}
