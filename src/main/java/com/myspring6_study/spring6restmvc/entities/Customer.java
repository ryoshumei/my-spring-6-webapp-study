package com.myspring6_study.spring6restmvc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    UUID id;
    String customerName;

    @Version//locking strategy that is being used by hibernate
    Integer version;
    LocalDateTime createdDate;
    LocalDateTime lastModifiedDate;

}
