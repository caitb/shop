package com.masiis.shop.api.controller.user;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.api.bean.user.UserBlackReq;
import com.masiis.shop.api.bean.user.UserBlackRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.dao.po.ComUserBlacklist;
import com.masiis.shop.web.platform.service.user.UserBlackService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by cai_tb on 16/9/6.
 */
@Controller
@RequestMapping("/userBlack")
public class UserBlackController {

    private final static Log log = LogFactory.getLog(UserBlackController.class);

    @Resource
    private UserBlackService userBlackService;

    @RequestMapping("/isBlack")
    @ResponseBody
    @SignValid(paramType = UserBlackReq.class)
    public UserBlackRes isBlack(HttpServletRequest request, UserBlackReq userBlackReq){
        UserBlackRes userBlackRes = new UserBlackRes();

        try {
            ComUserBlacklist comUserBlacklist = userBlackService.loadByMobile(userBlackReq.getMobile());

            userBlackRes.setBlack(comUserBlacklist==null?false:true);
            userBlackRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            userBlackRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e){
            userBlackRes.setResCode(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
            userBlackRes.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR_MSG);

            log.error("手机号查询黑名单失败!"+e);
            e.printStackTrace();
        }

        return userBlackRes;
    }
}
