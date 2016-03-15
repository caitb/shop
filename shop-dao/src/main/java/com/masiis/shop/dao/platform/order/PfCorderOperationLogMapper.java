package com.masiis.shop.dao.platform.order;

import com.masiis.shop.dao.po.PfCorderConsignee;
import com.masiis.shop.dao.po.PfCorderOperationLog;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 49134 on 2016/3/9.
 */
public interface PfCorderOperationLogMapper {

    void insert(@Param("pcol") PfCorderOperationLog pcol);
    PfCorderConsignee selectByKey(Long pfCorderId);
}
