package com.andersoncarvalho.pidtp;

/**
 * Created by anderson on 02/03/16.
 */
public class RTCException extends Exception {
    public RTCException(String message){
        super(message);
    }
    public RTCException(String message, Throwable ex){
        super(message, ex);
    }
}
