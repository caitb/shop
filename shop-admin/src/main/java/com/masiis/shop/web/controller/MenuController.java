package com.masiis.shop.web.controller;

import com.masiis.shop.dao.pbmenu.PbMenu;
import com.masiis.shop.service.PbMenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 菜单栏
 * Created by ZhaoLiang on 2016/2/18.
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private PbMenuService pbMenuService;

    @RequestMapping("/menu.shtml")
    public String menu(){
        return "menu/menu";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response, Long userID) {

        List<PbMenu> pbMenus = pbMenuService.findByPbUserId(userID);

        return pbMenus;
    }

}
