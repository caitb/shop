package com.masiis.shop.api.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.common.CommonRei;
import com.masiis.shop.api.bean.common.CommonReq;
import com.masiis.shop.api.bean.user.PersonalCenterRes;
import com.masiis.shop.api.bean.user.PersonalCenterRei;
import com.masiis.shop.api.bean.user.SkuAgentDetail;
import com.masiis.shop.api.constants.AuditStatusEnum;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.web.api.service.PersonalCenterService;
import com.masiis.shop.common.util.EmojiUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.PfSkuAgentDetail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
            String pattenForWxcode = "^(([\\u4e00-\\u9fa5]{2,8})|([a-zA-Z]{2,16}))$";
            if (startCheck(pattenForWxcode, rei.getWxNkName()) == false) {
                res.setResCode(SysResCodeCons.RES_CODE_ILLEGAL);
                res.setResCode(SysResCodeCons.NICHENG_IILEAGAL);
                logger.info("该昵称不合法");
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
}
