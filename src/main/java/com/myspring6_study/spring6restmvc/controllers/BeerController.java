package com.myspring6_study.spring6restmvc.controllers;

import com.myspring6_study.spring6restmvc.model.Beer;
import com.myspring6_study.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Slf4j
@RequiredArgsConstructor
@RestController  //indicate that this controller will return json not html
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID beerId,@RequestBody Beer beer){

        beerService.updateBeerPatchById(beerId,beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable UUID beerId){

        beerService.deleteById(beerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("beerId") UUID beerId,@RequestBody Beer beer){

        beerService.updateById(beerId, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(BEER_PATH)
    //@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handlePost(@RequestBody Beer beer){
        Beer savedBeer = beerService.saveNewBeer(beer);


        //Use HttpHeaders to return more information
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    @GetMapping(BEER_PATH)
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }
    @GetMapping(value = BEER_PATH_ID)
    public Beer getBeerById(@PathVariable("beerId") UUID id){

        log.debug("Get Beer by Id - in controller -1234 asdf");

        return beerService.getBeerById(id);
    }

}
