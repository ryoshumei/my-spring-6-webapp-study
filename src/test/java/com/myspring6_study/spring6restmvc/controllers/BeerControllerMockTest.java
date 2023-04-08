package com.myspring6_study.spring6restmvc.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myspring6_study.spring6restmvc.model.BeerDTO;
import com.myspring6_study.spring6restmvc.services.BeerService;
import com.myspring6_study.spring6restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//Test the interaction of the controller & framework
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

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;
    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;


    @Test
    void getBeerByIdNotFound() throws Exception{

        // This will replace @Service method in controller, if there is no beerService.getBeerById() given will not work
        // willReturn(Optional.empty() will hit .orElseThrow(NotFoundException::new)
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BeerController.BEER_PATH_ID,UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPatchBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        // Code below : just simulate what client actually does, prepare a Map(will be converted to a json content).Representing a property going to be updated
        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).updateBeerPatchById(uuidArgumentCaptor.capture(),beerArgumentCaptor.capture());

        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());

    }

    @Test
    void testDeleteBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);
        BeerDTO beer1 = beerServiceImpl.listBeers().get(1);

        given(beerService.deleteById(any())).willReturn(true);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        //using ArgumentCaptor can make sure beerService gets the right argument mockMvc performed without coding manually in verify

        verify(beerService).deleteById(uuidArgumentCaptor.capture());

        // this going to be a double check ? Once you use capture(), then you can use getValue()
        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());


    }


    @Test
    void testUpdateBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);

        BeerDTO beer1 = beerServiceImpl.listBeers().get(1);

        given(beerService.updateById(any(),any())).willReturn(Optional.of(beer));

        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        verify(beerService).updateById(eq(beer.getId()),any(BeerDTO.class));//make sure updateById was called 1 time with the params


    }

    @Test
    void testCreateNewBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers().get(0);
        beer.setVersion(null);
        beer.setId(null);

        given(beerService.saveNewBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers().get(1));

        mockMvc.perform(post(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))//ends here : do POST
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }
    @Test
    void testPrintAJsonString() throws JsonProcessingException {
        BeerDTO testBeer = beerServiceImpl.listBeers().get(0);

        System.out.println(objectMapper.writeValueAsString(testBeer));
    }


    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

        mockMvc.perform(get(BeerController.BEER_PATH)
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
        BeerDTO testBeer = beerServiceImpl.listBeers().get(0);// get a beer from beerServiceImpl
        given(beerService.getBeerById(testBeer.getId()))
                .willReturn(Optional.of(testBeer));// return testBeer if matched , to mockMvc.perform below

        //"GET" request to this url and get response , this will call method in controller
        mockMvc.perform(get(BeerController.BEER_PATH_ID,testBeer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));//"$" means root

    }
}