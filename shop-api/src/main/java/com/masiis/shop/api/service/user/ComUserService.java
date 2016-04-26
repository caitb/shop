package com.masiis.shop.api.service.user;

import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.PfUserTrialMapper;
import com.masiis.shop.dao.platform.user.ComUserAddressMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.ComWxUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.po.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Date 2016/4/26
 * @Auther lzh
 */
@Service
public class ComUserService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private ComUserAddressMapper comUserAddressMapper;
    @Resource
    private PfUserTrialMapper pfUserTrialMapper;
    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;
    @Resource
    private UserCertificateService userCertificateService;
    @Resource
    private ComUserAccountService accountService;
    @Resource
    private ComWxUserMapper wxUserMapper;
    @Resource
    private SfUserAccountService sfAccountService;

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
        if (pfUserCertificateMapper.selectByUserSkuId(pfUserCertificate.getPfUserSkuId()) == null) {
            pfUserCertificateMapper.insert(pfUserCertificate);
        }
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
    public ComUser bindPhone(ComUser comUser, String phone) throws Exception {
        try {
            if (comUser == null) {
                throw new BusinessException("用户对象为空");
            }
            if (comUser != null) {
                comUser.setMobile(phone);
                comUser.setIsBinding(1);
                //更新表中的信息
                comUserMapper.updateByPrimaryKey(comUser);
            } else {
                throw new Exception("查询用户信息失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return comUser;
    }

    /**
     * @Author Jing Hao
     * @Date 2016/3/18 0018 下午 3:44
     * 更新授权书，用户信息
     */
    public void updateUserCertificate(ComUser comUser, PfUserCertificate pfUserCertificate) throws Exception {
        comUserMapper.updateByPrimaryKey(comUser);
        pfUserCertificateMapper.updateById(pfUserCertificate);
    }

    /**
     * 根据unionid获取comuser
     *
     * @param unionid
     * @return
     */
    public ComUser getUserByUnionid(String unionid) {
        return comUserMapper.selectByUnionid(unionid);
    }

    /**
     * 根据openid在list获取wxuser
     *
     * @param openid
     * @param wxUsers
     * @return
     */
    private ComWxUser getWxUserByOpenidInList(String openid, List<ComWxUser> wxUsers) {
        ComWxUser user = null;
        if(StringUtils.isBlank(openid)){
            return user;
        }
        for(ComWxUser ex:wxUsers){
            if(openid.equals(ex.getOpenid())){
                return ex;
            }
        }
        return user;
    }

    /**
     * 初始化一个新的comuser
     *
     * @return
     */
    public ComUser initNewComUser() {
        ComUser user = new ComUser();

        user.setWxNkName("");
        user.setWxHeadImg("");
        user.setCreateTime(new Date());
        user.setWxUnionid("");
        user.setIsAgent(0);
        user.setIsBinding(0);
        user.setAuditStatus(0);
        user.setSendType(0);
        user.setRegisterSource(0);


        return user;
    }
}
