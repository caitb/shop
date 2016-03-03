package com.masiis.shop.admin.interceptors;


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 资源访问拦截器
 * Created by cai_tb on 16/2/23.
 */
public class AccessInterceptor extends HandlerInterceptorAdapter {
//
//    private static final String[] IGNORE_URI = {"/login.shtml", "/login"};
//
//    @Resource
//    private MenuService menuService;
//    @Resource
//    private UserMenuService userMenuService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        boolean flag = false;
//        String  url  = request.getRequestURL().toString();
//
//        for (String s : IGNORE_URI) {
//            if (url.contains(s)) {
//                flag = true;
//                break;
//            }
//        }
//
//        if(!flag){
//            SysUser sysUser = (SysUser)request.getSession().getAttribute("user");
//            if(sysUser == null){
//                request.getRequestDispatcher("/user/login.shtml").forward(request, response);
//                flag = false;
//            }else{
//                SysUserMenuExample sysUserMenuExample = new SysUserMenuExample();
//                sysUserMenuExample
//                        .createCriteria()
//                        .andUserIdEqualTo(sysUser.getId());
//                List<SysUserMenu> sysUserMenus = userMenuService.findByCondition(sysUserMenuExample);
//                for(SysUserMenu sysUserMenu : sysUserMenus){
//                    SysMenu sysMenu = menuService.findById(sysUserMenu.getMenuId());
//                    if(sysMenu.getUrl() != null && (url.contains("index") || url.contains(sysMenu.getUrl())) ){
//                        flag = true;
//                        break;
//                    }
//                }
//            }
//        }
//        return flag;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        super.postHandle(request, response, handler, modelAndView);
//    }
}
