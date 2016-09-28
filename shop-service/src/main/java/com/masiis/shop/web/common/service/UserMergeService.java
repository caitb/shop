package com.masiis.shop.web.common.service;

import com.masiis.shop.common.constant.platform.SysConstants;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.AESUtils;
import com.masiis.shop.dao.mall.user.SfUserRelationMapper;
import com.masiis.shop.dao.platform.user.ComUserBakMapper;
import com.masiis.shop.dao.platform.user.ComWxUserMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComWxUser;
import com.masiis.shop.dao.po.SfUserRelation;
import com.masiis.shop.web.common.utils.SpringRedisUtil;
import com.masiis.shop.web.mall.service.user.SfUserAccountService;
import com.masiis.shop.web.mall.service.user.SfUserStatisticsService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangbingjian on 2016/9/1.
 */
@Service
@Transactional
public class UserMergeService {

    private static final Logger logger = Logger.getLogger(UserMergeService.class);
    @Autowired
    private UserService userService;
    @Autowired
    private ComUserAccountService accountService;
    @Autowired
    private SfUserAccountService sfAccountService;
    @Autowired
    private SfUserStatisticsService sfUserStatisticsService;
    @Autowired
    private ComWxUserMapper comWxUserMapper;
    @Autowired
    private ComUserBakMapper comUserBakMapper;
    @Autowired
    private SfUserRelationMapper sfUserRelationMapper;

    /**
     * 手机号绑定
     * @param comUser   comUser
     * @param mobile    mobile
     * @return
     */
    public ComUser bindUser(ComUser comUser, String mobile) throws Exception{
        ComUser user = this.mergeAndBindingUser(comUser, 2, mobile, null, null);
        return user;
    }

    /**
     * 申请代理及绑定手机号时验证手机号及合并账号
     * @param comUser       comUser
     * @param flag          实名认证、手机绑定标识  1：实名认证 2：手机绑定
     * @param mobile        mobile
     * @param realName      真实姓名
     * @param wxId          微信号
     * @return
     */
    private ComUser mergeAndBindingUser(ComUser comUser, Integer flag, String mobile, String realName, String wxId) throws Exception{
        logger.info("申请代理及绑定手机号时验证手机号及合并账号");
        logger.info("mobile = " + mobile);
        logger.info("userId = " + comUser.getId());
        logger.info("flag = " + flag);
        logger.info("realName = " + realName);
        logger.info("wxId = " + wxId);
        ComUser user = null;
        //通过手机号码查询用户信息
        ComUser mobileUser = userService.getUserByMobile(mobile);
        logger.info("通过手机号查询ComUser【" + mobileUser + "】");
        switch (flag.intValue()){
            case 1 : {

                break;
            }
            case 2 : {
                try {
                    //当mobileUser为空时直接进行手机号绑定
                    if (mobileUser == null){
                        logger.info("直接进行手机号绑定");
                        user = userService.bindPhone(comUser, mobile);
                        //绑定结束之后初始化用户基础信息表
                        if (accountService.findAccountByUserid(user.getId()) == null){
                            accountService.createAccountByUser(user);
                        }
                        if (sfAccountService.findAccountByUserId(user.getId()) == null){
                            sfAccountService.createSfAccountByUser(user);
                        }
                        if (sfUserStatisticsService.selectByUserId(user.getId()) == null){
                            sfUserStatisticsService.initSfUserStatistics(user);
                        }
                    }else {
                        user = this.megreUser(mobileUser, comUser, 2);
                    }
                }catch (Exception e){
                    logger.error(e.getMessage(),e);
                    throw new BusinessException(e.getMessage());
                }
                break;
            }
        }
        return user;
    }

