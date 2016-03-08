package com.andersoncarvalho.pidtp.controller;

/**
 * Created by anderson on 02/03/16.
 */
import com.andersoncarvalho.pidtp.RTCException;
import com.andersoncarvalho.pidtp.RTCRuntimeException;
import com.andersoncarvalho.pidtp.controller.data.DataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class AbstractController {
    @ExceptionHandler({Exception.class,RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public DataResponse handleAllExceptions(Exception ex) {
        DataResponse response = new DataResponse(false);
        response.setMessage(ex.getMessage());
        ex.printStackTrace();
        return response;
    }
    @ExceptionHandler({RTCRuntimeException.class, RTCException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public DataResponse handleApplicationExceptions(Exception ex){
        DataResponse response = new DataResponse(false);
        response.setImportant(true);
        response.setMessage(ex.getMessage());
        return response;
    }
}
