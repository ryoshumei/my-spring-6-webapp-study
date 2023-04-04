package com.myspring6_study.spring6restmvc.controllers;

import com.myspring6_study.spring6restmvc.entities.Customer;
import com.myspring6_study.spring6restmvc.model.CustomerDTO;
import com.myspring6_study.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIntegrationTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerController customerController;

    @Test
    void listCustomers() {
        List<CustomerDTO> customerDTOList = customerController.listCustomers();

        assertThat(customerDTOList.size()).isEqualTo(3);

    }

    @Rollback
    @Transactional
    @Test
    void listCustomerNotFound() {
        customerRepository.deleteAll();

        List<CustomerDTO> customerDTOList = customerController.listCustomers();

        assertThat(customerDTOList.size()).isEqualTo(0);
    }

    @Test
    void getCustomerById() {
        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());

        assertThat(customerDTO).isNotNull();
    }

    @Test
    void getCustomerByIdNotFound() {
        assertThrows(NotFoundException.class,() -> {
           customerController.getCustomerById(UUID.randomUUID());
        });
    }
}