package com.masiis.shop.dao.platform.system;

import com.masiis.shop.dao.po.PbOperationLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PbOperationLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(@Param("pbOperationLog") PbOperationLog pbOperationLog);

    PbOperationLog selectByPrimaryKey(Long id);

    List<PbOperationLog> selectAll();

    int updateByPrimaryKey(PbOperationLog pbOperationLog);
}
