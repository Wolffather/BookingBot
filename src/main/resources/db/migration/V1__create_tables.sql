CREATE TABLE master (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        telegram_chat_id BIGINT NOT NULL
);

CREATE TABLE service (
                         id BIGSERIAL PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         duration_in_minutes BIGINT NOT NULL,
                         price BIGINT NOT NULL
);

CREATE TABLE time_slot (
                           id BIGSERIAL PRIMARY KEY,
                           start_at TIMESTAMP NOT NULL,
                           is_booked BOOLEAN NOT NULL DEFAULT FALSE,
                           master_id BIGINT NOT NULL REFERENCES master(id)
);

CREATE TABLE booking (
                         id BIGSERIAL PRIMARY KEY,
                         client_telegram_id BIGINT NOT NULL,
                         client_name VARCHAR(255) NOT NULL,
                         service_id BIGINT NOT NULL REFERENCES service(id),
                         time_slot_id BIGINT NOT NULL REFERENCES time_slot(id),
                         status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
                         created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE master_service (
                                master_id BIGINT NOT NULL REFERENCES master(id),
                                service_id BIGINT NOT NULL REFERENCES service(id),
                                PRIMARY KEY (master_id, service_id)
);