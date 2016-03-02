package com.masiis.shop.web.platform.constants;

/**
 * Created by lzh on 2016/2/29.
 */
public class WxResCodeCons {
    /**
     * 系统繁忙，此时请开发者稍候再试
     */
    public static final String SYS_BUSY = "-1";
    public static final String SYS_BUSY_MSG = "系统繁忙，此时请开发者稍候再试";
    /**
     * 请求成功
     */
    public static final String REQUEST_SUCCESS = "0";
    public static final String REQUEST_SUCCESS_MSG = "请求成功";

    public static final String ACCESS_TOKEN_INVALID = "40014";
    public static final String ACCESS_TOKEN_INVALID_MSG = "不合法的access_token，请开发者认真比对access_token的有效性";

    public static final String ACCESS_TOKEN_TIMEOUT = "42001";
    public static final String ACCESS_TOKEN_TIMEOUT_MSG = "access_token超时，请检查access_token的有效期";

    public static final String REFRESH_TOKEN_TIMEOUT = "42002";
    public static final String REFRESH_TOKEN_TIMEOUT_MSG = "refresh_token超时";
}
