package com.masiis.shop.api.interceptors;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wangbingjian on 2016/4/23.
 */
public class RequestPreventThirdpartyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
    }

    //防盗链：用于保护自己独有的消息，防止被其他的网站直接连接浏览，利于保护自己的信息
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        String referer=request.getHeader("shop-api");
        if (!request.getServletPath().contains("index")) {
            if(referer==null||!referer.startsWith(basePath))
            {
                System.out.println(basePath+"index.jsp");
                response.sendRedirect(basePath+"index.jsp");
                return;
            }
        }
        String data="haha";
        response.getWriter().write(data);
    }

    @Override
    public void destroy() {

    }
}
