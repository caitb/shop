package com.masiis.shop.web.platform.controller.user;

import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.web.platform.service.user.MyTeamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by cai_tb on 16/3/16.
 */
@Controller
@RequestMapping("/myteam")
public class MyTeamController {

    @Resource
    private MyTeamService myTeamService;

    @RequestMapping("/teamlist")
    public ModelAndView teamlist(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("platform/user/teamlist");

        List<ComSku> agentSkus = myTeamService.listAgentSku(2L);
        mav.addObject("agentSkus", agentSkus);

        return mav;
    }
}
