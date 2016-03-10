package com.masiis.shop.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不计入签名的字段注解,只能应用于Field之上
 *
 * Created by lzh on 2016/3/10.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SignField {

}
