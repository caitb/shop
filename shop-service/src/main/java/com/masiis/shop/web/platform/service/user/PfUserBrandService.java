package com.masiis.shop.web.platform.service.user;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.common.constant.platform.SysConstants;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.family.ComBrandForFamily;
import com.masiis.shop.dao.beans.family.FamilyList;
import com.masiis.shop.dao.beans.product.SkuInfoaAPP;
import com.masiis.shop.dao.beans.user.CountGroup;
import com.masiis.shop.dao.platform.product.*;
import com.masiis.shop.dao.platform.user.*;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.utils.ListSortUtil;
import com.masiis.shop.web.platform.service.product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jiajinghao on 2016/8/5.
 * 用户代理品牌service
 */
@Service
@Transactional
public class PfUserBrandService {

    @Resource
    private PfUserBrandMapper pfUserBrandMapper;
    @Resource
    private ComBrandMapper comBrandMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private ComSkuImageMapper comSkuImageMapper;
    @Resource
    private ComSpuMapper comSpuMapper;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private CountGroupService countGroupService;
    @Resource
    private PfUserOrganizationMapper pfUserOrganizationMapper;
    @Resource
    private ComSkuNewMapper comSkuNewMapper;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private PfSkuStatisticMapper pfSkuStatisticMapper;
    @Resource
    private ProductService productService;
    @Resource
    private CountGroupMapper countGroupMapper;
    @Resource
    private ComSkuExtensionMapper comSkuExtensionMapper;
    @Resource
    private PfUserRecommendRelationService pfUserRecommendRelationService;
    private static Integer pageSize = 10;

    private String backImg = PropertiesUtils.getStringValue("organization_img_url");

    private String logoUrl = PropertiesUtils.getStringValue("organization_img_url");

    /**
     * jjh
     * 世界市场--世界品牌 page 4
     */
    public List<ComBrandForFamily> organizationListForWorld() {
        PageHelper.startPage(1, 4, false);
        List<ComBrand> comBrands = comBrandMapper.selectAllForWorld();
        List<ComBrandForFamily> comBrandForFamilies = new ArrayList<>();
        for (ComBrand comBrand : comBrands) {
            ComBrandForFamily comBrandForFamily = new ComBrandForFamily();
            comBrandForFamily.setComBrand(comBrand);
            comBrandForFamilies.add(comBrandForFamily);
        }
        return comBrandForFamilies;
    }

    /**
     * 分页展示
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    public List<ComBrandForFamily> organizationListForWorldPaging(int currentPage, int pageSize) throws Exception {
        PageHelper.startPage(currentPage, pageSize, false);
        List<ComBrand> comBrands = comBrandMapper.selectAllForWorldPage();
        List<ComBrandForFamily> comBrandForFamilies = new ArrayList<>();
        for (ComBrand comBrand : comBrands) {
            ComBrandForFamily comBrandForFamily = new ComBrandForFamily();
            PfSkuStatistic pfSkuStatistic = pfSkuStatisticMapper.selectBySkuId(getPrimarySkuByBrandId(comBrand.getId()).get(0).getId());
            comBrandForFamily.setComBrand(comBrand);
            comBrandForFamily.setNumOfAgent(pfSkuStatistic.getAgentNum());
            comBrandForFamilies.add(comBrandForFamily);
        }
        return comBrandForFamilies;
    }


    /**
     * jjh
     * 世界市场--家族,团队显示
     * @return
     */
    public List<PfUserOrganization> familyListForWorld(Integer levelId, Integer type) {
        PageHelper.startPage(1, 4, false);
        List<PfUserOrganization> pfUserOrganizations = pfUserOrganizationMapper.selectAllFamily(levelId, type);
        for (PfUserOrganization pfUserOrganization : pfUserOrganizations) {
            pfUserOrganization.setLogo(backImg + pfUserOrganization.getLogo());
        }
        return pfUserOrganizations;
    }

