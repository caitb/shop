package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.user.ComUserAddressMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAddress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Service
public class UserService {

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private ComUserAddressMapper comUserAddressMapper;

    /**
     * 根据用户id获取用户
     *
     * @param userId
     * @return
     */
    public ComUser getUserById(Long userId) {
        ComUser comUser = comUserMapper.selectByPrimaryKey(userId);
        return comUser;
    }

    /**
     * 添加用户地址
     * @param comUserAddress
     */
    public void addComUserAddress(ComUserAddress comUserAddress) {
        if (comUserAddress == null) {
            throw new BusinessException("comUserAddress为空");
        }
        comUserAddressMapper.insert(comUserAddress);
    }

    /**
     * 修改用户地址
     * @param comUserAddress
     */
    public void updateComUserAddress(ComUserAddress comUserAddress) {
        if (comUserAddress == null) {
            throw new BusinessException("comUserAddress为空");
        }
        comUserAddressMapper.updateByPrimaryKey(comUserAddress);
    }

    /**
     * 根据id查找用户地址
     * @param id
     * @return
     */
    public ComUserAddress getComUserAddress(Integer id) {
        return comUserAddressMapper.selectByPrimaryKey(id);
    }

}
