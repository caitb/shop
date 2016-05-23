package com.masiis.shop.api.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.user.ComUserAddressReq;
import com.masiis.shop.api.bean.user.IdentityAuthReq;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.user.UserIdentityAuthService;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.dao.po.ComUser;
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
     * 获得身份证信息
     * @author hanzengzhi
     * @date 2016/3/31 15:25
     */
    @RequestMapping(value = "getIdentityAuthInfo.do")
    public String getIdentityAuthInfo(HttpServletRequest request,HttpServletResponse response,
                                      ComUser comUser,
                                      Model model){
        userIdentityAuthService.getIdentityAuthInfo(request,comUser);
        String returnPagePath = null;
        switch (comUser.getAuditStatus()){
            case 3://审核失败
                String basePath = "http://" + OSSObjectUtils.BUCKET + "." + OSSObjectUtils.ENDPOINT + "/" + OSSObjectUtils.OSS_DOWN_LOAD_IMG_KEY;
                model.addAttribute("idCardFrontName", comUser.getIdCardFrontUrl());
                model.addAttribute("idCardBackName", comUser.getIdCardBackUrl());
                model.addAttribute("idCardFrontUrl", basePath+ comUser.getIdCardFrontUrl());
                model.addAttribute("idCardBackUrl",  basePath + comUser.getIdCardBackUrl());
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
/*    @RequestMapping(value = "getAuditStatusInfo.do")
    public String getAuditStatusInfo(HttpServletRequest request,HttpServletResponse response){
        ComUser comUser = getComUser(request);
        if (comUser!=null){
            return comUser.getAuditStatus()+"";
        }
        return "";
    }*/
    /**
     * 提交身份认证审核
     * @author hanzengzhi
     * @date 2016/3/30 19:35
     */
    @ResponseBody
    @RequestMapping("sumbitAudit.do")
    @SignValid(paramType = IdentityAuthReq.class)
    public String sumbitAudit(HttpServletRequest request,
                              IdentityAuthReq identityAuthReq,
                              ComUser comUser

    ) {
        JSONObject object = new JSONObject();
        try {
/*            if (comUser == null) {
                throw new BusinessException("用户信息有误请重新登陆");
            } else if (comUser.getAuditStatus()==AuditStatusEnum.AUDITING.getIndex()){
                throw new BusinessException("已提交审核");
            }*/
            if (StringUtils.isBlank(identityAuthReq.getName())) {
                throw new BusinessException("姓名不能为空");
            }
            if (StringUtils.isBlank(identityAuthReq.getIdCard())) {
                throw new BusinessException("身份证不能为空");
            }
            if (StringUtils.isBlank(identityAuthReq.getIdCardFrontName())) {
                throw new BusinessException("身份证照片不能为空");
            }
            if (StringUtils.isBlank(identityAuthReq.getIdCardBackName())) {
                throw new BusinessException("身份证照片不能为空");
            }
            comUser.setRealName(identityAuthReq.getName());
            comUser.setIdCard(identityAuthReq.getIdCard());
            int i = userIdentityAuthService.sumbitAudit(request,comUser,identityAuthReq.getIdCardFrontName(),identityAuthReq.getIdCardBackName());
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
}
