package com.example.rekazfinalproject.Adivce;


import com.example.rekazfinalproject.Api.ApiException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLIntegrityConstraintViolationException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    // Our Exception
    // it is possible to be in All endpoints
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity ApiException(ApiException e){
        String message=e.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    // Server Validation Exception
    // it is possible to be in add and update endpoints
    // it happened to me when I send an object without not empty key
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getFieldError().getDefaultMessage();
        return ResponseEntity.status(400).body(message);
    }

    // Server Validation Exception
    // it is possible to be in All endpoints except get endpoints
    // it happened to me when I entered a value does not match with the Constraint
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity ConstraintViolationException(ConstraintViolationException e) {
        String message =e.getMessage();
        return ResponseEntity.status(400).body(message);
    }


    // SQL Constraint Ex:(Duplicate) Exception
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        String message=e.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    // wrong write SQL in @column Exception
    // it is possible to be in All endpoints
    // it happened to me when I entered value and there is an SQL wrong syntax
    @ExceptionHandler(value = InvalidDataAccessResourceUsageException.class )
    public ResponseEntity InvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e){
        String message=e.getMessage();
        return ResponseEntity.status(200).body(message);
    }

    // Database Constraint Exception
    // it is possible to be in add and update endpoints
    // it happened to me when I entered Duplicate entry for a unique key
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity DataIntegrityViolationException(DataIntegrityViolationException e){
        String message=e.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    // Method not allowed Exception
    // it is possible to be in All endpoints except get endpoints
    // it happened to me when I choose a get instead of post
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String message = e.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    // Json parse Exception
    // it is possible to be in All endpoints except get endpoints
    // it happened to me when I entered String to an integer value
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity HttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String message = e.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    // TypesMisMatch Exception
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String message = e.getMessage();
        return ResponseEntity.status(400).body(message);
    }



    // it is possible to be in All endpoints
    // it happened to me when I entered wrong mapping address
    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity NoResourceFoundException(NoResourceFoundException e) {
        String message = e.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    // it is possible to be in add and update endpoints
    // it happened to me when I entered a value of type int in the server and it is of type varchar in SQL
    @ExceptionHandler(value = UnexpectedTypeException.class)
    public ResponseEntity UnexpectedTypeException(UnexpectedTypeException e) {
        String message = e.getMessage();
        return  ResponseEntity.status(400).body(message);
    }

    // NullPointerException

    // IncorrectResultSizeDataAccessException
}

