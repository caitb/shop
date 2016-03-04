package com.masiis.shop.web.platform.service.product;

import com.masiis.shop.dao.beans.product.PfSkuAgentMapper;
import com.masiis.shop.dao.po.PfSkuAgent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ZhaoLiang on 2016/3/4.
 */
@Service
@Transactional
public class SkuAgentService {
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;

    /**
     * 获取SKU代理数据
     * @param skuId
     * @return
     */
    public List<PfSkuAgent> getAllBySkuId(Integer skuId) {
        return pfSkuAgentMapper.selectAllBySkuId(skuId);
    }
}
