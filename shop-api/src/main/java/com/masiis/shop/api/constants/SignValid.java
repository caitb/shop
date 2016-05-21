package com.masiis.shop.api.constants;

import com.masiis.shop.api.bean.base.BaseReq;

import java.lang.annotation.*;

/**
 * @Date 2016/4/28
 * @Auther lzh
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SignValid {
    Class paramType();
    Class superClass() default BaseReq.class;
    boolean hasToken() default true;
    boolean isPageReturn() default false;
}
