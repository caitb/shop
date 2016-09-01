package com.masiis.shop.api.controller.pay.alipay;

import com.masiis.shop.api.bean.base.BaseReq;
import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.dao.po.ComUser;
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
    @SignValid(paramType = BaseReq.class)
    public BaseRes getAlipayOrderStr(HttpServletRequest request, ComUser user){


        return null;
    }
}
