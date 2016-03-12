package com.masiis.shop.admin.controller.order;

import com.masiis.shop.dao.po.PfBorder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by cai_tb on 16/3/12.
 */
@Controller
@RequestMapping("/order/border")
public class PfBorderController {

    @RequestMapping("/list.shtml")
    public String list(){
        return "order/border/list";
    }

    @RequestMapping("/list.do")
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNo,
                       Integer pageSize,
                       PfBorder pfBorder){

        return null;
    }
}
