package com.masiis.shop.api.service.product;

import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.po.ComSpu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cai_tb on 16/5/19.
 */
@Service
public class SpuService {

    @Resource
    private ComSpuMapper comSpuMapper;

    public ComSpu getById(Integer spuId){
        return comSpuMapper.selectById(spuId);
    }
}
