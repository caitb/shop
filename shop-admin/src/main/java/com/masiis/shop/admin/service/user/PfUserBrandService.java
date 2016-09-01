package com.masiis.shop.admin.service.user;

import com.masiis.shop.dao.platform.user.PfUserBrandMapper;
import com.masiis.shop.dao.po.PfUserBrand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by jiajinghao on 2016/8/5.
 * 用户代理品牌service
 */
@Service
@Transactional
public class PfUserBrandService {

    @Resource
    private PfUserBrandMapper pfUserBrandMapper;
    /**
     * 查询用户品牌合伙关系
     *
     * @param userId  用户id
     * @param brandId 品牌id
     * @return
     */
    public PfUserBrand findByUserIdAndBrandId(Long userId, Integer brandId) {
        return pfUserBrandMapper.selectByUserIdAndBrandId(userId, brandId);
    }

    /**
     * 添加用户品牌代理关系
     *
     * @param pfUserBrand
     * @return
     */
    public int insert(PfUserBrand pfUserBrand) {
        return pfUserBrandMapper.insert(pfUserBrand);
    }

}

