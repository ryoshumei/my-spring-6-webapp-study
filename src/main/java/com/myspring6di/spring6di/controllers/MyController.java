package com.myspring6di.spring6di.controllers;

import com.myspring6di.spring6di.services.GreetingService;
import com.myspring6di.spring6di.services.GreetingServiceImpl;
import org.springframework.stereotype.Controller;

@Controller
public class MyController {

    private final GreetingService greetingService;

    public MyController() {
        this.greetingService = new GreetingServiceImpl();
    }

    public String sayHello(){
        System.out.println("This is MyController Class");

        return greetingService.sayGreeting();
    }
}
