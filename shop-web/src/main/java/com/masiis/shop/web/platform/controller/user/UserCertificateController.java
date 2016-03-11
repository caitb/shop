package com.masiis.shop.web.platform.controller.user;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.channels.InterruptedByTimeoutException;

/**
 * UserCertificateController
 *
 * @author ZhaoLiang
 * @date 2016/3/10
 */
@Controller
@RequestMapping("/userCertificate")
public class UserCertificateController {

    @Resource
    private UserSkuService userSkuService;
    @Resource
    private UserService userService;
    @Resource
    private SkuService skuService;

    /**
     * @author ZhaoLiang
     * @date 2016/3/10 18:37
     */
    @RequestMapping("/setUserCertificate.shtml")
    public ModelAndView setUserCertificate(HttpServletRequest request,
                                           @RequestParam(value = "userSkuId", required = true) Integer userSkuId
    ) {
        ModelAndView modelAndView = new ModelAndView();
        String skuImg = PropertiesUtils.getStringValue("index_user_idCard_url");
        try {
            PfUserSku pfUserSku = userSkuService.getUserSkuById(userSkuId);
            ComUser comUser = userService.getUserById(pfUserSku.getUserId());
            ComSku comSku = skuService.getSkuById(pfUserSku.getSkuId());
            modelAndView.addObject("skuName", comSku.getName());
            modelAndView.addObject("name", comUser.getRealName());
            modelAndView.addObject("idCard", comUser.getIdCard());
            modelAndView.addObject("idCardFrontUrl", skuImg + comUser.getIdCardFrontUrl());
            modelAndView.addObject("idCardBackUrl", skuImg + comUser.getIdCardBackUrl());
            modelAndView.setViewName("platform/user/tijiaosq");
        } catch (Exception ex) {

        }
        return modelAndView;
    }
}
