package com.masiis.shop.web.platform.service.product;

import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.po.ComSku;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * SkuService
 *
 * @author ZhaoLiang
 * @date 2016/3/7
 */
@Service
@Transactional
public class SkuService {

    @Resource
    private ComSkuMapper comSkuMapper;

    public ComSku getSkuById(Integer skuId) {
        return comSkuMapper.selectByPrimaryKey(skuId);
    }
}
