package com.myspring6_study.spring6restmvc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {

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
