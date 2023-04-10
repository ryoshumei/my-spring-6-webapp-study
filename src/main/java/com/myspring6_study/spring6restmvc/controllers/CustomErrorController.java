package com.myspring6_study.spring6restmvc.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.swing.event.ListDataEvent;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {
    @ExceptionHandler
    ResponseEntity handleJPAViolations(TransactionSystemException transactionSystemException){

        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();

        if(transactionSystemException.getCause().getCause() instanceof ConstraintViolationException){
            ConstraintViolationException violationException = (ConstraintViolationException) transactionSystemException.getCause().getCause();

            List errors = violationException.getConstraintViolations().stream()
                    .map(constraintViolation -> {
                        Map<String, String> errMap = new HashMap<>();
                        errMap.put(constraintViolation.getPropertyPath().toString(),
                                constraintViolation.getMessage());
                        return errMap;
                    }).collect(Collectors.toList());

            return responseEntity.body(errors);
        }

        return responseEntity.build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException methodArgumentNotValidException){

        List errorList = methodArgumentNotValidException.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());

//        System.out.println(methodArgumentNotValidException.getFieldErrors());
//        System.out.println(errorList);
//        System.out.println(methodArgumentNotValidException.getBindingResult().getFieldError());


        // Caution there are 2 methods getFieldErrors() with 's' and  getFieldError() without 's' , the latter of which will only return one error.
        //return ResponseEntity.badRequest().body(methodArgumentNotValidException.getBindingResult().getFieldErrors());
        return ResponseEntity.badRequest().body(errorList);

    }
}
