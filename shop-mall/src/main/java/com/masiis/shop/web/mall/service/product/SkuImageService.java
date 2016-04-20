package com.masiis.shop.web.mall.service.product;

import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.po.ComSkuImage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/4/20.
 */
@Service
public class SkuImageService {

    @Resource
    private ComSkuImageMapper comSkuImageMapper;

    /**
     * 获取sku图片
     * @param skuId
     * @return
     */
    public List<ComSkuImage> loadBySkuId(Integer skuId){
        return comSkuImageMapper.selectBySkuId(skuId);
    }
}
