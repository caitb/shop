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
    /**
     * 请求参数对象class
     * @return
     */
    Class paramType();
    Class superClass() default BaseReq.class;

    /**
     * 是否需要token校验
     * @return
     */
    boolean hasToken() default true;

    /**
     * 是否返回页面
     * @return
     */
    boolean isPageReturn() default false;

    /**
     * 是否有请求参数
     * @return
     */
    boolean hasData() default true;
}
