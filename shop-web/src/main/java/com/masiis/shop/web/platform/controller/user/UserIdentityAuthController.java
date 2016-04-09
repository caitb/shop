package com.masiis.shop.web.platform.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.asm.Opcodes;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.constants.AuditStatusEnum;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.user.UserIdentityAuthService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hzz on 2016/3/30.
 *
 * 实名认证
 */
@Controller
@RequestMapping(value = "identityAuth")
public class UserIdentityAuthController extends BaseController {

    @Resource
    private UserIdentityAuthService userIdentityAuthService;
    /**
     * 跳转到身份认证界面
     * @author hanzengzhi
     * @date 2016/3/30 16:19
     */
    @RequestMapping(value = "toInentityAuthPage.html")
    public String toInentityAuthPage(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam(value = "auditStatus",defaultValue = "0")int auditStatus,
                                     Model model) {
        ComUser comUser = getComUser(request);
        model.addAttribute("comUser",comUser);
        String jumpPage = "";
        AuditStatusEnum auditStatusEnum = AuditStatusEnum.getAuditStatusEnum(auditStatus) ;
        if (comUser!=null&&auditStatusEnum!=null){
            switch (auditStatusEnum){
                case NOAUDIT://未认证
                    jumpPage = "platform/user/shimingrenzheng";
                    break;
                case AUDITSUCCESS://审核通过
                case AUDITFAIL://审核不通过
                    jumpPage = "redirect:/identityAuth/getIdentityAuthInfo.do";
                    break;
                default:
                    break;
            }
        }
        return jumpPage;
    }
    /**
     * 获得身份证信息
     * @author hanzengzhi
     * @date 2016/3/31 15:25
     */
    @RequestMapping(value = "getIdentityAuthInfo.do")
    public String getIdentityAuthInfo(HttpServletRequest request,HttpServletResponse response,
                                      Model model){
        ComUser comUser = getComUser(request);
        userIdentityAuthService.getIdentityAuthInfo(request,comUser);
        String returnPagePath = null;
        switch (comUser.getAuditStatus()){
            case 3://审核失败
                model.addAttribute("idCardFrontUrl", SysConstants.ID_CARD_PATH + comUser.getIdCardFrontUrl());
                model.addAttribute("idCardBackUrl", SysConstants.ID_CARD_PATH + comUser.getIdCardBackUrl());
                returnPagePath = "platform/user/shimingrenzhengfail";
                break;
            default:
                returnPagePath = "platform/user/shimingyirenzheng";
                break;
        }
        model.addAttribute("comUser",comUser);
        return returnPagePath;
    }


    /**
     * 实时的获取审核状态
     * @author hanzengzhi
     * @date 2016/3/30 19:43
     */
    @RequestMapping(value = "getAuditStatusInfo.do")
    public String getAuditStatusInfo(HttpServletRequest request,HttpServletResponse response){
        ComUser comUser = getComUser(request);
        if (comUser!=null){
            return comUser.getAuditStatus()+"";
        }
        return "";
    }
    /**
     * 提交身份认证审核
     * @author hanzengzhi
     * @date 2016/3/30 19:35
     */
    @ResponseBody
    @RequestMapping("sumbitAudit.do")
    public String sumbitAudit(HttpServletRequest request,
                                  @RequestParam(value = "name", required = true) String name,
                                  @RequestParam(value = "idCard", required = true) String idCard,
                                  @RequestParam(value = "idCardFrontUrl", required = true) String idCardFrontUrl,
                                  @RequestParam(value = "idCardBackUrl", required = true) String idCardBackUrl,
                                  @RequestParam(value = "type", required = false,defaultValue = "0") int type

    ) {
        JSONObject object = new JSONObject();
        try {
            ComUser comUser = getComUser(request);
            if (comUser == null) {
                throw new BusinessException("用户信息有误请重新登陆");
            } else if (comUser.getAuditStatus()==AuditStatusEnum.AUDITING.getIndex()){
                throw new BusinessException("已提交审核");
            }
            if (org.apache.commons.lang.StringUtils.isBlank(name)) {
                throw new BusinessException("姓名不能为空");
            }
            if (org.apache.commons.lang.StringUtils.isBlank(idCard)) {
                throw new BusinessException("身份证不能为空");
            }
            if (org.apache.commons.lang.StringUtils.isBlank(idCardFrontUrl)) {
                throw new BusinessException("身份证照片不能为空");
            }
            if (org.apache.commons.lang.StringUtils.isBlank(idCardBackUrl)) {
                throw new BusinessException("身份证照片不能为空");
            }
            comUser.setRealName(name);
            comUser.setIdCard(idCard);
            int i = userIdentityAuthService.sumbitAudit(request,comUser,idCardFrontUrl,idCardBackUrl,type);
            if (i == 1){
                object.put("isError", false);
            }else{
                object.put("isError", true);
            }
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return object.toJSONString();
    }
    /**
     * 跳转到待审核界面
     * @author hanzengzhi
     * @date 2016/4/6 15:05
     */
    @RequestMapping(value = "toWaitIdentityPage.html")
    public String toWaitIdentityPage(HttpServletRequest request,HttpServletResponse response){
        return "platform/user/daishenhe";
    }
}
