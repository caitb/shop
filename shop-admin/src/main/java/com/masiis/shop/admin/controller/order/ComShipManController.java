package com.masiis.shop.admin.controller.order;

import com.masiis.shop.admin.service.order.ComShipManService;
import com.masiis.shop.dao.po.ComShipMan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by cai_tb on 16/3/16.
 */
@Controller
@RequestMapping("/comshipman")
public class ComShipManController {

    @Resource
    private ComShipManService comShipManService;

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response){
        List<ComShipMan> comShipManList = comShipManService.listByCondition(new ComShipMan());

        return comShipManList;
    }
}
