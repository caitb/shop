package com.masiis.shop.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lzh on 2016/2/24.
 */
public class CookieUtils {

    private static final int COOKIE_MAX_AGE = 7 * 24 * 3600;

    public static void removeCookie(HttpServletRequest request,
                                    HttpServletResponse response, String name) {
        if (null == name) {
            return;
        }
        Cookie cookie = getCookie(request, name);
        if(null != cookie){
            cookie.setPath("/");
            cookie.setValue("");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    /**
     * 根据Cookie名称得到Cookie对象，不存在该对象则返回Null
     *
     * @param request
     * @param name
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies || null == name || name.length() == 0) {
            return null;
        }
        Cookie cookie = null;
        for (Cookie c : cookies) {
            if (name.equals(c.getName())) {
                cookie = c;
                break;
            }
        }
        return cookie;
    }

    /**
     * 添加一条新的Cookie，默认7天过期时间(单位：秒)
     *
     * @param response
     * @param name
     * @param value
     * @param isHttpOnly
     */
    public static void setCookie(HttpServletResponse response, String name,
                                 String value, boolean isHttpOnly) {
        setCookie(response, name, value, COOKIE_MAX_AGE, isHttpOnly);
    }

    /**
     * 添加一条新的Cookie，可以指定过期时间(单位：秒)
     *
     * @param response
     * @param name
     * @param value
     * @param maxValue
     * @param isHttpOnly
     */
    public static void setCookie(HttpServletResponse response, String name,
                                 String value, int maxValue, boolean isHttpOnly) {
        if (null == name) {
            return;
        }
        if (null == value) {
            value = "";
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if(isHttpOnly) cookie.setHttpOnly(isHttpOnly);
        if (maxValue != 0) {
            cookie.setMaxAge(maxValue);
        } else {
            cookie.setMaxAge(COOKIE_MAX_AGE);
        }
        response.addCookie(cookie);
    }
}
