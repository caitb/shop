package com.masiis.shop.api.controller.pay.wxpay;

import com.masiis.shop.api.bean.pay.WxPayPrepareBOrderReq;
import com.masiis.shop.api.bean.pay.WxPayPrepareBOrderRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.pay.wxpay.WxPayService;
import com.masiis.shop.dao.po.ComUser;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Date 2016/5/19
 * @Auther lzh
 */
@Controller
@RequestMapping("/wxpay")
public class WxPayController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private WxPayService wxPayService;

    @RequestMapping("/preorder")
    @ResponseBody
    @SignValid(paramType = WxPayPrepareBOrderReq.class)
    public WxPayPrepareBOrderRes createPrepareBOrder(HttpServletRequest request,
                            WxPayPrepareBOrderReq req, ComUser user){
        WxPayPrepareBOrderRes res = new WxPayPrepareBOrderRes();
        String orderCode = req.getOrderCode();
        if(StringUtils.isBlank(orderCode)){
            res.setResCode(SysResCodeCons.RES_CODE_WXPAY_ORDERCODE_NULL);
            res.setResMsg(SysResCodeCons.RES_CODE_WXPAY_ORDERCODE_NULL_MSG);
            return res;
        }
        String orderType = orderCode.substring(0, 1);
        //wxPayService.createPrepareBOrderByOrderCode(orderCode, res);

        return null;
    }
}
