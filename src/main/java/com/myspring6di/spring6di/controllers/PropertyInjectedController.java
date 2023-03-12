package com.myspring6di.spring6di.controllers;

import com.myspring6di.spring6di.services.GreetingService;

public class PropertyInjectedController {

    GreetingService greetingService;

    public String sayHello(){
        return greetingService.sayGreeting();
    }

}
