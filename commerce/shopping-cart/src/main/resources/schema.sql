-- Shopping Cart Table
CREATE TABLE IF NOT EXISTS Shopping_Cart (
                               shopping_cart_id UUID PRIMARY KEY,
                               user_id VARCHAR(255) NOT NULL
);

-- Shopping Cart Products Mapping Table
CREATE TABLE Shopping_Cart_Products (
                                        shopping_cart_id UUID NOT NULL,
                                        product_id UUID NOT NULL,
                                        quantity INTEGER NOT NULL,
                                        PRIMARY KEY (shopping_cart_id, product_id),
                                        FOREIGN KEY (shopping_cart_id) REFERENCES Shopping_Cart (shopping_cart_id)
);
