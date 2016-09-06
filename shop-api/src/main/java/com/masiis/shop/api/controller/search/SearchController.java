package com.masiis.shop.api.controller.search;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.search.SearchReq;
import com.masiis.shop.api.bean.search.SearchRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.statistic.BrandStatistic;
import com.masiis.shop.dao.platform.user.PfUserBrandMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserBrand;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.service.statistics.BrandStatisticService;
import com.masiis.shop.web.platform.service.user.PfUserOrganizationService;
import com.masiis.shop.web.platform.service.user.UserBlackService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 搜索功能
 * Created by cai_tb on 16/8/9.
 */
@Controller
@RequestMapping("/search")
public class SearchController extends BaseController {

    private final static Log log = LogFactory.getLog(SearchController.class);

    @Resource
    private PfUserOrganizationService userOrganizationService;
    @Resource
    private UserService userService;
    @Resource
    private PfUserBrandMapper pfUserBrandMapper;
    @Resource
    private BrandStatisticService brandStatisticService;
    @Resource
    private UserBlackService userBlackService;

    @RequestMapping("/searchByMobile")
    @ResponseBody
    @SignValid(paramType = SearchReq.class)
    public SearchRes searchByMobile(HttpServletRequest request, SearchReq searchReq) {
        SearchRes searchRes = new SearchRes();

        try {
            if (StringUtils.isBlank(searchReq.getMobile())) {
                searchRes.setResCode(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
                searchRes.setResMsg("手机号为空");
                return searchRes;
            }

            if(userBlackService.isBlackByMobile(searchReq.getMobile())) {
                // 黑名单用户不能登录
                searchRes.setResCode(SysResCodeCons.RES_CODE_PHONESEARCH_PHONENUM_ISIN_BLACKLIST);
                searchRes.setResMsg(SysResCodeCons.RES_CODE_PHONESEARCH_PHONENUM_ISIN_BLACKLIST_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_PHONESEARCH_PHONENUM_ISIN_BLACKLIST_MSG);
            }

            Map<String, Object> dataMap = userOrganizationService.searchByMobile(searchReq.getMobile(), searchReq.getBrandId());

            searchRes.setDataMap(dataMap);
            searchRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            searchRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            if(StringUtils.isBlank(searchRes.getResCode())){
                searchRes.setResCode(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
                searchRes.setResMsg("手机号查询失败!");
            }

            log.error("手机号查询异常:" + e.getMessage(), e);
        }
        return searchRes;
    }


    /**
     * 团队数量和人数
     *
     * @param request
     * @param searchReq
     * @return
     */
    @RequestMapping("/count")
    @ResponseBody
    @SignValid(paramType = SearchReq.class)
    public SearchRes count(HttpServletRequest request, SearchReq searchReq) {
        SearchRes searchRes = new SearchRes();

        try {
            ComUser comUser = userService.getUserById(searchReq.getUserId());
            if (comUser != null && StringUtils.isBlank(comUser.getRealName())) {
                comUser.setRealName(comUser.getWxNkName());
            }
            Integer agentBrandNum = 0;
            Integer totalUserNum = 0;
            BigDecimal totalSaleAmount = BigDecimal.ZERO;
            List<PfUserBrand> pfUserBrands = pfUserBrandMapper.selectByUserId(comUser.getId());
            for (PfUserBrand pfUserBrand : pfUserBrands) {
                BrandStatistic brandStatistic = brandStatisticService.selectBrandStatisticByUserIdAndBrandId(pfUserBrand.getUserId(), pfUserBrand.getBrandId());
                agentBrandNum++;
                totalUserNum += brandStatistic.getUserNum();
                totalSaleAmount = totalSaleAmount.add(brandStatistic.getSellAmount());
            }
            searchRes.getDataMap().put("agentBrandNum", agentBrandNum);
            searchRes.getDataMap().put("totalUserNum", totalUserNum);
            searchRes.getDataMap().put("totalSaleAmount", totalSaleAmount);
            searchRes.getDataMap().put("comUser", comUser);
            searchRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            searchRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            searchRes.setResCode(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
            searchRes.setResMsg("统计团队人数和总销售额失败!");

            log.error("统计团队人数和总销售额失败!" + e);
            e.printStackTrace();
        }

        return searchRes;
    }

    /**
     * 用户家族
     *
     * @param request
     * @param searchReq
     * @return
     */
    @RequestMapping("/listOrganization")
    @ResponseBody
    @SignValid(paramType = SearchReq.class)
    public SearchRes listOrganization(HttpServletRequest request, SearchReq searchReq) {
        SearchRes searchRes = new SearchRes();

        try {
            ComUser comUser = userService.getUserById(searchReq.getUserId());
            if (comUser != null && StringUtils.isBlank(comUser.getRealName())) {
                comUser.setRealName(comUser.getWxNkName());
            }
            //List<Map<String, Object>> organizations = userOrganizationService.listByUserId(searchReq.getUserId());
            List<Map<String, Object>> organizations = userOrganizationService.listCreateAndJoinOrganization(searchReq.getUserId());

            searchRes.getDataMap().put("comUser", comUser);
            searchRes.getDataMap().put("organizations", organizations);
            searchRes.getDataMap().put("imgUrlPrefix", "http://file.qc.masiis.com/static/user/organization/");
            searchRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            searchRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            searchRes.setResCode(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
            searchRes.setResMsg("获取合伙人代理的品牌!");

            log.error("获取代理品牌失败!" + e);
            e.printStackTrace();
        }
        String json = JSONObject.toJSONString(searchRes);
        log.info("------获取合伙人代理的品牌的json数据-------" + json);
        return searchRes;
    }
}
