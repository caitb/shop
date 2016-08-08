package com.masiis.shop.admin.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by admin on 2016/8/8.
 */
@Controller
@RequestMapping("/userSkuStock")
public class UserSkuStockController {



    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                              Integer pageNumber,
                              Integer pageSize,
                              String sortName,
                              String sortOrder,
                              Integer orderStatus) {

        return null;

    }
}
