package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.mall.user.SfUserShareParamMapper;
import com.masiis.shop.dao.po.SfUserShareParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 二维码参数
 * Created by cai_tb on 16/7/19.
 */
@Service
public class SfUserShareParamService {

    @Resource
    private SfUserShareParamMapper sfUserShareParamMapper;

    /**
     * 条件查询二维码参数
     * @param sfUserShareParam
     * @return
     */
    public SfUserShareParam loadByCondition(SfUserShareParam sfUserShareParam){
        return sfUserShareParamMapper.selectByCondition(sfUserShareParam);
    }
}
