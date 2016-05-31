package com.masiis.shop.web.mall.service.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.EmojiUtils;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.user.SfUserRelationMapper;
import com.masiis.shop.dao.platform.order.PfUserTrialMapper;
import com.masiis.shop.dao.platform.user.ComUserAddressMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.ComWxUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.mall.beans.wxauth.AccessTokenRes;
import com.masiis.shop.web.mall.beans.wxauth.WxUserInfo;
import com.masiis.shop.common.constant.wx.WxConsSF;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
    private ComWxUserMapper comWxUserMapper;
    @Resource
    private SfUserRelationMapper sfUserRelationMapper;
    @Resource
    private ComWxUserMapper wxUserMapper;
    @Resource
    private ComUserAccountService accountService;
    @Resource
    private SfUserAccountService sfAccountService;
    @Resource
    private SfShopMapper sfShopMapper;

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

//    /**
//     * 根据openid查询用户
//     *
//     * @param openid
//     * @return
//     */
//    public ComUser getUserByOpenid(String openid) {
//        return comUserMapper.selectByOpenid(openid);
//    }

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

    public List<ComWxUser> findComWxUserByUserId(Long userId) {
        return comWxUserMapper.selectByUserId(userId);
    }

    /**
     * 来自分享人的信息
     *
     * @author muchaofeng
     * @date 2016/4/12 12:03
     */
    public void getShareUser(Long userId, Long userPid) {
        if (userPid == null) {
            userPid = 0l;
        }
        if (userId.equals(userPid)) {
            //点击自己的链接进入不会建立分销关系
            return;
        }
        //获取自己的分销关系
        SfUserRelation sfUserRelation = sfUserRelationMapper.getSfUserRelationByUserId(userId);
        //获取上级分销关系
        SfUserRelation sfUserPRelation = sfUserRelationMapper.getSfUserRelationByUserId(userPid);
        if (sfUserRelation == null) { //来自于分享链接
            SfUserRelation sfNewUserRelation = new SfUserRelation();
            sfNewUserRelation.setCreateTime(new Date());
            sfNewUserRelation.setUserId(userId);
            if (sfUserPRelation == null) {
                sfNewUserRelation.setTreeLevel(1);
                sfNewUserRelation.setUserPid(0l);//如果上级还没有建立分销关系则设为0
                sfUserRelationMapper.insert(sfNewUserRelation);
                String treeCode = sfNewUserRelation.getId() + ",";
                sfUserRelationMapper.updateTreeCodeById(sfNewUserRelation.getId(), treeCode);
            } else {
                sfNewUserRelation.setTreeLevel(sfUserPRelation.getTreeLevel() + 1);
                sfNewUserRelation.setUserPid(userPid);
                sfUserRelationMapper.insert(sfNewUserRelation);
                String treeCode = sfUserPRelation.getTreeCode() + sfNewUserRelation.getId() + ",";
                sfUserRelationMapper.updateTreeCodeById(sfNewUserRelation.getId(), treeCode);
            }
        }
//        else {
//            if (sfUserRelation.getUserPid() == 0l && sfUserPRelation != null) {
//                if (sfUserPRelation.getUserId() != null) {
//                    sfUserRelation.setUserPid(sfUserPRelation.getUserId());
//                }
//                sfUserRelationMapper.updateByPrimaryKey(sfUserRelation);
//            }
//        }
        log.info("分销关系");
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

    @Transactional
    public synchronized ComUser signWithCreateUserByWX(AccessTokenRes res, WxUserInfo userRes) {
        // 检查openid是否已经存在数据库
        List<ComWxUser> wxUsers = wxUserMapper.selectByUnionid(userRes.getUnionid());
        ComWxUser wxUser = null;
        ComUser user = null;
        user = getUserByUnionid(userRes.getUnionid());

        if (wxUsers != null && wxUsers.size() > 0) {
            // 有unionid
            if (user == null) {
                log.error("系统数据错误,请联系管理员!");
                throw new BusinessException("");
            }
            wxUser = getWxUserByOpenidInList(res.getOpenid(), wxUsers);
            log.info("wxUser:" + wxUser);
        }

        // 无unionid,创建comuser和comwxuser
        if (user == null) {
            log.info("创建新comUser");
            user = createComUser(userRes);
            insertComUser(user);
            accountService.createAccountByUser(user);
            sfAccountService.createSfAccountByUser(user);
        }

        if (wxUser == null) {
            log.info("创建新comWxUser");
            // 无openid创建新的wxUser
            wxUser = createWxUserInit(res, userRes, user);
            wxUserMapper.insert(wxUser);
        } else {
            log.info("更新comWxUser");
            // 有openid,更新这个openid数据
            updateWxUserByActkn(res, userRes, wxUser);
            wxUserMapper.updateByPrimaryKey(wxUser);
        }

        return user;
    }

    private ComUser createComUser(WxUserInfo userRes) {
        ComUser user = new ComUser();

        user.setWxNkName(EmojiUtils.removeNonBmpUnicode(userRes.getNickname()));
        user.setWxHeadImg(userRes.getHeadimgurl());
        user.setCreateTime(new Date());
        user.setWxUnionid(userRes.getUnionid());
        user.setIsAgent(0);
        user.setIsBinding(0);
        user.setAuditStatus(0);
        // 只有平台发货方式
        user.setSendType(1);
        user.setRegisterSource(0);
        user.setIsBuy(0);

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
        if (wxUser == null) {
            throw new BusinessException("传入目标对象为null");
        }

        wxUser.setAccessToken(res.getAccess_token());
        Long atoken_ex = res.getExpires_in();
        if (res.getExpires_in() == null || res.getExpires_in() <= 0) {
            atoken_ex = 7200L * 1000;
        }
        wxUser.setAtokenExpire(new Date(new Date().getTime() + atoken_ex));
        wxUser.setCity(userInfo.getCity());
        wxUser.setCountry(userInfo.getCountry());
        wxUser.setHeadImgUrl(userInfo.getHeadimgurl());
        wxUser.setNkName(EmojiUtils.removeNonBmpUnicode(userInfo.getNickname()));
        wxUser.setProvince(userInfo.getProvince());
        wxUser.setRefreshToken(res.getRefresh_token());
        wxUser.setSex(Integer.valueOf(userInfo.getSex()));
    }

    /**
     * 创建ComWxUser(注册微信用户)
     *
     * @param res
     * @param userInfo
     * @return
     */
    private ComWxUser createWxUserInit(AccessTokenRes res, WxUserInfo userInfo, ComUser user) {
        ComWxUser wxUser = new ComWxUser();

        wxUser.setCreateTime(new Date());
        wxUser.setAccessToken(res.getAccess_token());
        Long atoken_ex = res.getExpires_in();
        if (res.getExpires_in() == null || res.getExpires_in() <= 0) {
            atoken_ex = 7200L * 1000;
        }
        wxUser.setAtokenExpire(new Date(new Date().getTime() + atoken_ex));
        wxUser.setUnionid(userInfo.getUnionid());
        wxUser.setCity(userInfo.getCity());
        wxUser.setCountry(userInfo.getCountry());
        wxUser.setHeadImgUrl(userInfo.getHeadimgurl());
        wxUser.setNkName(EmojiUtils.removeNonBmpUnicode(userInfo.getNickname()));
        wxUser.setOpenid(userInfo.getOpenid());
        wxUser.setProvince(userInfo.getProvince());
        wxUser.setRefreshToken(res.getRefresh_token());
        wxUser.setSex(Integer.valueOf(userInfo.getSex()));
        wxUser.setAppid(WxConsSF.APPID);
        wxUser.setComUserId(user.getId());

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
        if (StringUtils.isBlank(openid)) {
            return user;
        }
        for (ComWxUser ex : wxUsers) {
            if (openid.equals(ex.getOpenid())) {
                return ex;
            }
        }
        return user;
    }

    public static void main(String... args) throws UnsupportedEncodingException {
        String xx = URLDecoder.decode("d%E2%80%86y%E2%80%86c%E2%80%86286588441", "UTF-8");
        System.out.println(xx);
    }
}
