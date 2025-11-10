package org.eatclub.deals.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class DealsExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestException(BadRequestException exception){
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("error", exception.getErrorMessage());
        errorBody.put("status", exception.getErrorCode());
        return ResponseEntity.status(exception.getErrorCode()).body(errorBody);
    }
}
