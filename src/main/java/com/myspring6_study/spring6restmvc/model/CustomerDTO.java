package com.myspring6_study.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Builder
@Data
public class CustomerDTO {

    UUID id;
    String customerName;
    Integer version;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;

}
