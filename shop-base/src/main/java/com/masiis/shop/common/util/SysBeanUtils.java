package com.masiis.shop.common.util;

import com.masiis.shop.common.exceptions.BusinessException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 *
 * Created by lzh on 2016/3/15.
 */
public class SysBeanUtils {

    /**
     * 检测对象中的属性是否都为null
     *
     * @param obj
     * @return
     */
    public static boolean isAllPropertiesBlank(Object obj){
        if(obj == null){
            throw new BusinessException("param is null");
        }
        Class clazz = obj.getClass();
        Method[] ms = clazz.getMethods();
        for(Method m : ms){
            String name = m.getName();
            if (name.startsWith("get") && m.getParameterTypes().length == 0) {
                try {
                    Object par = m.invoke(obj);
                    if(par == null){
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }
}
