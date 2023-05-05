drop table if exists beer;
drop table if exists customer;
drop table if exists beer_order;
drop table if exists beer_order_line;
create table beer
(
    id               varchar(36)    not null,
    beer_name        varchar(50)    not null,
    beer_style       smallint       not null,
    created_date     datetime(6),
    price            decimal(38, 2) not null,
    quantity_on_hand integer,
    upc              varchar(255)   not null,
    update_date      datetime(6),
    version          integer,
    PRIMARY KEY (id)
) engine = InnoDB;
create table customer
(
    id                 varchar(36) not null,
    created_date       datetime(6),
    customer_name      varchar(255),
    last_modified_date datetime(6),
    version            integer,
    PRIMARY KEY (id)
) engine = InnoDB;
create table beer_order
(
    id                 varchar(36) not null,
    created_date       datetime(6),
    customer_ref       varchar(255),
    last_modified_date datetime(6),
    version            bigint,
    customer_id        varchar(36),
    PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (customer_id) REFERENCES customer (id)
) engine InnoDB;
create table beer_order_line
(
    id                 varchar(36) not null,
    beer_id            varchar(36),
    created_date       datetime(6),
    last_modified_date datetime(6),
    order_quantity     int,
    quantity_allocated int,
    version            bigint,
    beer_order_id      varchar(36),
    PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (beer_id) REFERENCES beer (id),
    CONSTRAINT FOREIGN KEY (beer_order_id) REFERENCES beer_order (id)
) engine InnoDB;

