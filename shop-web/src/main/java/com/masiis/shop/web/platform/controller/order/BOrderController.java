package com.masiis.shop.web.platform.controller.order;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @autor jipengkun
 */
@Controller
@RequestMapping("/border")
public class BOrderController extends BaseController {

    /**
     * 合伙人申请
     * @return
     */
    @RequestMapping("/apply")
    public String partnersApply(HttpServletRequest request,
                          HttpServletResponse response,
                          Long skuId) {
        ComUser comUser = (ComUser) request.getSession().getAttribute("userId");
        //String str = request.getSession().getAttribute("comUser").toString();
        return "platform/order/shenqing";
    }
    @RequestMapping("/register")
    public String partnersRegister(HttpServletRequest request,
                                   HttpServletResponse response){

        return "";
    }
}
