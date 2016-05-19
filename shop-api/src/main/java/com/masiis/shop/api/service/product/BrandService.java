package com.masiis.shop.api.service.product;

import com.masiis.shop.dao.platform.product.ComBrandMapper;
import com.masiis.shop.dao.po.ComBrand;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cai_tb on 16/5/19.
 */
@Service
public class BrandService {

    @Resource
    private ComBrandMapper comBrandMapper;

    public ComBrand getById(Integer brandId){
        return comBrandMapper.selectById(brandId);
    }
}
