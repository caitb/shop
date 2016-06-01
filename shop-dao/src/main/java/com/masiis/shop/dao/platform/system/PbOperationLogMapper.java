package com.masiis.shop.dao.platform.system;

import com.masiis.shop.dao.po.PbOperationLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PbOperationLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(@Param("pbOperationLog") PbOperationLog pbOperationLog);

    PbOperationLog selectByPrimaryKey(Long id);

    List<PbOperationLog> selectAll();

    int updateByPrimaryKey(PbOperationLog pbOperationLog);
}
