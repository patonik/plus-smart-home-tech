CREATE TABLE payments (
                          payment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          order_id UUID NOT NULL,
                          total_payment DOUBLE PRECISION NOT NULL,
                          delivery_total DOUBLE PRECISION NOT NULL,
                          product_total DOUBLE PRECISION NOT NULL,
                          fee_total DOUBLE PRECISION NOT NULL,
                          state VARCHAR(50) NOT NULL
);
