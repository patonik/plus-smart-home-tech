CREATE TABLE orders (
                        order_id UUID PRIMARY KEY AUTO_INCREMENT,
                        shopping_cart_id UUID,
                        user_id VARCHAR(255) NOT NULL,
                        payment_id UUID,
                        delivery_id UUID,
                        state VARCHAR(50),
                        delivery_weight DOUBLE,
                        delivery_volume DOUBLE,
                        fragile BOOLEAN,
                        total_price DOUBLE,
                        delivery_price DOUBLE,
                        product_price DOUBLE,
                        address_id BIGINT,
                        FOREIGN KEY (address_id) REFERENCES address(address_id)
);

CREATE TABLE order_products (
                                order_id UUID NOT NULL,
                                product_id UUID NOT NULL,
                                quantity INTEGER NOT NULL,
                                PRIMARY KEY (order_id, product_id),
                                FOREIGN KEY (order_id) REFERENCES orders(order_id)
);
CREATE TABLE address (
                         address_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         country VARCHAR(255),
                         city VARCHAR(255),
                         street VARCHAR(255),
                         house VARCHAR(50),
                         flat VARCHAR(50)
);
