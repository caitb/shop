package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.ComUserBlacklistMapper;
import com.masiis.shop.dao.po.ComUserBlacklist;
import org.springframework.stereotype.Service;

/**
 * Created by cai_tb on 16/9/6.
 */
@Service
public class UserBlackService {

    private ComUserBlacklistMapper comUserBlacklistMapper;

    public ComUserBlacklist loadByMobile(String mobile){
        return comUserBlacklistMapper.selectByMobile(mobile);
    }
}
