package com.masiis.shop.admin.controller.order;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.admin.service.order.TrialOrderService;
import com.masiis.shop.admin.service.product.SkuService;
import com.masiis.shop.admin.service.user.ComUserService;
import com.masiis.shop.dao.beans.order.TrialOrder;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cai_tb on 16/3/12.
 */
@Controller
@RequestMapping("/order/trial")
public class TrialOrderController {

    @Resource
    private TrialOrderService trialOrderService;
    @Resource
    private SkuService skuService;

    @Resource
    private ComUserService comUserService;

    @RequestMapping("/list.shtml")
    public String list()throws Exception{
        return "/order/trial/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object listShow(HttpServletRequest request, HttpServletResponse response,
                           String beginTime,
                           String endTime,
                           String ctName,
                           String mobile,
                           String agentLevel,
                           String sort,
                           String order,
                           Integer offset,
                           Integer limit
    )throws Exception{
        offset = offset==null ? 0 : offset;
        limit  = limit ==null ? 10 : limit;
        Integer pageNo = offset/limit + 1;
        PageHelper.startPage(pageNo, limit);
        TrialOrder trialOrder = new TrialOrder();
        List<TrialOrder> trialOrders = new ArrayList<TrialOrder>();
        List<PfCorder> pfCorders = trialOrderService.queryAll();
        for (PfCorder pfCorder:pfCorders) {
            ComSku comSku = skuService.findById(pfCorder.getSkuId());
            ComUser comUser = comUserService.findById(pfCorder.getUserId());
            PfCorderConsignee pfCorderConsignee = trialOrderService.findPfCorderConsignee(pfCorder.getId());
            PfCorderPayment pfCorderPayment = trialOrderService.findPfCorderPayment(pfCorder.getId());
            trialOrder.setPfCorder(pfCorder);//名单信息
            trialOrder.setUserName(comUser.getRealName());//买家
            trialOrder.setOrderName(comSku.getName());//商品名
            trialOrder.setOrderNum(1);//数量
            trialOrder.setConsigneeName(pfCorderConsignee.getConsignee());//收货人
            trialOrder.setPfCorderPayment(pfCorderPayment);//支付订单
        }
        return "/order/trial/list";
    }
}
