package com.masiis.shop.admin.service.datastatistics;

import com.masiis.shop.dao.platform.user.ComWxUserMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/8/27.
 */
public class PlatformService {

    private ComWxUserMapper comWxUserMapper;

    /**
     * 注册用户量
     * @param beginTime
     * @return
     */
    public List<Map<String, Object>> registerCountUser(String beginTime){
        return comWxUserMapper.registerCountUser(beginTime);
    }

    /**
     * 新增注册用户量
     * @param beginTime
     * @return
     */
    public List<Map<String, Object>> registerNewCountUser(String beginTime){
        return comWxUserMapper.registerNewCountUser(beginTime);
    }
}
