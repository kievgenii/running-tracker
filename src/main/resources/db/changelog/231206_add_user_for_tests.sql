ALTER TABLE IF EXISTS users
    ADD COLUMN  IF NOT EXISTS test boolean default false;

INSERT INTO users (id, first_name, last_name, birth_date, sex, created_at, test)
VALUES (-1, 'Test', 'Integration', '1980-01-01', 'OTHER', CURRENT_TIMESTAMP, true);
