package com.masiis.shop.api.controller.system;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.system.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.common.beans.wx.wxauth.AccessTokenRes;
import com.masiis.shop.common.beans.wx.wxauth.WxUserInfo;
import com.masiis.shop.common.util.*;
import com.masiis.shop.web.api.service.ComUserKeyboxService;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.api.utils.SpringRedisUtil;
import com.masiis.shop.api.utils.SysSignUtils;
import com.masiis.shop.api.utils.TokenUtils;
import com.masiis.shop.api.utils.ValidCodeUtils;
import com.masiis.shop.common.constant.SMSConstants;
import com.masiis.shop.common.enums.api.ValidCodeTypeEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserKeybox;
import com.masiis.shop.web.platform.service.user.UserBlackService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * @date 2016/4/25
 * @author lzh
 */
@Controller
@RequestMapping("/sys")
public class LoginController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private UserService userService;
    @Resource
    private ComUserKeyboxService keyboxService;
    @Resource
    private UserBlackService userBlackService;

    @RequestMapping("/loginByWx")
    @ResponseBody
    @SignValid(paramType = LoginWxReq.class, hasToken = false)
    public LoginByWxRes loginByWx(HttpServletRequest request, LoginWxReq req) throws IOException {
        LoginByWxRes res = new LoginByWxRes();
        try{
            if(req == null){
                System.out.println("空");
            }
            if(StringUtils.isBlank(req.getOpenId())){
                res.setResCode(SysResCodeCons.RES_CODE_WXLOGIN_OPENID_NULL);
                res.setResMsg(SysResCodeCons.RES_CODE_WXLOGIN_OPENID_NULL_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_WXLOGIN_OPENID_NULL_MSG);
            }
            if(StringUtils.isBlank(req.getUnionid())){
                res.setResCode(SysResCodeCons.RES_CODE_WXLOGIN_UNIONID_NULL);
                res.setResMsg(SysResCodeCons.RES_CODE_WXLOGIN_UNIONID_NULL_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_WXLOGIN_UNIONID_NULL_MSG);
            }
            if(StringUtils.isBlank(req.getAppid())){
                res.setResCode(SysResCodeCons.RES_CODE_WXLOGIN_APPID_NULL);
                res.setResMsg(SysResCodeCons.RES_CODE_WXLOGIN_APPID_NULL_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_WXLOGIN_APPID_NULL_MSG);
            }

            AccessTokenRes qRes = createAccesTokenResByReq(req);
            WxUserInfo userInfo = createWxUserInfoByReq(req);
            // 微信登录
            ComUser user = userService.signWithCreateUserByWX(qRes, userInfo, req.getAppid());
            ComUserKeybox keybox = keyboxService.getKeyboxByUserId(user.getId());
            if(keybox == null){
                keybox = keyboxService.createKeyboxByUser(user);
            }
            // 创建token
            String token = TokenUtils.generateToken();
            // 创建userKey
            String userKey = MD5Utils.encrypt(user.getId() + user.getWxUnionid() + System.currentTimeMillis());
            keybox.setAppToken(token);
            keybox.setComUserId(user.getId());
            keybox.setUserKey(userKey);
            keybox.setExTime(DateUtil.getDateNextdays(30));
            if(keybox.getId() == null ){
                keyboxService.insertKeybox(keybox);
            } else {
                keyboxService.updateKeybox(keybox);
            }

            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            res.setToken(token);
            res.setExpire(30);
            res.setExpireUnit("天");
            res.setUserKey(userKey);
            res.setSign(SysSignUtils.toSignString(res, null));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if(e instanceof JSONException){
                res.setResCode(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID);
                res.setResMsg(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID_MSG);
            }
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }

        return res;
    }

    @RequestMapping("/loginByPhone")
    @ResponseBody
    @SignValid(paramType = LoginByPhoneReq.class, hasToken = false)
    public LoginByPhoneRes loginByPhone(HttpServletRequest request, LoginByPhoneReq req){
        LoginByPhoneRes res = new LoginByPhoneRes();
        try{
            if(req == null){
                throw new BusinessException();
            }
            if(StringUtils.isBlank(req.getPhoneNum())){
                // 电话号码为空
                res.setResCode(SysResCodeCons.RES_CODE_PHONENUM_BLANK);
                res.setResMsg(SysResCodeCons.RES_CODE_PHONENUM_BLANK_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_PHONENUM_BLANK_MSG);
            }
            if(StringUtils.isBlank(req.getValidcode())){
                // 检查验证码为空
                res.setResCode(SysResCodeCons.RES_CODE_VALIDCODE_IS_BLANK);
                res.setResMsg(SysResCodeCons.RES_CODE_VALIDCODE_IS_BLANK_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_VALIDCODE_IS_BLANK_MSG);
            }
            // 去除首尾空格
            String phoneNum = req.getPhoneNum().trim();
            String validcode = req.getValidcode().trim();
            // 获取redis存在的验证码
            String environment_conf = PropertiesUtils.getStringValue("sys_run_enviroment_key");
            if(!("6666".equals(validcode) && (StringUtils.isNotBlank(environment_conf) && "0".equals(environment_conf)))) {
                String codeRd = SpringRedisUtil.get(ValidCodeUtils.getRedisPhoneNumValidCodeName(phoneNum, ValidCodeTypeEnum.LOGIN_VCODE), String.class);
                if (codeRd == null) {
                    // 验证码不存在或已过期
                    res.setResCode(SysResCodeCons.RES_CODE_VALIDCODE_IS_EXPIRED);
                    res.setResMsg(SysResCodeCons.RES_CODE_VALIDCODE_IS_EXPIRED_MSG);
                    throw new BusinessException(SysResCodeCons.RES_CODE_VALIDCODE_IS_EXPIRED_MSG);
                }
                if (!codeRd.equals(validcode)) {
                    // 验证码不匹配
                    res.setResCode(SysResCodeCons.RES_CODE_VALIDCODE_IS_INVALID);
                    res.setResMsg(SysResCodeCons.RES_CODE_VALIDCODE_IS_INVALID_MSG);
                    throw new BusinessException(SysResCodeCons.RES_CODE_VALIDCODE_IS_INVALID_MSG);
                }
                // 移除redis验证码
                SpringRedisUtil.saveEx(ValidCodeUtils.getRedisPhoneNumValidCodeName(phoneNum, ValidCodeTypeEnum.LOGIN_VCODE), "aa", 1);
            }

            // 检查是否处于黑名单
            if(userBlackService.isBlackByMobile(phoneNum)) {
                // 黑名单用户不能登录
                res.setResCode(SysResCodeCons.RES_CODE_PHONENUM_ISIN_BLACKLIST);
                res.setResMsg(SysResCodeCons.RES_CODE_PHONENUM_ISIN_BLACKLIST_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_PHONENUM_ISIN_BLACKLIST_MSG);
            }

            // 按照phoneNum来查询用户
            ComUser user = userService.getUserByMobile(phoneNum);
            if(user == null){
                // 手机号注册
                log.info("创建新comUser");
                user = userService.createComUser(null);
                user.setMobile(phoneNum);
                user.setIsBinding(1);
                user.setWxHeadImg(PropertiesUtils.getStringValue("phone_sign_in_default_headimg"));
                user.setRealName(phoneNum);
                user.setWxNkName(phoneNum);
                userService.insertComUserWithAccount(user);
            }
            // 生成token
            String token = TokenUtils.generateToken();
            // 保存token
            ComUserKeybox keybox = keyboxService.getKeyboxByUserId(user.getId());
            if(keybox == null){
                keybox = keyboxService.createKeyboxByUser(user);
            }
            // 创建userKey
            String userKey = MD5Utils.encrypt(user.getId() + user.getWxUnionid() + System.currentTimeMillis());
            keybox.setAppToken(token);
            keybox.setComUserId(user.getId());
            keybox.setUserKey(userKey);
            keybox.setExTime(DateUtil.getDateNextdays(30));
            if(keybox.getId() == null ){
                keyboxService.insertKeybox(keybox);
            } else {
                keyboxService.updateKeybox(keybox);
            }
            // 返回数据
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            res.setToken(token);
            res.setExpire(30);
            res.setUserKey(userKey);
            res.setExpireUnit("天");
            return res;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }
        return res;
    }

    @RequestMapping("/getPhoneValidCode")
    @ResponseBody
    @SignValid(paramType = GetPhoneValidCodeReq.class, hasToken = false)
    public GetPhoneValidCodeRes getPhoneValidCode(HttpServletRequest request, GetPhoneValidCodeReq req){
        GetPhoneValidCodeRes res = new GetPhoneValidCodeRes();
        try {
            if(req == null){
                throw new BusinessException();
            }
            if (StringUtils.isBlank(req.getPhoneNum())) {
                // 电话号码为空
                res.setResCode(SysResCodeCons.RES_CODE_PHONENUM_BLANK);
                res.setResMsg(SysResCodeCons.RES_CODE_PHONENUM_BLANK_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_PHONENUM_BLANK_MSG);
            }
            String phoneNum = req.getPhoneNum().trim();
            // 检测手机号格式
            if(!PhoneNumUtils.isPhoneNum(phoneNum)){
                // 手机号码格式不正确
                res.setResCode(SysResCodeCons.RES_CODE_PHONENUM_INVALID);
                res.setResMsg(SysResCodeCons.RES_CODE_PHONENUM_INVALID_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_PHONENUM_INVALID_MSG);
            }
            // 手机号登录,检查手机号是否绑定
            /*ComUser phoneUser = userService.getUserByMobile(phoneNum);
            if(phoneUser == null){
                // 手机号未注册
                res.setResCode(SysResCodeCons.RES_CODE_USER_ISNOT_SIGNUP);
                res.setResMsg(SysResCodeCons.RES_CODE_USER_ISNOT_SIGNUP_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_USER_ISNOT_SIGNUP_MSG);
            }*/
            // 查询请求频率
            Date exTime = SpringRedisUtil.get(ValidCodeUtils.getRdPhoneNumVcodeNextOpTimeName(phoneNum, ValidCodeTypeEnum.LOGIN_VCODE), Date.class);
            if(exTime != null && exTime.compareTo(new Date()) > 0){
                res.setResCode(SysResCodeCons.RES_CODE_VALIDCODE_REQ_OFTEN);
                res.setResMsg(SysResCodeCons.RES_CODE_VALIDCODE_REQ_OFTEN_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_VALIDCODE_REQ_OFTEN_MSG);
            }
            // 获取验证码
            String code = ValidCodeUtils.generareValidCode(null);
            Date exNewTime = new Date();
            exNewTime.setTime(exNewTime.getTime() + 60 * 1000);
            // 发送短信
            if(!MobileMessageUtil.getInitialization("B").verificationCode(phoneNum, code, SMSConstants.REGESTER_VALID_TIME)){
                // 验证码短信发送失败
                res.setResCode(SysResCodeCons.RES_CODE_VALIDCODE_SMS_FAIL);
                res.setResMsg(SysResCodeCons.RES_CODE_VALIDCODE_SMS_FAIL_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_VALIDCODE_SMS_FAIL_MSG);
            }
            // 保存验证码到redis
            SpringRedisUtil.saveEx(ValidCodeUtils.getRdPhoneNumVcodeNextOpTimeName(phoneNum, ValidCodeTypeEnum.LOGIN_VCODE),
                    exNewTime, 60);
            SpringRedisUtil.saveEx(ValidCodeUtils.getRedisPhoneNumValidCodeName(phoneNum, ValidCodeTypeEnum.LOGIN_VCODE), code,
                    Integer.valueOf(SMSConstants.REGESTER_VALID_TIME) * 60);
            // 返回结果
            res.setValidcode(code);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
            return res;
        }
        // 返回结果
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        return res;
    }


    private WxUserInfo createWxUserInfoByReq(LoginWxReq req) {
        WxUserInfo info = new WxUserInfo();

        info.setCity(req.getCity());
        info.setCountry(req.getCountry());
        info.setHeadimgurl(req.getHeadImgUrl());
        info.setNickname(req.getNickName());
        info.setOpenid(req.getOpenId());
        info.setPrivilege(req.getPrivilege());
        info.setProvince(req.getProvince());
        info.setSex(req.getSex() + "");
        info.setUnionid(req.getUnionid());

        return info;
    }

    private AccessTokenRes createAccesTokenResByReq(LoginWxReq req) {
        AccessTokenRes res = new AccessTokenRes();

        res.setUnionid(req.getUnionid());
        res.setOpenid(req.getOpenId());
        res.setAccess_token(req.getAccessToken());

        return res;
    }

}
