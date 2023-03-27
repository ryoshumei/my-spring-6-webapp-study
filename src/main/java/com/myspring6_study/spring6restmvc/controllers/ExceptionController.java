package com.myspring6_study.spring6restmvc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// This class has higher priority than @ResponseStatus

//@ControllerAdvice//this will kick up globally
//public class ExceptionController {
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity handleNotFoundException(){
//        System.out.println("In ExceptionController Class");
//        return ResponseEntity.notFound().build();
//    }
//}
