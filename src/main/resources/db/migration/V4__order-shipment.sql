drop table if exists beer_order_shipment;

CREATE TABLE beer_order_shipment
(
    id                 VARCHAR(36) NOT NULL PRIMARY KEY,
    beer_order_id      VARCHAR(36) UNIQUE,
    created_date       TIMESTAMP,
    tracking_number    VARCHAR(50),
    last_modified_date DATETIME(6) DEFAULT NULL,
    version            BIGINT      DEFAULT NULL,
    CONSTRAINT bos_pk FOREIGN KEY (beer_order_id) REFERENCES beer_order (id)
) ENGINE = InnoDB;

ALTER TABLE beer_order
    ADD COLUMN beer_order_shipment_id VARCHAR(36);

ALTER TABLE beer_order
    ADD CONSTRAINT bos_shipment_fk
        FOREIGN KEY (beer_order_shipment_id) REFERENCES beer_order_shipment (id);