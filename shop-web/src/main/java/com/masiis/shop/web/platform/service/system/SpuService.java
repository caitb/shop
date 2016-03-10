package com.masiis.shop.web.platform.service.system;

import com.masiis.shop.dao.platform.product.ComSpuMapper;
import com.masiis.shop.dao.po.ComSpu;
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
public class SpuService {

    @Resource
    private ComSpuMapper comSpuMapper;

    public ComSpu getSpuById(Integer spuId) {
        return comSpuMapper.selectById(spuId);
    }
}
