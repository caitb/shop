package com.masiis.shop.web.platform.controller.system;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.controller.base.BaseController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Date 2016/5/17
 * @Auther lzh
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @RequestMapping("/pmnshop")
    public String promotionShop(HttpServletRequest request){
        ComUser user = getComUser(request);
        if(user.getIsAgent().intValue() == 1){
            // 是合伙人,去到店铺页面
        } else {
            // 非合伙人,提示页面
        }

        return null;
    }

    @RequestMapping("/pmnpartner")
    public String promotionPartner(){


        return null;
    }

}
