package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.SfUserRelation;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 49134 on 2016/3/4.
 */
public interface SfUserRelationMapper {
    /**
     * 查询推荐人id
     * @param id
     * @return
     */
    SfUserRelation findByUserId(@Param("id") Long id);

    SfUserRelation getUpperPartnerById(Integer pid);
}
