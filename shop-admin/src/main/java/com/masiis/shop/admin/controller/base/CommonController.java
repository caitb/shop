package com.masiis.shop.admin.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/common")
public class CommonController {

    @RequestMapping("/now")
    @ResponseBody
    public Object now() {
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("time", new Date());
        return dataMap;
    }

}
