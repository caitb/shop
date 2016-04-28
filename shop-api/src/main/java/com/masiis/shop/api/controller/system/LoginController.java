package com.masiis.shop.api.controller.system;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.system.LoginByPhoneRes;
import com.masiis.shop.api.bean.system.LoginByWxRes;
import com.masiis.shop.api.bean.system.LoginWxReq;
import com.masiis.shop.api.bean.system.GetPhoneValidCodeRes;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.user.ComUserService;
import com.masiis.shop.api.utils.SpringRedisUtil;
import com.masiis.shop.api.utils.SysSignUtils;
import com.masiis.shop.api.utils.TokenUtils;
import com.masiis.shop.api.utils.ValidCodeUtils;
import com.masiis.shop.common.constant.SMSConstants;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.MD5Utils;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PhoneNumUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserKeybox;
import org.apache.commons.lang.StringUtils;
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
    private ComUserService userService;

    @RequestMapping("/loginByWx")
    @ResponseBody
    public LoginByWxRes loginByWx(HttpServletRequest request) throws IOException {
        LoginWxReq req = null;
        LoginByWxRes res = new LoginByWxRes();
        try{
            req = JSONObject.parseObject(getRequestBody(request), LoginWxReq.class);
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
            String sign = SysSignUtils.toSignString(req, null);
            if(StringUtils.isBlank(req.getSign())
                    || !req.getSign().equals(sign)){
                res.setResCode(SysResCodeCons.RES_CODE_REQ_SIGN_INVALID);
                res.setResMsg(SysResCodeCons.RES_CODE_REQ_SIGN_INVALID_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_REQ_SIGN_INVALID_MSG);
            }

            // 微信登录
            ComUser user = userService.signWithCreateUserByWX(req);
            ComUserKeybox keybox = userService.getKeyboxByUserid(user.getId());
            if(keybox == null){
                keybox = userService.createKeyboxByUser(user);
            }
            String token = TokenUtils.generateToken();
            String userKey = MD5Utils.encrypt(user.getId() + user.getWxUnionid() + System.currentTimeMillis());
            keybox.setAppToken(token);
            keybox.setComUserId(user.getId());
            keybox.setUserKey(userKey);
            if(keybox.getId() == null ){
                userService.insertKeybox(keybox);
            } else {
                userService.updateKeybox(keybox);
            }

            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            res.setToken(token);
            res.setUserKey(userKey);
            res.setExpire(30);
            res.setExpireUnit("天");
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
    public LoginByPhoneRes loginByPhone(String phoneNum, String validcode){
        LoginByPhoneRes res = new LoginByPhoneRes();
        try{
            if(StringUtils.isBlank(phoneNum)){
                // 电话号码为空
                res.setResCode(SysResCodeCons.RES_CODE_PHONENUM_BLANK);
                res.setResMsg(SysResCodeCons.RES_CODE_PHONENUM_BLANK_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_PHONENUM_BLANK_MSG);
            }
            if(StringUtils.isBlank(validcode)){
                // 检查验证码为空
                res.setResCode(SysResCodeCons.RES_CODE_VALIDCODE_IS_BLANK);
                res.setResMsg(SysResCodeCons.RES_CODE_VALIDCODE_IS_BLANK_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_VALIDCODE_IS_BLANK_MSG);
            }
            // 去除首尾空格
            phoneNum = phoneNum.trim();
            validcode = validcode.trim();
            // 获取redis存在的验证码
            String codeRd = SpringRedisUtil.get(ValidCodeUtils.getRedisPhoneNumValidCodeName(phoneNum), String.class);
            if(codeRd == null){
                // 验证码不存在或已过期
                res.setResCode(SysResCodeCons.RES_CODE_VALIDCODE_IS_EXPIRED);
                res.setResMsg(SysResCodeCons.RES_CODE_VALIDCODE_IS_EXPIRED_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_VALIDCODE_IS_EXPIRED_MSG);
            }
            if(!codeRd.equals(validcode)){
                // 验证码不匹配
                res.setResCode(SysResCodeCons.RES_CODE_VALIDCODE_IS_INVALID);
                res.setResMsg(SysResCodeCons.RES_CODE_VALIDCODE_IS_INVALID_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_VALIDCODE_IS_INVALID_MSG);
            }
            // 移除redis验证码
            SpringRedisUtil.saveEx(ValidCodeUtils.getRedisPhoneNumValidCodeName(phoneNum), "aa", 1);
            // 按照phoneNum来查询用户
            ComUser user = userService.getUserByMobile(phoneNum);
            if(user == null){
                // 该手机号未注册,暂时不能登录
                res.setResCode(SysResCodeCons.RES_CODE_USER_ISNOT_SIGNUP);
                res.setResMsg(SysResCodeCons.RES_CODE_USER_ISNOT_SIGNUP_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_USER_ISNOT_SIGNUP_MSG);
            }
            // 生成token
            String token = TokenUtils.generateToken();
            // 保存token
            user.setAppToken(token);
            user.setAppTokenExpire(DateUtil.getDateNextdays(30));
            userService.updateComUser(user);
            // 返回数据
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            res.setToken(token);
            res.setExpire(30);
            res.setExpireUnit("天");
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
    public GetPhoneValidCodeRes getPhoneValidCode(String phoneNum){
        GetPhoneValidCodeRes res = new GetPhoneValidCodeRes();
        try {
            if (StringUtils.isBlank(phoneNum)) {
                // 电话号码为空
                res.setResCode(SysResCodeCons.RES_CODE_PHONENUM_BLANK);
                res.setResMsg(SysResCodeCons.RES_CODE_PHONENUM_BLANK_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_PHONENUM_BLANK_MSG);
            }
            phoneNum = phoneNum.trim();
            // 检测手机号格式
            if(!PhoneNumUtils.isPhoneNum(phoneNum)){
                // 手机号码格式不正确
                res.setResCode(SysResCodeCons.RES_CODE_PHONENUM_INVALID);
                res.setResMsg(SysResCodeCons.RES_CODE_PHONENUM_INVALID_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_PHONENUM_INVALID_MSG);
            }
            // 查询请求频率
            Date exTime = SpringRedisUtil.get(ValidCodeUtils.getRdPhoneNumVcodeNextOpTimeName(phoneNum), Date.class);
            if(exTime != null && exTime.compareTo(new Date()) > 0){
                res.setResCode(SysResCodeCons.RES_CODE_VALIDCODE_REQ_OFTEN);
                res.setResMsg(SysResCodeCons.RES_CODE_VALIDCODE_REQ_OFTEN_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_VALIDCODE_REQ_OFTEN_MSG);
            }
            // 获取验证码
            String code = ValidCodeUtils.generareValidCode();
            Date exNewTime = new Date();
            exNewTime.setTime(exNewTime.getTime() + 60 * 1000);
            // 发送短信
            if(!MobileMessageUtil.VerificationCode(phoneNum, code, SMSConstants.REGESTER_VALID_TIME)){
                // 验证码短信发送失败
                res.setResCode(SysResCodeCons.RES_CODE_VALIDCODE_SMS_FAIL);
                res.setResMsg(SysResCodeCons.RES_CODE_VALIDCODE_SMS_FAIL_MSG);
                throw new BusinessException(SysResCodeCons.RES_CODE_VALIDCODE_SMS_FAIL_MSG);
            }
            // 保存验证码到redis
            SpringRedisUtil.saveEx(ValidCodeUtils.getRdPhoneNumVcodeNextOpTimeName(phoneNum),
                    exNewTime, 60);
            SpringRedisUtil.saveEx(ValidCodeUtils.getRedisPhoneNumValidCodeName(phoneNum), code,
                    Integer.valueOf(SMSConstants.REGESTER_VALID_TIME) * 60);
            // 返回结果
            res.setValidcode(code);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if(StringUtils.isBlank(res.getResCode())){
                res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
                res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
            }
        }
        // 返回结果
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        return res;
    }

}
