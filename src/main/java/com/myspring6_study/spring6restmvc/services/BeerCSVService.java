package com.myspring6_study.spring6restmvc.services;

import com.myspring6_study.spring6restmvc.model.BeerCVSRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;


public interface BeerCSVService {
    List<BeerCVSRecord> convertCSV(File file);
}
