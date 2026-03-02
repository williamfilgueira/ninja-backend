CREATE TABLE machines (
                          id CHAR(36) PRIMARY KEY,
                          name VARCHAR(120) NOT NULL,
                          environment VARCHAR(20) NOT NULL,
                          token_hash CHAR(64) NOT NULL,
                          active BOOLEAN NOT NULL DEFAULT TRUE,
                          dt_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          dt_atz TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          CONSTRAINT uk_machines_token_hash UNIQUE (token_hash)
);