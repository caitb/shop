package com.masiis.shop.api.interceptors;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wangbingjian on 2016/4/23.
 */
public class RequestPreventThirdpartyFilter implements Filter {
    private static final Logger logger = Logger.getLogger(RequestPreventThirdpartyFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("init");
    }

    //防盗链：用于保护自己独有的消息，防止被其他的网站直接连接浏览，利于保护自己的信息
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        String referer=request.getHeader("shop-api");
        logger.info("referer:"+referer);
        logger.info(request.getServletPath());
        if ("shop-api".equals(referer)){
            chain.doFilter(req,res);
            return;
        }else {
            if (request.getServletPath().contains("index")){
                chain.doFilter(req,res);
                return;
            }else {
                response.sendRedirect(basePath+"index.jsp");
            }
        }
//        response.getWriter().write(data);
    }

    @Override
    public void destroy() {

    }
}
