package com.masiis.shop.web.platform.controller.order;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.order.BorderSkuStockService;
import com.masiis.shop.web.platform.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wangbingjian on 2016/3/30.
 */
@Controller
@RequestMapping(value = "payEnd")
public class OrderPayEndController extends BaseController {
    private final static Logger log = Logger.getLogger(OrderPayEndController.class);

    @Resource
    private BOrderService bOrderService;
    @Resource
    private BorderSkuStockService borderSkuStockService;
    @Resource
    private UserService userService;
    /**
     * 补货订单支付完成
     * @param bOrderId    订单编码
     * @param request
     * created by wangbingjian
     */
    @RequestMapping(value = "replenishment.shtml")
    public ModelAndView replenishmentOrderPaycompletion(@RequestParam(value = "bOrderId",required = true) Long bOrderId,
                                                        HttpServletRequest request)throws Exception{

        log.info("进入补货订单支付完成");
        ModelAndView mv = new ModelAndView();
        PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
        String skuImg = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        List<PfBorderItem> items = bOrderService.getPfBorderItemDetail(pfBorder.getId());
        List<PfBorderItem> its = null;
        Integer sumQuantity = 0;
        PfUserSkuStock pfUserSkuStock;
        for (PfBorderItem pfBorderItem:items){
            sumQuantity += pfBorderItem.getQuantity();
            pfUserSkuStock = borderSkuStockService.getUserSkuStockByUserIdAndSkuId(bOrderId,pfBorderItem.getSkuId());
            pfBorderItem.setRealStock(pfUserSkuStock.getStock()-pfUserSkuStock.getFrozenStock());
            its.add(pfBorderItem);
        }
        mv.addObject("pfBorder",pfBorder);
        mv.addObject("pfBorderItems",its);
        mv.addObject("skuImg",skuImg);
        mv.addObject("sumQuantity",sumQuantity);
        //sendtype  1:平台代发货  2:自己发货  0:未选择发货类型
        //orderType 1:补货 2:拿货 0:代理
        if (pfBorder.getSendType() == 2||pfBorder.getOrderType() == 2){
            PfBorderConsignee pfBorderConsignee = bOrderService.findpfBorderConsignee(pfBorder.getId());
            mv.addObject("pfBorderConsignee",pfBorderConsignee);
        }
        mv.setViewName("platform/order/ReplenishmentPayments");
        return mv;
    }
}
