package com.myspring6_study.spring6restmvc.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myspring6_study.spring6restmvc.model.Beer;
import com.myspring6_study.spring6restmvc.services.BeerService;
import com.myspring6_study.spring6restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BeerController.class) // bind Controller Class here
class BeerControllerMockTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired//this means we will use springboot context
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;;//No Autowired here , implementation of BeerService Interface, make an instance

    @BeforeEach
    void setUp(){
        beerServiceImpl = new BeerServiceImpl();// For providing data
    }


    @Test
    void testUpdateBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);

        Beer beer1 = beerServiceImpl.listBeers().get(1);

        mockMvc.perform(put("/api/v1/beer/" + beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        verify(beerService).updateById(eq(beer.getId()),any(Beer.class));//make sure updateById was called 1 time with the params


    }

    @Test
    void testCreateNewBeer() throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);
        beer.setVersion(null);
        beer.setId(null);

        given(beerService.saveNewBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1));

        mockMvc.perform(post("/api/v1/beer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))//ends here : do POST
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }
    @Test
    void testPrintAJsonString() throws JsonProcessingException {
        Beer testBeer = beerServiceImpl.listBeers().get(0);

        System.out.println(objectMapper.writeValueAsString(testBeer));
    }


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