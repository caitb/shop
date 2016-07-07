package com.masiis.shop.web.material.service;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.dao.platform.material.ComSkuMaterialLibraryMapper;
import com.masiis.shop.dao.platform.material.ComUserSubscriptionMapper;
import com.masiis.shop.dao.po.ComSkuMaterialLibrary;
import com.masiis.shop.dao.po.ComUserSubscription;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

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

    @Resource
    private ComSkuMaterialLibraryMapper comSkuMaterialLibraryMapper;
    /**
     * jjh
     * 更新订阅(创建订阅)
     * @param status
     * @param materialId
     * @param userId
     * @return
     * @throws Exception
     */
    public void  updateSubscriptionStatus(Integer status, Integer materialId, Long userId) throws Exception {
        ComUserSubscription comUserSubscription = comUserSubscriptionMapper.selectByUserIdAndMaterialId(userId, materialId);
        if(comUserSubscription==null){//第一次订阅
            ComUserSubscription record = new ComUserSubscription();
            record.setCreateTime(new Date());
            record.setStatus(status);
            record.setRemark("");
            record.setUserId(userId);
            record.setComSkuMaterialLibrary(materialId);
            record.setModifyTime(new Date());
            comUserSubscriptionMapper.insert(record);
            ComSkuMaterialLibrary comSkuMaterialLibrary = comSkuMaterialLibraryMapper.selectByPrimaryKey(materialId);
            comSkuMaterialLibrary.setSubscriptionNum(comSkuMaterialLibrary.getSubscriptionNum()+1);
            comSkuMaterialLibraryMapper.updateByPrimaryKey(comSkuMaterialLibrary);
        }else{
            comUserSubscription.setStatus(status);
            comUserSubscription.setModifyTime(new Date());
            comUserSubscriptionMapper.updateByPrimaryKey(comUserSubscription);
        }
    }
}
