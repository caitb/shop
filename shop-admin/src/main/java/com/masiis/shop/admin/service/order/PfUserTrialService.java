package com.masiis.shop.admin.service.order;


import com.masiis.shop.dao.platform.order.PfCorderMapper;
import com.masiis.shop.dao.platform.order.PfCorderOperationLogMapper;
import com.masiis.shop.dao.platform.order.PfUserTrialMapper;
import com.masiis.shop.dao.platform.order.SfUserRelationMapper;
import com.masiis.shop.dao.po.PfCorder;
import com.masiis.shop.dao.po.PfCorderOperationLog;
import com.masiis.shop.dao.po.PfUserTrial;
import com.masiis.shop.dao.po.SfUserRelation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 49134 on 2016/3/3.
 */
@Service
@Transactional
public class PfUserTrialService {

    @Resource
    private PfUserTrialMapper pfUserTrialMapper;
    @Resource
    private SfUserRelationMapper sfUserRealtionMapper;
    @Resource
    private PfCorderMapper pfCorderMapper;
    @Resource
    private PfCorderOperationLogMapper pfCorderOperationLogMapper;

    /**
     * 根据条件查询试用申请记录
     * @param pfUserTrial
     * @return
     */
    public List<PfUserTrial> listByCondition(PfUserTrial pfUserTrial){
        return pfUserTrialMapper.selectByCondition(pfUserTrial);
    }


    public void pass(PfUserTrial pfUserTrial) {
        pfUserTrialMapper.updateById(pfUserTrial);
    }

    public void reason(PfUserTrial pfUserTrial) {
        pfUserTrialMapper.reason(pfUserTrial);
    }

    public PfUserTrial findById(Long id) {
       return pfUserTrialMapper.selectById(id);
    }

    public SfUserRelation findPidById(Long userId) {
        return sfUserRealtionMapper.findByUserId(userId);
    }

    /**
     * 生成订单、日志、收货人信息
     * @param pfCorder
     * @param pcol
     */
    public void insert(PfCorder pfCorder,PfCorderOperationLog pcol) {
        pfCorderMapper.insert(pfCorder);
        pcol.setPfCorderId(pfCorder.getId());
        pfCorderOperationLogMapper.insert(pcol);

    }

    public String selectById(Long id) {
        return pfUserTrialMapper.selectReasonById(id);
    }

}
