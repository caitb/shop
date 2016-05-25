package com.masiis.shop.api.controller.user;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.api.bean.user.TeamDetailReq;
import com.masiis.shop.api.bean.user.TeamDetailRes;
import com.masiis.shop.api.bean.user.TeamListReq;
import com.masiis.shop.api.bean.user.TeamListRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.service.user.ComUserAccountService;
import com.masiis.shop.api.service.user.ComUserService;
import com.masiis.shop.api.service.user.TeamService;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/5/24.
 */
@Controller
@RequestMapping("/team")
public class TeamController {

    private final static Log log = LogFactory.getLog(TeamController.class);

    @Resource
    private TeamService teamService;
    @Resource
    private ComUserAccountService comUserAccountService;
    @Resource
    private ComUserService comUserService;

    @RequestMapping("/teamList")
    @ResponseBody
    @SignValid(paramType = TeamListReq.class, hasToken = false)
    public TeamListRes teamList(HttpServletRequest request, TeamListReq teamListReq, ComUser comUser){

        TeamListRes teamListRes = new TeamListRes();

        try {
            List<Map<String, Object>> agentSkuMaps = teamService.listAgentSku(comUser.getId());
            ComUserAccount comUserAccount = comUserAccountService.findAccountByUserid(comUser.getId());

            Integer countTeamMember = 0;
            BigDecimal totalIncomeFee = new BigDecimal("0");
            for(Map<String, Object> agentSkuMap : agentSkuMaps){
                countTeamMember += (Integer)agentSkuMap.get("countTeamMember");
                totalIncomeFee = totalIncomeFee.add((BigDecimal)agentSkuMap.get("totalIncomeFee"));
            }
            teamListRes.setCountTeamMember(countTeamMember);
            if(agentSkuMaps.size()==1) teamListRes.setTotalIncomeFee(totalIncomeFee);
            if(agentSkuMaps.size()>1) teamListRes.setTotalIncomeFee(totalIncomeFee.subtract(comUserAccount.getTotalIncomeFee().multiply(new BigDecimal(agentSkuMaps.size()-1))));
            teamListRes.setCountAgentSku(agentSkuMaps.size());
            teamListRes.setAgentSkuMaps(agentSkuMaps);
            teamListRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            teamListRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error("获取团队列表失败![teamListReq="+teamListReq+"][comUser="+comUser+"]");
            e.printStackTrace();
        }

        return teamListRes;
    }

    @RequestMapping("/teamDetail")
    @ResponseBody
    @SignValid(paramType = TeamDetailReq.class, hasToken = false)
    public TeamDetailRes teamDetail(HttpServletRequest request, TeamDetailReq teamDetailReq, ComUser comUser){

        TeamDetailRes teamDetailRes = new TeamDetailRes();

        try {
            Map<String, Object> teamMap = teamService.teamDetail(teamDetailReq.getUserSkuId());
            teamDetailRes.setTeamMap(teamMap);
            teamDetailRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            teamDetailRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error("获取团队明细出错![teamDetailReq="+teamDetailReq+"][comUser="+comUser+"]");
            e.printStackTrace();
        }

        return teamDetailRes;
    }


}
