CREATE TABLE deliveries (
                            delivery_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            from_address_id UUID NOT NULL,
                            to_address_id UUID NOT NULL,
                            order_id UUID NOT NULL,
                            delivery_state VARCHAR(50) NOT NULL,
                            CONSTRAINT fk_from_address
                                FOREIGN KEY (from_address_id) REFERENCES addresses(address_id),
                            CONSTRAINT fk_to_address
                                FOREIGN KEY (to_address_id) REFERENCES addresses(address_id)
);
