package com.masiis.shop.web.mall.controller.system;

import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.web.mall.service.user.UserService;
import com.masiis.shop.web.mall.utils.MobileVerificationUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by muchaofeng on 2016/3/5.
 */

@Controller
@RequestMapping("/binding")
public class BindingController {

    @Resource
    private UserService userService;

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
        result.put("msg", MobileVerificationUtil.sendIdentifyingCode(phone));
        return  result.toString();
    }

    /**
     * 校验验证码
     * @author muchaofeng
     * @date 2016/3/7 18:13
     */

    @RequestMapping("verificationCode.do")
    @ResponseBody
    public String VerificationCode(HttpServletRequest request, HttpServletResponse response, String phone ,String verificationCode) throws Exception{
        JSONObject result = new JSONObject();
        String identifyingCode = MobileVerificationUtil.getIdentifyingCode(phone);
        if(identifyingCode.equals(verificationCode)) {
            return "true";
        }else{
            return "false";
        }
    }
}

