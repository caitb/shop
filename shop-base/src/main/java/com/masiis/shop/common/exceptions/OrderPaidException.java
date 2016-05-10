package com.masiis.shop.common.exceptions;

/**
 * @Date 2016/5/10
 * @Auther lzh
 */
public class OrderPaidException extends RuntimeException {
    public OrderPaidException() {
        super();
    }

    public OrderPaidException(String message) {
        super(message);
    }

    public OrderPaidException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderPaidException(Throwable cause) {
        super(cause);
    }

    protected OrderPaidException(String message, Throwable cause,
                                boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
