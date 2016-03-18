package com.masiis.shop.web.platform.controller.user;

import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.web.platform.service.user.MyTeamService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/3/16.
 */
@Controller
@RequestMapping("/myteam")
public class MyTeamController {

    private final Log log = LogFactory.getLog(MyTeamController.class);

    @Resource
    private MyTeamService myTeamService;

    @RequestMapping("/teamlist")
    public ModelAndView teamlist(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("platform/user/teamList");

        List<Map<String, Object>> agentSkuMaps = myTeamService.listAgentSku(6L);
        mav.addObject("agentSkuMaps", agentSkuMaps);

        return mav;
    }

    @RequestMapping("/teamdetail")
    public ModelAndView teamDetail(HttpServletRequest request, HttpServletResponse response,
                             Integer userSkuId,
                             Integer skuId){

        try {
            ModelAndView mav = new ModelAndView("platform/user/teamDetail");

            Map<String, Object> teamMaps = myTeamService.findTeam(userSkuId, skuId);

            mav.addObject("teamMaps", teamMaps);
            return mav;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取团队成员列表失败!");

            return null;
        }
    }
}
