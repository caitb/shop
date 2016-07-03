package com.masiis.shop.web.mall.service.product;

import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.po.ComSpu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cai_tb on 16/4/20.
 */
@Service
public class SpuService {

    @Resource
    private ComSpuMapper comSpuMapper;

    /**
     * 获取spu
     * @param spuId
     * @return
     */
    public ComSpu loadSpu(Integer spuId){
        return comSpuMapper.selectById(spuId);
    }
}
