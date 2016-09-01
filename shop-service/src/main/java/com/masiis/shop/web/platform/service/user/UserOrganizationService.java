package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.CountGroupMapper;
import com.masiis.shop.dao.platform.user.PfUserBrandMapper;
import com.masiis.shop.dao.platform.user.PfUserOrganizationMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserOrganization;
import com.masiis.shop.web.common.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户组织
 * Created by cai_tb on 16/8/8.
 */
@Service
public class UserOrganizationService {

    @Resource
    private PfUserOrganizationMapper pfUserOrganizationMapper;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private CountGroupMapper countGroupMapper;
    @Resource
    private PfUserBrandMapper pfUserBrandMapper;
    @Resource
    private UserService userService;

    public PfUserOrganization loadByBrandIdAndUserId(Integer brandId, Long userId){
        return pfUserOrganizationMapper.selectByBrandIdAndUserId(brandId, userId);
    }

    public void save(PfUserOrganization pfUserOrganization){
        pfUserOrganizationMapper.insert(pfUserOrganization);
    }

    public void update(PfUserOrganization pfUserOrganization){
        pfUserOrganizationMapper.updateByPrimaryKey(pfUserOrganization);
    }

    public Map<String, Object> searchByMobile(String mobile,Integer brandId){
        return pfUserOrganizationMapper.selectByMobile(mobile,brandId);
    }

    /**
     * 获取代理品牌
     * @param userId
     * @return
     */
    public List<Map<String, Object>> listAgentBrand(Long userId){
        return pfUserSkuMapper.selectAgentBrand(userId, null);
    }

    /**
     * 获取代理品牌
     * @param userId
     * @return
     */
    public Map<String, Object> loadAgentBrand(Long userId, Integer brandId){
        List<Map<String, Object>> agentBrands = pfUserSkuMapper.selectAgentBrand(userId, brandId);
        return agentBrands.size()==0 ? null : agentBrands.get(0);
    }

    /**
     * 获取 上 级代理品牌
     * @param userId
     * @return
     */
    public Map<String, Object> loadPAgentBrand(Long userId, Integer brandId){
        List<Map<String, Object>> agentBrands = pfUserSkuMapper.selectPAgentBrand(userId, brandId);
        return agentBrands.size()==0 ? null : agentBrands.get(0);
    }

    /**
     * 查找加入的家族
     * @param userIds
     * @return
     */
    public List<Map<String, Object>> listFromOrganization(List<Long> userIds){
        return pfUserOrganizationMapper.selectFromOrganization(userIds);
    }

    /**
     * 获取家族或团队
     * @param userId
     * @return
     */
    public List<Map<String, Object>> listByUserId(Long userId) {
        return pfUserOrganizationMapper.selectByUserId(userId);
    }

    /**
     * 查询我创建的团队
     * @param userId
     * @return
     */
    public List<Map<String, Object>> listCreateOrganization(Long userId) {
        return pfUserOrganizationMapper.selectCreateOrganization(userId);
    }

    /**
     * 我加入的家族或团队
     * @param userId
     * @return
     */
    public List<Map<String, Object>> listJoinOrganization(Long userId){
        return pfUserOrganizationMapper.selectJoinOrganization(userId);
    }

    /**
     * 查询我创建的和(我最低等级时)直接上级的家族
     * @param userId
     * @return
     */
    public List<Map<String, Object>> listCreateAndJoinOrganization(Long userId){
        return pfUserOrganizationMapper.selectCreateAndJoinOrganization(userId);
    }

    /**
     * 统计团队人数和总销售额
     * @param userId
     * @return
     */
    public Map<String, Object> countByUserIdAndBrandId(Long userId, Integer brandId){
        return pfUserOrganizationMapper.countByUserId(userId, null);
    }

    /**
     * 统计所有品牌的总人数和总销售额
     * @param userId
     * @return
     */
    public Map<String, Object> allCountGroup(Long userId) {
        return countGroupMapper.allCountGroup(userId);
    }

    /**
     * 根据brandId统计团队人数和总销售额
     * @param userId
     * @param brandId
     * @return
     */
    public Map<String, Object> countGroupByBrandId(Long userId, Integer brandId){
        return countGroupMapper.countGroupByBrandId(userId, brandId);
    }

    /**
     * 是否有家族(创建的或加入的)
     * @param userId
     * @return
     */
    public Boolean hasOrganization(Long userId){
        ComUser comUser = userService.getUserById(userId);
        if(comUser != null && comUser.getIsAgent().intValue() == 0){
            return false;
        }

        List<Map<String, Object>> organizations = listCreateAndJoinOrganization(userId);
        if(organizations == null || organizations.size() == 0){
            return false;
        }

        return true;
    }


}
