INSERT INTO master (name, telegram_chat_id)
VALUES ('Мария',123456789),
       ('Сергей', 4678999222);


INSERT INTO service (title, duration_in_minutes, price)
VALUES
    ('Стрижка', 60, 1500),
    ('Окрашивание', 120, 3500),
    ('Укладка', 30, 800);

INSERT INTO time_slot (start_at, is_booked, master_id)
VALUES
    ('2026-04-01 10:00:00', FALSE, 1),
    ('2026-04-01 11:00:00', FALSE, 1),
    ('2026-04-01 12:00:00', FALSE, 1),
    ('2026-04-02 10:00:00', FALSE, 1),
    ('2026-04-02 11:00:00', FALSE, 1);

INSERT INTO master_service (master_id, service_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 3);