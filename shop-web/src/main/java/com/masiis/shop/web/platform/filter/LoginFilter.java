package com.masiis.shop.web.platform.filter;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.platform.beans.wxauth.RedirectParam;
import com.masiis.shop.web.platform.constants.RedirectCons;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.utils.ApplicationContextUtil;
import com.masiis.shop.web.platform.utils.WXBeanUtils;
import com.masiis.shop.web.system.init.SysUriInit;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by lzh on 2016/2/23.
 */
public class LoginFilter implements Filter{
    private Logger log = Logger.getLogger(this.getClass());
    private String enviromentkey = PropertiesUtils.getStringValue(SysConstants.SYS_RUN_ENVIROMENT_KEY);
    private List<String> uris = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        if(uris == null){
            SysUriInit sys = (SysUriInit) ApplicationContextUtil.getBean("sysUriInit");
            uris = sys.getUriLists();
        }

        String uri = request.getRequestURI();

        if(StringUtils.isBlank(enviromentkey)
                || enviromentkey.equals("0")){
            // 开发阶段可以先跳过
            log.info("uri:" + uri);
            // 给开发组织一个默认的登录人
            UserService userService = (UserService) ApplicationContextUtil.getBean("userService");
            ComUser user = userService.getUserById(217l);
            request.getSession().setAttribute(SysConstants.SESSION_LOGIN_USER_NAME, user);

            chain.doFilter(request, response);
            return;
        }else if(enviromentkey.equals("1")) {
            // 过滤静态资源,以及一些放行的路径
            if (uri.startsWith(request.getContextPath() + "/static/")
                    || (request.getContextPath() + "/verify/actk").equals(uri)
                    || (request.getContextPath() + "/verify/bactk").equals(uri)
                    || (request.getContextPath() + "/verify/wxcheck").equals(uri)
                    || (request.getContextPath() + "/wxntfy/orderNtfy").equals(uri)) {
                // 放行
                chain.doFilter(request, response);
                return;
            }

            /*if(!checkUriIsValid(uri)){
                // 404处理
                log.info("访问的uri不存在,uri:" + uri);
                return;
            }*/

            log.info("uri:" + uri);

            ComUser login = (ComUser) session.getAttribute(SysConstants.SESSION_LOGIN_USER_NAME);
            if (login != null
                    && StringUtils.isNotBlank(login.getId() + "")
                    && StringUtils.isNotBlank(login.getWxUnionid())) {
                // 后面再斟酌是否需要进行验证有效性
                //
                chain.doFilter(request, response);
                return;
            }

            // cookie验证由controller来验证
            RedirectParam rp = new RedirectParam();
            rp.setCode(RedirectCons.WX_CHECK_CODE);
            rp.setSurl(request.getRequestURL().toString() + "?" + request.getQueryString());
            rp.setNonceStr(WXBeanUtils.createGenerateStr());
            rp.creatSign();

            String basepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
            String reUrl = basepath + "verify/wxcheck?"
                    + "state=" + URLEncoder.encode(JSONObject.toJSONString(rp), "UTF-8");
            response.sendRedirect(reUrl);
            //request.getRequestDispatcher(reUrl).forward(request, response);
        } else {

        }
    }

    private boolean checkUriIsValid(String uri) {
        for (String str:uris) {
            // 去除多斜杠的判断影响
            uri = uri.replaceAll("(/[/]+)", "/");
            if(uri.matches(str)){
                return true;
            }
        }

        return false;
    }

    @Override
    public void destroy() {

    }

    public static void main(String[] args) {
        String aa = "/ss//s//////d/ss";
        String cc = aa.replaceAll("(/[/]+)", "/");//("\\{.*\\}", "(.*)");
        String bb = "/";
        System.out.println(cc);
    }
}
