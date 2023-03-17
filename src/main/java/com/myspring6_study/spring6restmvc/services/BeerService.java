package com.myspring6_study.spring6restmvc.services;

import com.myspring6_study.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    List<Beer> listBeers();

    Beer getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void updateById(UUID beerId, Beer beer);

    void deleteById(UUID beerId);

    void updateBeerPatchById(UUID beerId, Beer beer);
}