package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Service
public class UserService {

    @Resource
    private ComUserMapper comUserMapper;

    /**
     * 根据用户id获取用户
     * @param userId
     * @return
     */
    public ComUser getUserById(Long userId) {
        ComUser comUser = comUserMapper.selectByPrimaryKey(userId);
        return comUser;
    }
}
