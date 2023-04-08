package com.myspring6_study.spring6restmvc.controllers;

import com.myspring6_study.spring6restmvc.entities.Customer;
import com.myspring6_study.spring6restmvc.mappers.CustomerMapper;
import com.myspring6_study.spring6restmvc.model.CustomerDTO;
import com.myspring6_study.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testUpdateByIdByPatchNotFound(){

        assertThrows(NotFoundException.class,()->{
            customerController.updateCustomerPatchById(UUID.randomUUID(),CustomerDTO.builder().build());
        });
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateByIdByPatchFound(){

        Customer testCustomer = customerRepository.findAll().get(0);
        UUID testUUID = testCustomer.getId();

        CustomerDTO testCustomerDto = customerMapper.customerToCustomerDto(testCustomer);
        testCustomerDto.setId(null);
        testCustomerDto.setVersion(null);
        String nameToUpdate = "TestUpdateByIdByPatchFound";
        testCustomerDto.setCustomerName(nameToUpdate);

        ResponseEntity responseEntity = customerController.updateCustomerPatchById(testUUID,testCustomerDto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(testUUID).get();

        assertThat(updatedCustomer).isNotNull();

        assertThat(updatedCustomer.getCustomerName()).isEqualTo(nameToUpdate);



    }

    @Test
    void testDeleteByIdNotFound(){

        UUID testUUID = UUID.randomUUID();

        //ResponseEntity responseEntity = customerController.deleteCustomerById(testUUID);
        assertThrows(NotFoundException.class,() -> {customerController.deleteCustomerById(testUUID);});

        assertThat(customerRepository.findById(testUUID)).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteById(){

        Customer testCustomer = customerRepository.findAll().get(0);
        UUID testUUID = testCustomer.getId();

        ResponseEntity responseEntity = customerController.deleteCustomerById(testUUID);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(customerRepository.findById(testUUID)).isEmpty();

    }

    @Test
    void testUpdateCustomerNotFound(){

        assertThrows(NotFoundException.class,() ->{
            customerController.updateCustomerById(UUID.randomUUID(),CustomerDTO.builder().build());
        });

    }

    @Test
    @Transactional
    @Rollback// rollback will happen after each transaction, not just the current test that's why the version is always 0
    void testVersion(){
        Customer testCustomer0 = customerRepository.findAll().get(0);

        UUID uuid = testCustomer0.getId();

        System.out.println(customerMapper.customerToCustomerDto(customerRepository.findById(uuid).get()));



        //testCustomer0.setVersion(null);
        testCustomer0.setCustomerName("test");
        customerRepository.save(testCustomer0);

        testCustomer0 = customerRepository.findById(uuid).get();
        testCustomer0.setCustomerName("test1");
        customerRepository.save(testCustomer0);

        testCustomer0 = customerRepository.findById(uuid).get();
        testCustomer0.setCustomerName("test2");
        customerRepository.save(testCustomer0);

        System.out.println(customerMapper.customerToCustomerDto(customerRepository.findById(uuid).get()));


    }

    @Test
    @Transactional
    @Rollback
    void testUpdateExistingCustomer(){

        Customer testCustomer = customerRepository.findAll().get(0);
        UUID testUUID = testCustomer.getId();
        System.out.println(testCustomer.getVersion());
        System.out.println(customerMapper.customerToCustomerDto(customerRepository.findById(testUUID).get()));

        CustomerDTO testCustomerDto = customerMapper.customerToCustomerDto(testCustomer);

        testCustomerDto.setId(null);
        testCustomerDto.setVersion(null);

        String updateName = "TESTINGNAME";
        testCustomerDto.setCustomerName(updateName);

        ResponseEntity responseEntity = customerController.updateCustomerById(testUUID,testCustomerDto);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(customerRepository.findById(testUUID).get()).isNotNull();
        assertThat(customerRepository.findById(testUUID).get().getCustomerName()).isEqualTo(updateName);
        System.out.println(customerMapper.customerToCustomerDto(customerRepository.findById(testUUID).get()));

    }

    @Transactional
    @Rollback
    @Test
    void testSaveCustomer(){
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("test")
                .build();

        ResponseEntity responseEntity = customerController.handlePost(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        UUID savedUUID = UUID.fromString((responseEntity.getHeaders().getLocation().getPath().split("/"))[4]);

        Customer savedCustomer = customerRepository.findById(savedUUID).get();

        assertThat(savedCustomer).isNotNull();


    }

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