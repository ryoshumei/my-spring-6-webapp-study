package com.myspring6_study.spring6restmvc.controllers;


import com.myspring6_study.spring6restmvc.model.Beer;
import com.myspring6_study.spring6restmvc.services.BeerService;
import com.myspring6_study.spring6restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BeerController.class) // bind Controller Class here
class BeerControllerMockTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();//No Autowired here , implementation of BeerService Interface, make an instance

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));


    }

    @Test
    void getBeerById() throws Exception {
        // This test will return the testBeer object when UUID matches testBeer's UUID .
        // testBeer include some Beer Objects
        // that means ? this test can not make ture the method in service will work properly
        // this is the test only for Controller??? Yes
        // relationship between beerService and beerServiceImpl??
        Beer testBeer = beerServiceImpl.listBeers().get(0);// get a beer from beerServiceImpl
        given(beerService.getBeerById(testBeer.getId()))
                .willReturn(testBeer);// return testBeer if matched , to mockMvc.perform below

        //"GET" request to this url and get response , this will call method in controller
        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));//"$" means root

    }
}