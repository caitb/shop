package com.masiis.shop.web.platform.controller.order;

import com.masiis.shop.common.enums.promotion.SfTurnTableRuleTypeEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderConsignee;
import com.masiis.shop.dao.po.PfBorderItem;
import com.masiis.shop.dao.po.PfUserSkuStock;
import com.masiis.shop.common.constant.platform.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.order.BOrderSkuStockService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.promotion.cpromotion.service.gorder.SfTurnTableRuleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private BOrderSkuStockService borderSkuStockService;
    @Resource
    private UserService userService;
    @Resource
    private SfTurnTableRuleService turnTableRuleService;


    /**
     * 补货订单支付完成
     *
     * @param bOrderId 订单id
     * @param request  created by wangbingjian
     */
    @RequestMapping(value = "replenishment.shtml")
    public ModelAndView replenishmentOrderPaycompletion(@RequestParam(value = "bOrderId", required = true) Long bOrderId,
                                                        HttpServletRequest request) throws Exception {

        if (getComUser(request) == null) {
            throw new BusinessException("请重新登录");
        }
        log.info("进入补货订单支付完成");
        ModelAndView mv = new ModelAndView();
        PfBorder pfBorder = bOrderService.getPfBorderById(bOrderId);
        String skuImg = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
        List<PfBorderItem> items = bOrderService.getPfBorderItemDetail(pfBorder.getId());
        List<PfBorderItem> its = new ArrayList<>();
        Integer sumQuantity = 0;
        PfUserSkuStock pfUserSkuStock;
        for (PfBorderItem pfBorderItem : items) {
            sumQuantity += pfBorderItem.getQuantity();
            pfUserSkuStock = borderSkuStockService.getUserSkuStockByUserIdAndSkuId(pfBorder.getUserId(), pfBorderItem.getSkuId());
            pfBorderItem.setRealStock(pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock());
            its.add(pfBorderItem);
        }
        mv.addObject("pfBorder", pfBorder);
        mv.addObject("pfBorderItems", its);
        mv.addObject("skuImg", skuImg);
        mv.addObject("sumQuantity", sumQuantity);
        //sendtype  1:平台代发货  2:自己发货  0:未选择发货类型
        //orderType 1:补货 2:拿货 0:代理
        if (pfBorder.getSendType() == 2 || pfBorder.getOrderType() == 2) {
            PfBorderConsignee pfBorderConsignee = bOrderService.findpfBorderConsignee(pfBorder.getId());
            mv.addObject("pfBorderConsignee", pfBorderConsignee);
        }
        Map<String,String> map = turnTableRuleService.isTurnTableRule(SfTurnTableRuleTypeEnum.B.getCode());
        String bl = map.get("isTurnTableRule");
        mv.addObject("isTurnTableRule",bl);
        if (bl.equals("true")){
            mv.addObject("turnTableRuleTimes", map.get("turnTableRuleTimes"));
        }else{
            mv.addObject("turnTableRuleTimes", map.get("turnTableRuleTimes"));
        }
            mv.setViewName("platform/order/previeworder");
        return mv;
    }
}
