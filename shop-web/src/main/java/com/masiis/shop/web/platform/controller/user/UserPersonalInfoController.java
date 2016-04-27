package com.masiis.shop.web.platform.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.ComUserExtractwayInfo;
import com.masiis.shop.dao.po.PfUserCertificate;
import com.masiis.shop.web.platform.constants.AuditStatusEnum;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.user.UserPersonalInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by hzz on 2016/3/29.
 */
@Controller
@RequestMapping(value = "personalInfo")
public class UserPersonalInfoController extends BaseController {

    @Resource
    private UserPersonalInfoService userPersonalInfoService;

    /**
     * 获取个人信息的首页信息
     *
     * @author hanzengzhi
     * @date 2016/3/30 17:55
     */
    @RequestMapping(value = "personalHomePageInfo.html")
    public String getPersonalHomePageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        ComUser comUser = getComUser(request);
        Map<String, Object> map = null;
        if (comUser != null) {
            map = userPersonalInfoService.getPersonalHomePageInfo(comUser);
        }
        if (map != null) {
            model.addAttribute("pfSkuAgentDetails", map.get("pfSkuAgentDetails"));
        }
        model.addAttribute("comUser", comUser);
        model.addAttribute("agentLevelIConUrl", map.get("agentLevelIConUrl"));
        model.addAttribute("auditStatusName", AuditStatusEnum.getName(comUser.getAuditStatus()));
        model.addAttribute("comUserAccount", map.get("comUserAccount"));
        return "platform/user/gerenxinxi";
    }


    /**
     * 个人信息--微信号查询
     *
     * @author hanzengzhi
     * @date 2016/3/29 14:02
     */
    @RequestMapping(value = "selectSkuWeChatInfo.do")
    public String selectSkuWeChatInfo(HttpServletRequest request, HttpServletResponse response
            , Model model) {
        ComUser comUser = getComUser(request);
        if (comUser == null) {
            throw new BusinessException("请重新登录");
        }
        List<PfUserCertificate> pfUserCertificates = userPersonalInfoService.selectSkuWeChatInfo(comUser.getId());
        model.addAttribute("pfUserCertificates", pfUserCertificates);
        return "platform/user/weixinhao";
    }

    /**
     * 查看实名认证信息
     *
     * @author hanzengzhi
     * @date 2016/4/1 12:07
     */
    @RequestMapping(value = "lookIdentityAuthInfo.html")
    public String lookIdentityAuthInfo(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(value = "auditStatus", defaultValue = "0") int auditStatus) {
        //重定向到身份认证controller
        return "redirect:identityAuth/toInentityAuthPage.html?auditStatus=" + auditStatus;
    }

    /**
     * 跳转到银行卡界面
     *
     * @author hanzengzhi
     * @date 2016/4/6 19:54
     */
    @RequestMapping(value = "toBankCardPage.html")
    public String toBankCardPage(HttpServletRequest request, HttpServletResponse response) {
        return "platform/user/wodeyinhang";
    }

    /**
     * 个人信息查询银行卡信息
     *
     * @author hanzengzhi
     * @date 2016/3/29 14:14
     */
    @RequestMapping(value = "findBankCardInfoByUserId.do")
    @ResponseBody
    public String findBankCardInfoByUserId(HttpServletRequest request, HttpServletResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ComUser comUser = getComUser(request);
            if (comUser == null) {
                throw new BusinessException();
            }
            List<ComUserExtractwayInfo> comUserExtractwayInfos = userPersonalInfoService.findBankCardInfoByUserId(comUser.getId());
            String returnJson = objectMapper.writeValueAsString(comUserExtractwayInfos);
            return returnJson;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 跳转到新增银行界面
     *
     * @author hanzengzhi
     * @date 2016/4/6 20:38
     */
    @RequestMapping(value = "toAddBankCardPage.html")
    public String toAddBankCardPage(HttpServletRequest request, HttpServletResponse response) {
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return "redirect:" + basePath + "/extractwayinfo/toCreateBankcard.shtml?returnJumpType=1";
    }

    /**
     * 删除银行卡信息
     *
     * @author hanzengzhi
     * @date 2016/3/29 14:49
     */
    @RequestMapping(value = "deleteBankCardInfoById.do")
    @ResponseBody
    public String deleteBankCardInfoById(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(value = "id", required = true) Long id) {
        if (id == null) {
            throw new BusinessException("删除银行卡失败");
        }
        int i = userPersonalInfoService.deleteBankCardInfoById(id);
        if (i == 1) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * 跳转到管理地址界面
     *
     * @author hanzengzhi
     * @date 2016/4/1 12:22
     */
    @RequestMapping(value = "toAddressManagePage.do")
    public String toAddressManagePage(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam(value = "jumpType", required = false, defaultValue = "0") int jumpType) {
        //重定向到管理地址controller
        return "redirect:userAddress/toManageAddressPage.html?jumpType=" + jumpType;
    }
}
