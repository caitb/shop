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
     * 统计团队人数与销售额
     * @author muchaofeng
     * @date 2016/6/8 18:14
     */
    CountGroup countGroup(@Param("userId")Long  userId,@Param("treeCode")String treeCode);
    /**
     * 统计团队订单
     * @author muchaofeng
     * @date 2016/6/8 18:14
     */
    CountGroup countOrderNum(@Param("userId") Long  userId,@Param("treeCode") String treeCode);

}