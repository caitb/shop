package com.masiis.shop.admin.controller.finance;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.order.COrderService;
import com.masiis.shop.admin.service.system.DictionaryService;
import com.masiis.shop.common.enums.platform.BOrderShipStatus;
import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.enums.platform.BOrderType;
import com.masiis.shop.dao.po.ComDictionary;
import com.masiis.shop.dao.po.PfCorder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/8/30.
 */
@RequestMapping("/finance/corder")
@Controller
public class COrderController {

    private final static Log log = LogFactory.getLog(COrderController.class);

    @Resource
    private DictionaryService dictionaryService;
    @Resource
    private COrderService cOrderService;

    @RequestMapping("/list.shtml")
    public String list(Model model){

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
        return "finance/corder/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortName,
                       String sortOrder,
                       PfCorder pfCorder){

        try {
            Map<String, Object> pageMap = cOrderService.listByCondition(pageNumber, pageSize, sortName, sortOrder, pfCorder);
            if (pfCorder.getShipStatus() == null) {
                List<ComDictionary> wuliuList = dictionaryService.pickListOfBaseData("PF_CORDER_SHIP_STATUS");//物流状态
                pageMap.put("wuliuList", wuliuList);
            }
            if(pfCorder.getOrderStatus() == null){
                List<ComDictionary> orderStatusList = dictionaryService.pickListOfBaseData("PF_CORDER_STATUS");//订单状态
                pageMap.put("orderStatusList", orderStatusList);
            }
            return pageMap;
        } catch (Exception e) {
            log.error("查询试用订单列表出错![pageNumber="+pageNumber+"][pageSize="+pageSize+"][pfCorder="+pfCorder+"]");
            e.printStackTrace();

            return "查询试用订单列表出错!";
        }

    }


}
