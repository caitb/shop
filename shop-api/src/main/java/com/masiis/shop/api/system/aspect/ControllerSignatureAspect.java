package com.masiis.shop.api.system.aspect;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.base.BaseReq;
import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.bean.system.LoginByPhoneReq;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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
    private Logger log = Logger.getLogger(this.getClass());

    @Around("within(com.masiis.shop.api.controller..*) && @annotation(rl)")
    public Object signatureValid(ProceedingJoinPoint point, SignValid rl) throws Throwable {
        Class clazz = rl.paramType();

        Object[] parames = point.getArgs();//获取目标方法体参数
        System.out.println("......");
        Object req = null;
        try {
            for (Object obj : parames) {
                if (obj instanceof HttpServletRequest) {
                    HttpServletRequest request = (HttpServletRequest) obj;
                    String res = getRequestBody(request);
                    System.out.println("aspect:" + res);
                    req = JSONObject.parseObject(res, clazz);
                }
            }
            for(int i = 0; i < parames.length; i++){
                if (parames[i].getClass() == clazz) {
                    parames[i] = req;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            BaseRes errRes = new BaseRes();
            setResUnknown(errRes);
            return errRes;
        }

        log.info("进入controller......");
        // 进入controller
        Object res = point.proceed(parames);

        log.info("离开controller......");
        // 自动校验返回码
        BaseRes res1 = (BaseRes) res;
        if(res1 == null || StringUtils.isBlank(res1.getResCode())){
            setResUnknown(res1);
        }
        return res;
    }

    private void setResUnknown(BaseRes errRes) {
        errRes.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
        errRes.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
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
            if(is != null){
                is.close();
            }
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }
}
