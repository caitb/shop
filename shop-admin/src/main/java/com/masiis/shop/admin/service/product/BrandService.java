package com.masiis.shop.admin.service.product;

import com.masiis.shop.dao.platform.product.ComBrandMapper;
import com.masiis.shop.dao.po.ComBrand;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/3/5.
 */
@Service
public class BrandService {

    @Resource
    private ComBrandMapper comBrandMapper;

    /**
     * 获取所有品牌数据
     * @return
     */
    public List<ComBrand> list(ComBrand comBrand){
        return comBrandMapper.selectByCondition(comBrand);
    }
}
