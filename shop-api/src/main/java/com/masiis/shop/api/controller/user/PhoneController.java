package com.masiis.shop.api.controller.user;

import com.masiis.shop.api.bean.base.BaseReq;
import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.bean.system.GetPhoneValidCodeReq;
import com.masiis.shop.api.bean.system.GetPhoneValidCodeRes;
import com.masiis.shop.api.bean.user.BindPhoneReq;
import com.masiis.shop.api.bean.user.BindPhoneRes;
import com.masiis.shop.api.bean.user.CheckPUserPhoneReq;
import com.masiis.shop.api.bean.user.CheckPUserPhoneRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.product.SkuService;
import com.masiis.shop.api.service.user.ComUserService;
import com.masiis.shop.api.service.user.UserSkuService;
import com.masiis.shop.api.utils.SpringRedisUtil;
import com.masiis.shop.api.utils.ValidCodeUtils;
import com.masiis.shop.common.constant.SMSConstants;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PhoneNumUtils;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserSku;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Date 2016/5/24
 * @Auther lzh
 */
@Controller
@RequestMapping("/phone")
public class PhoneController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SkuService skuService;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private ComUserService userService;

    @RequestMapping("/parentcheck")
    @ResponseBody
    @SignValid(paramType = CheckPUserPhoneReq.class)
    public CheckPUserPhoneRes checkPPhone(HttpServletRequest request, CheckPUserPhoneReq req, ComUser user){
        CheckPUserPhoneRes res = new CheckPUserPhoneRes();
        Integer skuId = req.getSkuId();
        String phoneNum = req.getPhoneNum();
        if(StringUtils.isBlank(phoneNum)){
            // 手机号为空
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_NULL);
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_NULL_MSG);
            log.error(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_NULL_MSG);
            return res;
        }
        if(!PhoneNumUtils.isPhoneNum(phoneNum)){
            // 手机号格式不正确
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_INVALID);
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_INVALID_MSG);
            log.error(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_INVALID_MSG);
            return res;
        }
        if(skuId == null || skuId == 0){
            // skuId为空
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL);
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL_MSG);
            log.error(SysResCodeCons.RES_CODE_UPAPPLY_SKU_NULL_MSG);
            return res;
        }
        ComSku sku = skuService.getSkuById(skuId);
        if(sku == null){
            // skuId不存在
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_SKU_INVALID);
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_SKU_INVALID_MSG);
            log.info(SysResCodeCons.RES_CODE_UPAPPLY_SKU_INVALID_MSG);
            return res;
        }
        ComUser pUser = userService.getUserByMobile(phoneNum);
        if(pUser == null){
            // 手机号尚未注册
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_NOTKNOWN);
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_NOTKNOWN_MSG);
            log.info(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUM_NOTKNOWN_MSG);
            return res;
        }
        PfUserSku pPfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(pUser.getId(), skuId);
        if(pPfUserSku == null){
            // 手机号用户尚未代理该产品
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUMUSER_NOTAGENT);
            res.setResCode(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUMUSER_NOTAGENT_MSG);
            log.info(SysResCodeCons.RES_CODE_UPAPPLY_PHONENUMUSER_NOTAGENT_MSG);
            return res;
        }

        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        res.setPhoneIsOk(1);
        return null;
    }


    @RequestMapping("/bind")
    @ResponseBody
    @SignValid(paramType = BindPhoneReq.class)
    public BindPhoneRes bindPhone(HttpServletRequest request, BindPhoneReq req, ComUser user){

        return null;
    }

    @RequestMapping("/vcode")
    @ResponseBody
    @SignValid(paramType = GetPhoneValidCodeReq.class)
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
            if(!MobileMessageUtil.getInitialization("B").verificationCode(phoneNum, code, SMSConstants.REGESTER_VALID_TIME)){
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
            return res;
        }
        // 返回结果
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        return res;
    }

}
