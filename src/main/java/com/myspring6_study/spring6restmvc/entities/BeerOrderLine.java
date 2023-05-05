package com.myspring6_study.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.UUID;

//create table beer_order_line
//        (
//        id                 varchar(36) not null,
//        beer_id            varchar(36),
//        created_date       datetime(6),
//        last_modified_date datetime(6),
//        order_quantity     int,
//        quantity_allocated int,
//        version            bigint,
//        beer_order_id      varchar(36),
//        PRIMARY KEY (id),
//        CONSTRAINT FOREIGN KEY (beer_id) REFERENCES beer (id),
//        CONSTRAINT FOREIGN KEY (beer_order_id) REFERENCES beer_order (id)
//        ) engine InnoDB;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BeerOrderLine {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    //private String beerId;
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    public boolean isNew(){
        return this.id == null;
    }

    private Integer orderQuantity = 0;
    private Integer quantityAllocated = 0;

    @Version
    private Long version;

    @ManyToOne
    private BeerOrder beerOrder;

    @ManyToOne
    private Beer beer;

//        beer_order_id      varchar(36),

}
