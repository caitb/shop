package com.masiis.shop.admin.service.order;


import com.masiis.shop.dao.platform.order.PfUserTrialMapper;
import com.masiis.shop.dao.po.PfUserTrial;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 49134 on 2016/3/3.
 */
@Service
public class PfUserTrialService {

    @Resource
    private PfUserTrialMapper pfUserTrialMapper;

    /**
     * 根据条件查询试用申请记录
     * @param pfUserTrial
     * @return
     */
    public List<PfUserTrial> listByCondition(PfUserTrial pfUserTrial){
        return pfUserTrialMapper.selectByCondition(pfUserTrial);
    }


}
