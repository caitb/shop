package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.PfUserSku;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * UserSkuService
 *
 * @author ZhaoLiang
 * @date 2016/3/8
 */
@Service
@Transactional
public class UserSkuService {
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;

    /**
     * 获取用户产品代理关系
     *
     * @author ZhaoLiang
     * @date 2016/3/8 16:16
     */
    public PfUserSku getUserSkuByUserIdAndSkuId(Long userId, Integer SkuId) throws Exception{
        return pfUserSkuMapper.selectByUserIdAndSkuId(userId, SkuId);
    }
}
