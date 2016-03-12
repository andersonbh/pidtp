package com.andersoncarvalho.pidtp;

/**
 * Created by anderson on 02/03/16.
 */
public class RICRuntimeException extends RuntimeException {
    public RICRuntimeException(String message){
        super(message);
    }
    public RICRuntimeException(String message, Throwable ex){
        super(message, ex);
    }
}
