package com.masiis.shop.scheduler.mall.service.user;

import com.masiis.shop.common.enums.BOrder.BOrderStatus;
import com.masiis.shop.dao.mall.order.SfOrderMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfOrder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangbingjian on 2016/5/7.
 */
@Service
public class SfUserIsBuyService {
    private static final Logger logger = Logger.getLogger(SfUserIsBuyService.class);
    @Autowired
    private ComUserMapper comUserMapper;
    @Autowired
    private SfOrderMapper sfOrderMapper;

    @Transactional
    public void userIsBuyComplete() throws Exception{
        logger.info("查询出所有C端购买未成功用户");
        ComUser comUser = new ComUser();
        comUser.setIsBuy(0);
        List<ComUser> users = comUserMapper.selectByCondition(comUser);
        if (users == null || users.size() == 0){
            logger.info("无C端购买未成功用户");
            return;
        }
        logger.info("处理未购买成功的用户");
        SfOrder sfOrder;
        //遍历处理未购买成功用户
        for (ComUser user : users){
            sfOrder = sfOrderMapper.selectNotIsBuyByUserId(user.getId(), BOrderStatus.Complete.getCode());
            logger.info("订单是否为空：" + sfOrder + "  用户userId = " + user.getId());
            if (sfOrder != null){
                user.setIsBuy(1);
                comUserMapper.updateByPrimaryKey(user);
            }
        }
    }
}
