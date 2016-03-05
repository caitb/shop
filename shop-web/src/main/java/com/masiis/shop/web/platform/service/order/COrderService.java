package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserTrial;
import com.masiis.shop.web.platform.service.user.UserService;
import org.springframework.expression.ExpressionException;
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
     * @author  hanzengzhi
     * @date  2016/3/5 15:14
     */
    public void trialApplyService(ComUser comUser,PfUserTrial pfUserTrial){
        try {
            //插入试用表
            userService.insertUserTrial(pfUserTrial);
            //更新试用用户信息
            userService.updateComUser(comUser);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
