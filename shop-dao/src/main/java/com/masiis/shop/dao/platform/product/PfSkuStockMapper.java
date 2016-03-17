package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.PfSkuStock;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cai_tb on 16/3/10.
 */

@Repository
public interface PfSkuStockMapper {

    PfSkuStock selectById(Integer id);

    List<PfSkuStock> selectByCondition(PfSkuStock pfSkuStock);

    List<PfSkuStock> selectAll();

    void insert(PfSkuStock pfSkuStock);

    //void updateById(PfSkuStock pfSkuStock);//没有设置乐观锁不建议使用

    void deleteById(Integer id);

    PfSkuStock selectBySkuId(Integer skuId);

    void updateByIdAndVersion(PfSkuStock pfSkuStock);
}
