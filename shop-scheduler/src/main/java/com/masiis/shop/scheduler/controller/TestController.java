package com.masiis.shop.scheduler.controller;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lzh on 2016/3/22.
 */
@Controller
@RequestMapping("/task")
public class TestController {

    @Resource
    private ComUserMapper userMapper;

    @RequestMapping("testvisit")
    @ResponseBody
    public String testVisit(){
        List<ComUser> list = userMapper.selectAll();
        return JSONObject.toJSONString(list);
    }
}
