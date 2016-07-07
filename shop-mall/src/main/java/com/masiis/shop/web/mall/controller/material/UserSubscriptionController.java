package com.masiis.shop.web.mall.controller.material;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.material.service.UserSubscriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by jiajinghao on 2016/7/7.
 * 用户订阅素材controller
 */
@Controller
@RequestMapping("/subscribeC")
public class UserSubscriptionController extends BaseController {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private UserSubscriptionService userSubscriptionService;

    @RequestMapping(value = "/do")
    @ResponseBody
    public String updateSubscriptionStatus(HttpServletRequest request,
                                           @RequestParam(value = "status",required = true) Integer status,
                                           @RequestParam(value = "materialId",required = true) Integer materialId
                                           ){

        JSONObject object = new JSONObject();
        try {
            ComUser comUser = getComUser(request);
            userSubscriptionService.updateSubscriptionStatus(status,materialId,comUser.getId());
            object.put("isError",false);
        }catch (Exception e){
            object.put("isError",true);
            log.info(e.getMessage());
        }
        return object.toJSONString();
    }
}
