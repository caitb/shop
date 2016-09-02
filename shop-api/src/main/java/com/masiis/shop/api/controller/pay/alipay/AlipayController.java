package com.masiis.shop.api.controller.pay.alipay;

import com.masiis.shop.api.bean.base.BaseReq;
import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.bean.pay.GetAlipayOrderStrReq;
import com.masiis.shop.api.bean.pay.GetAlipayOrderStrRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfBorder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Date 2016/5/19
 * @Auther lzh
 */
@Controller
@RequestMapping("/alipay")
public class AlipayController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());


    @RequestMapping("/getOrderStr")
    @ResponseBody
    @SignValid(paramType = GetAlipayOrderStrReq.class)
    public GetAlipayOrderStrRes getAlipayOrderStr(HttpServletRequest request, GetAlipayOrderStrReq req, ComUser user){
        GetAlipayOrderStrRes res = new GetAlipayOrderStrRes();
        try {
            String orderCode = req.getOrderCode();
            if(StringUtils.isBlank(orderCode)){
                res.setResCode(SysResCodeCons.RES_CODE_ALIPAY_ORDERCODE_NULL);
                res.setResMsg(SysResCodeCons.RES_CODE_ALIPAY_ORDERCODE_NULL_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_ALIPAY_ORDERCODE_NULL_MSG);
            }



        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }

        return res;
    }
}
