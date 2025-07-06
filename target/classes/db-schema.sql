-- Client table
CREATE TABLE client (
    client_id BIGINT PRIMARY KEY,
    client_name VARCHAR(255) NOT NULL
);

-- Contract table
CREATE TABLE contract (
    contract_id BIGINT PRIMARY KEY,
    client_id BIGINT NOT NULL,
    contract_number VARCHAR(100) UNIQUE NOT NULL,
    CONSTRAINT fk_contract_client FOREIGN KEY (client_id) REFERENCES client(client_id) ON DELETE CASCADE
);

-- Payment table
CREATE TABLE payment (
    payment_id BIGINT PRIMARY KEY,
    payment_date DATE NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    type VARCHAR(20) CHECK (type IN ('incoming', 'outgoing')) NOT NULL,
    contract_id BIGINT NOT NULL,
    version INTEGER NOT NULL DEFAULT 0,
    CONSTRAINT fk_payment_contract FOREIGN KEY (contract_id) REFERENCES contract(contract_id) ON DELETE CASCADE
);
