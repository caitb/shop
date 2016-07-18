package com.masiis.shop.admin.controller.base;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.format.annotation.DateTimeFormat;
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
        Date date = new Date();
        String format = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("time", date.getTime());
        dataMap.put("format", format);
        return dataMap;
    }

}
