package com.myspring6_study.spring6restmvc.services;

import com.myspring6_study.spring6restmvc.controllers.NotFoundException;
import com.myspring6_study.spring6restmvc.mappers.CustomerMapper;
import com.myspring6_study.spring6restmvc.model.CustomerDTO;
import com.myspring6_study.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID uuid) {

        return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository
                .findById(uuid)
                .orElse(null)));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {

        return customerMapper.customerToCustomerDto(customerRepository.save(customerMapper.customerDtoToCustomer(customer)));

    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {

//        Customer customerToUpdate = customerRepository.findById(customerId).get();
//
//        System.out.println(customerToUpdate.getVersion());
//
//        customerToUpdate.setCustomerName(customer.getCustomerName());
//
//        customerRepository.save(customerToUpdate);
//
//        System.out.println(customerRepository.findById(customerId).get().getVersion());
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(customerId).ifPresentOrElse( foundCustomer -> {
            foundCustomer.setCustomerName(customer.getCustomerName());
            foundCustomer.setLastModifiedDate(LocalDateTime.now());
            atomicReference.set(Optional.of(customerMapper.customerToCustomerDto(customerRepository.save(foundCustomer))));
        },() ->{
            throw new NotFoundException();
        });

        return atomicReference.get();

    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        if(customerRepository.existsById(customerId)){
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;

    }

    @Override
    public Optional<CustomerDTO> updateCustomerPatchById(UUID customerId, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(customerId).ifPresentOrElse(
                (foundCustomer) ->{
                    if(StringUtils.hasText(customer.getCustomerName())){
                        foundCustomer.setCustomerName(customer.getCustomerName());
                    }
                    foundCustomer.setLastModifiedDate(LocalDateTime.now());
                    customerRepository.save(foundCustomer);
                },
                () -> {
                    throw new NotFoundException();
                });
        return atomicReference.get();
    }
}
