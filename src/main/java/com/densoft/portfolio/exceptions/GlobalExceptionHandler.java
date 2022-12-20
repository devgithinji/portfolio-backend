package com.densoft.portfolio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(resourceNotFoundException.getMessage(), webRequest.getDescription(false), new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApIException.class)
    public ResponseEntity<ErrorDetails> handleApiException(ApIException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(webRequest.getDescription(false), exception.getMessage(), new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", new Date());
        response.put("errors", errors);
        response.put("details", request.getDescription(false));


        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webRequest) {
        System.out.println(exception);
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), webRequest.getDescription(false), new Date());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
