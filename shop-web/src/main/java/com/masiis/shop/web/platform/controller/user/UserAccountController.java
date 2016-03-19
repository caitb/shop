package com.masiis.shop.web.platform.controller.user;

import com.masiis.shop.common.util.AESUtils;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.constants.WxConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.user.ComUserAccountService;
import com.masiis.shop.web.platform.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lzh on 2016/3/18.
 */
@Controller
@RequestMapping("/account")
public class UserAccountController extends BaseController{
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private UserService userService;
    @Resource
    private ComUserAccountService accountService;

    @RequestMapping("/home")
    public String accountHome(HttpServletRequest request, Model model){
        ComUser user = getComUser(request);
        if(user == null){
            user = userService.getUserByOpenid("oUIwkwgLzn8CKMDrvbCSE3T-u5fs");
        }
        //String kid = aesEncryptBySalt(String.valueOf(user.getId()), SysConstants.COOKIE_AES_KEY, SysConstants.COOKIE_KEY_SALT);
        ComUserAccount account = accountService.findAccountByUserid(user.getId());



        model.addAttribute("account", account);
        return "platform/user/account";
    }
}