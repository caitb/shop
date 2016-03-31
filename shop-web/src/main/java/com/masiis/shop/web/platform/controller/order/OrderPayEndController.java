package com.masiis.shop.web.platform.controller.order;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderConsignee;
import com.masiis.shop.dao.po.PfBorderItem;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
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
    private UserService userService;
    /**
     * 补货订单支付完成
     * @param borderCode    订单编码
     * @param request
     * created by wangbingjian
     */
//    @RequestMapping(value = "replenishment")
//    @ResponseBody
//    public ModelAndView replenishmentOrderPaycompletion(@RequestParam(value = "borderCode",required = true) String borderCode,
//                                                        HttpServletRequest request)throws Exception{
//
//        log.info("进入补货订单支付完成");
//        ComUser user = getComUser(request);
//        if (user == null){
//            user = userService.getUserByOpenid("oUIwkwgLzn8CKMDrvbCSE3T-u5fs");
//        }
//        ModelAndView mv = new ModelAndView();
//        PfBorder pfBorder = bOrderService.findByOrderCode(borderCode);
//        String skuImg = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
//        List<PfBorderItem> items = bOrderService.getPfBorderItemDetail(pfBorder.getId());
//        Integer sumQuantity = 0;
//        for (PfBorderItem pfBorderItem:items){
//            sumQuantity += pfBorderItem.getQuantity();
//        }
//        mv.addObject("pfBorder",pfBorder);
//        mv.addObject("pfBorderItems",items);
//        mv.addObject("skuImg",skuImg);
//        mv.addObject("sumQuantity",sumQuantity);
//        //sendtype  1:平台代发货  2:自己发货  0:未选择发货类型
//        if (pfBorder.getSendType() == 1){
//            PfBorderConsignee pfBorderConsignee = bOrderService.findpfBorderConsignee(pfBorder.getId());
//            mv.addObject("pfBorderConsignee",pfBorderConsignee);
//        }
//        mv.setViewName("platform/order/ReplenishmentPayments");
//        return mv;
//    }
}
