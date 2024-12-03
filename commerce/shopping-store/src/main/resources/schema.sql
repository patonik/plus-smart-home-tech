-- Product Table
CREATE TABLE Products (
                          product_id UUID PRIMARY KEY,
                          product_name VARCHAR(255) NOT NULL,
                          description TEXT NOT NULL,
                          image_src VARCHAR(255),
                          product_category VARCHAR(255) NOT NULL,
                          product_state VARCHAR(255) NOT NULL,
                          quantity_state VARCHAR(255) NOT NULL,
                          price DOUBLE NOT NULL,
                          rating DOUBLE NOT NULL
);
