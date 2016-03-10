package com.masiis.shop.web.platform.controller.base;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

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

    protected String getRequestBody(HttpServletRequest request) throws IOException {
        InputStream is = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String res = null;
        is = request.getInputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = is.read(buffer)) != -1){
            os.write(buffer, 0, len);
        }

        res = new String(os.toByteArray(), "UTF-8");

        if(is != null){
            is.close();
        }
        os.close();

        return res;
    }
}
