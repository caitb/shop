package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.ComUserBlacklistMapper;
import com.masiis.shop.dao.po.ComUserBlacklist;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by cai_tb on 16/9/6.
 */
@Service
public class UserBlackService {

    @Resource
    private ComUserBlacklistMapper comUserBlacklistMapper;

    public ComUserBlacklist loadByMobile(String mobile){
        return comUserBlacklistMapper.selectByMobile(mobile);
    }

    /**
     * 是否是黑名单
     * @param mobile
     * @return
     */
    public Boolean isBlackByMobile(String mobile){
        ComUserBlacklist comUserBlacklist = comUserBlacklistMapper.selectByMobile(mobile);
        return comUserBlacklist==null?false:true;
    }
}
