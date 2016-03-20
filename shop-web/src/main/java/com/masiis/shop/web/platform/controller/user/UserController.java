package com.masiis.shop.web.platform.controller.user;

import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by cai_tb on 16/2/16.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/login", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) {
        return "";
    }

    private final static com.alibaba.druid.support.logging.Log log = LogFactory.getLog(UserController.class);

    /**
     * 判断是否绑定了手机号
     * @author hanzengzhi
     * @date 2016/3/16 15:51
     */
    @RequestMapping(value = "/isBindPhone.do")
    @ResponseBody
    public String isBindPhone(HttpServletRequest request,HttpServletResponse response){
        ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
        if (comUser != null&&!StringUtils.isEmpty(comUser.getMobile())){
            return "true";
        }else {
            return "false";
        }
    }

    /**
     * 用户绑定手机号
     * @author hanzengzhi
     * @date 2016/3/16 13:54
     */
    @RequestMapping(value = "/bindPhone.do")
    @ResponseBody
    public String bindPhone(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value = "phone",required = true)String phone){
        JSONObject obj = new JSONObject();
        System.out.println("---------------phone----------------"+phone);
        try {
            ComUser comUser =  userService.bindPhone(request,phone);
            System.out.println("-------------coumserPhone-------------------"+comUser.getMobile());
            if (comUser!=null&& !StringUtils.isEmpty(comUser.getMobile())){
                obj.put("isError",false);
                obj.put("isError","绑定成功");
            }else{
                obj.put("isError",true);
                obj.put("msg","comUsr为null");
            }
        }catch (Exception e){
            obj.put("isError",true);
            obj.put("msg",e.getMessage());
        }
        System.out.println("-------------返回前台的json数据--------------------"+obj.toJSONString());
        return obj.toJSONString();
    }

    @RequestMapping(value = "/bingPhoneStatusToPage.shtml")
    public String bingPhoneStatusToPage(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam(value = "status",required = true)String status,
                                        @RequestParam(value = "skipPage",required = true)String skipPage,
                                        @RequestParam(value = "path",required = true)String path,
                                        Model model){
        model.addAttribute("path",path);
        switch (skipPage){
            case "register":
                model.addAttribute("message","自动跳转到合伙人申请页面...");
                break;
            case "trial":
                model.addAttribute("message","自动跳转到支付页面...");
                break;
            default:
                break;
        }
        if (status.equals("success")){
            //绑定成功界面
            return "platform/user/tiaozhuan";
        }else{
            //绑定失败界面
            return "platform/user/bangdingshibai";
        }
    }
}

