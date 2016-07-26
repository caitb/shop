package com.masiis.shop.admin.controller.delivery;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.order.BOrderService;
import com.masiis.shop.admin.service.order.COrderService;
import com.masiis.shop.admin.service.order.ComShipManService;
import com.masiis.shop.admin.service.order.OrderService;
import com.masiis.shop.admin.service.system.DictionaryService;
import com.masiis.shop.common.enums.mall.SfOrderStatusEnum;
import com.masiis.shop.common.enums.platform.BOrderShipStatus;
import com.masiis.shop.common.enums.platform.BOrderStatus;
import com.masiis.shop.common.enums.platform.BOrderType;
import com.masiis.shop.dao.po.ComDictionary;
import com.masiis.shop.dao.po.ComShipMan;
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
 * 发货管理
 * Created by cai_tb on 16/7/20.
 */
@Controller
@RequestMapping("/delivery")
public class DeliveryController {

    private final static Log log = LogFactory.getLog(DeliveryController.class);

    @Resource
    private DictionaryService dictionaryService;
    @Resource
    private OrderService orderService;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private ComShipManService comShipManService;
    @Resource
    private COrderService cOrderService;

    /**
     * 店铺代发货订单页面
     * @param model
     * @return
     */
    @RequestMapping("/orderList.shtml")
    public String orderList(Model model){

        try {
            List<ComDictionary> wuliuList = dictionaryService.pickListOfBaseData("PF_BORDER_SHIP_STATUS");//物流状态
            model.addAttribute("wuliuList", wuliuList);
            model.addAttribute("orderStatusList", SfOrderStatusEnum.values());
        } catch (Exception e) {
            log.error("查询物流状态失败!"+e);
            e.printStackTrace();
        }

        return "delivery/orderList";
    }

    /**
     * 店铺代发货订单列表
     * @param request
     * @param response
     * @param pageNumber
     * @param pageSize
     * @param sortName
     * @param beginTime
     * @param endTime
     * @param sortOrder
     * @param orderCode
     * @param orderStatus
     * @param payStatus
     * @param shipStatus
     * @param isCounting
     * @return
     */
    @RequestMapping("/orderList.do")
    @ResponseBody
    public Object orderList(HttpServletRequest request, HttpServletResponse response,
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
                       Integer isCounting
    ){

        Map<String, Object> conditionMap = new HashMap<>();
        try {
            if(StringUtils.isNotBlank(orderCode)){
                conditionMap.put("orderCode", orderCode);
            }
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

            Map<String, Object> pageMap = orderService.listDeliveryByCondition(pageNumber, pageSize, sortName, sortOrder, conditionMap);

            return pageMap;
        } catch (Exception e) {
            log.error("获取店铺订单列表失败![conditionMap="+conditionMap+"]");
            e.printStackTrace();
        }

        return "error";
    }


    /**
     * 合伙订单发货页面
     * @param model
     * @return
     */
    @RequestMapping("/bOrderList.shtml")
    public String bOrderList(Model model){

        try {
            List<ComShipMan> comShipManList = comShipManService.listByCondition(new ComShipMan());
            List<ComDictionary> payTypeList = dictionaryService.pickListOfBaseData("COM_USER_PAY_TYPE");//支付方式
            model.addAttribute("comShipManList", comShipManList);
            model.addAttribute("payTypes", payTypeList);
            model.addAttribute("bOrderTypes", BOrderType.values());
            model.addAttribute("bOrderStatuses", BOrderStatus.values());
            model.addAttribute("bOrderShipStatuses", BOrderShipStatus.values());
        } catch (Exception e) {
            log.error("获取支付方式失败!"+e);
            e.printStackTrace();
        }

        return "delivery/bOrderList";
    }

    /**
     * 合伙订单列表
     * @param request
     * @param response
     * @param pageNumber
     * @param pageSize
     * @param sortName
     * @param beginTime
     * @param endTime
     * @param sortOrder
     * @param orderCode
     * @param orderStatus
     * @param payStatus
     * @param shipStatus
     * @param isCounting
     * @return
     */
    @RequestMapping("/bOrderList.do")
    @ResponseBody
    public Object bOrderList(HttpServletRequest request, HttpServletResponse response,
                            Integer pageNumber,
                            Integer pageSize,
                            String sortName,
                            String sortOrder,
                            String beginTime,
                            String endTime,
                            String orderCode,
                            Integer orderType,
                            Integer orderStatus,
                            Integer payStatus,
                            Integer  shipStatus,
                            Integer isCounting
    ){

        Map<String, Object> conditionMap = new HashMap<>();
        try {
            if(StringUtils.isNotBlank(orderCode)){
                conditionMap.put("orderCode", orderCode);
            }
            if(orderType != null){
                conditionMap.put("orderType", orderType);
            }
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

            Map<String, Object> pageMap = bOrderService.listDeliveryByCondition(pageNumber, pageSize, sortName, sortOrder, conditionMap);

            return pageMap;
        } catch (Exception e) {
            log.error("获取店铺订单列表失败![conditionMap="+conditionMap+"]");
            e.printStackTrace();
        }

        return "error";
    }


    /**
     * 试用订单发货页面
     * @param model
     * @return
     */
    @RequestMapping("/cOrderList.shtml")
    public String cOrderList(Model model){

        try {
            List<ComShipMan> comShipManList = comShipManService.listByCondition(new ComShipMan());
            List<ComDictionary> payTypeList = dictionaryService.pickListOfBaseData("COM_USER_PAY_TYPE");//支付方式
            model.addAttribute("comShipManList", comShipManList);
            model.addAttribute("payTypes", payTypeList);
            model.addAttribute("bOrderTypes", BOrderType.values());
            model.addAttribute("bOrderStatuses", BOrderStatus.values());
            model.addAttribute("bOrderShipStatuses", BOrderShipStatus.values());
        } catch (Exception e) {
            log.error("获取支付方式失败!"+e);
            e.printStackTrace();
        }

        return "delivery/cOrderList";
    }

    /**
     * 试用订单列表
     * @param request
     * @param response
     * @param pageNumber
     * @param pageSize
     * @param sortName
     * @param beginTime
     * @param endTime
     * @param sortOrder
     * @param orderCode
     * @param orderStatus
     * @param payStatus
     * @param shipStatus
     * @return
     */
    @RequestMapping("/cOrderList.do")
    @ResponseBody
    public Object cOrderList(HttpServletRequest request, HttpServletResponse response,
                             Integer pageNumber,
                             Integer pageSize,
                             String sortName,
                             String sortOrder,
                             String beginTime,
                             String endTime,
                             String orderCode,
                             Integer orderType,
                             Integer orderStatus,
                             Integer payStatus,
                             Integer  shipStatus
    ){

        Map<String, Object> conditionMap = new HashMap<>();
        try {
            if(StringUtils.isNotBlank(orderCode)){
                conditionMap.put("orderCode", orderCode);
            }
            if(orderType != null){
                conditionMap.put("orderType", orderType);
            }
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

            Map<String, Object> pageMap = cOrderService.listDeliveryByCondition(pageNumber, pageSize, sortName, sortOrder, conditionMap);

            return pageMap;
        } catch (Exception e) {
            log.error("获取店铺订单列表失败![conditionMap="+conditionMap+"]");
            e.printStackTrace();
        }

        return "error";
    }

}
