package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.dao.platform.order.PfSupplierBankMapper;
import com.masiis.shop.dao.po.PfSupplierBank;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/4/25.
 */
@Service
public class PfSupplierBankService {

    @Resource
    private PfSupplierBankMapper supplierBankMapper;

    /**
     * 获得默认银行
     * @author hanzengzhi
     * @date 2016/4/25 14:17
     */
    public PfSupplierBank getDefaultBank(){
       return supplierBankMapper.getDefaultBank();
    }
}
