package com.masiis.shop.api.controller.user;

import com.masiis.shop.api.bean.user.PersonalCenterReq;
import com.masiis.shop.api.bean.user.PersonalCenterRes;
import com.masiis.shop.api.bean.user.SkuAgentDetail;
import com.masiis.shop.api.constants.AuditStatusEnum;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.user.PersonalCenterService;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.PfSkuAgent;
import com.masiis.shop.dao.po.PfSkuAgentDetail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangbingjian on 2016/5/5.
 */
@Controller
@RequestMapping(value = "/personal")
public class PersonalCenterController extends BaseController {
    private static final Logger logger = Logger.getLogger(PersonalCenterController.class);

    @Autowired
    private PersonalCenterService personalCenterService;

    @RequestMapping(value = "/centerHome.do")
    @ResponseBody
    @SignValid(paramType = PersonalCenterReq.class)
    public PersonalCenterRes centerHome(HttpServletRequest request, PersonalCenterReq req, ComUser user){
        logger.info("个人中心首页");
        PersonalCenterRes res = new PersonalCenterRes();
        Map<String, Object> map = personalCenterService.getPersonalHomePageInfo(user);
        List<PfSkuAgentDetail> pfSkuAgentDetails = null;
        ComUserAccount comUserAccount = null;
        List<SkuAgentDetail> skuAgentDetails = new ArrayList<>();
        if (map != null){
            SkuAgentDetail skuAgentDetail;
            pfSkuAgentDetails = (List<PfSkuAgentDetail>) map.get("pfSkuAgentDetails");
            if (pfSkuAgentDetails != null){
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
                res.setExtractableFee(comUserAccount.getExtractableFee()==null?new BigDecimal(0.00):comUserAccount.getExtractableFee());
            }else {
                res.setExtractableFee(new BigDecimal(0.00));
            }
        }
        res.setMobile(user.getMobile()==null?"":user.getMobile());
        res.setWxNkName(user.getWxNkName());
        res.setIsBinding(user.getIsBinding());
        res.setAuditStatus(user.getAuditStatus());
        res.setAuditStatusName(AuditStatusEnum.getName(user.getAuditStatus()));
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        return res;
    }

}
