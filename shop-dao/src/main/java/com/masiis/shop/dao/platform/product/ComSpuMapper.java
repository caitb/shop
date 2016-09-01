package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.ComSpu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by hzzh on 2016/3/5.
 */
@Repository
public interface ComSpuMapper {

    /**
     * 根据id查询一条记录
     *
     * @param id
     * @return
     */
    ComSpu selectById(@Param("id") Integer id);

    /**
     * 根据商品id获得所在的品牌信息
     * @param skuId
     * @return
     */
    ComSpu selectBrandBySkuId(@Param("skuId") Integer skuId);

    /**
     * 根据条件查询记录
     *
     * @param comSpu
     * @return
     */
    List<ComSpu> selectByCondition(@Param("comSpu") ComSpu comSpu);

    /**
     * 添加一条记录
     *
     * @param comSpu
     */
    Integer insert(ComSpu comSpu);

    /**
     * 根据id更新一条记录
     *
     * @param comSpu
     */
    void updateById(ComSpu comSpu);

    /**
     * 根据id删除一条记录
     *
     * @param id
     */
    void deleteById(@Param("id") Integer id);

    /**
     * 查询主打商品
     *
     * @return
     */
    List<Map<String, Object>> selectAll();

    /**
     * 查询品牌
     *
     * @return
     */
    List<Map<String, Object>> brandList();

    /**
     * 查询主打商品
     *
     * @return
     */
    List<Map<String, Object>> spuList();

    /**
     * 设置主打商品
     * @return
     */
    boolean updateSpuMaim(@Param("brandId")Integer brandId, @Param("spuId")Integer spuId, @Param("type")Integer type);

    /**
     * 根据ID查询品牌
     * @return
     */
    List<Map<String,Object>> selectByBrandId(Integer brandId);

    /**
     * 删除主打商品
     * @param id
     */
    void deleteMain(Integer spuId);
}