package com.myspring6_study.spring6restmvc.services;

import com.myspring6_study.spring6restmvc.model.BeerCVSRecord;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
@Service
public class BeerCSVServiceImpl implements BeerCSVService {
    @Override
    public List<BeerCVSRecord> convertCSV(File cvsFile) {

        try {
            List<BeerCVSRecord> beerCVSRecords = new CsvToBeanBuilder<BeerCVSRecord>(new FileReader(cvsFile))
                    .withType(BeerCVSRecord.class)
                    .build().parse();
            return beerCVSRecords;
        } catch (FileNotFoundException exception){
            throw new RuntimeException(exception);
        }
    }
}
