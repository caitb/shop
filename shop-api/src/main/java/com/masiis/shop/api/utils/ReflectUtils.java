package com.masiis.shop.api.utils;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;

/**
 * @Date 2016/5/20
 * @Auther lzh
 */
public class ReflectUtils {

    public static Field getFieldByNameAllInSuperClass(Class clazz, String name){
        Field field = null;
        Class cla = clazz;
        if(StringUtils.isBlank(name)){
            return null;
        }
        do{
            try {
                field = cla.getDeclaredField(name);
                if(field != null){
                    return field;
                }
            } catch (NoSuchFieldException e) {

            }
        } while ((cla = cla.getSuperclass()) != Object.class );

        return field;
    }
}
