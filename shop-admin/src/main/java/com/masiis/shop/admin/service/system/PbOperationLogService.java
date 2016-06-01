package com.masiis.shop.admin.service.system;


import com.masiis.shop.dao.platform.system.PbOperationLogMapper;
import com.masiis.shop.dao.po.PbOperationLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author muchaofeng
 * @date $date$ $time$
 */
@Service
public class PbOperationLogService {
    @Resource
    private PbOperationLogMapper pbOperationLogMapper;

    /**
     * 添加日志
     * @author muchaofeng
     * @date 2016/5/30 14:10
     */
    public void add(PbOperationLog pbOperationLog){
        pbOperationLogMapper.insert(pbOperationLog);
    }

    /**
     * 修改日志
     * @author muchaofeng
     * @date 2016/5/30 14:12
     */
    public int update(PbOperationLog pbOperationLog){
       return pbOperationLogMapper.updateByPrimaryKey(pbOperationLog);
    }
}
