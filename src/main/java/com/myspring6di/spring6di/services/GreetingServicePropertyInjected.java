package com.myspring6di.spring6di.services;

import org.springframework.stereotype.Service;

@Service("customNameForGreetingServicePropertyInjected")//we can set custom name ("here")
public class GreetingServicePropertyInjected implements GreetingService{
    @Override
    public String sayGreeting() {
        return "Friends don't let friends to property injection.";
    }
}
