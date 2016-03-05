package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.dao.po.PfUserTrial;
import com.masiis.shop.web.platform.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Service
@Transactional
public class COrderService {

    @Resource
    private UserService userService;

    /**
     * 试用申请
     * @param pfUserTrial
     */
    public void trialApplyService(PfUserTrial pfUserTrial){
        //更新试用用户信息
        userService.updateUserTrial(pfUserTrial);
    }
}
