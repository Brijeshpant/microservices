package com.brij.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();
        Map<String, String> fieldsError = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                FieldError::getField, FieldError::getDefaultMessage
        ));
        String code = "100";
        String message = "Invalid request body";
        return getErrorResponse(headers, status, fieldsError, code, message);
    }

    private ResponseEntity<Object> getErrorResponse(HttpHeaders headers, HttpStatusCode status, Map<String, String> fieldsError, String code, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(code);
        errorResponse.setMessage(message);
        errorResponse.setFields(fieldsError);
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException exx) {
            String completePath = exx.getPath().stream().map(JsonMappingException.Reference::getFieldName).collect(Collectors.joining());
            HashMap<String, String> fieldErrors = new HashMap<>();
            fieldErrors.put(completePath, "Invalid value");
            return getErrorResponse(headers, status, fieldErrors, "101", "Invalid type");
        }
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }


    @ExceptionHandler(MissingRecordException.class)
    ResponseEntity<Object> handleMissingRecordException(MissingRecordException missingRecordException) {
        return getErrorResponse(null, NOT_FOUND, null, "102", missingRecordException.getMessage());

    }


}


