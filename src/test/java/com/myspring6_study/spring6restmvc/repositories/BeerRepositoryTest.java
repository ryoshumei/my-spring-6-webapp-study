package com.myspring6_study.spring6restmvc.repositories;

import com.myspring6_study.spring6restmvc.entities.Beer;
import com.myspring6_study.spring6restmvc.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeerTooLong(){

        assertThrows(ConstraintViolationException.class, () ->{
            //this does not actually flush to the database
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("My BeerMy BeerMy BeerMy BeerMy BeerMy BeerMy BeerMy BeerMy BeerMy BeerMy Beer")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("123456")
                    .price(new BigDecimal(1234))
                    .build());
            // do flush
            beerRepository.flush();
        });



    }

    @Test
    void testSaveBeer(){

        //this does not actually flush to the database
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("My Beer")
                        .beerStyle(BeerStyle.PALE_ALE)
                        .upc("123456")
                        .price(new BigDecimal(1234))
                .build());

        // do flush
        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();

    }


}