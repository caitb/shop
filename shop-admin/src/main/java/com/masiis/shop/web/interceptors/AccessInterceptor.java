package com.masiis.shop.web.interceptors;


import com.masiis.shop.dao.menu.BMenu;
import com.masiis.shop.dao.menu.BMenuExample;
import com.masiis.shop.dao.user.User;
import com.masiis.shop.service.UserMenu.UserMenuService;
import com.masiis.shop.service.menu.MenuService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 资源访问拦截器
 * Created by cai_tb on 16/2/23.
 */
public class AccessInterceptor extends HandlerInterceptorAdapter {

    private static final String[] IGNORE_URI = {"/login.shtml", "/login"};

    @Resource
    private MenuService menuService;
    @Resource
    private UserMenuService userMenuService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        String  url  = request.getRequestURL().toString();

        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
                flag = true;
                break;
            }
        }

        if(!flag){
            User user = (User)request.getSession().getAttribute("user");
            if(user == null){
                request.getRequestDispatcher("/user/login.shtml").forward(request, response);
                flag = false;
            }else{
                List<Long> menuIds = userMenuService.findMenuIdsByUserId(user.getId());
                for(Long menuId : menuIds){
                    BMenu bMenu = menuService.findById(menuId);
                    if(bMenu.getUrl() != null && (url.contains("index") || url.contains(bMenu.getUrl())) ){
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
