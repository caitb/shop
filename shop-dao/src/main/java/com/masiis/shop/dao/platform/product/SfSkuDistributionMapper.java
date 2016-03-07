package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.SfSkuDistribution;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by cai_tb on 16/3/7.
 */
public interface SfSkuDistributionMapper {

    /**
     * 根据id查询一条记录
     * @param id
     * @return
     */
    SfSkuDistribution selectById(@Param("id")Long id);

    /**
     * 根据条件查询记录
     * @param sfSkuDistribution
     * @return
     */
    List<SfSkuDistribution> selectByCondition(@Param("comSku")SfSkuDistribution sfSkuDistribution);

    /**
     * 添加一条记录
     * @param sfSkuDistribution
     */
    Long insert(@Param("sfSkuDistribution")SfSkuDistribution sfSkuDistribution);

    /**
     * 根据id更新一条记录
     * @param sfSkuDistribution
     */
    void updateById(@Param("sfSkuDistribution")SfSkuDistribution sfSkuDistribution);

    /**
     * 根据id删除一条记录
     * @param id
     */
    void deleteById(@Param("id")Long id);
}
