package com.masiis.shop.web.material.service;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.dao.platform.material.ComUserSubscriptionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by jiajinghao on 2016/7/6.
 * 用户订阅service
 */
@Service
@Transactional
public class UserSubscriptionService {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private ComUserSubscriptionMapper comUserSubscriptionMapper;


}
