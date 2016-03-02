package com.masiis.shop.admin.controller.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.admin.service.system.PbMenuService;
import com.masiis.shop.admin.service.system.PbUserMenuService;
import com.masiis.shop.dao.generate.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 菜单栏
 * Created by ZhaoLiang on 2016/2/18.
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private PbMenuService pbMenuService;
    @Resource
    private PbUserMenuService pbUserMenuService;

    @RequestMapping("/menu.shtml")
    public String menu(){
        return "menu/menu";
    }

    @RequestMapping("/treeMenu.shtml")
    public ModelAndView treeMenu(HttpServletRequest request, HttpServletResponse response, Long userId) throws JsonProcessingException {

        ModelAndView mav = new ModelAndView("menu/tree_menu");

        List<PbMenu> pbMenus = pbMenuService.findByExample(new PbMenuExample());
        String pbMenusJson = objectMapper.writeValueAsString(pbMenus);

        PbUserMenuExample pbUserMenuExample = new PbUserMenuExample();
        PbUserMenuExample.Criteria criteria = pbUserMenuExample.createCriteria();
        criteria.andPbUserIdEqualTo(userId);
        List<PbUserMenu> pbUserMenus = pbUserMenuService.findByExample(pbUserMenuExample);
        String pbUserMenusJson = objectMapper.writeValueAsString(pbUserMenus);

        mav.addObject("pbMenusJson", pbMenusJson);
        mav.addObject("pbUserMenusJson", pbUserMenusJson);
        mav.addObject("userId", userId);

        return mav;
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response){

        HttpSession session = request.getSession();
        PbUser pbUser = (PbUser)session.getAttribute("pbUser");
        List<PbMenu> pbMenus = pbMenuService.findByPbUserId(pbUser.getId());

        return pbMenus;
    }

}
