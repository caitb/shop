package com.masiis.shop.admin.service.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.user.ComSkuNewMapper;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComSkuNew;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新品
 * Created by cai_tb on 16/8/6.
 */
@Service
public class SkuNewService {

    @Resource
    private ComSkuNewMapper comSkuNewMapper;
    @Resource
    private ComSkuMapper comSkuMapper;

    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Map<String, Object> conditionMap) {
        String sort = "sn.sort desc";
        if (sortName != null) sort = sortName + " " + sortOrder;

        PageHelper.startPage(pageNumber, pageSize, sort);
        List<Map<String, Object>> skuNews = comSkuNewMapper.selectByCondition(conditionMap);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(skuNews);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows",  skuNews);

        return pageMap;
    }

    /**
     * 获取所有sku
     * @return
     */
    public List<ComSku> listSku() {
        return comSkuMapper.selectAll();
    }

    public ComSkuNew findBySkuId(Integer skuId){
        return comSkuNewMapper.selectBySkuId(skuId);
    }

    /**
     * 添加新品
     * @param comSkuNew
     */
    public void add(ComSkuNew comSkuNew) {
        comSkuNewMapper.insert(comSkuNew);
    }

    /**
     * 更新新品
     * @param comSkuNew
     */
    public void update(ComSkuNew comSkuNew){
        comSkuNewMapper.updateByPrimaryKey(comSkuNew);
    }
}
