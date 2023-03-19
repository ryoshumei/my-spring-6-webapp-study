package com.myspring6_study.spring6restmvc.controllers;

import com.myspring6_study.spring6restmvc.model.Customer;
import com.myspring6_study.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PatchMapping("{customerId}")
    public ResponseEntity updateCustomerPatchById(@PathVariable("customerId") UUID customerId,@RequestBody Customer customer){

        customerService.updateCustomerPatchById(customerId,customer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID customerId){

        customerService.deleteCustomerById(customerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{customerId}")
    public ResponseEntity updateCustomerById(@PathVariable("customerId") UUID customerId,
                                             @RequestBody Customer customer){

        customerService.updateCustomerById(customerId, customer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity handlePost(@RequestBody Customer customer){

        Customer savedCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + savedCustomer.getId());

        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listCustomers(){
        return customerService.listCustomers();
    }

    @RequestMapping(value = "{customerId}",method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID uuid) {

        log.debug("Get Customer by Id - in controller");
        return customerService.getCustomerById(uuid);
    }

}
