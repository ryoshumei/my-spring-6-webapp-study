package com.myspring6di.spring6di.services.profile_assignment;

import com.myspring6di.spring6di.services.GreetingService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("uat")
@Service("profileAssignment")
public class UatService implements GreetingService {
    @Override
    public String sayGreeting() {
        return "uat";
    }
}
