CREATE TABLE products (
    product_id BINARY(16) PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_description VARCHAR(255) NULL,
    product_price DECIMAL(10, 2) NOT NULL,
    product_category VARCHAR(50) NOT NULL,
    product_stock INT NOT NULL
);