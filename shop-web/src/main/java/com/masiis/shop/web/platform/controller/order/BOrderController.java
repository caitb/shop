package com.masiis.shop.web.platform.controller.order;

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
        Long userId = Long.parseLong(request.getSession().getAttribute("userId").toString());
        return "platform/order/shenqing";
    }
}
