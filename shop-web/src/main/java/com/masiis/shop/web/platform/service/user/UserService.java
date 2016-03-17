package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.PfUserTrialMapper;
import com.masiis.shop.dao.platform.user.ComUserAddressMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;

    /**
     * 根据用户id获取用户
     *
     * @param userId
     * @return
     */
    public ComUser getUserById(Long userId) throws Exception {
        ComUser comUser = comUserMapper.selectByPrimaryKey(userId);
        return comUser;
    }

    /**
     * 添加用户地址
     *
     * @param comUserAddress
     */
    public void addComUserAddress(ComUserAddress comUserAddress) throws Exception {
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
    public void updateComUserAddress(ComUserAddress comUserAddress) throws Exception {
        if (comUserAddress == null) {
            throw new BusinessException("comUserAddress为空");
        }
        comUserAddressMapper.updateByPrimaryKey(comUserAddress);
    }

    /**
     * 更新用户信息
     *
     * @author hanzengzhi
     * @date 2016/3/5 15:22
     */
    public int updateComUser(ComUser comUser) {
        return comUserMapper.updateByPrimaryKey(comUser);
    }

    /**
     * 根据id查找用户地址
     *
     * @param id
     * @return
     */
    public ComUserAddress getComUserAddress(Long id) {
        return comUserAddressMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据userid查找用户地址
     *
     * @param userId
     * @return
     */
    public List<ComUserAddress> getComUserAddressByUserId(Long userId) {
        return comUserAddressMapper.selectAllByComUserId(userId);
    }

    /**
     * @param pfUserTrial
     */
    public void updateUserTrial(PfUserTrial pfUserTrial) {
        pfUserTrialMapper.updateById(pfUserTrial);
    }

    /**
     * 根据userId和skuId
     * 验证商品是否使用过
     *
     * @author hanzengzhi
     * @date 2016/3/9 11:16
     */
    public List<PfUserTrial> isApplyTrial(PfUserTrial pfUserTrial) {
        return pfUserTrialMapper.isApplyTrial(pfUserTrial);
    }

    /**
     * 试用表插入
     *
     * @author hanzengzhi
     * @date 2016/3/5 15:19
     */
    public void insertUserTrial(PfUserTrial pfUserTrial) {
        try {
            pfUserTrialMapper.insert(pfUserTrial);
        } catch (Exception e) {

        }
    }

    /**
     * 通过手机号获取用户
     *
     * @param mobile
     * @return
     */
    public ComUser getUserByMobile(String mobile) {
        return comUserMapper.selectByMobile(mobile);
    }

    /**
     * 添加用户
     *
     * @author ZhaoLiang
     * @date 2016/3/11 17:33
     */
    public void insertUserCertificate(ComUser comUser, PfUserCertificate pfUserCertificate) throws Exception {
        comUserMapper.updateByPrimaryKey(comUser);
        pfUserCertificateMapper.insert(pfUserCertificate);
    }

    /**
     * 根据openid查询用户
     *
     * @param openid
     * @return
     */
    public ComUser getUserByOpenid(String openid) {
        return comUserMapper.selectByOpenid(openid);
    }

    /**
     * 创建用户
     *
     * @param user
     */
    public void insertComUser(ComUser user) {
        comUserMapper.insert(user);
    }

    /**
     * 绑定手机号
     *
     * @author hanzengzhi
     * @date 2016/3/16 13:57
     */
    public ComUser bindPhone(HttpServletRequest request, String phone) throws Exception {
        ComUser comUser = null;
        try {
            comUser = (ComUser) request.getSession().getAttribute("comUser");
            if (comUser != null) {
                comUser = comUserMapper.selectByPrimaryKey(comUser.getId());
                comUser.setMobile(phone);
                //更新表中的信息
                int i = comUserMapper.updateByPrimaryKey(comUser);
                if (i == 1) {
                    //更新session缓存中的中user
                    request.getSession().removeAttribute("comUser");
                    request.getSession().setAttribute("comUser", comUser);
                } else {
                    throw new Exception("更新用户信息失败");
                }
            } else {
                throw new Exception("查询用户信息失败");
            }
        } catch (Exception e) {
            throw new Exception("绑定手机号失败");
        }
        return comUser;
    }
}
