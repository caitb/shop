package com.masiis.shop.api.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.user.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
    @SignValid(paramType = IdentityAuthReq.class)
    public IdentityAuthRes getIdentityAuthInfo(HttpServletRequest request,HttpServletResponse response,
                                      ComUser comUser){
        IdentityAuthRes identityAuthRes = new IdentityAuthRes();
        switch (comUser.getAuditStatus()){
            case 3://审核失败
                String basePath = "http://" + OSSObjectUtils.BUCKET + "." + OSSObjectUtils.ENDPOINT + "/" + OSSObjectUtils.OSS_DOWN_LOAD_IMG_KEY;
                identityAuthRes.setIdCardFrontName(comUser.getIdCardFrontUrl());
                identityAuthRes.setIdCardBackName(comUser.getIdCardBackUrl());
                identityAuthRes.setIdCardFrontUrl(basePath+ comUser.getIdCardFrontUrl());
                identityAuthRes.setIdCardBackUrl(basePath + comUser.getIdCardBackUrl());
                break;
            default:
                break;
        }
        return identityAuthRes;
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
    public IdentityAuthRes sumbitAudit(HttpServletRequest request,
                              IdentityAuthReq identityAuthReq,
                              ComUser comUser) {
        IdentityAuthRes identityAuthRes = new IdentityAuthRes();
        try {
            Boolean bl = isParamCorrect(identityAuthReq,comUser);
            if (bl){
                comUser.setRealName(identityAuthReq.getName());
                comUser.setIdCard(identityAuthReq.getIdCard());
                int i = userIdentityAuthService.sumbitAudit(request,comUser,identityAuthReq.getIdCardFrontName(),identityAuthReq.getIdCardBackName());
                if (i == 1){
                    identityAuthRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
                    identityAuthRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
                }else{
                    identityAuthRes.setResCode(SysResCodeCons.RES_CODE_IDENTITY_AUTH_FAIL);
                    identityAuthRes.setResMsg(SysResCodeCons.RES_CODE_IDENTITY_AUTH_FAIL_MSG);
                }
            }else{
                identityAuthRes.setResCode(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID);
                identityAuthRes.setResMsg(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID_MSG);
            }
        } catch (Exception ex) {
            identityAuthRes.setResCode(SysResCodeCons.RES_CODE_IDENTITY_AUTH_FAIL);
            identityAuthRes.setResMsg(ex.getMessage());
        }
        return identityAuthRes;
    }

    /**
     * 上传身份证正反面
     *
     * @author ZhaoLiang
     * @date 2016/3/11 11:54
     */
    @ResponseBody
    @RequestMapping("/idCardImgUpload.do")
    @SignValid(paramType = UploadIdentityReq.class)
    public UploadIdentityRes imgUpload(HttpServletRequest request, HttpServletResponse response,
                            ComUser comUser,
                            UploadIdentityReq uploadIdentityReq,
                            @RequestParam(value = "idCardImg", required = true) MultipartFile idCardImg) {
        UploadIdentityRes uploadIdentityRes = new UploadIdentityRes();
        try {
            isUploadParam(uploadIdentityReq,comUser);
            String savepath = "http://" + OSSObjectUtils.BUCKET + "." + OSSObjectUtils.ENDPOINT + "/" + OSSObjectUtils.OSS_CERTIFICATE_TEMP;
            String fileName = userIdentityAuthService.uploadCertificateToOss(uploadIdentityReq.getInputStream(),uploadIdentityReq.getSize(), uploadIdentityReq.getImageType(),comUser.getId());
            if (StringUtils.isBlank(fileName)) {
                uploadIdentityRes.setResCode(SysResCodeCons.RES_CODE_UPLOAD_IDENTITY_FAIL);
                uploadIdentityRes.setResMsg(SysResCodeCons.RES_CODE_UPLOAD_IDENTITY_FAIL_MSG);
            } else {
                uploadIdentityRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
                uploadIdentityRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
                uploadIdentityRes.setImageName(fileName);
                uploadIdentityRes.setImagePath(savepath + fileName);
            }
        } catch (Exception e) {
            uploadIdentityRes.setResCode(SysResCodeCons.RES_CODE_UPLOAD_IDENTITY_FAIL);
            uploadIdentityRes.setResMsg(SysResCodeCons.RES_CODE_UPLOAD_IDENTITY_FAIL_MSG);
        }
        return uploadIdentityRes;
    }
    private Boolean isUploadParam(UploadIdentityReq uploadIdentityReq,ComUser comUser){
        if (uploadIdentityReq == null||comUser == null||comUser.getId()==null){
            return  false;
        }
        if (uploadIdentityReq.getInputStream() == null){
            return false;
        }
        if (uploadIdentityReq.getImageType() == null){
            return false;
        }
        if (uploadIdentityReq.getSize() == null){
            return false;
        }
        return true;
    }

    private Boolean isParamCorrect( IdentityAuthReq identityAuthReq,ComUser comUser){
        if (comUser == null||comUser.getId()==null) {
            return false;
        }
        if (StringUtils.isBlank(identityAuthReq.getName())) {
            return false;
        }
        if (StringUtils.isBlank(identityAuthReq.getIdCard())) {
            return false;
        }
        if (StringUtils.isBlank(identityAuthReq.getIdCardFrontName())) {
            return false;
        }
        if (StringUtils.isBlank(identityAuthReq.getIdCardBackName())) {
            return false;
        }
        return true;
    }
}
