package com.masiis.shop.web.platform.controller.system;

import com.masiis.shop.common.constant.SMSConstants;
import com.masiis.shop.common.util.CCPRestSmsSDK;
import com.masiis.shop.common.util.KeysUtil;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.service.system.IndexShowService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.utils.MobileMessageUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Random;

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
        result.put("msg",MobileMessageUtil.sendIdentifyingCode(phone));
        return  result.toString();
//        // 发送短信之间的间隔要有60秒,这个要校验
//        String s = "";
//        Random random = new Random();
//        for (int i = 0; i < 4; i++) {
//            s += random.nextInt(10);
//        }
//        request.getSession().setAttribute(SMSConstants.CKCODE_RESET_PWD_NAME, s);
//        request.getSession().setAttribute(SMSConstants.CKCODE_RESET_PWD_EXPIRA_TIME_NAME,
//                new Date().getTime() + new Integer(SMSConstants.REGESTER_VALID_TIME) * 60 * 1000);
//
//        String[] content = new String[2];
//        content[0] = s;
//        content[1] = SMSConstants.REGESTER_VALID_TIME;
//
//        String[] smsRes = CCPRestSmsSDK.sendSMSWithResult(phone, SMSConstants.REGESTER_TEMPLETE_ID, content);
//        if (!"0".equals(smsRes[0])) {
//            result.put("code", "0");
//            result.put("msg", smsRes[1]);
//            return result.toString();
//        }
//
//        // 短信发送成功
//        result.put("code", "1");
//        result.put("msg", "短信发送成功,请注意查收!");
//        return result.toString();
//        //return  "";//^[0-9a-zA-Z]{6,16}$
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
//        HttpSession session = request.getSession();
//        Object attribute = session.getAttribute(SMSConstants.CKCODE_RESET_PWD_NAME);
        String identifyingCode = MobileMessageUtil.getIdentifyingCode(phone);
        if(identifyingCode.equals(verificationCode)) {
            return "true";
            //result.put("msg","验证成功");
        }else{
            return "false";
            //result.put("msg","验证失败");
        }
    }
    /**
     * 绑定用户信息
     * @author muchaofeng
     * @date 2016/3/8 11:28
     */
    @RequestMapping("bindingComUse.html")
    public ModelAndView BindingComUse(HttpServletRequest request,String phone,String password)throws  Exception{
        HttpSession session = request.getSession();
        //获取用户信息
//        ComUser comUser1=new ComUser();
//        comUser1.setRealName("王平");
//        session.setAttribute("comUser",comUser1);
        ComUser comUser =(ComUser)session.getAttribute("comUser");
        comUser.setMobile(phone);
        //comUser.setPassword(KeysUtil.md5Encrypt(password));
        userService.updateComUser(comUser);
        session.setAttribute("comUser",comUser);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("platform/system/bangdingchenggong");
        return mav;
    }
}

