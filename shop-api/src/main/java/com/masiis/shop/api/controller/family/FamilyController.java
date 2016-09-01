package com.masiis.shop.api.controller.family;

import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.bean.family.*;
import com.masiis.shop.api.bean.user.PartnerIndexReq;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.common.enums.api.MarketTypeEnum;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.family.ComBrandForFamily;
import com.masiis.shop.dao.beans.family.FamilyList;
import com.masiis.shop.dao.beans.product.SkuInfoaAPP;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.PbBannerMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.BrandService;
import com.masiis.shop.web.platform.service.product.PfUserAgentApplicationService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.statistics.BrandStatisticService;
import com.masiis.shop.web.platform.service.user.CountGroupService;
import com.masiis.shop.web.platform.service.user.PfUserBrandService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiajinghao on 2016/8/3.
 */
@Controller
@RequestMapping("/pickList")
public class FamilyController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfUserBrandService pfUserBrandService;
    @Resource
    private PfUserAgentApplicationService pfUserAgentApplicationService;
    @Resource
    private BrandService brandService;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private PbBannerMapper pbBannerMapper;
    @Resource
    private BrandStatisticService brandStatisticService;


    private static Integer agentLevelId_family = 3;

    private static Integer agentLevelId_team = 4;

    private static Integer pageSize = 10;

    private String bannerUrl = PropertiesUtils.getStringValue("index_banner_url");

    /**
     * jjh
     * 世界市场展示
     *
     * @param req
     * @param user
     * @return
     */
    @RequestMapping("/world")
    @ResponseBody
    @SignValid(paramType = PartnerIndexReq.class)
    public WorldRes worldInfo(HttpServletRequest request, PartnerIndexReq req, ComUser user) {
        log.info("世界市场展示");
        WorldRes res = new WorldRes();
        try {
            List<PbBanner> pbBanners = pbBannerMapper.selectAll();//banner 图列表
            for (PbBanner pbBanner : pbBanners) {
                pbBanner.setImgUrl(bannerUrl + pbBanner.getImgUrl());
            }
            List<ComBrandForFamily> comBrandForFamilies = pfUserBrandService.organizationListForWorld();//品牌
            List<PfUserOrganization> pfUserOrganizations = pfUserBrandService.familyListForWorld(agentLevelId_family, MarketTypeEnum.TYPE_Family.getCode());//家族
            List<PfUserOrganization> pfUserOrganizations1 = pfUserBrandService.familyListForWorld(agentLevelId_team, MarketTypeEnum.TYPE_Team.getCode());//团队
            List<SkuInfoaAPP> skuInfoaAPPs = pfUserBrandService.skuListOfWorld(user.getId());//世界新品
            res.setBannerList(pbBanners);
            res.setComBrandForFamilies(comBrandForFamilies);
            res.setFamilyList(pfUserOrganizations);
            res.setTeamList(pfUserOrganizations1);
            res.setSkuInfoaAPPs(skuInfoaAPPs);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
        }
        return res;
    }

    /**
     * 全部世界品牌
     *
     * @param req
     * @param user
     * @return
     */
    @RequestMapping("/allWorldSku")
    @ResponseBody
    @SignValid(paramType = PartnerIndexReq.class)
    public FamilyRes worldInfoPage(HttpServletRequest request, PartnerIndexReq req, ComUser user) {
        log.info("全部世界品牌展示");
        FamilyRes res = new FamilyRes();
        try {
            List<ComBrandForFamily> comBrands = pfUserBrandService.organizationListForWorldPaging(req.getPageNum(), pageSize);
            res.setComBrandForFamilies(comBrands);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
        }
        return res;
    }

    /**
     * 全部家族，团队列表--未选择
     *
     * @param req
     * @param user
     * @return
     */
    @RequestMapping("/allWorldFamily")
    @ResponseBody
    @SignValid(paramType = FamilyInfoReq.class)
    public FamilyInfoRes worldOFFamilyInfoPage(HttpServletRequest request, FamilyInfoReq req, ComUser user) {
        FamilyInfoRes res = new FamilyInfoRes();
        try {

            List<FamilyList> familyLists = pfUserBrandService.allFamilyPaging(agentLevelId_family, req.getPageNum(), pageSize);
            res.setFamilyLists(familyLists);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
        }
        return res;
    }

    /**
     * jjh
     * 非二维码用户进来申请代理选择上级团队,家族的过程
     * 当前品牌已经选择
     */
    @RequestMapping("/upper")
    @ResponseBody
    @SignValid(paramType = UpperFamilyReq.class)
    public FamilyInfoRes upperFamilyListInfoPage(HttpServletRequest request, UpperFamilyReq req, ComUser user) {
        log.info("小白用户选择上级--当前品牌为" + req.getBrandId() + "");
        FamilyInfoRes res = new FamilyInfoRes();
        try {
            List<FamilyList> familyLists = pfUserBrandService.familyListForWorldPaging(req.getAgentLevelId(), req.getPageNum(), pageSize, req.getReorder(), req.getBrandId());
            res.setLimitMoney(getLimitMoneyBySkuId(comSkuMapper.getPrimarySkuByBrandId(req.getBrandId()).get(0).getId(), req.getAgentLevelId() + 1));
            res.setCreateLimitMoney(getLimitMoneyBySkuId(comSkuMapper.getPrimarySkuByBrandId(req.getBrandId()).get(0).getId(), agentLevelId_family));
            res.setFamilyLists(familyLists);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
        }
        return res;
    }

    /**
     * jjh
     * 非二维码用户进来申请代理选择上创建家族的过程
     * 创建家族
     */
    @RequestMapping("/addFamily")
    @ResponseBody
    @SignValid(paramType = AddFamilyReq.class)
    public BaseRes addFamily(HttpServletRequest request, AddFamilyReq req, ComUser user) {
        log.info("创建家族");
        AddFamilyRes res = new AddFamilyRes();
        try {
            res.setIsDuplicatePhone(0);
            res.setIsLegalMobile(0);
            res.setIslegalWxcode(0);
            PfUserAgentApplication hasApplication = pfUserAgentApplicationService.getPfUserAgentApplicationByPhoneAndSkuId(req.getPhone(), req.getSkuId());
            if (hasApplication != null) {
                res.setIsDuplicatePhone(1);
                res.setResCode(SysResCodeCons.RES_CODE_ILLEGAL);
                res.setResCode(SysResCodeCons.PHONE_DUPLICATE);
                log.info("该sku的手机号已经被绑定");
                return res;
            }
            String pattenForWxcode = "^[a-zA-Z0-9_-]{6,20}$";
            String pattenForMobile = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";
            if (startCheck(pattenForWxcode, req.getWxCode()) == false) {
                res.setIslegalWxcode(1);
                res.setResCode(SysResCodeCons.RES_CODE_ILLEGAL);
                res.setResCode(SysResCodeCons.WXCODE_IILEAGAL);
                log.info("该微信号不合法");
                return res;
            }
            if (startCheck(pattenForMobile, req.getPhone()) == false) {
                res.setIsLegalMobile(1);
                res.setResCode(SysResCodeCons.RES_CODE_ILLEGAL);
                res.setResCode(SysResCodeCons.PHONE_IILEAGAL);
                log.info("该手机号不合法");
                return res;
            }
            PfUserAgentApplication pfUserAgentApplication = new PfUserAgentApplication();
            pfUserAgentApplication.setCreateMan(user.getId());
            pfUserAgentApplication.setCreateTime(new Date());
            pfUserAgentApplication.setMobile(req.getPhone());
            pfUserAgentApplication.setName(req.getUserName());
            pfUserAgentApplication.setWxId(req.getWxCode());
            pfUserAgentApplication.setSkuId(req.getSkuId());
            pfUserAgentApplication.setStatus(0);
            pfUserAgentApplication.setRemark("");
            pfUserAgentApplication.setAgentLevelId(3);
            pfUserAgentApplicationService.addApplicationUser(pfUserAgentApplication);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            log.info("申请家族提交OK");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
        }
        return res;
    }

    /**
     * jjh
     * 品牌下所有商品--家族
     */
    @RequestMapping("/familySkuList")
    @ResponseBody
    @SignValid(paramType = BrandOfSkuReq.class)
    public BrandOfSkuRes listOfWorldBrand(HttpServletRequest request, BrandOfSkuReq req, ComUser user) {
        BrandOfSkuRes res = new BrandOfSkuRes();
        log.info("" + req.getBrandId() + "下主打商品列表");
        try {
            List<SkuInfoaAPP> skuInfoaAPPs = pfUserBrandService.getSkuByBrandId(req.getBrandId(), user.getId());
            ComBrand comBrand = brandService.getById(req.getBrandId());
            res.setComBrand(comBrand);
            res.setSkuInfoaAPPs(skuInfoaAPPs);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
        }
        return res;
    }

    /**
     * 全部家族
     *
     * @param req
     * @param user
     * @return
     */
    @RequestMapping("/listFamilyPage")
    @ResponseBody
    @SignValid(paramType = FamilyInfoReq.class)
    public OrganazitionRes allFamilyInfoPage(HttpServletRequest request, FamilyInfoReq req, ComUser user) {
        log.info("全部家族展示");
        OrganazitionRes res = new OrganazitionRes();
        try {
            List<PfUserOrganization> familyLists = pfUserBrandService.allfamilyListForWorld(agentLevelId_family, MarketTypeEnum.TYPE_Family.getCode(), req.getPageNum());
            for (PfUserOrganization pfUserOrganization : familyLists) {
                res.setNumberOfOrganization(brandStatisticService.selectBrandStatisticByUserIdAndBrandId(pfUserOrganization.getUserId(), pfUserOrganization.getBrandId()).getUserNum());
            }
            res.setFamilyList(familyLists);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
        }
        return res;
    }

    /**
     * 全部团队
     *
     * @param req
     * @param user
     * @return
     */
    @RequestMapping("/listTeamPage")
    @ResponseBody
    @SignValid(paramType = FamilyInfoReq.class)
    public OrganazitionRes allTeamInfoPage(HttpServletRequest request, FamilyInfoReq req, ComUser user) {
        log.info("全部团队展示");
        OrganazitionRes res = new OrganazitionRes();
        try {
            List<PfUserOrganization> familyLists = pfUserBrandService.allfamilyListForWorld(agentLevelId_team, MarketTypeEnum.TYPE_Team.getCode(), req.getPageNum());
            for (PfUserOrganization pfUserOrganization : familyLists) {
                res.setNumberOfOrganization(brandStatisticService.selectBrandStatisticByUserIdAndBrandId(pfUserOrganization.getUserId(), pfUserOrganization.getBrandId()).getUserNum());
            }
            res.setTeamList(familyLists);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
        }
        return res;
    }

    /**
     * jjh
     * 品牌下所有商品--page
     */
    @RequestMapping("/familySkuListPage")
    @ResponseBody
    @SignValid(paramType = BrandOfSkuReq.class)
    public BrandOfSkuRes listOfWorldBrandPaing(HttpServletRequest request, BrandOfSkuReq req, ComUser user) {
        log.info("" + req.getBrandId() + "下非主打商品列表");
        BrandOfSkuRes res = new BrandOfSkuRes();
        try {

            List<SkuInfoaAPP> skuInfoaAPPs = pfUserBrandService.getSkuByBrandIdPaging(req.getBrandId(), user.getId(), req.getPageNum(), pageSize);
            res.setSkuInfoaAPPs(skuInfoaAPPs);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
        }
        return res;
    }

    private boolean startCheck(String reg, String str) {
        boolean tem = false;
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        tem = matcher.matches();
        return tem;
    }

    private BigDecimal getLimitMoneyBySkuId(Integer skuId, Integer agentLevelId) {
        BigDecimal agentMoney = new BigDecimal(0);
        PfSkuAgent pfSkuAgent = skuAgentService.getBySkuIdAndLevelId(skuId, agentLevelId);
        BigDecimal amount = pfSkuAgent.getTotalPrice();
        agentMoney = amount.add(pfSkuAgent.getBail()).setScale(2, RoundingMode.HALF_DOWN);
        return agentMoney;
    }
}