    /**
     * jjh
     * 世界新品列表
     */
    public List<SkuInfoaAPP> skuListOfWorld(Long userId) throws Exception {
        List<SkuInfoaAPP> skuInfoaAPPs = new ArrayList<>();
        List<ComSkuNew> comSkuList = comSkuNewMapper.selectAll();
        String Value = PropertiesUtils.getStringValue("index_product_prototype_url");
        for (ComSkuNew comSkuNew : comSkuList) {
            SkuInfoaAPP skuInfoaAPP = new SkuInfoaAPP();
            ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(comSkuNew.getSkuId());
            ComSku comSku = comSkuMapper.findBySkuId(comSkuNew.getSkuId());
            ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
            ComSkuExtension comSkuExtension = comSkuExtensionMapper.selectBySkuId(comSkuNew.getSkuId());
            //check 是否代理
            PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(userId, comSku.getId());
            if (pfUserSku != null) {
                skuInfoaAPP.setIsAgentSku(1); //代理了该商品
            } else { //检查能不能代理
                skuInfoaAPP.setIsAgentSku(0);
            }
            skuInfoaAPP.setComSku(comSku);
            skuInfoaAPP.setMaxDiscount(productService.getMaxDiscount(comSku.getId()));
            skuInfoaAPP.setSlogo(comSpu.getSlogan());
            skuInfoaAPP.setComSkuImgDefault(Value + comSkuExtension.getIllustratingPicture());
            skuInfoaAPP.setNumOfSale(pfSkuStatisticMapper.selectBySkuId(comSku.getId()).getAgentNum());
            skuInfoaAPPs.add(skuInfoaAPP);
        }
        return skuInfoaAPPs;
    }

    /**
     * 家族,团队列表显示
     */
    public List<FamilyList> allFamilyPaging(Integer levelId, int currentPage, int pageSize) throws Exception {
        List<FamilyList> familyLists = new ArrayList<>();
        PageHelper.startPage(currentPage, pageSize, false);
        List<PfUserOrganization> pfUserOrganizations = pfUserOrganizationMapper.selectAllFamilyByAgentLevelId(levelId);
        for (PfUserOrganization pfUserOrganization : pfUserOrganizations) {
            FamilyList familyList = new FamilyList();
            CountGroup countGroup = countFamilyOrTeamNumber(pfUserOrganization.getUserId());
            pfUserOrganization.setLogo(backImg + pfUserOrganization.getLogo());
            familyList.setPfUserOrganization(pfUserOrganization);
            familyList.setNumOfFamily(countGroup.getCount());
            familyLists.add(familyList);
        }
        return familyLists;
    }

    /**
     * jjh
     * 世界市场--家族列表,团队列表 page
     * @return
     */
    public List<FamilyList> familyListForWorldPaging(Integer levelId, int currentPage, int pageSize, Integer reorder, Integer brandId) throws Exception {
        List<FamilyList> familyLists = new ArrayList<>();
        PageHelper.startPage(currentPage, pageSize, false);
        List<PfUserOrganization> pfUserOrganizations = pfUserOrganizationMapper.selectAllFamilyByAgentLevelIdAndBrandId(levelId, brandId);
        for (PfUserOrganization pfUserOrganization : pfUserOrganizations) {
            FamilyList familyList = new FamilyList();
            Map<String, Object> countMap = countGroupMapper.countGroupByBrandId(pfUserOrganization.getUserId(),pfUserOrganization.getBrandId());
            pfUserOrganization.setLogo(backImg + pfUserOrganization.getLogo());
            familyList.setPfUserOrganization(pfUserOrganization);
            familyList.setNumOfFamily(Integer.valueOf(countMap.get("COUNT").toString()));
            familyList.setSaleNum(new BigDecimal(countMap.get("groupMoney").toString()));
            familyLists.add(familyList);
        }
        //默认按人数排序
        ListSortUtil<FamilyList> listListSortUtil = new ListSortUtil<FamilyList>();
        if(reorder==0){
            listListSortUtil.sort(familyLists, "numOfFamily", "desc");
        }
        if (reorder == 1) { //按销售额重排
            listListSortUtil.sort(familyLists, "saleNum", "desc");
        }
        return familyLists;
    }

