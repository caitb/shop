package com.masiis.shop.web.platform.service.order;


import com.masiis.shop.dao.platform.order.PfCorderMapper;
import com.masiis.shop.dao.platform.order.PfCorderOperationLogMapper;
import com.masiis.shop.dao.platform.user.SfUserRelationMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.user.UserService;
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
    private SfUserRelationMapper sfUserRealtionMapper;
    @Resource
    private PfCorderMapper pfCorderMapper;
    @Resource
    private PfCorderOperationLogMapper pfCorderOperationLogMapper;
    @Resource
    private UserService userService;

    public SfUserRelation findPidById(Long userId) {
        return sfUserRealtionMapper.findByUserId(userId);
    }

    /**
     * 生成订单、日志、收货人信息
     * @param pfCorder
     * @param pcol
     */
    public void insert(PfCorder pfCorder,PfCorderOperationLog pcol,ComUser comUser )throws Exception {
        try {
            pfCorderMapper.insert(pfCorder);
            pcol.setPfCorderId(pfCorder.getId());
            pfCorderOperationLogMapper.insert(pcol);
            //更新试用用户信息
            userService.updateComUser(comUser);
        }catch (Exception e){
         throw new Exception("生成用户订单失败");
        }
    }
}
