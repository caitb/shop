/*
 * PfUserSearchLogMapper.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-09-12 Created
 */
package com.masiis.shop.dao.platform.user;


import com.masiis.shop.dao.po.PfUserSearchLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PfUserSearchLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PfUserSearchLog record);

    PfUserSearchLog selectByPrimaryKey(Long id);

    List<PfUserSearchLog> selectAll();

    int updateByPrimaryKey(PfUserSearchLog record);

    List<String> selectSearchContent(Long userId);

    /**
     * 查询记录是否存在
     * @param content
     * @param userId
     * @return
     */
    PfUserSearchLog selectByContent(String content, Long userId);

    /**
     * 清空指定查询记录
     * @param content
     * @param userId
     */
    void deleteContent(@Param("content")String content, @Param("userId")Long userId);

    /**
     * 清空查询记录
     * @param userId
     */
    void clearContent(Long userId);
}