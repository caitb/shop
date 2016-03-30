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
        ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
        if (comUser != null && !StringUtils.isEmpty(comUser.getMobile())) {
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
            ComUser comUser = userService.bindPhone(request, phone);
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
            case "register":
                model.addAttribute("message", "自动跳转到合伙人申请页面...");
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

        ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
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

    @ResponseBody
    @RequestMapping("userVerified/save.do")
    public String userVerifiedAdd(HttpServletRequest request,
                                  @RequestParam(value = "name", required = true) String name,
                                  @RequestParam(value = "idCard", required = true) String idCard,
                                  @RequestParam(value = "idCardFrontUrl", required = true) String idCardFrontUrl,
                                  @RequestParam(value = "idCardBackUrl", required = true) String idCardBackUrl
    ) {
        JSONObject object = new JSONObject();
        try {
            ComUser comUser = getComUser(request);
            if (comUser == null) {
                throw new BusinessException("用户信息有误请重新登陆");
            } else if (comUser.getIsVerified() == 1) {
                throw new BusinessException("用户已经实名认证");
            }
            if (org.apache.commons.lang.StringUtils.isBlank(name)) {
                throw new BusinessException("姓名不能为空");
            }
            if (org.apache.commons.lang.StringUtils.isBlank(idCard)) {
                throw new BusinessException("身份证不能为空");
            }
            if (org.apache.commons.lang.StringUtils.isBlank(idCardFrontUrl)) {
                throw new BusinessException("身份证照片不能为空");
            }
            if (org.apache.commons.lang.StringUtils.isBlank(idCardBackUrl)) {
                throw new BusinessException("身份证照片不能为空");
            }
            String rootPath = request.getServletContext().getRealPath("/");
            String webappPath = rootPath.substring(0, rootPath.lastIndexOf(File.separator));
            String frontFillFullName = uploadFile(webappPath + "/static/upload/user/idCard/" + idCardFrontUrl);
            String backFillFullName = uploadFile(webappPath + "/static/upload/user/idCard/" + idCardBackUrl);
            //修改用户数据
            comUser.setRealName(name);
            comUser.setIdCard(idCard);
            comUser.setIdCardFrontUrl(frontFillFullName);
            comUser.setIdCardBackUrl(backFillFullName);
            comUser.setIsVerified(1);
            userService.updateComUser(comUser);
            object.put("isError", false);
        } catch (Exception ex) {
            if (StringUtils.isNotBlank(ex.getMessage())) {
                throw new BusinessException(ex.getMessage(), ex);
            } else {
                throw new BusinessException("网络错误", ex);
            }
        }
        return object.toJSONString();
    }

    /**
     * 上传文件
     *
     * @author ZhaoLiang
     * @date 2016/3/11 15:12
     */
    private String uploadFile(String filePath) throws FileNotFoundException {
        File frontFile = new File(filePath);
        OSSObjectUtils.uploadFile("mmshop", frontFile, "static/user/idCard/");
        return frontFile.getName();
    }
}

