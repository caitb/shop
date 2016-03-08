package com.masiis.shop.web.platform.controller.system;

import com.masiis.shop.common.constant.SMSConstants;
import com.masiis.shop.common.util.CCPRestSmsSDK;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;

/**
 * Created by muchaofeng on 2016/3/5.
 */

@Controller
@RequestMapping("/binding")
public class BindingController {

   /**
    * 跳转到绑定界面
    * @author muchaofeng
    * @date 2016/3/7 13:52
    */

    @RequestMapping("bindingList")
   public String BindingList() throws Exception{
       return "platform/system/bangding";
   }
    /**
     * 发送验证码
     * @author muchaofeng
     * @date 2016/3/7 13:53
     */

    @RequestMapping("securityCode.do")
    @ResponseBody
    public String SecurityCode(HttpServletRequest request, HttpServletResponse response, String phone ) throws Exception{
        JSONObject result = new JSONObject();
        // 发送短信之间的间隔要有60秒,这个要校验
        String s = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            s += random.nextInt(10);
        }

        request.getSession().setAttribute(SMSConstants.CKCODE_RESET_PWD_NAME, s);
        request.getSession().setAttribute(SMSConstants.CKCODE_RESET_PWD_EXPIRA_TIME_NAME,
                new Date().getTime() + new Integer(SMSConstants.REGESTER_VALID_TIME) * 60 * 1000);

        String[] content = new String[2];
        content[0] = s;
        content[1] = SMSConstants.REGESTER_VALID_TIME;

        String[] smsRes = CCPRestSmsSDK.sendSMSWithResult(phone, SMSConstants.REGESTER_TEMPLETE_ID, content);
        if (!"0".equals(smsRes[0])) {
            result.put("code", "0");
            result.put("msg", smsRes[1]);
            return result.toString();
        }

        // 短信发送成功
        result.put("code", "1");
        result.put("msg", "短信发送成功,请注意查收!");
        return result.toString();
        //return  "";//^[0-9a-zA-Z]{6,16}$
    }

    /**
     * 校验验证码
     * @author muchaofeng
     * @date 2016/3/7 18:13
     */

    @RequestMapping("verificationCode.do")
    @ResponseBody
    public String VerificationCode(HttpServletRequest request, HttpServletResponse response, String verificationCode ) throws Exception{
        JSONObject result = new JSONObject();
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(SMSConstants.CKCODE_RESET_PWD_NAME);
        if(attribute.equals(verificationCode)) {
            result.put("msg","验证成功");
        }else{
            result.put("msg","验证失败");
        }
        return result.toString();
    }
}
