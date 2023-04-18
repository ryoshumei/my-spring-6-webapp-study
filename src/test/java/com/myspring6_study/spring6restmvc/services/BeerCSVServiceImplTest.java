package com.myspring6_study.spring6restmvc.services;

import com.myspring6_study.spring6restmvc.model.BeerCVSRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BeerCSVServiceImplTest {

    BeerCSVService beerCsvService = new BeerCSVServiceImpl();

    @Test
    void convertCSV() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

        List<BeerCVSRecord> records = beerCsvService.convertCSV(file);

        System.out.println(records.size());

        assertThat(records.size()).isGreaterThan(0);
    }

}