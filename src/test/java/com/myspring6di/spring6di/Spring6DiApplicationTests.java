package com.myspring6di.spring6di;

import com.myspring6di.spring6di.controllers.MyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class Spring6DiApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MyController myController;

    @Test
    void autowiredOfController(){
        System.out.println("In autowiredOfController method");
        System.out.println(myController.sayHello());
    }

    @Test
    void testOfAppContext(){
        MyController myController = applicationContext.getBean(MyController.class);
        System.out.println("In testOfAPPContext method ");
        System.out.println(myController.sayHello());
    }

    @Test
    void contextLoads() {
    }

}
