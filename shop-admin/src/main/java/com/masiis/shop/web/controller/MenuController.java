package com.masiis.shop.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.dao.menu.BMenu;
import com.masiis.shop.dao.menu.BUserMenu;
import com.masiis.shop.service.menu.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhaoLiang on 2016/2/18.
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    @RequestMapping("/userMenu")
    public String load(HttpServletRequest request, HttpServletResponse response, Long userID) {
        List<BUserMenu> list = menuService.getUserMenu(userID);
        List<Long> menus = new ArrayList();
        for (BUserMenu bm : list) {
            menus.add(bm.getMenuId());
        }
        request.setAttribute("userID", userID);
        request.setAttribute("menuIds", org.apache.commons.lang.StringUtils.join(menus, ','));
        return "menu/userMenu";
    }

    @RequestMapping("/getTreeData")
    @ResponseBody
    public String getTreeData(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException {
        //JSONObject res = new JSONObject();
        List<BMenu> data = menuService.getData();
//        if(null==data){
//            res.put("isError", true);
//            res.put("Message", "获取数据失败");
//        }else{
//            res.put("isError", false);
//            res.put("Message", "成功");
//            res.put("data", new ObjectMapper().writeValueAsString(data));
//        }
        return new ObjectMapper().writeValueAsString(data);
    }

    @RequestMapping("/getUserMenu")
    @ResponseBody
    public String getUserMenu(HttpServletRequest request, HttpServletResponse response, Long userID) {
        List<BUserMenu> list = menuService.getUserMenu(userID);
        List<Long> menus = new ArrayList();
        for (BUserMenu bm : list) {
            menus.add(bm.getMenuId());
        }
        return org.apache.commons.lang.StringUtils.join(menus, ',');
    }

    @RequestMapping("/save")
    @ResponseBody
    public String updateUserMenu(HttpServletRequest request, HttpServletResponse response, Long userID, String menuIDs) {
        menuService.updateUserMenu(userID, menuIDs.split(","));
        JSONObject res = new JSONObject();
        res.put("isError", false);
        return res.toJSONString();
    }
}
