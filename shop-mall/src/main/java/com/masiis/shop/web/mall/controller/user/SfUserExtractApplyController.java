package com.masiis.shop.web.mall.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComWxUser;
import com.masiis.shop.dao.po.SfUserAccount;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.user.SfUserAccountService;
import com.masiis.shop.web.mall.service.user.SfUserExtractApplyService;
import com.masiis.shop.web.mall.service.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * Created by wangbingjian on 2016/4/10.
 */
@Controller
@RequestMapping(value = "/withdraw")
public class SfUserExtractApplyController extends BaseController{

    private final Logger log = Logger.getLogger(SfUserAccountController.class);

    @Autowired
    private SfUserAccountService userAccountService;
    @Autowired
    private UserService userService;
    @Autowired
    private SfUserExtractApplyService sfUserExtractApplyService;

    /**
     * 用户提现申请
     * @param request
     * @auth:wbj
     * @return
     */
    @RequestMapping(value = "/withdrawRequest.shtml")
    public ModelAndView withdrawRequest(HttpServletRequest request) throws Exception{
        log.info("进入用户提现申请");
        ComUser comUser = getComUser(request);
        if (comUser == null){
            throw new BusinessException("用户没有登录");
        }
        ModelAndView mv = new ModelAndView();
        Long userId = comUser.getId();
        log.info("userId="+userId);
        SfUserAccount userAccount = userAccountService.findAccountByUserId(userId);
        ComWxUser comWxUser = userService.findComWxUserByUserId(userId);
        mv.addObject("userAccount",userAccount);
        mv.addObject("comWxUser",comWxUser);
        mv.setViewName("mall/user/sf_withdrawRequest");
        return mv;
    }

    /**
     *  分销用户确认提现
     * @param inputAccount  页面输入金额
     * @param userId        用户id
     * @param request
     * @Auth:wbj
     * @return
     */
    @RequestMapping(value = "confirmWithdraw.do")
    @ResponseBody
    public String confirmWithdraw(@RequestParam(value = "inputAccount",required = true) String inputAccount,
                                  @RequestParam(value = "userId",required = true) Long userId,
                                  HttpServletRequest request){

        ComUser user = getComUser(request);
        log.info("ajax 分销用户确认提现");
        log.info("用户申请提现金额：" + inputAccount);
        JSONObject jsonobject = new JSONObject();
        if (user == null){
            jsonobject.put("isTrue","false");
            jsonobject.put("message","用户未登录");
            log.info(jsonobject.toJSONString());
            return jsonobject.toJSONString();
        }
        if (user.getId() != userId){
            jsonobject.put("isTrue","false");
            jsonobject.put("message","用户信息错误");
            log.info(jsonobject.toJSONString());
            return jsonobject.toJSONString();
        }
        if(StringUtils.isBlank(inputAccount)){
            jsonobject.put("isTrue","false");
            jsonobject.put("message","提现金额未填写!");
            log.info(jsonobject.toJSONString());
            return jsonobject.toJSONString();
        }
        if(!SysBeanUtils.isNumeric(inputAccount)){
            jsonobject.put("isTrue","false");
            jsonobject.put("message","金额不是数字类型的值!");
            log.info(jsonobject.toJSONString());
            return jsonobject.toJSONString();
        }
        BigDecimal b_inputAccount = new BigDecimal(inputAccount);
        //根据userId查询用户账户信息
        SfUserAccount userAccount = userAccountService.findAccountByUserId(userId);
        if (userAccount == null || b_inputAccount.compareTo(userAccount.getExtractableFee()) == 1 || b_inputAccount.compareTo(new BigDecimal(0)) <= 0){
            jsonobject.put("isTrue","false");
            jsonobject.put("message","可提现余额错误");
            log.info(jsonobject.toJSONString());
            return jsonobject.toJSONString();
        }

        try {
            //提现申请处理
            sfUserExtractApplyService.applyExtract(b_inputAccount, user);
        }catch (Exception e){
            e.printStackTrace();
            jsonobject.put("isTrue","false");
            jsonobject.put("message","用户提现处理失败");
        }
        jsonobject.put("isTrue","true");
        jsonobject.put("resUrl","withdraw/requestSuccess");
        log.info(jsonobject.toJSONString());
        return jsonobject.toJSONString();
    }
}
