package com.masiis.shop.admin.controller.order;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.beans.order.Order;
import com.masiis.shop.admin.controller.base.BaseController;
import com.masiis.shop.admin.service.order.*;
import com.masiis.shop.admin.service.system.DictionaryService;
import com.masiis.shop.dao.po.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/3/12.
 */
@Controller
@RequestMapping("/order/border")
public class PfBorderController extends BaseController {

    private final static Log log = LogFactory.getLog(PfBorderController.class);

    @Resource
    private BOrderService bOrderService;
    @Resource
    private OrderQueueDealService orderQueueDealService;
    @Resource
    private BOrderPaymentService bOrderPaymentService;
    @Resource
    private BOrderPayService bOrderPayService;
    @Resource
    private DictionaryService dictionaryService;
    @Resource
    private BorderItemService borderItemService;
    @Resource
    private UserSkuStockService userSkuStockService;

    @RequestMapping("/list.shtml")
    public String list() {
        return "order/border/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortName,
                       String sortOrder,
                       PfBorder pfBorder,
                       Integer payTypeId
    ) {
        try {
            Map<String, Object> pageMap = bOrderService.listByCondition(pageNumber, pageSize, sortName, sortOrder, pfBorder, payTypeId);
            if (pfBorder.getOrderType() == null) {
                List<ComDictionary> orderTypeList = dictionaryService.pickListOfBaseData("PF_BORDER_TYPE");//订单类型
                pageMap.put("orderTypeList", orderTypeList);
            }
            if (pfBorder.getShipType() == null) {
                List<ComDictionary> payTypeList = dictionaryService.pickListOfBaseData("COM_USER_PAY_TYPE");//支付方式
                pageMap.put("payTypeList", payTypeList);
            }
            if (pfBorder.getOrderStatus() == null) {
                List<ComDictionary> orderStatusList = dictionaryService.pickListOfBaseData("PF_BORDER_STATUS");//订单状态
                pageMap.put("orderStatusList", orderStatusList);
            }
            if (payTypeId == null) {
                List<ComDictionary> wuliuList = dictionaryService.pickListOfBaseData("PF_BORDER_SHIP_STATUS");//物流状态
                pageMap.put("wuliuList", wuliuList);
            }
            return pageMap;
        } catch (Exception e) {
            log.error("查询合伙人列表失败![pfBorder=" + pfBorder + "]");
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping("detail.shtml")
    public ModelAndView detail(HttpServletRequest request, HttpServletResponse response, Long borderId) {
        try {
            ModelAndView mav = new ModelAndView("order/border/detail");

            Order order = bOrderService.find(borderId);

            mav.addObject("order", order);

            return mav;
        } catch (Exception e) {
            log.error("查看合伙人订单明细失败![borderId=" + borderId + "]");
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping("/offline/list.shtml")
    public String offlineList() {
        return "order/border/offlinePayList";
    }

    /**
     * 合伙人线下支付订单
     *
     * @param request
     * @param response
     * @param pageNumber
     * @param pageSize
     * @param sortOrder
     * @param pfBorder
     * @return
     */
    @RequestMapping("/offline/list.do")
    @ResponseBody
    public Object offlineList(HttpServletRequest request, HttpServletResponse response,
                              Integer pageNumber,
                              Integer pageSize,
                              String sortName,
                              String sortOrder,
                              Integer orderStatus,
                              PfBorder pfBorder) {

        try {
            pfBorder.setOrderStatus(9);
            Map<String, Object> pageMap = bOrderService.listByCondition(pageNumber, pageSize, sortName, sortOrder, pfBorder, null);
            if (pfBorder.getShipStatus() == null) {
                List<ComDictionary> wuliuList = dictionaryService.pickListOfBaseData("PF_BORDER_SHIP_STATUS");//物流状态
                pageMap.put("wuliuList", wuliuList);
            }
            if (orderStatus == null) {
                List<ComDictionary> comDictionaryList = dictionaryService.pickListOfBaseData("PF_BORDER_STATUS");//订单状态
                pageMap.put("orderStatusList", comDictionaryList);
            }
            return pageMap;
        } catch (Exception e) {
            log.error("查询合伙人线下支付订单列表失败![pfBorder=" + pfBorder + "]");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 合伙人线下支付订单收款确认
     *
     * @param request
     * @param response
     * @param bOrderId   合伙人订单ID
     * @param outOrderId 银行流水号
     * @param payAmount  实付金额
     * @return
     */
    @RequestMapping("/offline/Receipt.do")
    @ResponseBody
    public Object Receipt(HttpServletRequest request, HttpServletResponse response, Long bOrderId, String outOrderId, BigDecimal payAmount) {
        Map<String, String> resultMap = new HashMap<>();

        try {
            PfBorder pfBorder = bOrderService.findById(bOrderId);
            if (pfBorder == null) {
                resultMap.put("result_code", "1");
                resultMap.put("result_msg", "此订单不存在!");
                return resultMap;
            }
            if (payAmount.compareTo(pfBorder.getReceivableAmount())==-1 && pfBorder.getOrderType().intValue() != 0) {
                resultMap.put("result_code", "1");
                resultMap.put("result_msg", "此订单不是代理订单,必须全额支付!");
                return resultMap;
            }

            PfBorderPayment borderPayment = bOrderPaymentService.findOfflinePayByBOrderId(bOrderId);

            bOrderPayService.payBOrderOffline(borderPayment, outOrderId, payAmount, request.getServletContext().getRealPath("/"), getPbUser(request));
            resultMap.put("result_code", "0");
            resultMap.put("result_msg", "确认收款成功!");

            return resultMap;
        } catch (Exception e) {
            log.error("合伙人线下支付收款确认失败![bOrderId=" + bOrderId + "][outOrderId=" + outOrderId + "]");
            e.printStackTrace();

            resultMap.put("result_code", "1");
            resultMap.put("result_msg", "操作异常!");
            return resultMap;
        }

    }

    /**
     * 处理排单
     *
     * @param request
     * @param response
     * @param borderId 订单id
     * @param sendType 发货类型
     * @return
     */
    @RequestMapping("/scheduling.do")
    @ResponseBody
    public Object scheduling(HttpServletRequest request, HttpServletResponse response, Long borderId, String sendType) {

        Map<Long, String> orderMap = new HashMap<>();
        try {
            if (borderId == null || sendType == null) {
                throw new RuntimeException("参数有误!不知道处理哪个订单![orderMap=" + orderMap + "]");
            }
            orderMap.put(borderId, sendType);

            orderQueueDealService.commonQueuingOrder(orderMap);

            return "success";
        } catch (Exception e) {
            log.error("处理排单出错了![orderMap=" + orderMap + "]");
            e.printStackTrace();
        }

        return "error";
    }

    /**
     * 发货
     *
     * @param request
     * @param response
     * @param pfBorderFreight
     * @return
     */
    @RequestMapping("/delivery.do")
    @ResponseBody
    public Object delivery(HttpServletRequest request, HttpServletResponse response,
                           PfBorderFreight pfBorderFreight) {

        try {
            if (pfBorderFreight.getShipManId() == null) {
                return "请选择一个快递";
            }
            if (StringUtils.isBlank(pfBorderFreight.getFreight())) {
                return "请填写运单号";
            }

            PfBorder pfBorder = bOrderService.findById(pfBorderFreight.getPfBorderId());
            if(pfBorder == null){
                return "此订单不存在!";
            }

            PfBorderItem pfBorderItem = borderItemService.findByBorderId(pfBorder.getId());
            PfUserSkuStock pfUserSkuStock = userSkuStockService.findByUserIdAndSkuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
            if(pfUserSkuStock.getStock()-pfBorderItem.getQuantity()<0){
                return "库存不足,请补货!";
            }

            bOrderService.delivery(pfBorderFreight, getPbUser(request));

            return "success";
        } catch (Exception e) {
            log.error("合伙人订单发货失败![pfBorderFreight=" + pfBorderFreight + "]");
            e.printStackTrace();
        }

        return "error";
    }

}
