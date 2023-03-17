package com.myspring6_study.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Builder
@Data
public class Customer {

    String customerName;
    UUID id;
    Integer version;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;

}
