package com.myspring6di.spring6di.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class MyController {

    public String sayHello(){
        System.out.println("This is MyController Class");

        return "Hello";
    }
}