    /**
     * 用户合并
     * @param mobileUser    手机号对应的用户
     * @param wechatUser    微信号对应的用户
     * @param flag          实名认证、手机绑定标识  1：实名认证 2：手机绑定
     * @return
     * @throws Exception
     */
    private ComUser megreUser(ComUser mobileUser, ComUser wechatUser, Integer flag) throws Exception {
        switch (flag.intValue()){
            case 1 : {
//                mobileUser.setWxHeadImg(wechatUser.getWxHeadImg());
//                mobileUser.setWxNkName(wechatUser.getWxNkName());
//                mobileUser.setWxUnionid(wechatUser.getWxUnionid());
//                mobileUser.setWxId(wechatUser.getWxId());
                break;
            }
            case 2 : {
                String headImg = mobileUser.getWxHeadImg();
                if (headImg.indexOf("default.png") > -1 || headImg == null || headImg.isEmpty() || headImg.equals("")){
                    mobileUser.setWxHeadImg(wechatUser.getWxHeadImg());
                }
                if (mobileUser.getAuditStatus() == 0){
                    mobileUser.setAuditStatus(wechatUser.getAuditStatus());
                    mobileUser.setAuditDate(wechatUser.getAuditDate());
                    mobileUser.setAuditReason(wechatUser.getAuditReason());
                    mobileUser.setIdCard(wechatUser.getIdCard());
                    mobileUser.setIdCardBackUrl(wechatUser.getIdCardBackUrl());
                    mobileUser.setIdCardFrontUrl(wechatUser.getIdCardFrontUrl());
                    mobileUser.setWxId(wechatUser.getWxId());
                }
                if (mobileUser.getIsAgent().intValue() != wechatUser.getIsAgent().intValue() && mobileUser.getIsAgent().intValue() == 0){
                    mobileUser.setIsAgent(mobileUser.getIsAgent());
                }
                if (mobileUser.getIsBuy().intValue() != wechatUser.getIsBuy().intValue() && mobileUser.getIsBuy().intValue() == 0){
                    mobileUser.setIsBuy(mobileUser.getIsBuy());
                }
                mobileUser.setWxNkName(wechatUser.getWxNkName());
                mobileUser.setWxUnionid(wechatUser.getWxUnionid());
                break;
            }
        }
        //备份com_user至com_user_bak
        ComUser comUserBak = (ComUser) wechatUser.clone();
        if (comUserBakMapper.insert(comUserBak) == 0){
            throw new BusinessException("备份ComUser数据失败");
        }
        //更新com_wx_user表中的com_user_id
        List<ComWxUser> comWxUsers = comWxUserMapper.selectByUnionid(wechatUser.getWxUnionid());
        for (ComWxUser comWxUser : comWxUsers){
            logger.info("comWxUserId = " + comWxUser.getId());
            comWxUser.setComUserId(mobileUser.getId());
            if (comWxUserMapper.updateByPrimaryKey(comWxUser) == 0){
                throw new BusinessException("跟新ComWxUser数据失败");
            }
        }
        //删除原微信用户
        if (userService.delComUser(wechatUser) == 0){
            throw new BusinessException("删除用户信息失败");
        }
        //更新手机号账户
        if (userService.updateComUser(mobileUser) == 0){
            throw new BusinessException("更新ComUser失败");
        }
        //通过wxUserId查询小铺分销关系
        //userId = wxUserId
        List<SfUserRelation> sfUserRelations_userId = sfUserRelationMapper.getSfUserRelationByUserId(comUserBak.getId());
        //userPid = wxUserId
        List<SfUserRelation> sfUserRelations_userPid = sfUserRelationMapper.getSfUserRelationByUserPid(comUserBak.getId());
        for (SfUserRelation userRelation : sfUserRelations_userId){
            userRelation.setUserId(mobileUser.getId());
            if (sfUserRelationMapper.updateByPrimaryKey(userRelation) == 0){
                throw new BusinessException("更新分销关系失败");
            }
        }
        for (SfUserRelation userRelation : sfUserRelations_userPid){
            userRelation.setUserId(mobileUser.getId());
            if (sfUserRelationMapper.updateByPrimaryKey(userRelation) == 0){
                throw new BusinessException("更新分销关系失败");
            }
        }
        //删除redis中的数据
        delRedisWx(comUserBak);
        return mobileUser;
    }

    public void delRedisWx(ComUser comUser){
        List<ComWxUser> comWxUsers = comWxUserMapper.selectByUnionid(comUser.getWxUnionid());
        for (ComWxUser comWxUser : comWxUsers){
            String openidkey = getEncrypt(comWxUser.getUnionid());
            //删除Redis
            logger.info("openidkey" + openidkey);
            logger.info(comWxUser.getUnionid() + comWxUser.getOpenid() + "_token");
            logger.info(comWxUser.getUnionid() + comWxUser.getOpenid() + "_rftoken");
            SpringRedisUtil.del(openidkey);
            SpringRedisUtil.del(comWxUser.getUnionid() + comWxUser.getOpenid() + "_token");
            SpringRedisUtil.del(comWxUser.getUnionid() + comWxUser.getOpenid() + "_rftoken");
        }
    }

    private String getEncrypt(String openid){
        if(StringUtils.isBlank(openid)){
            throw new BusinessException("openid is null");
        }
        String res = AESUtils.encrypt(openid + SysConstants.COOKIE_KEY_SALT, SysConstants.COOKIE_AES_KEY);
        return res;
    }
}
