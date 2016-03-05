package com.masiis.shop.admin.service.product;

import com.masiis.shop.dao.platform.product.ComCategoryMapper;
import com.masiis.shop.dao.po.ComCategory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/3/5.
 */
@Service
public class CategoryService {

    @Resource
    private ComCategoryMapper comCategoryMapper;

    /**
     * 根据条件查询品牌列表
     * @param comCategory
     * @return
     */
    public List<ComCategory> listByCondition(ComCategory comCategory){
        return comCategoryMapper.selectByCondition(comCategory);
    }
}
