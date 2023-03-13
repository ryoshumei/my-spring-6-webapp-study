package com.myspring6di.spring6di.controllers.profile_assignment;

import com.myspring6di.spring6di.services.GreetingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class MyProfileAssignmentController {


    private final GreetingService greetingService;

    //Spring will use constructor
    public MyProfileAssignmentController(@Qualifier("profileAssignment") GreetingService greetingService) {
        System.out.println("MyProfileAssignmentController 's Constructor");
        this.greetingService = greetingService;
    }

    public String sayHello(){

        return greetingService.sayGreeting();
    }
}
