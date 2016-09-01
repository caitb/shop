package com.masiis.shop.web.platform.service.product;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.user.AgentSkuViewInfo;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.ComAgentLevel;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.PfSkuAgent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    @Resource
    private ComSkuImageMapper comSkuImageMapper;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;

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

    public List<Map<String, String>> getBrandLevelIconByUserId(Long userId){
        return pfSkuAgentMapper.selectBrandLevelIconByUserId(userId);
    }

    public List<PfSkuAgent> getLessLevelByLevelIdAndSkuId(Integer agentLevelId, Integer skuId) {
        return pfSkuAgentMapper.selectLessLevelByLevelIdAndSkuId(agentLevelId, skuId);
    }

    public AgentSkuViewInfo createAgentSkuView(PfSkuAgent pfSkuAgent, ComSku sku, boolean isNeedLevel) {
        AgentSkuViewInfo view = new AgentSkuViewInfo();
        BigDecimal totalFee = pfSkuAgent.getTotalPrice().add(pfSkuAgent.getBail());
        BigDecimal minProfit = BigDecimal.ZERO;
        BigDecimal minSingle = BigDecimal.ZERO;
        BigDecimal maxProfit = sku.getPriceRetail()
                .subtract(pfSkuAgent.getUnitPrice())
                .multiply(new BigDecimal(pfSkuAgent.getQuantity()))
                .setScale(2, BigDecimal.ROUND_DOWN);
        BigDecimal maxSingle = sku.getPriceRetail()
                        .subtract(pfSkuAgent.getUnitPrice())
                        .setScale(2, BigDecimal.ROUND_DOWN);
        //获取下级代理信息
        PfSkuAgent lowerSkuAgent = getBySkuIdAndLevelId(pfSkuAgent.getSkuId(), pfSkuAgent.getAgentLevelId() + 1);
        if (lowerSkuAgent == null) {
            minProfit = maxProfit;
            minSingle = maxSingle;
        } else {
            minProfit = (lowerSkuAgent.getUnitPrice().subtract(pfSkuAgent.getUnitPrice()))
                    .multiply(BigDecimal.valueOf(pfSkuAgent.getQuantity()))
                    .setScale(2, BigDecimal.ROUND_DOWN);//最低利润
            minSingle = lowerSkuAgent.getUnitPrice().subtract(pfSkuAgent.getUnitPrice())
                    .setScale(2, BigDecimal.ROUND_DOWN);//最低利润
        }

        view.setLevelId(pfSkuAgent.getAgentLevelId());
        view.setSkuName(sku.getName());
        view.setRetailFee(sku.getPriceRetail());
        view.setBailFee(pfSkuAgent.getBail());
        view.setIsShow(pfSkuAgent.getIsShow());
        view.setAgentFee(totalFee);
        view.setQuantity(pfSkuAgent.getQuantity());
        view.setSinFee(pfSkuAgent.getUnitPrice());
        view.setMaxProfit(maxProfit);
        view.setMinProfit(minProfit);
        view.setMaxSingle(maxSingle);
        view.setMinSingle(minSingle);
        view.setSkuUrl(PropertiesUtils.getStringValue("index_product_220_220_url")
                + comSkuImageMapper.selectDefaultImgBySkuId(sku.getId()).getImgUrl());
        view.setAgentNum(pfUserSkuMapper.selectAgentNumByLevelAndSku(pfSkuAgent.getAgentLevelId(), sku.getId()));
        if(isNeedLevel) {
            ComAgentLevel level = comAgentLevelMapper.selectByPrimaryKey(pfSkuAgent.getAgentLevelId());
            view.setOrganizationSuffix(level.getOrganizationSuffix());
            view.setIsOrganization(level.getIsOrganization());
            view.setLevelName(level.getName());
        }

        return view;
    }
}
