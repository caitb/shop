package com.masiis.shop.web.mall.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Date:2016/4/7
 * @auth:lzh
 */
@Controller
@RequestMapping(value = "/sfuser")
public class SfUserController extends BaseController {

    private final Logger logger = Logger.getLogger(SfUserController.class);

    @Autowired
    private UserService userService;

    /**
     * 校验用户是否已经绑定
     * @param userId       //用户id
     * @param request
     * @auth:wangbingjian
     * @return
     */
    @RequestMapping(value = "/checkBinding.do")
    public String checkBinding(@RequestParam(value = "userId",required = true) Long userId,
                               HttpServletRequest request){

        ComUser user = getComUser(request);
        logger.info("校验用户是否已经绑定");
        JSONObject jsonobject = new JSONObject();

        if (user == null){
            jsonobject.put("isTrue","false");
            jsonobject.put("message","用户未登录");
            logger.info(jsonobject.toJSONString());
            return jsonobject.toJSONString();
        }
        if (user.getId() != userId){
            jsonobject.put("isTrue","false");
            jsonobject.put("message","用户信息错误");
            logger.info(jsonobject.toJSONString());
            return jsonobject.toJSONString();
        }
        //通过userId查询comuser
        user = userService.getUserById(userId);
        if (user == null){
            jsonobject.put("isTrue","false");
            jsonobject.put("message","用户不存在");
            logger.info(jsonobject.toJSONString());
            return jsonobject.toJSONString();
        }
        int isBinding = user.getIsBinding();
        if (isBinding == 0){
            jsonobject.put("isTrue","false");
            jsonobject.put("message","用户未绑定");
        }else {
            jsonobject.put("isTrue","true");
        }
        logger.info(jsonobject.toJSONString());
        return jsonobject.toJSONString();
    }
}
