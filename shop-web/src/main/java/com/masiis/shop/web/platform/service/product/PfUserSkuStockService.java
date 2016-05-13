package com.masiis.shop.web.platform.service.product;

import com.masiis.shop.dao.platform.product.PfSkuStockMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuStockMapper;
import com.masiis.shop.dao.po.PfUserSkuStock;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Date 2016/5/13
 * @Auther lzh
 */
@Service
public class PfUserSkuStockService {
    private Logger log = Logger.getLogger(this.getClass());

    private PfUserSkuStockMapper userSkuStockMapper;

    public void updateUserSkuStockWithLog(Integer change, PfUserSkuStock before, Long billId, Integer handleType){

    }
}
