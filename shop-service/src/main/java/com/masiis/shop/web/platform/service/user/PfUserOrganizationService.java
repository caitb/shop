package com.masiis.shop.web.platform.service.user;

import com.alibaba.druid.sql.visitor.functions.Substring;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.family.FamilyHomeListPo;
import com.masiis.shop.dao.beans.family.PfUserOrganizationExtend;
import com.masiis.shop.dao.beans.message.PfMessageToNewBean;
import com.masiis.shop.dao.platform.user.CountGroupMapper;
import com.masiis.shop.dao.platform.user.PfUserBrandMapper;
import com.masiis.shop.dao.platform.user.PfUserOrganizationMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserOrganization;
import com.masiis.shop.web.common.service.UserService;
import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;
import org.springframework.oxm.support.SaxResourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.org.mozilla.javascript.internal.regexp.SubString;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.StreamHandler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiajinghao on 2016/8/4.
 * 组织（家族，团队）service
 */
@Service
@Transactional
public class PfUserOrganizationService {

    private static final Logger logger = Logger.getLogger(PfUserOrganizationService.class);
    @Resource
    private PfUserOrganizationMapper pfUserOrganizationMapper;
    @Resource
    private CountGroupService countGroupService;
    @Resource
    private PfUserSkuService pfUserSkuService;

    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private CountGroupMapper countGroupMapper;
    @Resource
    private PfUserBrandMapper pfUserBrandMapper;
    @Resource
    private UserService userService;


    private static Integer pageSize = 10;

    private String backImg = PropertiesUtils.getStringValue("organization_img_url");

    private String logoUrl = PropertiesUtils.getStringValue("organization_logo_url");
    private String logoImgUrl = PropertiesUtils.getStringValue("organization_img_url");

    /**
     * jjh
     * 获取组织基础信息
     *
     * @param userId
     * @return
     */
    public List<PfUserOrganization> getOrganizationInfoByUserId(Long userId) {
        List<PfUserOrganization> pfUserOrganizationList = pfUserOrganizationMapper.selectOrganizationByUserId(userId);
        for (PfUserOrganization pfUserOrganization : pfUserOrganizationList) {
            if (pfUserOrganization.getBackImg() != null && !pfUserOrganization.getBackImg().equals("")) {
                pfUserOrganization.setBackImg(backImg + pfUserOrganization.getBackImg());
            }
            if (pfUserOrganization.getLogo() != null && !pfUserOrganization.getLogo().equals("")) {
                pfUserOrganization.setLogo(logoUrl + pfUserOrganization.getLogo());
            }
        }
        return pfUserOrganizationList;
    }

    public PfUserOrganization getById(Integer organizationId) {
        return pfUserOrganizationMapper.selectByPrimaryKey(organizationId);
    }

    public PfUserOrganization getByUserIdAndBrandId(Long userId, Integer brandId) {
        return pfUserOrganizationMapper.selectByBrandIdAndUserId(brandId, userId);
    }

    public int update(PfUserOrganization pfUserOrganization) {
        return pfUserOrganizationMapper.updateByPrimaryKey(pfUserOrganization);
    }

