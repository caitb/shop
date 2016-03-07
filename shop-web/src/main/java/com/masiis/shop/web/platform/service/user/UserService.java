package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.PfUserTrialMapper;
import com.masiis.shop.dao.platform.user.ComUserAddressMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAddress;
import com.masiis.shop.dao.po.PfUserTrial;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Service
@Transactional
public class UserService {

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private ComUserAddressMapper comUserAddressMapper;
    @Resource
    private PfUserTrialMapper pfUserTrialMapper;


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
     *
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
     *
     * @param comUserAddress
     */
    public void updateComUserAddress(ComUserAddress comUserAddress) {
        if (comUserAddress == null) {
            throw new BusinessException("comUserAddress为空");
        }
        comUserAddressMapper.updateByPrimaryKey(comUserAddress);
    }
    /**
     * 更新用户信息
     * @author  hanzengzhi
     * @date  2016/3/5 15:22
     */
    public int updateComUser(ComUser comUser){
        return comUserMapper.updateByPrimaryKey(comUser);
    }

    /**
     * 根据id查找用户地址
     *
     * @param id
     * @return
     */
    public ComUserAddress getComUserAddress(Integer id) {
        return comUserAddressMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据userid查找用户地址
     *
     * @param userId
     * @return
     */
    public List<ComUserAddress> getComUserAddress(Long userId) {
        return comUserAddressMapper.selectAllByComUserId(userId);
    }
    /**
     *
     * @param pfUserTrial
     */
    public void updateUserTrial(PfUserTrial pfUserTrial){
        pfUserTrialMapper.updateById(pfUserTrial);
    }

    /**
     * 试用表插入
     * @author  hanzengzhi
     * @date  2016/3/5 15:19
     */
    public void insertUserTrial(PfUserTrial pfUserTrial){
        pfUserTrialMapper.insert(pfUserTrial);
    }

    /**
     * 通过手机号获取用户
     * @param mobile
     * @return
     */
    public ComUser getUserByMobile(String mobile){
        return comUserMapper.selectByMobile(mobile);
    }
}
