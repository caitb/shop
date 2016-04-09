package com.masiis.shop.web.platform.service.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.PfUserTrialMapper;
import com.masiis.shop.dao.platform.user.*;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.beans.wxauth.AccessTokenRes;
import com.masiis.shop.web.platform.beans.wxauth.WxUserInfo;
import com.masiis.shop.web.platform.constants.WxConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Service
@Transactional
public class UserService {
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
    public ComUser bindPhone(HttpServletRequest request, String phone) throws Exception {
        ComUser comUser = null;
        JSONObject jsonObject = new JSONObject();
        try {
            comUser = (ComUser) request.getSession().getAttribute("comUser");
            if (comUser != null) {
                comUser = comUserMapper.selectByPrimaryKey(comUser.getId());
                comUser.setMobile(phone);
                //更新表中的信息
                int i = comUserMapper.updatePhone(comUser);
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

    public ComUser signWithCreateUserByWX(AccessTokenRes res, WxUserInfo userRes) {
        // 检查openid是否已经存在数据库
        List<ComWxUser> wxUsers = wxUserMapper.selectByUnionid(userRes.getUnionid());
        ComWxUser wxUser = null;
        ComUser user = null;
        if(wxUsers != null && wxUsers.size() > 0){
            // 有unionid
            user = getUserByUnionid(userRes.getUnionid());
            if(user == null){
                log.error("系统数据错误,请联系管理员!");
                throw new BusinessException("");
            }
            wxUser = getWxUserByOpenidInList(res.getOpenid(), wxUsers);
            if(wxUser != null){
                // 有openid,更新这个openid数据
                updateWxUserByActkn(res, userRes, wxUser);
            }
        } else {
            // 无unionid,创建comuser和comwxuser
            user = new ComUser();
            user.setWxNkName(userRes.getNickname());
            user.setWxHeadImg(userRes.getHeadimgurl());
            user.setCreateTime(new Date());
            user.setWxUnionid(userRes.getUnionid());
            user.setIsAgent(0);
            user.setAuditStatus(0);
            user.setSendType(0);
            user.setRegisterSource(0);
            insertComUser(user);
            accountService.createAccountByUser(user);
        }

        wxUser = createWxUserInit(res, userRes, user);
        wxUser.setAppid(WxConstants.APPID);
        wxUser.setComUserId(user.getId());
        if(wxUser.getId() == null) {
            wxUserMapper.insert(wxUser);
        } else {
            wxUserMapper.updateByPrimaryKey(wxUser);
        }

        return user;
    }

    /**
     * 根据最新请求数据更新wxUser
     *
     * @param res
     * @param userInfo
     * @param wxUser
     */
    private void updateWxUserByActkn(AccessTokenRes res, WxUserInfo userInfo, ComWxUser wxUser) {
        if(wxUser == null){
            throw new BusinessException("传入目标对象为null");
        }

        wxUser.setAccessToken(res.getAccess_token());
        Long atoken_ex = res.getExpires_in();
        if(res.getExpires_in() == null || res.getExpires_in() <= 0){
            atoken_ex = 7200L * 1000;
        }
        wxUser.setAtokenExpire(new Date(new Date().getTime() + atoken_ex));
        wxUser.setCity(userInfo.getCity());
        wxUser.setCountry(userInfo.getCountry());
        wxUser.setHeadImgUrl(userInfo.getHeadimgurl());
        wxUser.setNkName(userInfo.getNickname());
        wxUser.setProvince(userInfo.getProvince());
        wxUser.setRefreshToken(res.getRefresh_token());
        wxUser.setSex(Integer.valueOf(userInfo.getSex()));
    }

    /**
     * 创建ComWxUser(注册微信用户)
     *
     * @param res
     * @param userInfo
     * @param user
     * @return
     */
    private ComWxUser createWxUserInit(AccessTokenRes res, WxUserInfo userInfo, ComUser user) {
        ComWxUser wxUser = new ComWxUser();

        wxUser.setCreateTime(new Date());
        wxUser.setAccessToken(res.getAccess_token());
        Long atoken_ex = res.getExpires_in();
        if(res.getExpires_in() == null || res.getExpires_in() <= 0){
            atoken_ex = 7200L * 1000;
        }
        wxUser.setAtokenExpire(new Date(new Date().getTime() + atoken_ex));
        wxUser.setUnionid(userInfo.getUnionid());
        wxUser.setCity(userInfo.getCity());
        wxUser.setCountry(userInfo.getCountry());
        wxUser.setHeadImgUrl(userInfo.getHeadimgurl());
        wxUser.setNkName(userInfo.getNickname());
        wxUser.setOpenid(userInfo.getOpenid());
        wxUser.setProvince(userInfo.getProvince());
        wxUser.setRefreshToken(res.getRefresh_token());
        wxUser.setSex(Integer.valueOf(userInfo.getSex()));

        return wxUser;
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
}
