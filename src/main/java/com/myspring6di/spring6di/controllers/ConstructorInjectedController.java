package com.myspring6di.spring6di.controllers;

import com.myspring6di.spring6di.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class ConstructorInjectedController {


    private final GreetingService greetingService;

    //Spring will use this constructor
    //@Qualifier select which class will be used(begin with lower case(class name))
    public ConstructorInjectedController(@Qualifier("greetingServiceImpl") GreetingService greetingService) {
        System.out.println("ConstructorInjectedController Constructor");
        this.greetingService = greetingService;
    }

    public String sayHello(){
        return greetingService.sayGreeting();
    }
}
