package com.masiis.shop.api.controller.system;

import com.masiis.shop.api.bean.system.RegisterValidCodeRes;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.utils.SpringRedisUtil;
import com.masiis.shop.api.utils.ValidCodeUtils;
import com.masiis.shop.common.constant.SMSConstants;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PhoneNumUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @date 2016/4/25
 * @author lzh
 */
@Controller
@RequestMapping("/sys")
public class LoginController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @RequestMapping("/loginByPhone")
    public void loginByPhone(String phoneNum, String validcode){

    }

    @RequestMapping("/getPhoneValidCode")
    @ResponseBody
    public RegisterValidCodeRes getPhoneValidCode(String phoneNum){
        RegisterValidCodeRes res = new RegisterValidCodeRes();
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
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return res;
    }

}
