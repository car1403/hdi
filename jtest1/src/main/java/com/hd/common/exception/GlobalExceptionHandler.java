package com.hd.common.exception;

import com.hd.common.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {


    // User Define Exception
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<?> handleEmailNotFoundException(NameDuplicateException ex){
        return Response.fail(ex.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // User Define Exception
    @ExceptionHandler(NameDuplicateException.class)
    public ResponseEntity<?> handleEmailDuplicateException(NameDuplicateException ex){
        return Response.fail(ex.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex){
        return Response.fail(ErrorCode.ALL_Exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex){
        return Response.fail(ErrorCode.ALL_Exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}