    /**
     * 通过userId获取家族首页 我创建的列表
     *
     * @return
     */
//    public FamilyHomeListPo getMyFamilyHomeList(Long userId, Integer pageNum) throws Exception{
//        logger.info("通过userId获取家族首页 我创建的列表");
//        logger.info("userId = " + userId);
//        FamilyHomeListPo family = new FamilyHomeListPo();
//        Page pageHelp = PageHelper.startPage(pageNum, pageSize);
//        List<PfUserOrganization> organizations = pfUserOrganizationMapper.selectOrganization(userId);
//        if (pageHelp.getPages() > 0){
//            if (pageHelp.getPages() < pageNum.intValue()){
//                throw new BusinessException("1");
//            }
//        }
//        List<PfUserOrganizationExtend> organizationExtends = new LinkedList<>();
//        for (PfUserOrganization organization : organizations){
//            PfUserOrganizationExtend extend = new PfUserOrganizationExtend();
//            if (organization.getBackImg() != null && !organization.getBackImg().equals("")){
//                organization.setBackImg(backImg + organization.getBackImg());
//            }
//            if (organization.getLogo() != null && !organization.getLogo().equals("")){
//                organization.setLogo(logoUrl + organization.getLogo());
//            }
//            extend.setPfUserOrganization(organization);
//            extend.setCountGroup(countGroupService.countGroupInfoByBrand(organization.getUserId(), organization.getBrandId()));
//            organizationExtends.add(extend);
//        }
//        family.setOrganizations(organizationExtends);
//        family.setPageNum(pageHelp.getPageNum());
//        family.setPageSize(pageHelp.getPageSize());
//        family.setTotalCount(pageHelp.getTotal());
//        family.setTotalPage(pageHelp.getPages());
//        return family;
//    }

//    /**
//     * 获取我加入的组织
//     * @param userId
//     * @param pageNum
//     * @return
//     */
//    public FamilyHomeListPo getJoinFamilyHomeList(Long userId, Integer pageNum) throws Exception{
//        logger.info("通过userId获取家族首页 我加入的组织列表");
//        logger.info("userId = " + userId);
//        FamilyHomeListPo family = new FamilyHomeListPo();
//        List<Map<String, Number>> maps = pfUserSkuService.getUpBrand(userId);
//        //key:userId  value:brandId
//        Map<Long, Long> brandMap;
//        Map<Integer, Map<Long, Long>> brandMaps = new HashMap<>();
//        int a = 0;
//        for (Map<String, Number> map : maps){
//            logger.info("userPid = " + map.get("userPid"));
//            logger.info("brandId = " + map.get("brandId"));
//            brandMap = new HashMap<>();
//            brandMap.put(map.get("userPid").longValue(), map.get("brandId").longValue());
//            brandMaps.put(a, brandMap);
//            a++;
//        }
//        Page pageHelp = PageHelper.startPage(pageNum, pageSize);
//        List<PfUserOrganization> organizations = pfUserOrganizationMapper.selectOrganizationMap(brandMaps);
//        logger.info("organizations.size = " + organizations.size());
//        if (pageHelp.getPages() > 0){
//            if (pageHelp.getPages() < pageNum.intValue()){
//                throw new BusinessException("1");
//            }
//        }
//        List<PfUserOrganizationExtend> organizationExtends = new LinkedList<>();
//        for (PfUserOrganization organization : organizations){
//            PfUserOrganizationExtend extend = new PfUserOrganizationExtend();
//            if (organization.getBackImg() != null && !organization.getBackImg().equals("")){
//                organization.setBackImg(backImg + organization.getBackImg());
//            }
//            if (organization.getLogo() != null && !organization.getLogo().equals("")){
//                organization.setLogo(logoUrl + organization.getLogo());
//            }
//            extend.setPfUserOrganization(organization);
//            extend.setCountGroup(countGroupService.countGroupInfoByBrand(organization.getUserId(), organization.getBrandId()));
//            organizationExtends.add(extend);
//        }
//        family.setOrganizations(organizationExtends);
//        family.setPageNum(pageHelp.getPageNum());
//        family.setPageSize(pageHelp.getPageSize());
//        family.setTotalCount(pageHelp.getTotal());
//        family.setTotalPage(pageHelp.getPages());
//        return family;
//    }
    public void fillLogoUrl(List<PfUserOrganization> orgs) {
        if (orgs != null) {
            for (PfUserOrganization org : orgs) {
                org.setLogo(logoImgUrl + org.getLogo());
            }
        }
    }

    public List<PfUserOrganization> selectByUserIdAndLevelId(Long userId, Integer levelId) {
        PfUserOrganization org = new PfUserOrganization();
        org.setUserId(userId);
        org.setAgentLevelId(levelId);
        return pfUserOrganizationMapper.select(org);
    }

    public List<PfUserOrganization> selectParentByUserIdAndLevelId(Long userId, Integer agentLevelId) {
        return pfUserOrganizationMapper.selectParentByUserIdAndLevelId(userId, agentLevelId);
    }

