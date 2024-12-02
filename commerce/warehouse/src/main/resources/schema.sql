-- Address Table
CREATE TABLE Address (
                         warehouse_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         country VARCHAR(255) NOT NULL,
                         city VARCHAR(255) NOT NULL,
                         street VARCHAR(255) NOT NULL,
                         house VARCHAR(255) NOT NULL,
                         flat VARCHAR(255)
);

-- Warehouse Product Table
CREATE TABLE Warehouse_Product (
                                   product_id UUID PRIMARY KEY,
                                   fragile BOOLEAN,
                                   dimension_width DOUBLE,
                                   dimension_height DOUBLE,
                                   dimension_depth DOUBLE,
                                   weight DOUBLE,
                                   quantity INTEGER
);
