package com.myspring6_study.spring6restmvc.controllers;

import com.myspring6_study.spring6restmvc.model.Beer;
import com.myspring6_study.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Slf4j
@AllArgsConstructor
@RestController  //indicate that this controller will return json not html
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    @PatchMapping("{beerId}")
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID beerId,@RequestBody Beer beer){

        beerService.updateBeerPatchById(beerId,beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping("{beerId}")
    public ResponseEntity deleteById(@PathVariable UUID beerId){

        beerService.deleteById(beerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
    @PutMapping("{beerId}")
    public ResponseEntity updateById(@PathVariable("beerId") UUID beerId,@RequestBody Beer beer){

        beerService.updateById(beerId, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    //@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handlePost(@RequestBody Beer beer){
        Beer savedBeer = beerService.saveNewBeer(beer);


        //Use HttpHeaders to return more information
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers(){
        return beerService.listBeers();
    }
    @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID id){

        log.debug("Get Beer by Id - in controller -1234 asdf");

        return beerService.getBeerById(id);
    }

}
