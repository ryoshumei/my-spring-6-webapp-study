package com.myspring6di.spring6di.controllers.i18n;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class Myi18NControllerTestES {

    @Autowired
    Myi18NController myi18NController;

    @Test
    void sayHello() {
        System.out.println(myi18NController.sayHello());
    }
}