package com.masiis.shop.web.platform.filter;

import com.masiis.shop.web.platform.beans.sysbeans.XSSRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Date:2016/4/6
 * @auth:lzh
 */
public class HtmlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        chain.doFilter(new XSSRequestWrapper(request), servletResponse);
    }

    @Override
    public void destroy() {

    }
}
