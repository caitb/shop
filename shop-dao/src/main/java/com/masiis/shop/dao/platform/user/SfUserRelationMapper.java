package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.po.SfUserRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by cai_tb on 16/3/12.
 */
public interface SfUserRelationMapper {

    SfUserRelation selectById(Long id);

    SfUserRelation selectByUserId(Long userId);

    List<SfUserRelation> selectByCondition(SfUserRelation sfUserRelation);

    void insert(SfUserRelation sfUserRelation);

    void updateById(SfUserRelation sfUserRelation);

    void deleteById(Long id);






    /**
     * 查询推荐人id
     * @param id
     * @return
     */
    SfUserRelation findByUserId(@Param("id") Long id);

    SfUserRelation getUpperPartnerById(Integer pid);

}
