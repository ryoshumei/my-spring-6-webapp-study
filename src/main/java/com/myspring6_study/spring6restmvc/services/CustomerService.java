package com.myspring6_study.spring6restmvc.services;

import com.myspring6_study.spring6restmvc.controllers.CustomerController;
import com.myspring6_study.spring6restmvc.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<Customer> listCustomers();

    Optional<Customer> getCustomerById(UUID uuid);

    Customer saveNewCustomer(Customer customer);

    void updateCustomerById(UUID customerId, Customer customer);

    void deleteCustomerById(UUID customerId);

    void updateCustomerPatchById(UUID customerId, Customer customer);
}
