package com.masiis.shop.web.platform.controller.order;

import com.masiis.shop.web.platform.controller.base.BaseController;
import org.springframework.jdbc.core.metadata.CallMetaDataContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Controller
@RequestMapping("/corder")
public class COrderController extends BaseController {

    /**
     * 
     * @author ZhaoLiang
     * @date 2016/3/5 13:51
     */
    @RequestMapping("/index")
    public String toIndex(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute("userId", "1");
        return "index";
    }
}
