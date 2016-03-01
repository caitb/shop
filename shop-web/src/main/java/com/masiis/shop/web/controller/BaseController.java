package com.masiis.shop.web.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础controller,用来编写一些基础方法
 *
 * Created by lzh on 2016/2/27.
 */
public class BaseController {
    public final static String SUCCESS ="success";

    public final static String MSG ="msg";

    public final static String DATA ="data";

    /**
     * 获取IP地址
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    protected String createRedirectRes(String uri){
        return "redirect:" + uri;
    }

    protected String createForwardRes(String uri){
        return "forward:" + uri;
    }
}
