package com.masiis.shop.admin.service.product;

import com.masiis.shop.dao.platform.product.ComSkuImageMapper;
import com.masiis.shop.dao.po.ComSkuImage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/4/27.
 */
@Service
public class SkuImageService {

    @Resource
    private ComSkuImageMapper comSkuImageMapper;

    /**
     * 查找商品主图
     * @param skuId
     * @return
     */
    public List<ComSkuImage> listBySkuId(Integer skuId){
        return comSkuImageMapper.selectBySkuId(skuId);
    }
}
