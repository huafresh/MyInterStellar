package com.hua.myinterstellar_core;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-03-01 10:16
 */

public class StellarException extends RuntimeException {
    public StellarException(String message) {
        super(message);
    }

    public StellarException(String message, Throwable cause) {
        super(message, cause);
    }
}
