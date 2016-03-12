package com.andersoncarvalho.pidtp;

/**
 * Created by anderson on 02/03/16.
 */
public class RICException extends Exception {
    public RICException(String message){
        super(message);
    }
    public RICException(String message, Throwable ex){
        super(message, ex);
    }
}
