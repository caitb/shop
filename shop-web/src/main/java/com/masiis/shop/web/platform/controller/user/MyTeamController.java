package com.masiis.shop.web.platform.controller.user;

import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserCertificate;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.user.MyTeamService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
public class MyTeamController extends BaseController {

    private final Log log = LogFactory.getLog(MyTeamController.class);

    @Resource
    private MyTeamService myTeamService;

    @RequestMapping("/teamlist")
    public ModelAndView teamlist(HttpServletRequest request, HttpServletResponse response){
        try {
            ModelAndView mav = new ModelAndView("platform/user/teamList");

            ComUser comUser = getComUser(request);

            List<Map<String, Object>> agentSkuMaps = myTeamService.listAgentSku(comUser.getId());
            Integer totalChild = 0;
            Double totalSales = 0.0;
            for(Map<String, Object> agentSkuMap : agentSkuMaps){
                totalChild += Integer.parseInt(agentSkuMap.get("countChild").toString());
                totalSales += Double.parseDouble(agentSkuMap.get("countSales").toString());
            }
            mav.addObject("totalChild", totalChild);
            mav.addObject("totalSales", totalSales);
            mav.addObject("agentSkuMaps", agentSkuMaps);

            return mav;
        } catch (Exception e){
            log.error("获取代理产品列表失败!");
            e.printStackTrace();

            return null;
        }
    }

    /**
     * 团队列表
     * @param request
     * @param response
     * @param userSkuId
     * @param skuId
     * @return
     */
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
            log.error("获取团队成员列表失败!");
            e.printStackTrace();

            return null;
        }
    }

    /**
     * 团队成员详细信息
     * @param request
     * @param response
     * @param comUserId
     * @param skuId
     * @param agentLevelId
     * @return
     */
    @RequestMapping("/memberinfo")
    public ModelAndView memberInfo(HttpServletRequest request, HttpServletResponse response,
                                   Long comUserId,
                                   Integer skuId,
                                   Integer agentLevelId,
                                   Integer userSkuId){

        try {
            ModelAndView mav = new ModelAndView("platform/user/memberInfo");

            Map<String, Object> memberMap = myTeamService.viewMember(comUserId, skuId, agentLevelId, userSkuId);
            mav.addObject("memberMap", memberMap);

            return mav;
        } catch (Exception e) {
            log.error("获取团队成员信息失败!");
            e.printStackTrace();

            return null;
        }
    }


    @RequestMapping("/toaudit")
    public ModelAndView toAudit(HttpServletRequest request, HttpServletResponse response,
                                Long comUserId,
                                Integer skuId,
                                Integer agentLevelId,
                                Integer userSkuId){

        try {
            ModelAndView mav = new ModelAndView("platform/user/audit");

            Map<String, Object> memberMap = myTeamService.viewMember(comUserId, skuId, agentLevelId, userSkuId);
            mav.addObject("memberMap", memberMap);

            return mav;
        } catch (Exception e) {
            log.error("获取被审核人信息失败!");
            e.printStackTrace();

            return null;
        }
    }

    /**
     * 证书审核
     * @param request
     * @param response
     * @param userSkuId
     * @param pfUserCertificateId
     * @param status
     * @param reason
     * @return
     */
    @RequestMapping("/audit")
    @ResponseBody
    public Object audit(HttpServletRequest request, HttpServletResponse response,
                        Integer userSkuId,
                        Long pfUserCertificateId,
                        Integer status,
                        String reason){

        try {
            myTeamService.audit(userSkuId, pfUserCertificateId, status, reason, request.getServletContext().getRealPath("/"));
            return "success";
        } catch (Exception e) {
            log.error("审核失败![userSkuId="+userSkuId+"][pfUserCertificateId="+pfUserCertificateId+"][status="+status+"][reason="+reason+"]");
            e.printStackTrace();
            return "error";
        }
    }
}
