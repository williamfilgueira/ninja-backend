CREATE TABLE machine_status_last (
                                     machine_id CHAR(36) PRIMARY KEY,
                                     status VARCHAR(20) NOT NULL,
                                     email_user VARCHAR(160),
                                     last_seen_at TIMESTAMP NOT NULL,
                                     payload_json JSON NULL,
                                     dt_atz TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     CONSTRAINT fk_machine_status_last_machine
                                         FOREIGN KEY (machine_id) REFERENCES machines(id)
                                             ON DELETE CASCADE
);

CREATE INDEX idx_machine_status_last_seen ON machine_status_last(last_seen_at);