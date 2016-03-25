package com.masiis.shop.web.platform.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * MySimpleMappingExceptionResolver
 *
 * @author ZhaoLiang
 * @date 2016/3/25
 */
public class MySimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

    private Logger log = Logger.getLogger(this.getClass());

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Object handler,
                                              Exception ex) {
        ex.printStackTrace();
        log.error(ex.getMessage(), ex);
        String viewName = determineViewName(ex, request);
        if (viewName != null) {//JSP格式返回
            if (!(request.getHeader("accept").indexOf("application/json") > -1 || request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)) {//如果不是异步请求
                // Apply HTTP status code for error views, if specified.
                // Only apply it if we're processing a top-level request.
                Integer statusCode = determineStatusCode(request, viewName);
                if (statusCode != null) {
                    applyStatusCodeIfPossible(request, response, statusCode);
                    return getModelAndView(viewName, ex, request);
                }
            } else {//JSON格式返回
                try {
                    response.getWriter().write(ex.getMessage());
                } catch (IOException ioex) {
                    log.error(ex.getMessage(), ioex);
                }
                return new ModelAndView();
            }
        }
        return null;
    }
}
