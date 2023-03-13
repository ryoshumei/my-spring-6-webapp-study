package com.myspring6di.spring6di.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

//set priority
@Primary
@Service
public class GreetingServicePrimary implements GreetingService{
    @Override
    public String sayGreeting() {
        return "Hello from the Primary Bean";
    }
}
