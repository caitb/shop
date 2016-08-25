package com.masiis.shop.admin.controller.finance;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.order.BOrderService;
import com.masiis.shop.admin.service.system.DictionaryService;
import com.masiis.shop.common.enums.platform.BOrderShipStatus;
import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.enums.platform.BOrderType;
import com.masiis.shop.dao.po.ComDictionary;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合伙订单
 * Created by cai_tb on 16/8/25.
 */
@Controller
@RequestMapping("/finance/border")
public class BOrderController {

    private final static Log log = LogFactory.getLog(BOrderController.class);

    @Resource
    private BOrderService bOrderService;
    @Resource
    private DictionaryService dictionaryService;

    @RequestMapping("/list.shtml")
    public String list(Model model) {
        try {
            List<ComDictionary> payTypeList = dictionaryService.pickListOfBaseData("COM_USER_PAY_TYPE");//支付方式
            model.addAttribute("payTypes", payTypeList);
            model.addAttribute("bOrderTypes", BOrderType.values());
            model.addAttribute("bOrderStatuses", BOrderStatus.values());
            model.addAttribute("bOrderShipStatuses", BOrderShipStatus.values());
        } catch (Exception e) {
            log.error("获取支付方式失败!"+e);
            e.printStackTrace();
        }

        return "finance/border/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortName,
                       String sortOrder,
                       String orderCode,
                       String beginTime,
                       String endTime,
                       Integer orderType,
                       Integer orderStatus,
                       Integer payStatus,
                       Integer shipStatus,
                       Integer isCounting,
                       Integer payTypeId
    ) {

        Map<String, Object> conditionMap = new HashMap<>();
        try {
            if(StringUtils.isNotBlank(orderCode)){
                conditionMap.put("orderCode", orderCode);
            }
            if(StringUtils.isNotBlank(request.getParameter("uRealName"))){
                conditionMap.put("uRealName", "%"+request.getParameter("uRealName")+"%");
            }
            if(StringUtils.isNotBlank(request.getParameter("skuName"))){
                conditionMap.put("skuName", "%"+request.getParameter("skuName")+"%");
            }
            if(orderType != null){
                conditionMap.put("orderType", orderType);
            }
            if(orderStatus != null){
                conditionMap.put("orderStatus", orderStatus);
            }
            if(payStatus != null){
                conditionMap.put("payStatus", payStatus);
            }
            if(shipStatus != null){
                conditionMap.put("shipStatus", shipStatus);
            }
            if(isCounting != null){
                conditionMap.put("isCounting", isCounting);
            }
            if(payTypeId != null){
                conditionMap.put("payTypeId", payTypeId);
            }
            Map<String, Object> pageMap = bOrderService.listByCondition(pageNumber, pageSize, sortName, sortOrder, conditionMap);
            return pageMap;
        } catch (Exception e) {
            log.error("查询合伙人列表失败![conditionMap=" + conditionMap + "]");
            e.printStackTrace();
        }

        return null;
    }
}
