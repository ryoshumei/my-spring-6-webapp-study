package com.myspring6_study.spring6restmvc.mappers;

import com.myspring6_study.spring6restmvc.entities.Customer;
import com.myspring6_study.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
