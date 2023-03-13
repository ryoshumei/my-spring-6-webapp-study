package com.myspring6di.spring6di.services;

import org.springframework.stereotype.Service;

@Service("setterGreetingBean")
public class GreetingServicesSetterInjection implements GreetingService{
    @Override
    public String sayGreeting() {
        return "Hi i'm setting a greeting ";
    }
}
