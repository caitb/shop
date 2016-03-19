package com.masiis.shop.web.platform.controller.user;

import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by lzh on 2016/3/19.
 */
@Controller
@RequestMapping("/extractapply")
public class UserExtractApplyController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private UserService userService;

    @RequestMapping("/toapply")
    public String toApply(){

        return "platform/user/extract_pply";
    }
}
