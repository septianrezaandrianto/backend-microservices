package com.service.begateway.filter;

import com.service.begateway.dto.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Response<Object>> handleGeneralExceptions(Exception ex) {
        List<String> errorList = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(mappingError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), errorList),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Response<Object>> handleRuntimeExceptions(RuntimeException ex) {
        List<String> errorList = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(mappingError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), errorList),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Response<Object> mappingError(int responseCode, String responseMessage, List<String> errorList) {
        return Response.builder()
                .responseCode(responseCode)
                .responseMessage(responseMessage)
                .errorList(errorList)
                .build();
    }
}