    /**
     * jjh
     * 查看家族，团队的人数,销售额
     */
    public CountGroup countFamilyOrTeamNumber(Long userId) throws Exception {
        Integer numb = 0;
        Integer orderNum = 0;
        BigDecimal countNum = new BigDecimal("0");
        CountGroup countGroup = new CountGroup();
        List<PfUserSku> agentNum = userSkuService.getAgentNumByUserId(userId);
        if (agentNum != null) {
            for (PfUserSku pfUserSku : agentNum) {
                countGroup = countGroupService.countGroupInfo(pfUserSku.getTreeCode());
                countNum = countGroup.getGroupMoney().add(countNum);
                numb += countGroup.getCount();
                orderNum += countGroup.getOrderNum();
            }
        }
        countGroup.setCount(numb);
        countGroup.setGroupMoney(countNum);
        countGroup.setOrderNum(orderNum);
        return countGroup;
    }


    /**
     * jjh
     * 单个品牌下的商品列表
     */
    public List<SkuInfoaAPP> getSkuByBrandId(Integer brandId, Long userId) throws Exception{
        List<SkuInfoaAPP> allSkuInfoApps = new ArrayList<>();
        List<ComSku> primarySku = comSkuMapper.getPrimarySkuByBrandId(brandId);//主打
        String Value = PropertiesUtils.getStringValue("index_product_prototype_url");
        for (ComSku comSku :primarySku){//主打列表
            SkuInfoaAPP skuInfoaAPP = new SkuInfoaAPP();
            ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(comSku.getId());
            ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
            ComSkuExtension comSkuExtension = comSkuExtensionMapper.selectBySkuId(comSku.getId());
            //check 是否代理
            PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(userId, comSku.getId());
            if (pfUserSku != null) {
                skuInfoaAPP.setIsAgentSku(1);//代理了该商品
            } else {
                skuInfoaAPP.setIsAgentSku(0);
            }
            skuInfoaAPP.setMaxDiscount(productService.getMaxDiscount(comSku.getId()));
            skuInfoaAPP.setComSku(comSku);
            skuInfoaAPP.setComSkuImgDefault(Value + comSkuExtension.getIllustratingPicture());
            skuInfoaAPP.setNumOfSale(pfSkuStatisticMapper.selectBySkuId(comSku.getId()).getAgentNum());
            skuInfoaAPP.setSlogo(comSpu.getSlogan());
            allSkuInfoApps.add(skuInfoaAPP);
        }
        return allSkuInfoApps;
    }

