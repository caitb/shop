package com.masiis.shop.web.platform.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by lzh on 2016/2/23.
 */
public class LoginFilter implements Filter{
    private Logger log = Logger.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        String uri = request.getRequestURI();

        // 开发阶段可以先跳过
        chain.doFilter(request, response);
        return;

        // 过滤静态资源,以及一些放行的路径
        /*if(uri.startsWith(request.getContextPath() + "/static/")
                ||(request.getContextPath() + "/verify/actk").equals(uri)
                || (request.getContextPath() + "/verify/wxcheck").equals(uri)){
            // 放行
            chain.doFilter(request, response);
            return;
        }
        String login = (String) session.getAttribute("login");
        System.out.println("code:" + request.getParameter("code"));
        if(StringUtils.isNotBlank(login)){
            // 后面再斟酌是否需要进行验证有效性
            //
            chain.doFilter(request, response);
            return;
        }

        // cookie验证由controller来验证
        RedirectParam rp = new RedirectParam();
        rp.setCode(RedirectCons.WX_CHECK_CODE);
        rp.setSurl(request.getRequestURL().toString());
        rp.creatSign();

        String basepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
        String reUrl = request.getContextPath() + "/verify/wxcheck?"
                + "state=" + URLEncoder.encode(JSONObject.toJSONString(rp), "UTF-8");

        request.getRequestDispatcher(reUrl).forward(request, response);*/
    }

    @Override
    public void destroy() {

    }
}
