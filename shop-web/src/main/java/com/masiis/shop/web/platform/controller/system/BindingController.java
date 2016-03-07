package com.masiis.shop.web.platform.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("securityCode")
    public String SecurityCode(String phone ) throws Exception{

        return  "";//^[0-9a-zA-Z]{6,16}$
    }
}
