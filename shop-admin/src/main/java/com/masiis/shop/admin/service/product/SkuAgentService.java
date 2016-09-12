package com.masiis.shop.admin.service.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.po.ComAgentLevel;
import com.masiis.shop.dao.po.PfSkuAgent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoLiang on 2016/3/4.
 */
@Service
@Transactional
public class SkuAgentService {
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;

    /**
     * 获取SKU代理数据
     *
     * @param skuId
     * @return
     */
    public List<PfSkuAgent> getAllBySkuId(Integer skuId) {
        return pfSkuAgentMapper.selectAllBySkuId(skuId);
    }

    /**
     * 获取代理等级数据信息
     *
     * @return
     */
    public List<ComAgentLevel> getComAgentLevel() {
        return comAgentLevelMapper.selectAll();
    }

    /**
     * 获取商品保证金区间
     * @author muchaofeng
     * @date 2016/4/14 17:40
     */

    public String getSkuAgentLevel(Integer skuId) {
        BigDecimal maxBail = pfSkuAgentMapper.selectMaxBail(skuId);
        BigDecimal minBail = pfSkuAgentMapper.selectMinBail(skuId);
        String bail = minBail.toString()+"-"+maxBail.toString();
        return bail;
    }

    /**
     * 根据sku和代理等级获取sku代理数据
     * @author ZhaoLiang
     * @date 2016/3/8 14:26
     */
    public PfSkuAgent getBySkuIdAndLevelId(Integer skuId, Integer levelId) {
        return pfSkuAgentMapper.selectBySkuIdAndLevelId(skuId, levelId);
    }
    /**
     * 获取用户商品等级标志
     * @author hanzengzhi
     * @date 2016/3/30 17:36
     */
    public List<PfSkuAgent> getSkuLevelIconByUserId(Long userId){
        return pfSkuAgentMapper.getSkuLevelIconByUserId(userId);
    }

    /**
     * 分页列表
     * @param pageNumber
     * @param pageSize
     * @param sortName
     * @param sortOrder
     * @param conditionMap
     * @return
     */
    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Map<String, Object> conditionMap) {
        String sort = "sa.sku_id asc, sa.agent_level_id asc";
        if (sortName != null) sort = sortName + " " + sortOrder;
        PageHelper.startPage(pageNumber, pageSize, sort);
        List<Map<String, Object>> skuAgents = pfSkuAgentMapper.selectByCondition(conditionMap);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(skuAgents);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", skuAgents);

        return pageMap;
    }

    /**
     * 保存
     * @param pfSkuAgent
     */
    public void save(PfSkuAgent pfSkuAgent){
        if(pfSkuAgent == null) return;

        if(pfSkuAgent.getId() != null){
            pfSkuAgentMapper.updateByPrimaryKey(pfSkuAgent);
        }else{
            pfSkuAgentMapper.insert(pfSkuAgent);
        }
    }

    public PfSkuAgent findBySkuIdAndLevelId(Integer skuId, Integer levelId){
        return pfSkuAgentMapper.selectBySkuIdAndLevelId(skuId, levelId);
    }

    public PfSkuAgent loadById(Integer id){
        return pfSkuAgentMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据
     * @param skuId
     * @return
     */
    public List<PfSkuAgent> listBySkuId(Integer skuId){
        return pfSkuAgentMapper.selectBySkuId(skuId);
    }

}