    public List<SkuInfoaAPP> getSkuByBrandIdPaging(Integer brandId, Long userId,int currentPage, int pageSize) throws Exception{
        Long tempUserPid = 0L;
        boolean isBoss = false;
        List<SkuInfoaAPP> allSkuInfoApps = new ArrayList<>();
        PageHelper.startPage(currentPage, pageSize, false);
        List<ComSku> comSkus = comSkuMapper.getSkuListByBrandId(brandId);//非主打
        List<ComSku> primarySku = comSkuMapper.getPrimarySkuByBrandId(brandId);//主打
        PfUserSku primaryPfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(userId, primarySku.get(0).getId());
        if(primaryPfUserSku!=null && primaryPfUserSku.getUserPid()==0){
            isBoss = true;//该品牌下是boss
        }
        if(primaryPfUserSku!=null){
            tempUserPid = primaryPfUserSku.getUserPid();
        }
        //获取推荐关系
        PfUserRecommenRelation pfUserRecommenRelation =  pfUserRecommendRelationService.selectRecommenRelationByUserIdAndSkuId(userId, primarySku.get(0).getId());
        String Value = PropertiesUtils.getStringValue("index_product_prototype_url");
        for (ComSku comSku : comSkus) { //非主打列表
            SkuInfoaAPP skuInfoaAPP = new SkuInfoaAPP();
            ComSkuImage comSkuImage = comSkuImageMapper.selectDefaultImgBySkuId(comSku.getId());
            ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
            //check 自己是否代理
            PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(userId, comSku.getId());
            ComSkuExtension comSkuExtension = comSkuExtensionMapper.selectBySkuId(comSku.getId());
            if (pfUserSku != null) {
                skuInfoaAPP.setIsAgentSku(1);//代理了该商品
            } else {
                skuInfoaAPP.setIsAgentSku(0);
                if(pfUserRecommenRelation!=null && pfUserRecommenRelation.getUserPid()!=0){
                    PfUserSku upgradePfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(pfUserRecommenRelation.getUserPid(),comSku.getId());//推荐人对该商品的代理状态
                    if (upgradePfUserSku==null){
                        skuInfoaAPP.setUpperisAgentSku(0);
                    }else {
                        skuInfoaAPP.setUpperisAgentSku(1);
                    }
                }else {
                    if(!isBoss){
                        //查找上级的代理状态
                        PfUserSku upperUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(tempUserPid, comSku.getId());
                        if(upperUserSku==null){ //上级没代理
                            skuInfoaAPP.setUpperisAgentSku(0);
                        }else {
                            skuInfoaAPP.setUpperisAgentSku(1);
                        }
                        skuInfoaAPP.setIsBoss(0);
                    }else {
                        skuInfoaAPP.setIsBoss(1);
                    }
                }
            }
            skuInfoaAPP.setMaxDiscount(productService.getMaxDiscount(comSku.getId()));
            skuInfoaAPP.setComSku(comSku);
            skuInfoaAPP.setComSkuImgDefault(Value + comSkuExtension.getIllustratingPicture());
            skuInfoaAPP.setNumOfSale(pfSkuStatisticMapper.selectBySkuId(comSku.getId()).getAgentNum());
            skuInfoaAPP.setSlogo(comSpu.getSlogan());
            allSkuInfoApps.add(skuInfoaAPP);
        }
        return allSkuInfoApps;
    }

    /**
     * 全部家族，团队
     */
    public List<PfUserOrganization> allfamilyListForWorld(Integer levelId, Integer type,int currentPage) {
        PageHelper.startPage(currentPage, pageSize, false);
        List<PfUserOrganization> pfUserOrganizations = pfUserOrganizationMapper.selectAllFamily(levelId, type);
        for (PfUserOrganization pfUserOrganization : pfUserOrganizations) {
            pfUserOrganization.setLogo(backImg + pfUserOrganization.getLogo());
        }
        return pfUserOrganizations;
    }

    /**
     * 查询用户品牌合伙关系
     *
     * @param userId  用户id
     * @param brandId 品牌id
     * @return
     */
    public PfUserBrand findByUserIdAndBrandId(Long userId, Integer brandId) {
        return pfUserBrandMapper.selectByUserIdAndBrandId(userId, brandId);
    }

    /**
     * 添加用户品牌代理关系
     *
     * @param pfUserBrand
     * @return
     */
    public int insert(PfUserBrand pfUserBrand) {
        return pfUserBrandMapper.insert(pfUserBrand);
    }

    /**
     * 根据品牌查找主打商品
     */
    public List<ComSku> getPrimarySkuByBrandId(Integer brandId){
        return comSkuMapper.getPrimarySkuByBrandId(brandId);
    }

    /**
     * 查询我代理的品牌数量
     * @param userId
     * @return
     */
    public Integer getMyBrandNum(Long userId){
        return pfUserBrandMapper.selectMyBrandNum(userId);
    }
}

