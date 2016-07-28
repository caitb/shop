/*
 * PfUserSkuMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-03 Created
 */
package com.masiis.shop.dao.platform.user;

import com.masiis.shop.dao.beans.user.CountGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CountGroupMapper {
    /**
     * 统计团队人数、销售额、订单总数
     * @author muchaofeng
     * @date 2016/6/8 18:14
     */
    CountGroup countGroup(@Param("treeCode")String treeCode);

}