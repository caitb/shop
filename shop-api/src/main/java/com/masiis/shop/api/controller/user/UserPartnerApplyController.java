package com.masiis.shop.api.controller.user;

import com.masiis.shop.api.bean.user.canApplyReq;
import com.masiis.shop.api.bean.user.canApplyRes;
import com.masiis.shop.api.bean.user.PartnerPreApplyReq;
import com.masiis.shop.api.bean.user.PartnerPreApplyRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.order.BOrderService;
import com.masiis.shop.api.service.product.SkuService;
import com.masiis.shop.api.service.user.PfUserRelationService;
import com.masiis.shop.api.service.user.UserSkuService;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserRelation;
import com.masiis.shop.dao.po.PfUserSku;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Date 2016/5/23
 * @Auther lzh
 */
@Controller
@RequestMapping("/upapply")
public class UserPartnerApplyController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SkuService skuService;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private PfUserRelationService pfUserRelationService;
    @Resource
    private BOrderService bOrderService;

    @RequestMapping("/toapply")
    @ResponseBody
    @SignValid(paramType = PartnerPreApplyReq.class)
    public canApplyRes isAgent(HttpServletRequest request, canApplyReq req, ComUser user){
        Integer skuId = req.getSkuId();
        canApplyRes res = new canApplyRes();
        if(skuId == null){
            log.error(SysResCodeCons.RES_CODE_CANAGENT_SKU_NULL_MSG);
            res.setResCode(SysResCodeCons.RES_CODE_CANAGENT_SKU_NULL);
            res.setResMsg(SysResCodeCons.RES_CODE_CANAGENT_SKU_NULL_MSG);
            return res;
        }
        ComSku sku = skuService.getSkuById(skuId);
        if (sku == null) {
            log.error(SysResCodeCons.RES_CODE_CANAGENT_SKU_INVALID_MSG);
            res.setResCode(SysResCodeCons.RES_CODE_CANAGENT_SKU_INVALID);
            res.setResMsg(SysResCodeCons.RES_CODE_CANAGENT_SKU_INVALID_MSG);
            return res;
        }
        PfUserRelation relation = pfUserRelationService.getRelation(user.getId(), skuId);
        PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(user.getId(), skuId);
        if (pfUserSku != null) {
            // 已经代理过该产品
            res.setResCode(SysResCodeCons.RES_CODE_CANAGENT_ALREADY_AGENT);
            res.setResMsg(SysResCodeCons.RES_CODE_CANAGENT_ALREADY_AGENT_MSG);
        } else {
            if(relation == null) {
                // 临时代理关系没有,不能申请
                res.setResCode(SysResCodeCons.RES_CODE_CANAGENT_NOTAGENT);
                res.setResMsg(SysResCodeCons.RES_CODE_CANAGENT_NOTAGENT_MSG);
            } else {
                // 已经绑定过临时代理关系
                res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
                res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            }
        }
        log.error(res.getResMsg());
        return res;
    }

    @RequestMapping("/toapply")
    @ResponseBody
    @SignValid(paramType = PartnerPreApplyReq.class)
    public PartnerPreApplyRes toApply(HttpServletRequest request, PartnerPreApplyReq req, ComUser user){
        PartnerPreApplyRes res = new PartnerPreApplyRes();
        Integer skuId = req.getSkuId();
        if(skuId == null){
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL_MSG);
            return res;
        }
        ComSku sku = skuService.getSkuById(skuId);
        if (sku == null) {
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_SKU_INVALID);
            res.setResMsg(SysResCodeCons.RES_CODE_UPAPPLY_SKU_INVALID_MSG);
            return res;
        }
        Long pUserId = req.getpUserId();
        Long temPUserId = pfUserRelationService.getPUserId(user.getId(), skuId);
        res.setIsAgent(0);
        if (pUserId != null && pUserId > 0) {
            PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(user.getId(), skuId);
            if (pfUserSku != null && pfUserSku.getIsCertificate() == 1) {
                // 已经绑定过代理关系
                res.setIsAgent(1);
            }
        }

        try {
            if (temPUserId == 0) {
                if (pUserId != null && pUserId > 0) {
                    //校验上级合伙人数据是否合法,如果合法则建立临时绑定关系
                    userSkuService.checkParentData(user, pUserId, skuId);
                    PfUserRelation pfUserRelation = new PfUserRelation();
                    pfUserRelation.setUserId(user.getId());
                    pfUserRelation.setSkuId(skuId);
                    pfUserRelation.setCreateTime(new Date());
                    pfUserRelation.setIsEnable(1);
                    pfUserRelation.setUserPid(pUserId);
                    pfUserRelationService.insert(pfUserRelation);
                } else {
                    pUserId = 0l;
                }
            } else {
                pUserId = temPUserId;
            }
        } catch (Exception e) {
            if(e instanceof BusinessException) {
                res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_PUSER_INVALID);
                res.setResMsg(e.getMessage());
            }else{
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
            return res;
        }
        boolean isQueuing = false;
        Integer count = 0;
        int status = skuService.getSkuStockStatus(skuId, 1, pUserId);
        if (status == 1) {
            isQueuing = true;
            count = bOrderService.selectQueuingOrderCount(skuId);
        }

        res.setIsBind(user.getIsBinding());
        res.setIsQueuing(isQueuing?1:0);
        res.setQueueNum(count);

        return res;
    }
}
