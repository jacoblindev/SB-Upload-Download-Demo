package com.jacoblindev.updnfiledemo.exception;

import com.jacoblindev.updnfiledemo.helper.ResponseMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        log.warn("MaxUploadSizeExceededException occured with {}", exc.getMessage());
        return new ResponseEntity<>(new ResponseMessage("One or more files are too large!"),
                HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException aexc) {
        log.warn("ApiException occured with {}", aexc.getMessage());
        return new ResponseEntity<>(new ResponseMessage(aexc.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest req) {
        log.warn("Exception occured with [ {} ]", ex.getMessage());
        return new ResponseEntity<>(new ResponseMessage("Unknown error occured...！"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
