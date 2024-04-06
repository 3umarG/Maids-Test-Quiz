package com.example.maidsquizapi.exceptions;


import com.example.maidsquizapi.shared.ApiCustomResponse;
import com.example.maidsquizapi.shared.ResponseWrapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
@ResponseBody
public class ControllerExceptionsHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiCustomResponse> handleCustomExceptions(CustomException ex) {
        return handleCustomException(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiCustomResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return handleValidationException(ex);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiCustomResponse> handleAuthenticationExceptions(AuthenticationException ex) {
        var message = "You are UN_AUTHORIZED of accessing this resource with exception : " + ex.getMessage();
        return handleAuthenticationException(message);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiCustomResponse> handleAccessDeniedExceptions() {
        return handleAccessDeniedException();
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiCustomResponse> handleExpiredJwtTokenExceptions() {
        var message = "JWT Token is already expired ...!!";
        return handleAuthenticationException(message);
    }

    @ExceptionHandler({MalformedJwtException.class, SignatureException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiCustomResponse> handleWrongJwtTokenFormat(Exception ex) {
        var message = ex.getMessage() != null
                ? ex.getMessage()
                : "JWT Token has not correct format ...!!";

        return handleAuthenticationException(message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiCustomResponse> handleGlobalExceptions(Exception ex){
        return handleGlobalException(ex);
    }

//    @ExceptionHandler
//    public ResponseEntity<ApiCustomResponse> handleExceptions(Exception globalException) {
//        if (globalException instanceof CustomException customException) {
//            return handleCustomException(customException);
//        } else if (globalException instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
//            return handleValidationException(methodArgumentNotValidException);
//        } else if (globalException instanceof AuthenticationException) {
//            String message = "You are UN_AUTHORIZED of accessing this resource with exception : " + globalException.getMessage();
//            return handleAuthenticationException(message);
//        } else if (globalException instanceof AccessDeniedException) {
//            return handleAccessDeniedException();
//        } else if (globalException instanceof ExpiredJwtException) {
//            var message = "JWT Token is already expired ...!!";
//            return handleAuthenticationException(message);
//        } else if (globalException instanceof SignatureException || globalException instanceof MalformedJwtException) {
//            String message = globalException.getMessage() != null ? globalException.getMessage() : "JWT Token has not correct format ...!!";
//            return handleAuthenticationException(message);
//        } else {
//            return handleGlobalException(globalException);
//        }
//    }


    private static ResponseEntity<ApiCustomResponse> handleGlobalException(Exception globalException) {
        String errorMessage = "SERVER_ERROR_EXCEPTION , with cause : " + globalException.getMessage();

        return ResponseWrapper
                .error(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR.value())
                .toResponseEntity();
    }

    private static ResponseEntity<ApiCustomResponse> handleAccessDeniedException() {
        String message = "Forbidden Authorization Exception";
        return ResponseWrapper
                .error(message, HttpStatus.FORBIDDEN.value())
                .toResponseEntity();
    }

    private static ResponseEntity<ApiCustomResponse> handleAuthenticationException(String message) {
        return ResponseWrapper
                .error(message, HttpStatus.UNAUTHORIZED.value())
                .toResponseEntity();
    }

    private static ResponseEntity<ApiCustomResponse> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        HashMap<String, String> errors = new HashMap<>();
        for (Object error : methodArgumentNotValidException.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) error;
            String fieldName = fieldError.getField();
            String errorMessage = ((FieldError) error).getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        return ResponseWrapper
                .error("Please validate your inputs !!", 400, errors)
                .toResponseEntity();
    }

    private static ResponseEntity<ApiCustomResponse> handleCustomException(CustomException customException) {
        return ResponseWrapper
                .error(customException.getMessage(), customException.getStatusCode())
                .toResponseEntity();
    }

}
