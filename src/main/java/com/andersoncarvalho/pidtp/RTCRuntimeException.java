package com.andersoncarvalho.pidtp;

/**
 * Created by anderson on 02/03/16.
 */
public class RTCRuntimeException extends RuntimeException {
    public RTCRuntimeException(String message){
        super(message);
    }
    public RTCRuntimeException(String message, Throwable ex){
        super(message, ex);
    }
}
