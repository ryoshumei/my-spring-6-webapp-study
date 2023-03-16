package com.myspring6_study.spring6restmvc.services;

import com.myspring6_study.spring6restmvc.model.Beer;

import java.util.UUID;

public interface BeerService {

    Beer getBeerById(UUID id);
}
