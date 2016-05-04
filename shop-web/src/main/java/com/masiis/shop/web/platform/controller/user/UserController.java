package com.masiis.shop.web.platform.controller.user;

import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;


/**
 * Created by cai_tb on 16/2/16.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/login", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "";
    }

    private final static com.alibaba.druid.support.logging.Log log = LogFactory.getLog(UserController.class);

    /**
     * 判断用户是否绑定了手机号
     *
     * @author hanzengzhi
     * @date 2016/3/16 15:51
     */
    @RequestMapping(value = "/isBindPhone.do")
    @ResponseBody
    public String isBindPhone(HttpServletRequest request, HttpServletResponse response) {
        ComUser comUser = getComUser(request);
        if (comUser != null && comUser.getIsBinding() == 1) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * 判断手机号是否已经被其他人绑定使用过
     *
     * @author hanzengzhi
     * @date 2016/3/24 10:43
     */
    @RequestMapping(value = "/isBindedPhone.do")
    @ResponseBody
    public String isBindedPhone(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "phone", required = true) String phone) {
        JSONObject obj = new JSONObject();
        try {
            ComUser comUser = userService.getUserByMobile(phone);
            if (comUser != null) {
                obj.put("isError", true);
                obj.put("msg", "手机号已经被绑定请更换手机号");
                return obj.toJSONString();
            } else {
                obj.put("isError", false);
                return obj.toJSONString();
            }
        } catch (Exception e) {
            obj.put("isError", true);
            obj.put("msg", "手机号查询是否被绑定失败:" + e.getMessage());
            return obj.toJSONString();
        }
    }

    /**
     * 用户绑定手机号
     *
     * @author hanzengzhi
     * @date 2016/3/16 13:54
     */
    @RequestMapping(value = "/bindPhone.do")
    @ResponseBody
    public String bindPhone(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value = "phone", required = true) String phone) {
        JSONObject obj = new JSONObject();
        try {
            ComUser comUser = userService.bindPhone(getComUser(request), phone);
            if (comUser != null && !StringUtils.isEmpty(comUser.getMobile())) {
                obj.put("isError", false);
            } else {
                obj.put("isError", true);
                obj.put("msg", "comUsr为null");
            }
        } catch (Exception e) {
            obj.put("isError", true);
            obj.put("msg", e.getMessage());
        }
        return obj.toJSONString();
    }

    @RequestMapping(value = "/bingPhoneStatusToPage.shtml")
    public String bingPhoneStatusToPage(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam(value = "status", required = true) String status,
                                        @RequestParam(value = "skipPage", required = true) String skipPage,
                                        @RequestParam(value = "path", required = true) String path,
                                        Model model) {
        model.addAttribute("path", path);
        switch (skipPage) {
            case "applyPartner":
                model.addAttribute("message", "自动跳转到合伙人引导页面...");
                break;
            case "trial":
                model.addAttribute("message", "自动跳转到支付页面...");
                break;
            default:
                break;
        }
        if (status.equals("success")) {
            //绑定成功界面
            return "platform/user/tiaozhuan";
        } else {
            //绑定失败界面
            return "platform/user/bangdingshibai";
        }
    }

    /**
     * 获得个人信息
     *
     * @author hanzengzhi
     * @date 2016/3/21 10:23
     */
    @RequestMapping(value = "getPersonalInfo.do")
    public String getPersonalInfo(HttpServletRequest request, HttpServletResponse response,
                                  Model model) {

        ComUser comUser = getComUser(request);
        model.addAttribute("comUser", comUser);
        return "platform/user/gerenxinxi";
    }


    @RequestMapping(value = "userVerified.shtml")
    public ModelAndView userVerified(HttpServletRequest request,
                                     @RequestParam(value = "goToURL", required = true) String goToURL) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("platform/user/shimingrenzheng");
        modelAndView.addObject("goToURL", goToURL);
        return modelAndView;
    }


}

