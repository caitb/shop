package com.masiis.shop.web.platform.service.product;

import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.PfSkuAgentMapper;
import com.masiis.shop.dao.po.ComAgentLevel;
import com.masiis.shop.dao.po.PfSkuAgent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
        Double maxBail = pfSkuAgentMapper.selectMaxBail(skuId);
        Double minBail = pfSkuAgentMapper.selectMinBail(skuId);
        String bail = String.valueOf(minBail)+"-"+String.valueOf(maxBail);
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

}
