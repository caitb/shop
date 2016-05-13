package com.masiis.shop.web.platform.service.product;

import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.po.PfSkuStock;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Date 2016/5/13
 * @Auther lzh
 */
@Service
public class PfSkuStockService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfSkuStockMapper skuStockMapper;

    public void updateSkuStockWithLog(Integer change, PfSkuStock before,
                                      Long billId, Integer handleType){





    }
}
