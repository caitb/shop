package com.masiis.shop.api.system.aspect;

import com.masiis.shop.api.constants.SignValid;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Date 2016/4/28
 * @Auther lzh
 */
@Aspect
@Component
public class ControllerSignatureAspect {

    @Before("within(com.masiis.shop.api.controller..*) && @annotation(rl)")
    public void signatureValid(JoinPoint jp, SignValid rl) {
        Class clazz = rl.paramType();
        rl.superClass().getSuperclass();
        Object[] parames = jp.getArgs();//获取目标方法体参数
        System.out.println("aspect......");
        for(Object obj:parames){
            if(obj instanceof HttpServletRequest){
                HttpServletRequest request = (HttpServletRequest) obj;
                String res = getRequestBody(request);
                System.out.println("aspect:" + res);
            }
        }
    }

    private String getRequestBody(HttpServletRequest request) {
        InputStream is = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String res = null;
        try {
            is = request.getInputStream();

            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            res = new String(os.toByteArray(), "UTF-8");
            if (is != null) {
                is.close();
            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }
}
