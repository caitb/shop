package com.masiis.shop.api.controller.user;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.api.bean.user.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.web.platform.service.user.UserCertificateService;
import com.masiis.shop.web.platform.service.user.UserIdentityAuthService;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.dao.po.ComUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hzz on 2016/3/30.
 *
 * 实名认证
 */
@Controller
@RequestMapping(value = "identityAuth")
public class UserIdentityAuthController extends BaseController {

    private final static Log log = LogFactory.getLog(UserIdentityAuthController.class);

    @Resource
    private UserIdentityAuthService userIdentityAuthService;
    @Resource
    private UserCertificateService userCertificateService;

    @ResponseBody
    @RequestMapping(value = "isAudit.do")
    @SignValid(paramType = IdentityAuthReq.class)
    public IdentityAuthRes isAudit(HttpServletRequest request, ComUser comUser){
        IdentityAuthRes identityAuthRes = new IdentityAuthRes();

        try {
            ComUser user = userIdentityAuthService.getUser(comUser.getId());
            identityAuthRes.setAudit(user.getAuditStatus().intValue()==2?true:false);
            identityAuthRes.setAuditStatus(user.getAuditStatus());
            identityAuthRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            identityAuthRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e){
            identityAuthRes.setResCode(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
            identityAuthRes.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR_MSG);

            log.error("获取审核状态失败![comUser="+comUser+"]"+e);
            e.printStackTrace();
        }

        return identityAuthRes;
    }

    /**
     * 获得身份证信息
     * @author hanzengzhi
     * @date 2016/3/31 15:25
     */
    @ResponseBody
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
                identityAuthRes.setAuditReason(comUser.getAuditReason());
                break;
            default:
                break;
        }
        identityAuthRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        identityAuthRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        identityAuthRes.setWxId(comUser.getWxId());
        identityAuthRes.setName(comUser.getRealName());
        identityAuthRes.setIdCard(comUser.getIdCard());
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
                comUser.setWxId(identityAuthReq.getWxId());
                int i = userIdentityAuthService.sumbitAudit(request,comUser,identityAuthReq.getIdCardFrontName(),identityAuthReq.getIdCardBackName(), null);
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
                                       UploadIdentityReq req,
                            ComUser comUser,
                            MultipartFile imgInputStream) {
        UploadIdentityRes uploadIdentityRes = new UploadIdentityRes();
        try {
            String savepath = "http://" + OSSObjectUtils.BUCKET + "." + OSSObjectUtils.ENDPOINT + "/" + OSSObjectUtils.OSS_CERTIFICATE_TEMP;
            String fileName = userCertificateService.uploadImageToOss(imgInputStream,comUser,1);
            if (StringUtils.isBlank(fileName)) {
                log.info("-----------身份证名字为null-----------");
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

    private Boolean isParamCorrect( IdentityAuthReq identityAuthReq,ComUser comUser){
        if (comUser == null||comUser.getId()==null) {
            log.info("comUser------为null");
            return false;
        }
        if (StringUtils.isBlank(identityAuthReq.getName())) {
            log.info("identityAuthReq.getName()------为null");
            return false;
        }
        if (StringUtils.isBlank(identityAuthReq.getIdCard())) {
            log.info("identityAuthReq.getIdCard()------为null");
            return false;
        }
        if (StringUtils.isBlank(identityAuthReq.getIdCardFrontName())) {
            log.info("identityAuthReq.getIdCardFrontName()------为null");
            return false;
        }
        if (StringUtils.isBlank(identityAuthReq.getIdCardBackName())) {
            log.info("identityAuthReq.getIdCardBackName()------为null");
            return false;
        }
        if (StringUtils.isBlank(identityAuthReq.getWxId())){
            log.info("identityAuthReq.getWxId()------为null");
            return false;
        }
        return true;
    }
}
