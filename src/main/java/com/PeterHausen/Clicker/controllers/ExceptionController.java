package com.PeterHausen.Clicker.controllers;


import com.PeterHausen.Clicker.models.exceptions.NotFoundException;
import com.PeterHausen.Clicker.models.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest req) {
        Object resBody = ex.getMessage();
        return handleExceptionInternal(ex, resBody, new HttpHeaders(), HttpStatus.NOT_FOUND, req);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest req) {
        Object resBody = ex.getMessage();
        return handleExceptionInternal(ex, resBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, req);
    }

    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<Object> handleSQLException(SQLException ex, WebRequest req) {
        logger.error(ex.getMessage());
        Object resBody = ex.getMessage();
        return handleExceptionInternal(ex, resBody, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, req);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(SQLException ex, WebRequest req) {
        logger.error(ex.getMessage());
        Object resBody = ex.getMessage();
        return handleExceptionInternal(ex, resBody, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, req);
    }
}
