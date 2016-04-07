package com.masiis.shop.web.mall.beans.sysbeans;

import net.sf.xsshtmlfilter.HTMLFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;

/**
 * html标签过滤器,防止部分XSS攻击
 *
 * @Date:2016/4/6
 * @auth:lzh
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {
    private HttpServletRequest request = null;

    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public String getParameter(String parameter){
        String value = super.getParameter(parameter);
        if(value != null){
            value = new HTMLFilter().filter(value);
        }
        return value;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if(value != null) {
            value = new HTMLFilter().filter(value);
        }
        return value;
    }

    @Override
    public Map<String, String[]> getParameterMap(){
        return super.getParameterMap();
    }

    public HttpServletRequest getOriRequest(){
        return this.request;
    }
}
