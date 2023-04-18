package com.myspring6_study.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myspring6_study.spring6restmvc.entities.Beer;
import com.myspring6_study.spring6restmvc.mappers.BeerMapper;
import com.myspring6_study.spring6restmvc.model.BeerDTO;
import com.myspring6_study.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

//Test the controller and its interaction with the JPA DataLayer
@SpringBootTest//bring up the full context
class BeerControllerIntegrationTest {
    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    //this is for making mockMvc be with webApplicationContext
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    //more complete integration test with MockMVC
    @Test
    void testPatchBeerLongName() throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        // Code below : just simulate what client actually does, prepare a Map(will be converted to a json content).Representing a property going to be updated
        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name 12345678901234567890123456789012345678901234567890");

        MvcResult result = mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());




    }

    @Test
    void testUpdateByIdByPatchNotFound(){

        assertThrows(NotFoundException.class,() ->{
           beerController.updateBeerPatchById(UUID.randomUUID(), BeerDTO.builder().build());
        });

    }

    @Test
    @Transactional
    @Rollback
    void testUpdateByIdByPatchFound(){
        Beer beer = beerRepository.findAll().get(0);
        UUID testUUID = beer.getId();

        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);

        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String beerName = "UPDATED BY PATCH";
        beerDTO.setBeerName(beerName);

        ResponseEntity responseEntity = beerController.updateBeerPatchById(testUUID,beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(beerRepository.findById(testUUID).get()).isNotNull();
        assertThat(beerRepository.findById(testUUID).get().getBeerName()).isEqualTo(beerName);



    }

    @Test
    void testDeleteNotFound(){
        assertThrows(NotFoundException.class, () ->{
            beerController.deleteById(UUID.randomUUID());
        });
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteByIdFound(){
        Beer beer = beerRepository.findAll().get(0);
        Beer beer1 = beerRepository.findAll().get(1);
        ResponseEntity responseEntity = beerController.deleteById(beer.getId());

        assertThat(beerRepository.findById(beer.getId())).isEmpty();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

//        Beer foundBeer = beerRepository.findById(beer.getId()).get();
//
//        assertThat(foundBeer).isNull();


    }

    @Test
    void testUpdateNotFound(){
        assertThrows(NotFoundException.class, () ->{
            beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingBeer(){
        Beer beer = beerRepository.findAll().get(0);
        System.out.println(beer.getVersion());
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);

        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String beerName = "UPDATED";
        beerDTO.setBeerName(beerName);

        ResponseEntity responseEntity = beerController.updateById(beer.getId(),beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
        System.out.println(updatedBeer.getVersion());


    }

    @Rollback
    @Transactional
    @Test
    void saveNewBeerTest() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("test")
                .build();

        ResponseEntity responseEntity = beerController.handlePost(beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();


    }

    @Test
    void testGetById() {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO dto = beerController.getBeerById(beer.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void testBeerIdNotFound() {
        assertThrows(NotFoundException.class,() -> {
            beerController.getBeerById(UUID.randomUUID());
        });


    }

    @Test
    void testListBeers() {
        List<BeerDTO> beerDTOS = beerController.listBeers();

        assertThat(beerDTOS.size()).isEqualTo(2413);

    }
    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> beerDTOS = beerController.listBeers();

        assertThat(beerDTOS.size()).isEqualTo(0);
    }
}