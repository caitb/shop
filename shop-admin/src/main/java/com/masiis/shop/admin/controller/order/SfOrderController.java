package com.masiis.shop.admin.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.admin.beans.order.Order;
import com.masiis.shop.admin.controller.base.BaseController;
import com.masiis.shop.admin.service.order.OrderService;
import com.masiis.shop.admin.service.system.DictionaryService;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.po.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺订单
 * Created by cai_tb on 16/4/14.
 */
@Controller
@RequestMapping("/order/order")
public class SfOrderController extends BaseController {

    private final static Log log = LogFactory.getLog(SfOrderController.class);

    @Resource
    private OrderService orderService;
    @Resource
    private DictionaryService dictionaryService;


    @RequestMapping("/list.shtml")
    public String list(){
        return "order/order/list";
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
                       Integer  shipStatus,
                       Integer isCounting
                       ){

        Map<String, Object> conditionMap = new HashMap<>();
        try {
            if (shipStatus!=null){
                conditionMap.put("shipStatus",shipStatus);
            }
            if (orderStatus!=null){
                conditionMap.put("orderStatus",orderStatus);
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

    /**
     * 订单详情
     * @param request
     * @param response
     * @param orderId
     * @return
     */
    @RequestMapping("/detail.shtml")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, Long orderId){
        try {
            ModelAndView mav = new ModelAndView("order/order/detail");

            Order order = orderService.find(orderId);

            mav.addObject("order", order);

            return mav;
        } catch (Exception e) {
            log.error("查看合伙人订单明细失败![orderId="+orderId+"]");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 平台代理小铺发货
     * @param request
     * @param response
     * @param sfOrderFreight
     * @return
     */
    @RequestMapping("/delivery.do")
    @ResponseBody
    public Object delivery(HttpServletRequest request, HttpServletResponse response,
                           SfOrderFreight sfOrderFreight){

        try {
            if (sfOrderFreight.getShipManId() == null){
                return "请选择一个快递";
            }
            if(StringUtils.isBlank(sfOrderFreight.getFreight())){
                return "请填写运单号";
            }

            orderService.delivery(sfOrderFreight, (PbUser)request.getSession().getAttribute("pbUser"));

            return "success";
        } catch (Exception e) {
            log.error("合伙人订单发货失败![sfOrderFreight="+sfOrderFreight+"]");
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping("/sfOrderRefund.do")
    @ResponseBody
    public String sfOrderRefund(String orderId){
        JSONObject res = new JSONObject();
        try {
            // 校验参数
            Long oid = checkParam(orderId, res);

            // 退货service执行
            orderService.sfOrderRefund(oid, res);
        } catch (Exception e) {
            log.error("小铺订单退货失败:" + e.getMessage());
            if(!(e instanceof BusinessException)){
                res.put("resCode", -1);
                res.put("resMsg", "网络错误");
            }
        }
        if(StringUtils.isBlank(res.getString("resCode"))){
            res.put("resCode", -1);
            res.put("resMsg", "网络错误");
        }
        return res.toJSONString();
    }

    private Long checkParam(String orderId, JSONObject res) {
        if (StringUtils.isBlank(orderId)) {
            res.put("resCode", 1);
            res.put("resMsg", "参数为空");
            throw new BusinessException("参数为空");
        }
        Long oid = null;
        try {
            oid = Long.valueOf(orderId);
        } catch (Exception e) {
            res.put("resCode", 2);
            res.put("resMsg", "参数格式不正确");
            throw new BusinessException("参数格式不正确");
        }

        log.info("参数校验通过,orderId:" + orderId);

        return oid;
    }
}
