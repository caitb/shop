package com.masiis.shop.web.platform.controller.order;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @autor jipengkun
 */
@Controller
@RequestMapping("/border")
public class BOrderController extends BaseController {
    /**
     * @return
     */
    @RequestMapping("/apply")
    public String toIndex(HttpServletRequest request, HttpServletResponse response) {
//        ComUser comUser = (ComUser) request.getSession().getAttribute("userId");
        //String str = request.getSession().getAttribute("comUser").toString();
        return "platform/order/shenqing";
    }
}
