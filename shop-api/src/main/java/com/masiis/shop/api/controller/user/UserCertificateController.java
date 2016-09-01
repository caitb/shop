package com.masiis.shop.api.controller.user;

import com.masiis.shop.api.bean.base.BasePagingReq;
import com.masiis.shop.api.bean.common.CommonReq;
import com.masiis.shop.api.bean.user.UserCertificateRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.service.user.PfUserRecommendRelationService;
import com.masiis.shop.web.platform.service.user.UserCertificateService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
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
    @Resource
    private SkuService skuService;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private UserService userService;
    @Resource
    private PfUserRecommendRelationService pfUserRecommendRelationService;


    /**
      * @Author jjh
      * @Date 2016/5/25 0025 下午 3:10
      * 个人授权书列表接口
      */
    @RequestMapping("/list")
    @ResponseBody
    @SignValid(paramType = BasePagingReq.class)
    public UserCertificateRes listOfCertificateByUser(HttpServletRequest request, BasePagingReq req, ComUser user) {
        UserCertificateRes userCertificateRes = new UserCertificateRes();
        try {
            List<CertificateInfo> pfUserCertificates = userCertificateService.CertificateByUserForApp(user.getId(),req.getPageNum());
            userCertificateRes.setCertificateInfoList(pfUserCertificates);
            userCertificateRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            userCertificateRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            userCertificateRes.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            userCertificateRes.setResMsg(e.getMessage());
        }
        return userCertificateRes;
    }

    /**
      * @Author jjh
      * @Date 2016/5/25 0025 下午 5:29
      * 授权书详情展示
      */
    @RequestMapping("/detail")
    @ResponseBody
    @SignValid(paramType = CommonReq.class)
    public UserCertificateRes certificateDetail(HttpServletRequest request, CommonReq req, ComUser user) {
        UserCertificateRes userCertificateRes = new UserCertificateRes();
        try {
            PfUserCertificate cdetail = userCertificateService.CertificateDetailsByUser(req.getId().intValue());
            String ctName = userCertificateService.getCtname(cdetail.getAgentLevelId());
            ComSku comSku = skuService.getSkuById(cdetail.getSkuId());
            PfUserRecommenRelation pfUserRecommenRelation = pfUserRecommendRelationService.selectRecommenRelationByUserIdAndSkuId(cdetail.getUserId(),cdetail.getSkuId());
            userCertificateRes.setCtname(ctName);
            if(pfUserRecommenRelation!=null && pfUserRecommenRelation.getUserPid()!=0){//有推荐人
                userCertificateRes.setUpgradeUser(userService.getUserById(pfUserRecommenRelation.getUserPid()).getRealName());
            }else{
                userCertificateRes.setUpgradeUser("");
            }
            userCertificateRes.setSkuName(comSku.getName());
            userCertificateRes.setSjName(cdetail.getSjName());
            userCertificateRes.setImgUrl(cdetail.getImgUrl());
            userCertificateRes.setRealName(user.getRealName());
            userCertificateRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            userCertificateRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            userCertificateRes.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            userCertificateRes.setResMsg(e.getMessage());
        }
        return userCertificateRes;
    }

    /**
      * @Author jjh
      * @Date 2016/5/26 0026 下午 1:51
      * 授权书上级信息展示接口
      */
    @RequestMapping("/upperInfo")
    @ResponseBody
    @SignValid(paramType = CommonReq.class)
    public UserCertificateRes upperInfoOfCertificate(HttpServletRequest request, CommonReq req, ComUser user) {
        UserCertificateRes userCertificateRes = new UserCertificateRes();
        try {
            PfUserSku pfUserSku = userSkuService.getUserSkuById(req.getId().intValue());
            ComUser userInfo = userService.getUserById(pfUserSku.getUserId());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String sDate = sdf.format(userInfo.getCreateTime());
            ComSku comSku = skuService.getSkuById(pfUserSku.getSkuId());
            PfUserCertificate pfUserCertificate = userCertificateService.getCertificateBypfuId(pfUserSku.getId());
            String ctName = userCertificateService.getCtname(pfUserCertificate.getAgentLevelId());
            String ctValue = PropertiesUtils.getStringValue("index_user_certificate_url");
            pfUserCertificate.setImgUrl(ctValue + pfUserCertificate.getImgUrl());
            userCertificateRes.setRealName(userInfo.getRealName());
            userCertificateRes.setSkuName(comSku.getName());
            userCertificateRes.setCtname(ctName);
            userCertificateRes.setPfUserCertificate(pfUserCertificate);
            userCertificateRes.setsDate(sDate);
            userCertificateRes.setWxId(user.getWxId());
            userCertificateRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            userCertificateRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            userCertificateRes.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            userCertificateRes.setResMsg(e.getMessage());
        }
        return userCertificateRes;
    }

}
