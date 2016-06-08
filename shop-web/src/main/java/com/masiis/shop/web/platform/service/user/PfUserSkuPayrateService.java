package com.masiis.shop.web.platform.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by JingHao on 2016/6/6 0006.
 */
@Service
@Transactional
public class PfUserSkuPayrateService {

    @Autowired
    private PfUserSkuPayrateMapper pfUserSkuPayrateMapper;

    /**
      * @Author 贾晶豪
      * @Date 2016/6/6 0006 下午 5:41
      * 查询代理差价
      */
    public PfUserSkuPayrate selectByUserIdAndSkuId(Long userId, Integer skuId) {
        return pfUserSkuPayrateMapper.selectByUserIdAndSkuId(userId, skuId);
    }
}
