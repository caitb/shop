package com.masiis.shop.web.filter;

import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by lzh on 2016/2/23.
 */
public class LoginFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        String uri = request.getRequestURI();
        System.out.println("uri:" + uri);
        if(uri.equals("/shop-web/verify/actk")){
            // 放行
            chain.doFilter(request, response);
            return;
        }
        String login = (String) session.getAttribute("login");
        System.out.println("code:" + request.getParameter("code"));
        if(StringUtils.isNotBlank(login)){
            // 后面再斟酌是否需要进行验证有效性
            chain.doFilter(request, response);
            return;
        }

        // 未登录
        response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7c874d5a102dccef&redirect_uri=http%3A%2F%2Fweixin.masiis.com%2Fshop-web%2Fverify%2Factk&response_type=code&scope=snsapi_userinfo&state=" + URLEncoder.encode(request.getRequestURL().toString(), "UTF-8") + "#wechat_redirect");
        //response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7c874d5a102dccef&redirect_uri=http%3A%2F%2Fweixin.masiis.com%2Fshop-web%2Fverify%2Factk&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
    }

    @Override
    public void destroy() {

    }
}
