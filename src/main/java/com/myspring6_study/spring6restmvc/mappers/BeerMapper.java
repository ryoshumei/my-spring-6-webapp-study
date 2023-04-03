package com.myspring6_study.spring6restmvc.mappers;

import com.myspring6_study.spring6restmvc.entities.Beer;
import com.myspring6_study.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
