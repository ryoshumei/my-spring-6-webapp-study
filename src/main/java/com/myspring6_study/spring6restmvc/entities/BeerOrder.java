package com.myspring6_study.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

//table beer_order
//        (
//        id                 varchar(36) not null,
//        created_date       datetime(6),
//        customer_ref       varchar(255),
//        last_modified_date datetime(6),
//        version            bigint,
//        customer_id        varchar(36),
//        PRIMARY KEY (id),
//        CONSTRAINT FOREIGN KEY (customer_id) REFERENCES customer (id)
//        ) engine InnoDB;
@Entity
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BeerOrder {

    public BeerOrder(UUID id, Timestamp createdDate, Timestamp lastModifiedDate, Long version,
                     String customerRef, Customer customer, Set<BeerOrderLine> beerOrderLineSet,
                     BeerOrderShipment beerOrderShipment) {
        this.id = id;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.version = version;
        this.customerRef = customerRef;
        setCustomer(customer);
        this.beerOrderLineSet = beerOrderLineSet;
        this.setBeerOrderShipment(beerOrderShipment);
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    @Version
    private Long version;

    private String customerRef;

    @ManyToOne
    private Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.getBeerOrderSet().add(this);
    }

    public void setBeerOrderShipment(BeerOrderShipment beerOrderShipment){
        this.beerOrderShipment = beerOrderShipment;
        beerOrderShipment.setBeerOrder(this);
    }

    @OneToMany(mappedBy = "beerOrder")
    private Set<BeerOrderLine> beerOrderLineSet;

    @OneToOne(cascade = CascadeType.PERSIST)//Duo to following default naming convention , no need override
    private BeerOrderShipment beerOrderShipment;

}
