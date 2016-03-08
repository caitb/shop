package com.masiis.shop.admin.service.order;


import com.masiis.shop.dao.platform.order.PfCorderMapper;
import com.masiis.shop.dao.platform.order.PfUserTrialMapper;
import com.masiis.shop.dao.platform.order.SfUserRelationMapper;
import com.masiis.shop.dao.po.PfCorder;
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

    public void insert(PfCorder pfCorder) {
        pfCorderMapper.insert(pfCorder);
    }

    public String selectById(Long id) {
        return pfUserTrialMapper.selectReasonById(id);
    }
}
