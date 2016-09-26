package com.masiis.shop.api.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.cloopen.rest.sdk.utils.PropertiesUtil;
import com.masiis.shop.api.bean.common.CommonRei;
import com.masiis.shop.api.bean.common.CommonReq;
import com.masiis.shop.api.bean.user.*;
import com.masiis.shop.api.constants.AuditStatusEnum;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.web.api.service.PersonalCenterService;
import com.masiis.shop.common.util.EmojiUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.PfSkuAgentDetail;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.platform.service.user.UserCertificateService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangbingjian on 2016/5/5.
 */
@Controller
@RequestMapping(value = "/personal")
public class PersonalCenterController extends BaseController {
    private static final Logger logger = Logger.getLogger(PersonalCenterController.class);

    @Autowired
    private PersonalCenterService personalCenterService;
    @Autowired
    private UserCertificateService userCertificateService;
    @Autowired
    private SfShopService sfShopService;
    @Autowired
    private SfShopMapper sfShopMapper;


    @RequestMapping(value = "/centerHome.do",method = RequestMethod.POST)
    @ResponseBody
    @SignValid(paramType = CommonReq.class)
    public PersonalCenterRes centerHome(HttpServletRequest request, CommonReq req, ComUser user){
        logger.info("个人中心首页");
        PersonalCenterRes res = new PersonalCenterRes();
        Map<String, Object> map = personalCenterService.getPersonalHomePageInfo(user);
        List<PfSkuAgentDetail> pfSkuAgentDetails;
        ComUserAccount comUserAccount;
        List<SkuAgentDetail> skuAgentDetails = new ArrayList<>();
        if (map != null){
            SkuAgentDetail skuAgentDetail;
            pfSkuAgentDetails = (List<PfSkuAgentDetail>) map.get("pfSkuAgentDetails");
            if (pfSkuAgentDetails != null && pfSkuAgentDetails.size() > 0){
                for (PfSkuAgentDetail pfSkuAgentDetail : pfSkuAgentDetails){
                    skuAgentDetail = new SkuAgentDetail();
                    skuAgentDetail.setAgentLevelIConUrl(map.get("agentLevelIConUrl").toString() + pfSkuAgentDetail.getPfSkuAgent().getIcon());
                    skuAgentDetail.setSkuName(pfSkuAgentDetail.getSkuName());
                    skuAgentDetails.add(skuAgentDetail);
                }
            }
            res.setSkuAgentDetails(skuAgentDetails);
            comUserAccount = (ComUserAccount) map.get("comUserAccount");
            if (comUserAccount != null){
                res.setExtractableFee(comUserAccount.getExtractableFee()==null?new BigDecimal(0l).setScale(2,BigDecimal.ROUND_HALF_UP).toString():
                        comUserAccount.getExtractableFee().subtract(comUserAccount.getAppliedFee() == null?new BigDecimal(0):comUserAccount.getAppliedFee()).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            }else {
                res.setExtractableFee(new BigDecimal(0l).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
            }
        }
        res.setMobile(user.getMobile()==null?"":user.getMobile());
        res.setWxNkName(EmojiUtils.encodeEmojiStr(user.getWxNkName()));
        res.setWxHeadImg(user.getWxHeadImg());
        res.setIsBinding(user.getIsBinding());
        res.setAuditStatus(user.getAuditStatus());
        res.setAuditStatusName(AuditStatusEnum.getName(user.getAuditStatus()));
        res.setWxId(user.getWxId());
        logger.info("微信号：" + user.getWxId());
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);

        logger.info("返回参数：" + JSONObject.toJSONString(res));
        return res;
    }


    @RequestMapping(value = "/modifyName.do")//,method = RequestMethod.POST
    @ResponseBody
    @SignValid(paramType = CommonRei.class)
    public PersonalCenterRei modifyName(HttpServletRequest request, CommonRei rei, ComUser user){
        logger.info("修改昵称");
        PersonalCenterRei res = new PersonalCenterRei();
        try {
            String pattenForWxcode = "^(([a-zA-Z0-9\\u4e00-\\u9fa5]{1,8})|([0-9a-zA-Z]{1,16}))$";
            if (startCheck(pattenForWxcode, rei.getWxNkName()) == false) {
                res.setResCode(SysResCodeCons.RES_CODE_ILLEGAL);
                res.setResMsg(SysResCodeCons.NICHENG_IILEAGAL);
                logger.info("该昵称不合法"+rei.getWxNkName());
                return res;
            }
            user.setWxNkName(rei.getWxNkName());
            personalCenterService.modifyName(user);
        }catch (Exception e){
            e.printStackTrace();
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
        }
        logger.info("变更名：" + user.getWxNkName());
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        logger.info("返回参数：" + JSONObject.toJSONString(res));
        return res;
    }

    private boolean startCheck(String reg, String str) {
        boolean tem = false;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        tem = matcher.matches();
        return tem;
    }


    /**
     * 上传头像图片
     *
     * @author
     * @date
     */
    @ResponseBody
    @RequestMapping("/imgUpload.do")
    @SignValid(paramType = UploadIdentityRei.class)
    public UploadIdentityRes imgUpload(HttpServletRequest request, HttpServletResponse response,
                                       UploadIdentityRei rei,
                                       ComUser user,
                                       MultipartFile imgInputStream) {
        UploadIdentityRes uploadIdentityRes = new UploadIdentityRes();
        try {
            String savepath = "http://" + OSSObjectUtils.BUCKET + "." + OSSObjectUtils.ENDPOINT + "/" + OSSObjectUtils.OSS_HEADIMAGE_HEADIMAGE;
            String fileName = userCertificateService.uploadImageToOss(imgInputStream,user,2);
            user.setWxHeadImg(savepath + fileName);
            personalCenterService.modifyHeadImg(user);
            if(sfShopMapper.selectByUserId(user.getId()) != null){
                SfShop sfShop = sfShopMapper.selectByUserId(user.getId());
                sfShop.setLogo(user.getWxHeadImg());
                sfShopService.modifyHeadImg(sfShop);
            }
            if (StringUtils.isBlank(fileName)) {
                logger.info("-----------图片名为null-----------");
                uploadIdentityRes.setResCode(SysResCodeCons.RES_CODE_UPLOAD_IMG_FAIL);
                uploadIdentityRes.setResMsg(SysResCodeCons.RES_CODE_UPLOAD_IMG_FAIL_MSG);
            } else {
                uploadIdentityRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
                uploadIdentityRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
                uploadIdentityRes.setImageName(fileName);
                uploadIdentityRes.setImagePath(savepath + fileName);
            }
        } catch (Exception e) {
            uploadIdentityRes.setResCode(SysResCodeCons.RES_CODE_UPLOAD_IMG_FAIL);
            uploadIdentityRes.setResMsg(SysResCodeCons.RES_CODE_UPLOAD_IMG_FAIL_MSG);
        }
        return uploadIdentityRes;
    }
}
