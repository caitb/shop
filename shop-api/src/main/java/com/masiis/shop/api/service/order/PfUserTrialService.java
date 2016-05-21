package com.masiis.shop.api.service.order;


import com.masiis.shop.api.service.user.ComUserService;
import com.masiis.shop.dao.platform.order.PfCorderMapper;
import com.masiis.shop.dao.platform.order.PfCorderOperationLogMapper;
import com.masiis.shop.dao.po.PfCorder;
import com.masiis.shop.dao.po.PfCorderOperationLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by 49134 on 2016/3/3.
 */
@Service
@Transactional
public class PfUserTrialService {

    @Resource
    private PfCorderMapper pfCorderMapper;
    @Resource
    private PfCorderOperationLogMapper pfCorderOperationLogMapper;
    @Resource
    private ComUserService userService;

    /**
     * 生成订单、日志、收货人信息
     * @param pfCorder
     * @param pcol
     */
    public Long insert(PfCorder pfCorder,PfCorderOperationLog pcol )throws Exception {
        try {
            pfCorderMapper.insert(pfCorder);
            pcol.setPfCorderId(pfCorder.getId());
            pfCorderOperationLogMapper.insert(pcol);
            return pfCorder.getId();
        }catch (Exception e){
         throw new Exception("生成用户订单失败");
        }
    }
}
