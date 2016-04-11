package com.masiis.shop.web.mall.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.web.mall.beans.pay.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.mall.service.order.SfOrderPaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by hzz on 2016/4/10.
 *
 */
@Controller
@RequestMapping(value = "orderPay")
public class SfOrderPaymentController {


}
