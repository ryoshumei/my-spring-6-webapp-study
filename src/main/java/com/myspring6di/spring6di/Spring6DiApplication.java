package com.myspring6di.spring6di;
//Caution :  every package for this project should be under this package
import com.myspring6di.spring6di.controllers.MyController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Spring6DiApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(Spring6DiApplication.class, args);


        //create a instance using via Spring Context
        MyController myController = ctx.getBean(MyController.class);

        System.out.println("This is Main Method");

        System.out.println(myController.sayHello());
    }

}
