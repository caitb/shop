package com.masiis.shop.web.platform.controller.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserExtractwayInfo;
import com.masiis.shop.dao.po.PfUserCertificate;
import com.masiis.shop.web.platform.constants.AuditStatusEnum;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.user.UserPersonalInfoService;
import com.masiis.shop.web.platform.service.user.UserService;
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
@RequestMapping(value ="personalInfo" )
public class UserPersonalInfoController extends BaseController {

    @Resource
    private UserPersonalInfoService userPersonalInfoService;

    /**
     * 获取个人信息的首页信息
     * @author hanzengzhi
     * @date 2016/3/30 17:55
     */
    @RequestMapping(value = "personalHomePageInfo.html")
    public String getPersonalHomePageInfo(HttpServletRequest request ,HttpServletResponse response,Model model){
        ComUser comUser = getComUser(request);
        Map<String,Object> map = null;
        if (comUser!=null){
            map = userPersonalInfoService.getPersonalHomePageInfo(comUser);
        }
        if (map!=null){
            model.addAttribute("pfskuAgents",map.get("pfskuAgents"));
        }
        model.addAttribute("comUser",comUser);
        model.addAttribute("auditStatusName", AuditStatusEnum.getName(comUser.getAuditStatus()));
        return "platform/user/gerenxinxi";
    }


    /**
     * 个人信息--微信号查询
     * @author hanzengzhi
     * @date 2016/3/29 14:02
     */
    @RequestMapping(value = "selectSkuWeChatInfo.do")
    @ResponseBody
    public String selectSkuWeChatInfo(HttpServletRequest request, HttpServletResponse response
            , Model model){
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw  new BusinessException("请重新登录");
        }
        comUser.setId(1L);
        List<PfUserCertificate> pfUserCertificates = userPersonalInfoService.selectSkuWeChatInfo(comUser.getId());
        model.addAttribute("pfUserCertificate",pfUserCertificates);
        return null;
    }
    /**
     * 查看实名认证信息
     * @author hanzengzhi
     * @date 2016/4/1 12:07
     */
    @RequestMapping(value = "lookIdentityAuthInfo.html")
    public String lookIdentityAuthInfo(HttpServletRequest request,HttpServletResponse response,
                                       @RequestParam(value = "auditStatus",defaultValue = "0")int auditStatus){
        //重定向到身份认证controller
        return "redirect:identityAuth/toInentityAuthPage.html?auditStatus="+auditStatus;
    }

    /**
     * 个人信息查询银行卡信息
     * @author hanzengzhi
     * @date 2016/3/29 14:14
     */
    @RequestMapping(value ="findBankCardInfoByUserId.do" )
    public String findBankCardInfoByUserId(HttpServletRequest request,HttpServletResponse response){
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException();
        }
        List<ComUserExtractwayInfo> comUserExtractwayInfos = userPersonalInfoService.findBankCardInfoByUserId(comUser.getId());
        return null;
    }
    /**
     * 删除银行卡信息
     * @author hanzengzhi
     * @date 2016/3/29 14:49
     */
    @RequestMapping(value = "deletBankCardInfoById.do")
    @ResponseBody
    public String deletBankCardInfoById(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam(value = "id")Long id){
        if (id.equals("")){
            throw new BusinessException("删除银行卡失败");
        }
        int i = userPersonalInfoService.deleteBankCardInfoById(id);
        if (i == 1){
            return "true";
        }else{
            return "false";
        }
    }
    /**
     * 跳转到管理地址界面
     * @author hanzengzhi
     * @date 2016/4/1 12:22
     */
    @RequestMapping(value = "toAddressManagePage.do")
    public String toAddressManagePage(HttpServletRequest request,HttpServletResponse response,
                                      @RequestParam(value = "jumpType",required = false,defaultValue = "0")int jumpType){
        //重定向到管理地址controller
        return "redirect:userAddress/toManageAddressPage.html?jumpType="+jumpType;
    }
}
