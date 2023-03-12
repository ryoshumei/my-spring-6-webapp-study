package com.myspring6di.spring6di.controllers;

import com.myspring6di.spring6di.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SetterInjectedController {

    // In this case the setter won't be called by test
    //If move the Autowired Annotation  above the setter then the setter will be called
    @Autowired
    private GreetingService greetingService;

    //setter for GreetingService
    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public String sayHello(){
        return greetingService.sayGreeting();
    }
}
