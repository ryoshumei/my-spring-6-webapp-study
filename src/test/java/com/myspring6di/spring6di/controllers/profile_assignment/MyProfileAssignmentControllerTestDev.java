package com.myspring6di.spring6di.controllers.profile_assignment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

//Profile cover a huge area, maybe whole project, We need Set all  ActiveProfiles we set in diff Services.
@ActiveProfiles({"den", "default"})
@SpringBootTest
class MyProfileAssignmentControllerTestDev {

    @Autowired
    MyProfileAssignmentController myProfileAssignmentController;

    @Test
    void sayHello() {
        System.out.println(myProfileAssignmentController.sayHello());
    }
}