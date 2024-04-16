-- Drop existing tables in the correct order to avoid foreign key constraint errors
DROP TABLE IF EXISTS portfolio_stock;
DROP TABLE IF EXISTS portfolio_forex;
DROP TABLE IF EXISTS portfolio_crypto;
DROP TABLE IF EXISTS stock;
DROP TABLE IF EXISTS forex;
DROP TABLE IF EXISTS crypto;
DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS role;

-- Table creation for 'role'
CREATE TABLE role (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL
);

-- Table creation for 'user'
CREATE TABLE "user" (
                        id SERIAL PRIMARY KEY,
                        username VARCHAR(255) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        role_id INT DEFAULT NULL,
                        created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (role_id) REFERENCES role (id)
);

-- Table creation for 'crypto'
CREATE TABLE crypto (
                        id SERIAL PRIMARY KEY,
                        ticker VARCHAR(10) NOT NULL,
                        base_currency VARCHAR(10) DEFAULT NULL,
                        quote_currency VARCHAR(10) DEFAULT NULL,
                        last_price NUMERIC(16, 2) DEFAULT NULL
);

-- Table creation for 'forex'
CREATE TABLE forex (
                       id SERIAL PRIMARY KEY,
                       symbol VARCHAR(10) NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       rate NUMERIC(10, 6) DEFAULT NULL
);

-- Table creation for 'stock'
CREATE TABLE stock (
                       id SERIAL PRIMARY KEY,
                       symbol VARCHAR(10) NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       exchange VARCHAR(50) DEFAULT NULL,
                       last_price NUMERIC(10, 2) DEFAULT NULL
);

-- Table creation for 'portfolio_crypto'
CREATE TABLE portfolio_crypto (
                                  id SERIAL PRIMARY KEY,
                                  user_id INT NOT NULL,
                                  crypto_id INT NOT NULL,
                                  quantity_owned NUMERIC(10, 4) NOT NULL,
                                  purchase_price NUMERIC(16, 2) NOT NULL,
                                  purchase_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                  FOREIGN KEY (user_id) REFERENCES "user" (id),
                                  FOREIGN KEY (crypto_id) REFERENCES crypto (id)
);

-- Table creation for 'portfolio_forex'
CREATE TABLE portfolio_forex (
                                 id SERIAL PRIMARY KEY,
                                 user_id INT NOT NULL,
                                 forex_id INT NOT NULL,
                                 quantity_owned NUMERIC(10, 4) NOT NULL,
                                 purchase_rate NUMERIC(10, 6) NOT NULL,
                                 purchase_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                 FOREIGN KEY (user_id) REFERENCES "user" (id),
                                 FOREIGN KEY (forex_id) REFERENCES forex (id)
);

-- Table creation for 'portfolio_stock'
CREATE TABLE portfolio_stock (
                                 id SERIAL PRIMARY KEY,
                                 user_id INT NOT NULL,
                                 stock_id INT NOT NULL,
                                 quantity_owned INT NOT NULL,
                                 purchase_price NUMERIC(10, 2) NOT NULL,
                                 purchase_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                 FOREIGN KEY (user_id) REFERENCES "user" (id),
                                 FOREIGN KEY (stock_id) REFERENCES stock (id)
);
