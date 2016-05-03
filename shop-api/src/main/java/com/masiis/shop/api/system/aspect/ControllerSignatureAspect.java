package com.masiis.shop.api.system.aspect;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.service.user.ComUserKeyboxService;
import com.masiis.shop.api.service.user.ComUserService;
import com.masiis.shop.api.utils.ApplicationContextUtil;
import com.masiis.shop.api.utils.SysSignUtils;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUserKeybox;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Map;

/**
 * @Date 2016/4/28
 * @Auther lzh
 */
@Aspect
@Component
public class ControllerSignatureAspect {
    private Logger log = Logger.getLogger(this.getClass());
    private final String DATA_NAME = "data";

    @Resource
    private ComUserService userService;
    @Resource
    private ComUserKeyboxService keyboxService;

    @Around("within(com.masiis.shop.api.controller..*) && @annotation(rl)")
    public Object signatureValid(ProceedingJoinPoint point, SignValid rl) throws Throwable {
        System.out.println("aspect......");
        Class clazz = rl.paramType();
        //获取目标方法体参数
        Object[] parames = point.getArgs();
        String tarName = point.getTarget().getClass().getSimpleName();

        Object req = null;
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class returnType = method.getReturnType();
        Object errRes = returnType.getDeclaredConstructor().newInstance();
        try {
            // 对请求参数进行解析，并获取参数对象引用
            req = getReqBean(parames, clazz);
            // 校验token
            String token = (String) clazz.getDeclaredField("token").get(req);
            ComUserKeybox keybox = keyboxService.getComUserKeyboxByToken(token);
            //userService.;
            // 校验签名
            String sign = SysSignUtils.toSignString(req, null);
            // 暂时取父类
            Field field = clazz.getSuperclass().getDeclaredField("sign");
            field.setAccessible(true);
            /*if(!sign.equals(field.get(req))){
                errRes.setResCode(SysResCodeCons.RES_CODE_REQ_SIGN_INVALID);
                errRes.setResMsg(SysResCodeCons.RES_CODE_REQ_SIGN_INVALID_MSG);
                return errRes;
            }*/
            log.info("sign:" + field.get(req));

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

        setResUnknown((BaseRes) errRes);
        return errRes;
    }

    /**
     * 根据参数数组来绑定参数
     *
     * @param parames
     * @param clazz
     * @return
     */
    private Object getReqBean(Object[] parames, Class clazz) throws UnsupportedEncodingException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object req = null;
        boolean isSet = true;
        for (Object obj : parames) {
            if (obj instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) obj;
                String conType = request.getHeader("Content-Type");
                if(StringUtils.isNotBlank(conType) && !conType.contains("application/x-www-form-urlencoded")){
                    String result = getRequestBody(request);
                    System.out.println("aspect:" + result);
                    try {
                        req = JSONObject.parseObject(result, clazz);
                    } catch (Exception e) {
                        req = JSONObject.parseObject(URLDecoder.decode(result, "UTF-8"), clazz);
                    }
                } else {
                    String data = request.getParameter(DATA_NAME);
                    if(StringUtils.isBlank(data)){
                        isSet = false;
                    } else {
                        data = URLDecoder.decode(data, "UTF-8");
                        try {
                            req = JSONObject.parseObject(data, clazz);
                        } catch (Exception e) {
                            req = JSONObject.parseObject(URLDecoder.decode(data, "UTF-8"), clazz);
                        }
                    }
                }
                break;
            }
        }
        for(int i = 0; i < parames.length; i++){
            if (parames[i].getClass() == clazz) {
                if(!isSet){
                    return parames[i];
                } else {
                    parames[i] = req;
                    break;
                }
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
            len = is.read(buffer);
            while (len != -1) {
                os.write(buffer, 0, len);
                len = is.read(buffer);
            }
            os.flush();
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
