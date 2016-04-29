package com.masiis.shop.api.system.aspect;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.utils.SysSignUtils;
import com.masiis.shop.common.exceptions.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

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
        System.out.println("aspect......");
        Class clazz = rl.paramType();
        //获取目标方法体参数
        Object[] parames = point.getArgs();
        String tarName = point.getTarget().getClass().getSimpleName();

        Object req = null;
        BaseRes errRes = new BaseRes();
        try {
            // 对请求参数进行解析，并获取参数对象引用
            req = getReqBean(parames, clazz);
            //校验签名
            String sign = SysSignUtils.toSignString(req, null);
            // 暂时取父类
            Field field = clazz.getSuperclass().getDeclaredField("sign");
            field.setAccessible(true);
            if(!sign.equals(field.get(req))){
                errRes.setResCode(SysResCodeCons.RES_CODE_REQ_SIGN_INVALID);
                errRes.setResMsg(SysResCodeCons.RES_CODE_REQ_SIGN_INVALID_MSG);
                return errRes;
            }

            log.info("进入" + tarName + "......");

            // 进入controller
            Object res = point.proceed(parames);

            log.info("离开" + tarName + "......");
            // 自动校验返回码,没有补齐系统繁忙
            BaseRes res1 = (BaseRes) res;
            if(res1 == null || StringUtils.isBlank(res1.getResCode())){
                setResUnknown(res1);
            }
            return res;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        setResUnknown(errRes);
        return errRes;
    }

    /**
     * 根据参数数组来绑定参数
     *
     * @param parames
     * @param clazz
     * @return
     */
    private Object getReqBean(Object[] parames, Class clazz) {
        Object req = null;
        for (Object obj : parames) {
            if (obj instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) obj;
                String result = getRequestBody(request);
                System.out.println("aspect:" + result);
                req = JSONObject.parseObject(result, clazz);
                break;
            }
        }
        for(int i = 0; i < parames.length; i++){
            if (parames[i].getClass() == clazz) {
                parames[i] = req;
                break;
            }
        }

        return req;
    }

    private void setResUnknown(BaseRes errRes) {
        errRes.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
        errRes.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
    }

    /**
     * 从requestbody中拿到请求字符串
     *
     * @param request
     * @return
     */
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
