package com.masiis.shop.admin.service.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.product.ProductInfo;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComSpu;
import com.masiis.shop.dao.po.PfSkuStock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/3/12.
 */
@Service
public class SkuStockService {

    @Resource
    private PfSkuStockMapper pfSkuStockMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private ComSpuMapper comSpuMapper;

    /**
     * 根据条件查询记录
     * @param pageNo
     * @param pageSize
     * @param pfSkuStock
     * @return
     */
    public Map<String, Object> listByCondition(Integer pageNo, Integer pageSize, PfSkuStock pfSkuStock){
        PageHelper.startPage(pageNo, pageSize);
        List<PfSkuStock> pfSkuStocks = pfSkuStockMapper.selectByCondition(pfSkuStock);
        PageInfo<PfSkuStock> pageInfo = new PageInfo<>(pfSkuStocks);

        List<ProductInfo> productInfos = new ArrayList<>();
        for(PfSkuStock pss : pfSkuStocks){
            ComSku comSku = comSkuMapper.selectById(pss.getSkuId());
            ComSpu comSpu = comSpuMapper.selectById(pss.getSpuId());

            ProductInfo productInfo = new ProductInfo();
            productInfo.setPfSkuStock(pss);
            productInfo.setComSku(comSku);
            productInfo.setComSpu(comSpu);

            productInfos.add(productInfo);
        }

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", productInfos);

        return pageMap;
    }

    /**
     * 更新库存
     * @param pfSkuStock
     */
    public void update(PfSkuStock pfSkuStock){
        pfSkuStockMapper.updateById(pfSkuStock);
    }
}
