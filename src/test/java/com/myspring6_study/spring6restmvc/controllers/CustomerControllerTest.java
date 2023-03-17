package com.myspring6_study.spring6restmvc.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerTest {

    @Autowired
    CustomerController customerController;

    @Test
    void listCustomers() {
        System.out.println(customerController.listCustomers());

    }

    @Test
    void getCustomerById() {
        UUID uuid = customerController.listCustomers().get(0).getId();
        System.out.println(customerController.getCustomerById(uuid));
    }
}