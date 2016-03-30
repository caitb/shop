package com.masiis.shop.admin.controller.user;

import com.masiis.shop.admin.service.user.ComUserService;
import com.masiis.shop.dao.po.ComUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 会员管理
 * Created by cai_tb on 16/3/30.
 */
@Controller
@RequestMapping("/comuser")
public class ComUserController {

    @Resource
    private ComUserService comUserService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "user/comuser/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber,
                       Integer pageSize,
                       String sortOrder,
                       ComUser comUser){

        Map<String, Object> pageMap = comUserService.listByCondition(pageNumber, pageSize, comUser);

        return pageMap;
    }
}
