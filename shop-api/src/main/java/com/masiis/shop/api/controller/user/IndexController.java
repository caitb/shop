package com.masiis.shop.api.controller.user;

import com.masiis.shop.api.bean.user.PartnerIndexReq;
import com.masiis.shop.api.bean.user.PartnerIndexRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.common.enums.platform.UpGradeStatus;
import com.masiis.shop.dao.beans.statistic.BrandStatistic;
import com.masiis.shop.dao.beans.statistic.RecommendBrandStatistic;
import com.masiis.shop.dao.platform.user.PfUserBrandMapper;
import com.masiis.shop.web.platform.service.message.PfMessageSrRelationService;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.order.PfUserUpgradeNoticeService;
import com.masiis.shop.web.platform.service.statistics.BrandStatisticService;
import com.masiis.shop.web.platform.service.statistics.RecommentBrandStatisticService;
import com.masiis.shop.common.util.EmojiUtils;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2016/4/28
 * @Auther lzh
 */
@Controller
@RequestMapping("/user")
public class IndexController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private BOrderService bOrderService;
    @Resource
    private PfMessageSrRelationService pfMessageSrRelationService;
    @Resource
    private PfUserUpgradeNoticeService userUpgradeNoticeService;
    @Resource
    private PfUserBrandMapper pfUserBrandMapper;
    @Resource
    private BrandStatisticService brandStatisticService;
    @Resource
    private RecommentBrandStatisticService recommentBrandStatisticService;

    @RequestMapping("/index")
    @ResponseBody
    @SignValid(paramType = PartnerIndexReq.class)
    public PartnerIndexRes toIndex(HttpServletRequest request, PartnerIndexReq req, ComUser user) {
        PartnerIndexRes res = new PartnerIndexRes();
        try {
            List<PfUserOrganization> pfUserOrganizations = null;
            List<PfUserUpgradeNotice> notices = null;
            //组织(家族，团队)信息
//            pfUserOrganizations = pfUserOrganizationService.getOrganizationInfoByUserId(user.getId());
            //下级申请人数
            List<Integer> statusList = new ArrayList<Integer>();
            statusList.add(UpGradeStatus.STATUS_Untreated.getCode());
            statusList.add(UpGradeStatus.STATUS_Processing.getCode());
            notices = userUpgradeNoticeService.selectByUserPidAndInStatus(user.getId(), statusList);
            //UI数量统计
            List<PfBorder> pfBorders = bOrderService.findByUserId(user.getId(), null, null);
            List<PfBorder> pfBorders10 = new ArrayList<>();//代发货
            List<PfBorder> pfBorders6 = new ArrayList<>();//排单中
            for (PfBorder pfBord : pfBorders) {
                if (pfBord.getOrderStatus() == 7) {
                    pfBorders10.add(pfBord);//代发货
                } else if (pfBord.getOrderStatus() == 6) {
                    pfBorders6.add(pfBord);//排单中
                }
            }
            Integer countMsg = pfMessageSrRelationService.queryNoSeeNumsByToUserAndType(user.getId(), 2);
            Integer countUpgate = notices.size();
            Integer borderNum = pfBorders10.size() + pfBorders6.size();
            // 绑定数据
            res.setbOrderNums(borderNum);
            res.setIsPartner(user.getIsAgent());
            res.setIsBind(user.getIsBinding());
            res.setNkName(EmojiUtils.encodeEmojiStr(user.getWxNkName()));
            res.setUserHeadImg(user.getWxHeadImg());
            res.setCountMsg(countMsg);
//            res.setPfUserOrganizations(pfUserOrganizations);
            res.setUpgradeNum(countUpgate);
            res.setAuditStatus(user.getAuditStatus());
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return res;
    }

    @RequestMapping("/ajaxIndex")
    @ResponseBody
    @SignValid(paramType = PartnerIndexReq.class)
    public PartnerIndexRes AjaxtoIndex(HttpServletRequest request, PartnerIndexReq req, ComUser user) {
        PartnerIndexRes res = new PartnerIndexRes();
        //团队数量统计
        try {
            if (user.getIsAgent().intValue() == 1) {// 是合伙人
                Integer agentBrandNum = 0;
                Integer totalUserNum = 0;
                BigDecimal totalSaleAmount = BigDecimal.ZERO;
                List<PfUserBrand> pfUserBrands = pfUserBrandMapper.selectByUserId(user.getId());
                for (PfUserBrand pfUserBrand : pfUserBrands) {
                    BrandStatistic brandStatistic = brandStatisticService.selectBrandStatisticByUserIdAndBrandId(pfUserBrand.getUserId(), pfUserBrand.getBrandId());
                    RecommendBrandStatistic recommendBrandStatistic = recommentBrandStatisticService.selectRecommentBrandStatisticByUserIdAndBrandId(pfUserBrand.getUserId(), pfUserBrand.getBrandId());
                    agentBrandNum++;
                    totalUserNum += brandStatistic.getUserNum() + recommendBrandStatistic.getUserNum();
                    totalSaleAmount = totalSaleAmount.add(brandStatistic.getSellAmount()).add(recommendBrandStatistic.getSellAmount());
                }
                res.setPartnerNums(totalUserNum);
                res.setTotalIncomeFee(String.valueOf(totalSaleAmount));
                res.setBrandNum(agentBrandNum);
            }
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return res;
    }

}
