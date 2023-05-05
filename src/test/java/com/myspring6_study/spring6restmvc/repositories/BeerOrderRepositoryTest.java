package com.myspring6_study.spring6restmvc.repositories;

import com.myspring6_study.spring6restmvc.entities.Beer;
import com.myspring6_study.spring6restmvc.entities.BeerOrder;
import com.myspring6_study.spring6restmvc.entities.BeerOrderShipment;
import com.myspring6_study.spring6restmvc.entities.Customer;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    EntityManager entityManager;

    Customer testCustomer;
    Beer testBeer;

    @BeforeEach
    public void setUp() {
        testCustomer = customerRepository.findAll().get(0);
        testBeer = beerRepository.findAll().get(0);
    }

    @Test
    @Transactional
    void testBeerOrders() {
        BeerOrder beerOrder = BeerOrder.builder()
                .customerRef("Test Order")
                .customer(testCustomer)
                .beerOrderShipment(BeerOrderShipment.builder()
                        .tracking_number("123fff")
                        .build())
                .build();
//
//        BeerOrder savedBeerOrder = beerOrderRepository.saveAndFlush(beerOrder);

        // Work with save not saveAndFlush (Added some helper methods in entities)
        //So that we can make a right relationship without DB
        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);

        //If no this "detach" , beerOrderRepository.findAll().get(0) might be changed duo to the next line
        //This could happen in Spring Integration Test Env
        entityManager.detach(beerOrder);
        beerOrder.setCustomerRef("Test 2 ");
        System.out.println(beerOrderRepository.findAll().get(0).getCustomerRef());
        System.out.println(savedBeerOrder.getCustomerRef());
    }
}