package com.masiis.shop.api.controller.user;

import com.masiis.shop.api.bean.common.CommonReq;
import com.masiis.shop.api.bean.user.UserCertificateRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.user.UserCertificateService;
import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.po.ComUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by JingHao on 2016/5/25 0025.
 */
@Controller
@RequestMapping("/userCertificate")
public class UserCertificateController extends BaseController {

    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private UserCertificateService userCertificateService;


    /**
      * @Author jjh
      * @Date 2016/5/25 0025 下午 3:10
      * 个人授权书列表接口
      */
    @RequestMapping("/list")
    @ResponseBody
    @SignValid(paramType = CommonReq.class)
    public UserCertificateRes listOfCertificateByUser(HttpServletRequest request, CommonReq req, ComUser user) {
        UserCertificateRes userCertificateRes = new UserCertificateRes();
        try {
            List<CertificateInfo> pfUserCertificates = userCertificateService.CertificateByUser(user.getId());
            userCertificateRes.setCertificateInfoList(pfUserCertificates);
            userCertificateRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            userCertificateRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            userCertificateRes.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            userCertificateRes.setResMsg(e.getMessage());
        }
        return userCertificateRes;
    }
}
