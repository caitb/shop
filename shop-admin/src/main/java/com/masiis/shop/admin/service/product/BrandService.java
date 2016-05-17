package com.masiis.shop.admin.service.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.dao.platform.product.ComBrandMapper;
import com.masiis.shop.dao.po.ComBrand;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Object> list(Integer pageNumber, Integer pageSize, ComBrand comBrand){
        String sort = "create_time desc";
        PageHelper.startPage(pageNumber, pageSize, sort);
        List<ComBrand> comBrands = comBrandMapper.selectByCondition(comBrand);
        PageInfo<ComBrand> pageInfo = new PageInfo<>(comBrands);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", comBrands);

        return pageMap;
    }
}
