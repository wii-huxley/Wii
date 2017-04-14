package com.huxley.wii.yl.common;

/**
 * 总异常
 * Created by huxley on 17/1/14.
 */
public class BaseException extends RuntimeException {
    /**
     * Generated UID
     */
    private static final long serialVersionUID = -6213149635297151442L;

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException() {
        super();
    }

    public BaseException(Throwable cause) {
        super(cause);
    }
}
