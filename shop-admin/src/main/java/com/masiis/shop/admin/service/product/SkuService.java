package com.masiis.shop.admin.service.product;

import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.po.ComSku;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by caitingbiao on 2016/3/2.
 */
@Service
public class SkuService {

    @Resource
    private ComSkuMapper comSkuMapper;

    /**
     * 根据id查找商品
     * @param id
     * @return
     */
    public ComSku findById(Long id){
        return comSkuMapper.selectById(id);
    }
    /**
     * 根据条件查询商品
     * @param comSku
     * @return
     */
    public List<ComSku> listByCondition(ComSku comSku){
        return comSkuMapper.selectByCondition(comSku);
    }
}