    public List<PfMessageToNewBean> getNumsGroupByOrganizationAndUserId(Long userId) {
        return pfUserOrganizationMapper.selectNumsGroupByOrganization(userId);
    }

    public PfUserOrganization loadByBrandIdAndUserId(Integer brandId, Long userId) {
        return pfUserOrganizationMapper.selectByBrandIdAndUserId(brandId, userId);
    }

    public void save(PfUserOrganization pfUserOrganization) {
        pfUserOrganizationMapper.insert(pfUserOrganization);
    }


    public Map<String, Object> searchByMobile(String mobile, Integer brandId) {
        return pfUserOrganizationMapper.selectByMobile(mobile, brandId);
    }

    /**
     * 获取代理品牌
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> listAgentBrand(Long userId) {
        return pfUserSkuMapper.selectAgentBrand(userId, null);
    }

    /**
     * 获取代理品牌
     *
     * @param userId
     * @return
     */
    public Map<String, Object> loadAgentBrand(Long userId, Integer brandId) {
        List<Map<String, Object>> agentBrands = pfUserSkuMapper.selectAgentBrand(userId, brandId);
        return agentBrands.size() == 0 ? null : agentBrands.get(0);
    }

    /**
     * 获取 上 级代理品牌
     *
     * @param userId
     * @return
     */
    public Map<String, Object> loadPAgentBrand(Long userId, Integer brandId) {
        List<Map<String, Object>> agentBrands = pfUserSkuMapper.selectPAgentBrand(userId, brandId);
        return agentBrands.size() == 0 ? null : agentBrands.get(0);
    }

    /**
     * 查找加入的家族
     *
     * @param userIds
     * @return
     */
    public List<Map<String, Object>> listFromOrganization(List<Long> userIds) {
        return pfUserOrganizationMapper.selectFromOrganization(userIds);
    }

    /**
     * 获取家族或团队
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> listByUserId(Long userId) {
        return pfUserOrganizationMapper.selectByUserId(userId);
    }

    /**
     * 查询我创建的团队
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> listCreateOrganization(Long userId) {
        return pfUserOrganizationMapper.selectCreateOrganization(userId);
    }

    /**
     * 我加入的家族或团队
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> listJoinOrganization(Long userId) {
        return pfUserOrganizationMapper.selectJoinOrganization(userId);
    }

    /**
     * 查询我创建的和(我最低等级时)直接上级的家族
     *
     * @param userId
     * @return
     */
    public List<Map<String, Object>> listCreateAndJoinOrganization(Long userId) {
        return pfUserOrganizationMapper.selectCreateAndJoinOrganization(userId);
    }

    /**
     * 统计团队人数和总销售额
     *
     * @param userId
     * @return
     */
    public Map<String, Object> countByUserIdAndBrandId(Long userId, Integer brandId) {
        return pfUserOrganizationMapper.countByUserId(userId, null);
    }

    /**
     * 统计所有品牌的总人数和总销售额
     *
     * @param userId
     * @return
     */
    public Map<String, Object> allCountGroup(Long userId) {
        return countGroupMapper.allCountGroup(userId);
    }

    /**
     * 根据brandId统计团队人数和总销售额
     *
     * @param userId
     * @param brandId
     * @return
     */
    public Map<String, Object> countGroupByBrandId(Long userId, Integer brandId) {
        return countGroupMapper.countGroupByBrandId(userId, brandId);
    }

    /**
     * 是否有家族(创建的或加入的)
     *
     * @param userId
     * @return
     */
    public Boolean hasOrganization(Long userId) {
        ComUser comUser = userService.getUserById(userId);
        if (comUser != null && comUser.getIsAgent().intValue() == 0) {
            return false;
        }

        List<Map<String, Object>> organizations = listCreateAndJoinOrganization(userId);
        if (organizations == null || organizations.size() == 0) {
            return false;
        }

        return true;
    }

    public String handlerName(String name, String suffix) {
        int index = name.lastIndexOf(suffix);
        return name.substring(0, index);
    }
}
