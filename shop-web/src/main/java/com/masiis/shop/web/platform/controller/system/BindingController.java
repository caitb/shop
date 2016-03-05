package com.masiis.shop.web.platform.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by muchaofeng on 2016/3/5.
 */

@Controller
@RequestMapping("/binding")
public class BindingController {
    @RequestMapping("bindingPhone")
   public String BindingPhone() throws Exception{
       return "platform/system/bangding";
   }

}
